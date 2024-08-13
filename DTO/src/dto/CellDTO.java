package dto;

import api.CellData;
import impl.Cell;

public class CellDTO implements CellData {
    private final String OriginalValue;
    private final String EffectiveValue;
    private final int version;
    private final String data;

    public CellDTO(Cell cell) {
        OriginalValue = cell.getOriginalValue();
        EffectiveValue = cell.getCellEffectiveValue();
        version = cell.getVersion();
        data = cell.toString();
    }
}
