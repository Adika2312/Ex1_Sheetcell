package impl.cell.value;

import api.CellValue;

public class StringValue implements CellValue {
    private final String value;

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
}
