package impl.sheet;

import api.CellValue;
import generated.STLCell;
import generated.STLCells;
import impl.EngineImpl;
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

    public void setCellValues(String cellIdentity, CellValue value, String originalValue, boolean isFromFile) {
        Cell cell = getCell(cellIdentity);
        if (cell == null){
            createNewCell(cellIdentity, value, originalValue, isFromFile);
        }
        else{
            cell.update(value,originalValue,isFromFile);
        }
        version++;
    }

    private void createNewCell(String cellIdentity, CellValue value, String originalValue, boolean isFromFile) {
        Cell cell = new Cell(this);
        activeCells.put(cellIdentity, cell);
        cell.update(value, originalValue, isFromFile);
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

    public void setActiveCells(List<STLCell> stlCellsList) {
        for (STLCell stlCell : stlCellsList) {
            String cellIdentity = stlCell.getColumn() + stlCell.getRow();
            String orgValue = stlCell.getSTLOriginalValue();
            createNewCell(cellIdentity, EngineImpl.convertStringToCellValue(orgValue), orgValue, true);
        }
    }
}
