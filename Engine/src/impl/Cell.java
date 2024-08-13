package impl;

import api.CellValue;
import api.Editable;
import java.util.HashSet;
import java.util.Set;

public class Cell implements Editable {
    private CellValue value = new StringValue(" ");
    private String Identity;
    private final Set<Cell> id2DepedentCell = new HashSet<>();
    private final Set<Cell> id2InfluencedCell = new HashSet<>();
    private int version = 1;

    @Override
    public void update(CellValue value) {
        this.value = value;
        version++;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Effective Value: ").append(value.getFormattedValue()).append("\n");
        str.append("Original Value: ").append(value.getRawValue());

        return str.toString();
    }

    public String getCellEffectiveValue(){
        return value.getFormattedValue();
    }

    public int getVersion() {
        return version;
    }
}
