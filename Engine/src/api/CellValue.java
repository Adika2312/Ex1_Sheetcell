package api;

import impl.cell.Cell;

public interface CellValue {
    Object getEffectiveValue();
    Object eval();
    void setActivatingCell(Cell cell);
    void calculateAndSetEffectiveValue();
}

