package Console;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class TestConsole implements IConsole {
    public void Print(String input) {
        System.out.print(input);
    }

    public void PrintLn(String input) {
        System.out.println(input);
    }

    public String Input () {
        return "";
    }
}
