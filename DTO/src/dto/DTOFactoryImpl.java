package dto;

import api.DTO;
import api.DTOFactory;
import impl.cell.Cell;
import impl.sheet.Sheet;

public class DTOFactoryImpl implements DTOFactory {
    @Override
    public DTO createSheetData(Sheet sheet) {
        return new SheetDTO(sheet);
    }

    @Override
    public DTO createCellData(Cell cell) {
        return new CellDTO(cell);
    }
}