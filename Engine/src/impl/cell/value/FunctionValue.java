package impl.cell.value;

import api.CellValue;
import impl.EngineImpl;
import impl.cell.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionValue implements CellValue {
    private final FunctionType functionType;
    List<CellValue> arguments = new ArrayList<>();
    private double effectiveValue;


    public FunctionValue(String functionDefinition) {
        functionType = parseFunctionType(functionDefinition);
        List<String> argsStr = extractArguments(functionDefinition);
        for (String argument : argsStr.subList(1, argsStr.size())) {
            CellValue value = EngineImpl.convertStringToCellValue(argument);
            arguments.add(value);
        }
        effectiveValue = (double) this.eval();
    }

    public static List<String> extractArguments(String input) {
        List<String> arguments = new ArrayList<>();
        int level = 0;
        int start = 0;
        boolean insideArgument = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '{') {
                if (level == 0) {
                    start = i;
                }
                level++;
            } else if (c == '}') {
                level--;
                if (level == 0) {
                    arguments.add(input.substring(start, i).trim());
                    insideArgument = false;
                }
                else{
                    insideArgument = true;
                }
            } else if (c == ',' && level == 1) {
                if (insideArgument) {
                    arguments.add(input.substring(start, i).trim());
                }
                start = i + 1;
                insideArgument = false;
            } else if (level == 1 && !insideArgument && c != ' ') {
                start = i;
                insideArgument = true;
            }
        }

        // Add the last argument if there is one
        if (insideArgument) {
            arguments.add(input.substring(start).trim());
        }

        return arguments;
    }

    @Override
    public Object eval() {
        switch (functionType) {
            case PLUS:
            case MINUS:
            case TIMES:
            case DIVIDE:
            case MOD:
            case POW:
                double arg1 = (double) arguments.get(0).eval();
                double arg2 = (double) arguments.get(1).eval();
                try {
                    return functionType.apply(arg1, arg2);
                } catch (ArithmeticException e) {
                    return "NaN";
                }
            case ABS:
                double arg = (double) arguments.get(0).eval();
                try {
                    return functionType.apply(arg);
                } catch (ArithmeticException e) {
                    return "NaN";
                }

        }
        return null;
    }

    public enum FunctionType {
        PLUS {
            @Override
            public double apply(double arg1, double arg2) {
                return arg1 + arg2;
            }
        },
        MINUS {
            @Override
            public double apply(double arg1, double arg2) {
                return arg1 - arg2;
            }
        },
        TIMES {
            @Override
            public double apply(double arg1, double arg2) {
                return arg1 * arg2;
            }

        },
        MOD{
            @Override
            public double apply(double arg1, double arg2) {
                return arg1 % arg2;
            }
        },
        DIVIDE {
            @Override
            public double apply(double arg1, double arg2) {
                if (arg2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return arg1 / arg2;
            }
        },
        POW {
            @Override
            public double apply(double arg1, double arg2) {
                return Math.pow(arg1, arg2);
            }
        },
        ABS{
            @Override
            public double apply(double arg) {
                return Math.abs(arg);
            }
        },
        CONCAT {
            @Override
            public String apply(String str1, String str2) {
                return str1 + str2;
            }
        },
        SUB {
            @Override
            public String apply(String source, int startIndex, int endIndex) {
                if (startIndex < 0 || endIndex >= source.length() || startIndex > endIndex) {
                    return "!UNDEFINED!";
                }
                return source.substring(startIndex, endIndex + 1);
            }
        };

        // Overloaded methods to handle different argument types
        public double apply(double arg1, double arg2) {
            throw new UnsupportedOperationException("This function does not support numeric operations");
        }

        public String apply(String str1, String str2) {
            throw new UnsupportedOperationException("This function does not support string concatenation");
        }

        public String apply(String source, int startIndex, int endIndex) {
            throw new UnsupportedOperationException("This function does not support substring operations");
        }

        public double apply(double arg) {
            throw new UnsupportedOperationException("This function does not support numeric operations");
        }
    }

    @Override
    public Object getEffectiveValue() {
        return effectiveValue;
    }

    public boolean isValid() {
        return functionType != null;
    }

    private FunctionType parseFunctionType(String functionDefinition) {
        String functionName = functionDefinition.substring(1, functionDefinition.indexOf(','));
        try {
            return FunctionType.valueOf(functionName);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }






}
