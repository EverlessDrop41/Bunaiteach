import Console.GUIConsole;
import Language.Parser;
import Console.IConsole;
import Console.TestConsole;

import static javafx.application.Application.launch;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        // IConsole console = new TestConsole();
        // Parser parser = new Parser(console);
        // //parser.Read("VAR A:INT = 2\nFUNC X:INT A:INT B:INT\nRETURN A + B\nEND\nVAR B:INT = X(A, 3) + 2\nPRINT B");
        // parser.Read("FUNC ADDTEN:INT B:INT\nRETURN B + 10\nEND\nVAR C:INT = 1\nIF C > 2\nPRINT A\nELSE\nPRINT ADDTEN(C)\nENDIF");
        // parser.RunApp();

        GUIConsole.Main(args);
//        IConsole console = new TestConsole();
//        Parser parser = new Parser(console);
//        //parser.Read("VAR A:INT = 2\nFUNC X:INT A:INT B:INT\nRETURN A + B\nEND\nVAR B:INT = X(A, 3) + 2\nPRINT B");
//        parser.Read("VAR A:INT = 0\nWHILE A < 10\nPRINT A\nVAR A:INT = A + 1\nENDWHILE");
//        parser.RunApp();
    }
}
