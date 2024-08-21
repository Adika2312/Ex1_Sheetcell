package impl.cell.value;

import api.CellValue;
import impl.cell.Cell;

public class BooleanValue implements CellValue {
    private final Boolean value;
    private Cell activatingCell;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public String getEffectiveValue() {
        return Boolean.toString(value);
    }

    @Override
    public CellValue eval() {
        return this;
    }

    @Override
    public void setActivatingCell(Cell cell) {
        this.activatingCell = cell;
    }


}
