package impl;

import api.CellValue;

public class NumericValue implements CellValue {
    private final Double value;

    public NumericValue(double value) {
        this.value = value;
    }

    @Override
    public String getFormattedValue() {
        if (value % 1 == 0) {
            return String.format("%,d", value.longValue());
        } else {
            return String.format("%,.2f", value);
        }
    }

    @Override
    public Double getRawValue() {
        return value;
    }

    @Override
    public boolean isValid() {
        // Add validation logic if needed
        return true;
    }


}
