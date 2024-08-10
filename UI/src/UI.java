import API.Engine;
import Impl.EngineImpl;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UI {
    Engine engine = new EngineImpl();
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
                        System.out.println("Loading file...");
                        break;
                    case DISPLAY_SHEET:
                        PrintSheet();
                        break;
                    case DISPLAY_CELL:
                        PrintCell();
                        break;
                    case UPDATE_CELL:
                        System.out.println("Updating cell...");
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

    private void PrintCell() {

        Scanner scanner = new Scanner(System.in);
        String cellIdentity;
        Pattern cellPattern = Pattern.compile("^[A-Z]+[1-9][0-9]*$");
        int col;
        int row;

        while(true){
            System.out.println("Please enter the cell identity (e.g., A4) to view its value and status:");
            cellIdentity = scanner.nextLine().trim();

            if (cellPattern.matcher(cellIdentity).matches()) {
                String columnString = cellIdentity.replaceAll("[0-9]", "");
                String rowString = cellIdentity.replaceAll("[A-Z]", "");
                col = extractColumn(columnString);
                row = extractRow(rowString);

                if(engine.IsCellInBounds(row, col)){
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

        System.out.println("Cell Identity: " + cellIdentity);
        System.out.println(engine.getCellValue(row, col));
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
            System.out.println(engine.GetSheet().toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
