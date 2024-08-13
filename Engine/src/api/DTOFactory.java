package api;

import impl.Cell;
import impl.Sheet;

public interface DTOFactory {
    SheetData createSheetData(Sheet sheet);
    CellData createCellData(Cell cell);
}
