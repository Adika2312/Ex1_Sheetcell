package dto;

import api.CellValue;
import api.DTO;
import impl.cell.Cell;
import impl.cell.value.StringValue;

public class CellDTO implements DTO {
    private final int version;
    private final CellValue value;
    private final String originalValue;
//    private final Set<Cell> id2DepedentCell = new HashSet<>();
//    private final Set<Cell> id2InfluencedCell = new HashSet<>();

    public CellDTO(){
        this.version = 1;
        this.value = new StringValue("");
        this.originalValue = "";
    }

    public CellDTO(Cell cell) {
        version = cell.getVersion();
        value = cell.getEffectiveValue();
        originalValue = cell.getOriginalValue();
    }

    public int getVersion() {
        return version;
    }

    public CellValue getValue() {
        return value;
    }

    public String getOriginalValue() {
        return originalValue;
    }
}
