package dto;

import api.DTO;
import impl.cell.Cell;
import impl.sheet.Sheet;

import java.util.ArrayList;
import java.util.List;

public class SheetDTO implements DTO {
    private final String name;
    private final int version;
    private final int numOfRows;
    private final int numOfCols;
    private final List<List<Cell>> cellsDTO;
    private final String data;

    public SheetDTO(Sheet sheet) {
        name = sheet.getName();
        version = sheet.getVersion();
        numOfRows = sheet.getNumOfRows();
        numOfCols = sheet.getNumOfCols();
        data = sheet.toString();
        List<List<Cell>> cellsToCopy = sheet.getCells();
        cellsDTO = new ArrayList<>();

        for (List<Cell> innerList : cellsToCopy) {
            cellsDTO.add(new ArrayList<>(innerList));
        }
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getNumOfCols() {
        return numOfCols;
    }

    public List<List<Cell>> getCells() {
        return cellsDTO;
    }

    @Override
    public String toString() {
        return data;
    }
}
