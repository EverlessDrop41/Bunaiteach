package Language;

import Console.IConsole;

import java.io.Console;
import java.util.LinkedHashMap;

/**
 * Created by emilyperegrine on 28/08/2016.
 */
public class InputFunction implements IFunction {

    private IConsole mConsole;

    public InputFunction(IConsole console) {
        mConsole = console;
    }

    @Override
    public Object call(VariableCollection params) throws Exception {
        return mConsole.Input();
    }

    @Override
    public String getName() {
        return "PRINT";
    }

    @Override
    public LinkedHashMap<String, String> getParams() {
        LinkedHashMap<String, String> params = new LinkedHashMap<>(0);
        return params;
    }


}
