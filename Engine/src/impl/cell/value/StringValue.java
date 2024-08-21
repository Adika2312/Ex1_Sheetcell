package impl.cell.value;

import api.CellValue;
import impl.cell.Cell;

public class StringValue implements CellValue {
    private final String value;
    private Cell activatingCell;

    public StringValue(String value) {
        this.value = value.trim();
    }

    @Override
    public String getEffectiveValue() {
        return value;
    }


    @Override
    public String eval() {
        return this.value;
    }

    @Override
    public void setActivatingCell(Cell cell) {
        this.activatingCell = cell;
    }


}
