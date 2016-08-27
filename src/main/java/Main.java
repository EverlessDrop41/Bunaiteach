import Language.Parser;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser("FUNC X:INT\nRETURN 1\nEND\nVAR B:INT = 2n\nX");
        parser.RunApp();
    }
}
