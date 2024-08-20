package dto;

import api.CellValue;
import api.DTO;
import impl.cell.Cell;
import impl.cell.value.StringValue;

import java.util.HashSet;
import java.util.Set;

public class CellDTO implements DTO {
    private final int version;
    private final CellValue effectiveValue;
    private final String originalValue;
    private final String Identity;
//    private final Set<Cell> id2DepedentCell = new HashSet<>();
//    private final Set<Cell> id2InfluencedCell = new HashSet<>();


    public CellDTO(Cell cell) {
        version = cell.getVersion();
        effectiveValue = cell.getEffectiveValue();
        originalValue = cell.getOriginalValue();
        Identity = cell.getIdentity();
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

    public String getIdentity() {
        return Identity;
    }
}
