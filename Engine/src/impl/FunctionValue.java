package impl;

import api.CellValue;
import java.util.List;

public class FunctionValue implements CellValue {
    private final FunctionType functionType;
    private final List<CellValue> arguments;

    public FunctionValue(String functionDefinition) {
        this.functionType = parseFunctionType(functionDefinition);
        this.arguments = parseArguments(functionDefinition);
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
        DIVIDE {
            @Override
            public double apply(double arg1, double arg2) {
                if (arg2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return arg1 / arg2;
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
    }

    @Override
    public String getFormattedValue() {
        return functionType.name() + arguments.toString();
    }

    @Override
    public Object getRawValue() {
        return executeFunction();
    }

    @Override
    public boolean isValid() {
        return functionType != null && arguments.stream().allMatch(CellValue::isValid);
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

    private List<CellValue> parseArguments(String functionDefinition) {
//        // Logic to parse arguments and create corresponding CellValue instances
//        List<CellValue> args = new ArrayList<>();
//        // Add argument parsing logic here
//        return args;
        return null;
    }

    private Object executeFunction() {
        switch (functionType) {
            case PLUS:
            case MINUS:
            case TIMES:
            case DIVIDE:
                double arg1 = getNumericArgument(0);
                double arg2 = getNumericArgument(1);
                try {
                    return functionType.apply(arg1, arg2);
                } catch (ArithmeticException e) {
                    return "NaN"; // or Double.NaN depending on how you want to represent it
                }
            case CONCAT:
                String str1 = getStringArgument(0);
                String str2 = getStringArgument(1);
                return functionType.apply(str1, str2);
            case SUB:
                String source = getStringArgument(0);
                //int startIndex = getNumericArgument(1).intValue();
                //int endIndex = getNumericArgument(2).intValue();
                //return functionType.apply(source, startIndex, endIndex);
            default:
                return "!UNDEFINED!";
        }
    }

    private double getNumericArgument(int index) {
        CellValue arg = arguments.get(index);
        if (arg instanceof NumericValue) {
            return (double) arg.getRawValue();
        } else if (arg instanceof FunctionValue) {
            return (double) ((FunctionValue) arg).executeFunction();
        } else {
            throw new IllegalArgumentException("Invalid argument type for numeric function");
        }
    }

    private String getStringArgument(int index) {
        CellValue arg = arguments.get(index);
        if (arg instanceof StringValue) {
            return (String) arg.getRawValue();
        } else if (arg instanceof FunctionValue) {
            return (String) ((FunctionValue) arg).executeFunction();
        } else {
            throw new IllegalArgumentException("Invalid argument type for string function");
        }
    }

}
