package impl;

import api.Engine;
import api.SheetData;
import api.DTOFactory;

public class EngineImpl implements Engine {
    private Sheet currentSheet = new Sheet();
    private final DTOFactory DTOFactory;

    @Override
    public SheetData getSheetDTO() {
        if(currentSheet == null)
            throw new NullPointerException("You must load a file first.");
        return DTOFactory.createSheetData(currentSheet);
    }

    public EngineImpl(DTOFactory DTOFactory) {
        this.DTOFactory = DTOFactory;
    }

    public SheetData getSheetData() {
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



}
