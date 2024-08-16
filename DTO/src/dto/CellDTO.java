package dto;

import api.DTO;
import impl.cell.Cell;

public class CellDTO implements DTO {
    private final int version;

    public CellDTO(Cell cell) {
        version = cell.getVersion();
    }

    public int getVersion() {
        return version;
    }
}
