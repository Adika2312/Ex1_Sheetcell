package impl;

import api.*;
import impl.cell.value.BooleanValue;
import impl.cell.value.FunctionValue;
import impl.cell.value.NumericValue;
import impl.cell.value.StringValue;
import impl.sheet.Sheet;

public class EngineImpl implements Engine {
    private Sheet currentSheet = new Sheet();
    private final DTOFactory DTOFactory;

    @Override
    public DTO getSheetDTO() {
        if(currentSheet == null)
            throw new NullPointerException("You must load a file first.");
        return DTOFactory.createSheetData(currentSheet);
    }

    @Override
    public DTO getCellDTO(int row, int col) {
        if(currentSheet == null)
            throw new NullPointerException("You must load a file first.");
        return DTOFactory.createCellData(currentSheet.getCell(row, col));
    }

    public EngineImpl(DTOFactory DTOFactory) {
        this.DTOFactory = DTOFactory;
    }

    public DTO getSheetData() {
        return DTOFactory.createSheetData(this.currentSheet);
    }

    @Override
    public String getCellValue(int row, int col) {
        return currentSheet.getCellValues(row, col);
    }

    @Override
    public boolean isCellInBounds(int row, int col) {
        return(row >= 0 && row < currentSheet.getNumOfRows() && col >= 0 && col < currentSheet.getNumOfCols());
    }

    @Override
    public void updateCellValue(int row, int col, CellValue value, String originalValue) {
        currentSheet.setCellValues(row, col, value,originalValue);
    }


    public static CellValue convertStringToCellValue(String newValue) {

        CellValue cellValue = null;

        while (true){
            // Check for Boolean
            if (newValue.equals("TRUE") || newValue.equals("FALSE")) {
                cellValue = new BooleanValue(Boolean.parseBoolean(newValue));
            }
            // Check for Numerical
            else if (newValue.matches("-?\\d+(\\.\\d+)?")) {
                try {
                    double numericValue = Double.parseDouble(newValue);
                    cellValue = new NumericValue(numericValue);
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid numeric value.");
                }
            }
            // Check for Function
            else if (newValue.matches("\\{[A-Z]+(,[^,]+)*\\}")) {
                cellValue = new FunctionValue(newValue);
            }
            // Otherwise, treat as String
            else {
                cellValue = new StringValue(newValue);
            }

            if (true) {
                break;
            }
            else {
                System.out.println("Invalid value entered.");
            }
        }

        return cellValue;
    }
}
