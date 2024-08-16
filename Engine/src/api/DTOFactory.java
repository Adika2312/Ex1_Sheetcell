package api;

import impl.cell.Cell;
import impl.sheet.Sheet;

public interface DTOFactory {
    DTO createSheetData(Sheet sheet);
    DTO createCellData(Cell cell);
}
