package ui;

import api.CellValue;
import api.DTO;
import api.Engine;
import dto.DTOFactoryImpl;
import dto.SheetDTO;
import impl.*;
import impl.cell.Cell;
import impl.cell.value.BooleanValue;
import impl.cell.value.FunctionValue;
import impl.cell.value.NumericValue;
import impl.cell.value.StringValue;
import utility.CellCoord;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UI {
    Engine engine = new EngineImpl(new DTOFactoryImpl());
    Boolean isProgramRunning = true;

    public enum MenuOptions {
        LOAD_FILE(1, "Load File"), DISPLAY_SHEET(2, "Display Sheet"), DISPLAY_CELL(3, "Display Cell"), UPDATE_CELL(4,"Update Cell"), DISPLAY_VERSIONS(5,"Display Versions"), EXIT(6,"Exit");

        private final int value;
        private final String name;

        MenuOptions(int value, String name) {
            this.value = value;
            this.name = name;
        }
        public int getValue() {
            return value;
        }
        public String getName() {
            return name;
        }

    }

    public void Run(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
Welcome to the Sheetcell!
*************************""");
        while(isProgramRunning) {
            DisplayMenu(scanner);
        }
    }
    public void DisplayMenu(Scanner scanner) {
        PrintMenu();
        try {
            int userInput = scanner.nextInt();
            if (userInput >= 1 && userInput < MenuOptions.values().length + 1) {

                MenuOptions option = MenuOptions.values()[userInput-1];

                switch (option) {
                    case LOAD_FILE:
                        loadFile();
                        break;
                    case DISPLAY_SHEET:
                        PrintSheet();
                        break;
                    case DISPLAY_CELL:
                        PrintCell();
                        break;
                    case UPDATE_CELL:
                        UpdateCell();
                        break;
                    case DISPLAY_VERSIONS:
                        System.out.println("Displaying versions...");
                        break;
                    case EXIT:
                        isProgramRunning = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            }
            else {
                System.out.println("Invalid choice. Please enter a number between 1 and " + (MenuOptions.values().length) + ".");
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid choice, please enter a whole number.");
            scanner.nextLine();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            scanner.nextLine();
        }
    }

    private void loadFile() {
        System.out.println("Please enter a file path to load:");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        try {
            engine.loadFile(filePath);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void UpdateCell() {
        CellCoord cellInput =  getCheckAndPrintBasicCellInfo("update its value:");
        System.out.println("\nPlease enter a new value for the cell:");
        Scanner scanner = new Scanner(System.in);
        String orgValue = scanner.nextLine();
        CellValue newCellValue = convertStringToCellValue(orgValue);
        engine.updateCellValue(cellInput.getRow(), cellInput.getCol(), newCellValue, orgValue);
    }


    private void PrintCell() {
        CellCoord cellInput =  getCheckAndPrintBasicCellInfo("view its value and status:");
        int currVersion = engine.getCellDTO(cellInput.getRow(), cellInput.getCol()).getVersion();
        System.out.println("Current version: " + currVersion);
    }

    private CellCoord getCheckAndPrintBasicCellInfo(String massage){
        CellCoord cellInput = getAndCheckCellInput(massage);
        System.out.println("Cell Identity: " + cellInput.getIdentity());
        System.out.println(engine.getCellValue(cellInput.getRow(), cellInput.getCol()));
        return cellInput;
    }

    private CellCoord getAndCheckCellInput(String massage) {
        Pattern cellPattern = Pattern.compile("^[A-Z]+[1-9][0-9]*$");
        int col;
        int row;
        String cellIdentity;
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("Please enter the cell identity (e.g., A4) to " + massage);
            cellIdentity = scanner.nextLine().trim();

            if (cellPattern.matcher(cellIdentity).matches()) {
                String columnString = cellIdentity.replaceAll("[0-9]", "");
                String rowString = cellIdentity.replaceAll("[A-Z]", "");
                col = extractColumn(columnString);
                row = extractRow(rowString);

                if(engine.isCellInBounds(row, col)){
                    break;
                }
                else{
                    System.out.println("Invalid cell identity, Please enter a cell within the sheet boundaries");
                }
            }
            else {
                System.out.println("Invalid cell identity. Please enter a cell in the right format (e.g., A4).");
            }
        }

        return new CellCoord(row, col, cellIdentity);
    }

    private static int extractRow(String cellName) {
        String rowPart = cellName.replaceAll("[A-Z]+", "");
        return Integer.parseInt(rowPart) - 1;
    }

    private static int extractColumn(String cellName) {
        String columnPart = cellName.replaceAll("[0-9]+", "");
        int column = 0;

        for (int i = 0; i < columnPart.length(); i++) {
            column = column * 26 + (columnPart.charAt(i) - 'A' + 1);
        }

        return column - 1;
    }


    private void PrintMenu() {
        System.out.println("""

Please enter the option's number you wish to use:""");
        for (MenuOptions menuOption : MenuOptions.values()){
            System.out.println(menuOption.getValue() + ". " + menuOption.getName());
        }
    }

    private void PrintSheet() {
        try {
            String SheetDataToPrint = convertSheetDTOToString((SheetDTO) engine.getSheetDTO());
            System.out.println(SheetDataToPrint);
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private String convertSheetDTOToString(SheetDTO sheetDTO) {
        StringBuilder sb = new StringBuilder();
        int rowsCounter = 1;
        char colCounter = 'A';
        int widthOfFirstCol = countDigits(sheetDTO.getNumOfRows());

        sb.append("Name: ").append(sheetDTO.getName()).append("\n");
        sb.append("Version: ").append(sheetDTO.getVersion()).append("\n");
        sb.append(String.format("%" + (widthOfFirstCol+2) + "s", ""));

        for(int i=0;i<sheetDTO.getNumOfCols();i++){
            sb.append(String.format("%-" + (int) (sheetDTO.getColWidth()+1) + "s", colCounter++));
        }
        sb.append("\n");

        for (List<Cell> row : sheetDTO.getCells()) {
            sb.append(String.format("%0" + widthOfFirstCol + "d", rowsCounter++)).append(" ");
            for (Cell cell : row) {
                String cellValue = cell.getCellEffectiveValue();

                if (cellValue.length() > sheetDTO.getColWidth()) {
                    cellValue = cellValue.substring(0, (int) sheetDTO.getColWidth());
                }
                sb.append(String.format("|" + "%-" + (int) sheetDTO.getColWidth() + "s", cellValue));
            }
            sb.append("|\n");
            for(int j=0;j<sheetDTO.getRowHeight()-1;j++){
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

    public static CellValue convertStringToCellValue(String newValue) {

        CellValue cellValue = null;

        while (true){
            // Check for Boolean
            if (newValue.equals("TRUE") || newValue.equals("FALSE")) {
                cellValue = new BooleanValue(Boolean.parseBoolean(newValue));
            }
            // Check for Numerical
            else if (newValue.matches("-?\\d+(\\.\\d+)?")) {
                try {
                    double numericValue = Double.parseDouble(newValue);
                    cellValue = new NumericValue(numericValue);
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid numeric value.");
                }
            }
            // Check for Function
            else if (newValue.matches("\\{[A-Z]+(,[^,]+)*\\}")) {
                cellValue = new FunctionValue(newValue);
            }
            // Otherwise, treat as String
            else {
                cellValue = new StringValue(newValue);
            }

            if (true) {
                break;
            }
            else {
                System.out.println("Invalid value entered.");
            }
        }

        return cellValue;
    }
}
