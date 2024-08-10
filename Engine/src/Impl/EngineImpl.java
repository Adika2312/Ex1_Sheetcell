package Impl;

import API.Engine;

public class EngineImpl implements Engine {
    private Sheet currentSheet = new Sheet();

    @Override
    public Sheet GetSheet() {
        if(currentSheet == null)
            throw new NullPointerException("You must load a file first.");
        return currentSheet;
    }

    @Override
    public String getCellValue(int row, int col) {
        return currentSheet.getCellData(row, col);
    }

    @Override
    public boolean IsCellInBounds(int row, int col) {
        return(row >= 0 && row < currentSheet.getNumOfRows() && col >= 0 && col < currentSheet.getNumOfCols());
    }
}
