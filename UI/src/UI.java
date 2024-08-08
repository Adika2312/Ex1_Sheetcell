import API.Engine;
import Impl.EngineImpl;

import java.util.Scanner;

public class UI {
    Engine engine = new EngineImpl();

    public void Run(){
        Scanner scanner = new Scanner(System.in);
        PrintSheet();
        var i = scanner.nextInt();
    }

    private void PrintSheet() {
        System.out.println(engine.GetSheet().toString());
    }
}
