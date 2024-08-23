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

    @Override
    protected Sheet clone(){
        Sheet sheet = new Sheet();
        sheet.name = name;
        sheet.version = version;
        sheet.numOfRows = numOfRows;
        sheet.numOfCols = numOfCols;
        sheet.rowHeight = rowHeight;
        sheet.colWidth = colWidth;
        for (Map.Entry<String, Cell> entry : activeCells.entrySet()) {
            String copiedKey = entry.getKey();
            Cell copiedValue = new Cell(sheet, entry.getValue());
            sheet.activeCells.put(copiedKey, copiedValue);
        }
        return sheet;
    }

    public Map<String,Cell> getActiveCells() {
        return activeCells;
    }

    public Cell getCell(String cellIdentity){
        return activeCells.get(cellIdentity);
    }

    public Sheet setCellValues(String cellIdentity, CellValue value, String originalValue, boolean isFromFile) {
        Cell cell = getCell(cellIdentity);
        Sheet alternativeSheet = this.clone();
        if (cell == null){
            createNewCell(cellIdentity, value, originalValue, isFromFile, alternativeSheet);
        }
        else{
            cell.update(value,originalValue, isFromFile, alternativeSheet);
        }
        alternativeSheet.recalculate();
        alternativeSheet.updateVersion();
        return alternativeSheet;
    }

    private void createNewCell(String cellIdentity, CellValue value, String originalValue, boolean isFromFile, Sheet alternativeSheet) {
        Cell cell = new Cell(this, cellIdentity);
        alternativeSheet.getActiveCells().put(cellIdentity, cell);
        cell.update(value, originalValue, isFromFile, alternativeSheet);

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

    public void updateVersion(){
        version++;
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
            createNewCell(cellIdentity, EngineImpl.convertStringToCellValue(orgValue), orgValue, true, this);
        }
    }

    public void recalculate(){
        for(Cell cell : activeCells.values()){
            cell.getEffectiveValue().setActivatingCell(cell);
            cell.getEffectiveValue().calculateAndSetEffectiveValue();
        }
    }
}
