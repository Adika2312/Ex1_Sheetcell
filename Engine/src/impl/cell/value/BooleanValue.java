package impl.cell.value;

import api.CellValue;

public class BooleanValue implements CellValue {
    private final Boolean value;

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
}
