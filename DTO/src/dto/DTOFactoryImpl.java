package dto;

import api.CellData;
import api.SheetData;
import api.DTOFactory;
import impl.Cell;
import impl.Sheet;

public class DTOFactoryImpl implements DTOFactory {
    @Override
    public SheetData createSheetData(Sheet sheet) {
        return new SheetDTO(sheet);
    }

    @Override
    public CellData createCellData(Cell cell) {
        return new CellDTO(cell);
    }
}