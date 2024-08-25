package impl.sheet;

import api.CellValue;
import generated.STLCell;
import generated.STLCells;
import impl.EngineImpl;
import impl.cell.Cell;

import java.util.*;

public class Sheet {
    private String name;
    private int version = 1;
    private final Map<String, Cell> activeCells = new HashMap<>();
    private int numOfRows;
    private int numOfCols;
    private int rowHeight;
    private int colWidth;

    @Override
    public Sheet clone(){
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
        Cell cell = new Cell(this, cellIdentity);
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

    public void recalculate(){
    }

    public List getTopologicalOrderFromActiveCells() {
        List<Cell> topologicalOrder = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Set<String> inStack = new HashSet<>();
        Stack<Cell> stack = new Stack<>();

        for (String cellId : activeCells.keySet()) {
            if (!visited.contains(cellId)) {
                if (!iterativeDFS(activeCells.get(cellId), visited, inStack, stack, topologicalOrder)) {
                    throw new IllegalStateException("Cycle detected! Topological sorting is not possible.");
                }
            }
        }

        return topologicalOrder;
    }

    private boolean iterativeDFS(Cell startNode, Set<String> visited, Set<String> inStack, Stack<Cell> stack, List<Cell> topologicalOrder) {
        stack.push(startNode);

        while (!stack.isEmpty()) {
            Cell currentNode = stack.peek();

            if (!visited.contains(currentNode.getIdentity())) {
                visited.add(currentNode.getIdentity());
                inStack.add(currentNode.getIdentity());
            }

            boolean hasUnvisitedDependency = false;
            Cell currentCell = activeCells.get(currentNode.getIdentity());
            if (currentCell != null) {
                for (Cell dependency : currentCell.getCellsImDependentOn()) {
                    if (inStack.contains(dependency.getIdentity())) {
                        return false; //cycle
                    }

                    if (!visited.contains(dependency.getIdentity())) {
                        stack.push(dependency);
                        hasUnvisitedDependency = true;
                    }
                }
            }

            if (!hasUnvisitedDependency) {
                inStack.remove(currentNode.getIdentity());
                stack.pop();
                topologicalOrder.add(currentNode);
            }
        }

        return true;
    }
}
