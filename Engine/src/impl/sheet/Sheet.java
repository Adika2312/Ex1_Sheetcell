package impl.sheet;

import api.CellValue;
import impl.cell.Cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sheet {
    private String name;
    private int version = 1;
    private final Map<String, Cell> activeCells = new HashMap<>();
    private int numOfRows;
    private int numOfCols;
    private int rowHeight;
    private int colWidth;

    public Map<String,Cell> getActiveCells() {
        return activeCells;
    }

    public Cell getCell(String cellIdentity){
        return activeCells.get(cellIdentity);
    }

    public void setCellValues(String cellIdentity, CellValue value, String originalValue) {
        Cell cell = getCell(cellIdentity);
        if (cell == null){
            createNewCell(cellIdentity, value, originalValue);
        }
        else{
            cell.update(value,originalValue);
        }
        version++;
    }

    private void createNewCell(String cellIdentity, CellValue value, String originalValue) {
        Cell cell = new Cell();
        activeCells.put(cellIdentity, cell);
        cell.update(value,originalValue);
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getNumOfCols() {
        return numOfCols;
    }

    public void setNumOfCols(int numOfCols) {
        this.numOfCols = numOfCols;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getVersion(){
        return version;
    }

    public int getColWidth() {
        return colWidth;
    }
    public void setColWidth(int colWidth) {
        this.colWidth = colWidth;
    }

    public int getRowHeight() {
        return rowHeight;
    }
    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

}
