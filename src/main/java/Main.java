import Language.Parser;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser("VAR A:INT = 2\nFUNC X:INT A:INT B:INT\nRETURN A + B\nEND\nVAR B:INT = X(A, 3) + 2\nPRINT B");
        parser.RunApp();
    }
}
