package impl.cell.value;

import api.CellValue;

public class NumericValue implements CellValue {
    private final Double value;

    public NumericValue(double value) {
        this.value = value;
    }

    @Override
    public String getEffectiveValue() {
        if (value % 1 == 0) {
            return String.format("%,d", value.longValue());
        } else {
            return String.format("%,.2f", value);
        }
    }

    @Override
    public Double eval() {
        return value;
    }
}
