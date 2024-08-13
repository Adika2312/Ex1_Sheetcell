package dto;

import api.CellData;
import impl.Cell;

public class CellDTO implements CellData {
    private final int version;

    public CellDTO(Cell cell) {
        version = cell.getVersion();
    }

    public int getVersion() {
        return version;
    }
}
