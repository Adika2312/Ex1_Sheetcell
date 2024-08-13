package impl;

import api.CellValue;

public class BooleanValue implements CellValue {
    private final Boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public String getFormattedValue() {
        return Boolean.toString(value);
    }

    @Override
    public Boolean getRawValue() {
        return value;
    }

    @Override
    public boolean isValid() {
        // Add validation logic if needed
        return true;
    }
}
