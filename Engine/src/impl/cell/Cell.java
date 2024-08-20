package impl.cell;

import api.CellValue;
import api.Editable;
import impl.cell.value.StringValue;
import java.util.HashSet;
import java.util.Set;

public class Cell implements Editable {
    private CellValue effectiveValue;
    private String originalValue;
    private final Set<Cell> id2DepedentCell = new HashSet<>();
    private final Set<Cell> id2InfluencedCell = new HashSet<>();
    private int version = 1;

    @Override
    public void update(CellValue value, String originalValue, boolean isFromFile) {
        this.effectiveValue = value;
        this.originalValue = originalValue;
        if(!isFromFile)
            version++;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Effective Value: ").append(effectiveValue.getEffectiveValue()).append("\n");
        str.append("Original Value: ").append(originalValue);

        return str.toString();
    }

    public int getVersion() {
        return version;
    }

    public CellValue getEffectiveValue() {
        return effectiveValue;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public Set<Cell> getId2DepedentCell() {
        return id2DepedentCell;
    }

    public Set<Cell> getId2InfluencedCell() {
        return id2InfluencedCell;
    }
}
