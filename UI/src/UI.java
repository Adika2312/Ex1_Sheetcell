import API.Engine;
import Impl.EngineImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

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
                        System.out.println("Displaying cell...");
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
            System.out.println("Invalid input, please enter a whole number.");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
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
