package Language;

import Console.IConsole;

import java.io.Console;

/**
 * Created by emilyperegrine on 28/08/2016.
 */
public class InputFunction implements IFunction {

    IConsole mConsole;

    public InputFunction(IConsole console) {
        mConsole = console;
    }

    @Override
    public Object call(VariableCollection params) throws Exception {
        return mConsole.Input();
    }
}
