package impl.sheet;

import api.CellValue;
import impl.cell.Cell;

import java.util.ArrayList;
import java.util.List;

public class Sheet {
    private String name = "";
    private int version = 1;
    private final List<List<Cell>> cells = new ArrayList<>();
    private int numOfRows = 1 ;
    private int numOfCols = 1;
    private int rowHeight = 1;
    private int colWidth = 1;

    public Sheet(){
        for(int i=0;i<numOfRows;i++){
            cells.add(new ArrayList<>());
            for(int j=0;j<numOfCols;j++){
                cells.get(i).add(new Cell());
            }
        }
    }

    public List<List<Cell>> getCells() {
        return cells;
    }

    public Cell getCell(int row, int col){
        return cells.get(row).get(col);
    }

    public String getCellValues(int row, int col) {
        return getCell(row,col).toString();
    }

    public void setCellValues(int row, int col, CellValue value, String originalValue) {
        getCell(row,col).update(value, originalValue);
        version++;
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
