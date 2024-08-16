package impl.cell;

import api.CellValue;
import api.Editable;
import impl.cell.value.StringValue;
import java.util.HashSet;
import java.util.Set;

public class Cell implements Editable {
    private CellValue value = new StringValue(" ");
    private String originalValue = "";
    private String Identity;
    private final Set<Cell> id2DepedentCell = new HashSet<>();
    private final Set<Cell> id2InfluencedCell = new HashSet<>();
    private int version = 1;

    @Override
    public void update(CellValue value, String originalValue) {
        this.value = value;
        this.originalValue = originalValue;
        version++;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Effective Value: ").append(value.getEffectiveValue()).append("\n");
        str.append("Original Value: ").append(originalValue);

        return str.toString();
    }

    public String getCellEffectiveValue(){
        return value.getEffectiveValue().toString();
    }

    public int getVersion() {
        return version;
    }
}
