package impl;

import api.CellValue;

public class StringValue implements CellValue {
    private final String value;

    public StringValue(String value) {
        this.value = value.trim();
    }

    @Override
    public String getFormattedValue() {
        return value;
    }

    @Override
    public String getRawValue() {
        return value;
    }

    @Override
    public boolean isValid() {
        // Add validation logic if needed
        return true;
    }
}
