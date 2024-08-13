package impl;

import api.CellValue;

import java.util.ArrayList;
import java.util.List;

public class Sheet {
    private String name = "Baby";
    private int version = 1;
    private final List<List<Cell>> cells = new ArrayList<>();
    private int numOfRows = 12;
    private int numOfCols = 10;
    private float rowHeight = 2;
    private float colWidth = 4;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int rowsCounter = 1;
        char colCounter = 'A';
        int widthOfFirstCol = countDigits(numOfRows);

        sb.append("Name: ").append(name).append("\n");
        sb.append("Version: ").append(version).append("\n");
        sb.append(String.format("%" + (widthOfFirstCol+2) + "s", ""));

        for(int i=0;i<numOfCols;i++){
            sb.append(String.format("%-" + (int) (colWidth+1) + "s", colCounter++));
        }
        sb.append("\n");

        for (List<Cell> row : cells) {
            sb.append(String.format("%0" + widthOfFirstCol + "d", rowsCounter++)).append(" ");
            for (Cell cell : row) {
                String cellValue = cell.getCellEffectiveValue();
                // Adjust cell value to fit within the column width TODO
                if (cellValue.length() > colWidth) {
                    cellValue = cellValue.substring(0, (int) colWidth);
                }
                sb.append(String.format("|" + "%-" + (int) colWidth + "s", cellValue));
            }
            sb.append("|\n");
            for(int j=0;j<rowHeight-1;j++){
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    public static int countDigits(int number) {
        if (number == 0) {
            return 1;
        }

        int count = 0;
        while (number != 0) {
            number /= 10;
            count++;
        }
        return count;
    }

    public Cell getCell(int row, int col){
        return cells.get(row).get(col);
    }

    public String getCellValues(int row, int col) {
        return getCell(row,col).toString();
    }

    public void setCellValues(int row, int col, CellValue value) {
        getCell(row,col).update(value);
        version++;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getNumOfCols() {
        return numOfCols;
    }

    public String getName() {
        return name;
    }

    public int getVersion(){
        return version;
    }

//    public SheetDTO toDTO() {
//        List<List<String>> dtoCells = new ArrayList<>();
//        for (List<Cell> row : cells) {
//            List<String> dtoRow = new ArrayList<>();
//            for (Cell cell : row) {
//                dtoRow.add(cell.getCellEffectiveValue());
//            }
//            dtoCells.add(dtoRow);
//        }
//        return new SheetDTO(name, version, numOfRows, numOfCols, dtoCells);
//    }
}
