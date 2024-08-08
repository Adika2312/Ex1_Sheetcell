package Impl;

import java.util.ArrayList;
import java.util.List;

public class Sheet {
    private String name="Baby";
    private int version=1;
    private List<List<Cell>> cells = new ArrayList<>();
    private int numOfRows=7;
    private int numOfCols=10;
    private float rowWidth=1;
    private float colWidth=1;

    public Sheet(){
        for(int i=0;i<numOfRows;i++){
            cells.add(new ArrayList<>());
            for(int j=0;j<numOfCols;j++){
                cells.get(i).add(new Cell());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int rowsCounter = 1;
        char colCounter = 'A';
        int widthOfFirstCol = countDigits(numOfRows);

        sb.append("Name: " + name + "\n");
        sb.append("Version: " + version + "\n");
        sb.append(String.format("%" + widthOfFirstCol + "s", "") + String.format("%-" + (int) (colWidth) + "s", ""));

        for(int i=0;i<numOfCols;i++){
            sb.append(String.format("%-" + (int) (colWidth+1) + "s", colCounter++));
        }
        sb.append("\n");

        for (List<Cell> row : cells) {
            sb.append(String.format("%" + widthOfFirstCol + "d", rowsCounter++));
            sb.append(" ");
            for (Cell cell : row) {
                String cellValue = cell.toString();
                // Adjust cell value to fit within the column width TODO
                if (cellValue.length() > colWidth) {
                    cellValue = cellValue.substring(0, (int) colWidth);
                }
                sb.append(String.format("|" + "%-" + (int) colWidth + "s", cellValue));
            }
            sb.append("|\n");
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
}
