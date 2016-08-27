package Language;

import java.util.HashMap;
import java.util.Arrays;

public class Function {
    private String mCode;
    private String mName;
    private VariableType mReturnType;
    private HashMap<String, VariableType> mParams;

    public Function(String[] code) {
        mParams = new HashMap<>();

        // Split first line to get name and params
        String[] firstLine = code[0].split("\\s+");
        String declaration = firstLine[1].split(":");
        String mName = declaration[0];
        String mReturnType = declaration[1];

        // Check function takes parms
        if (firstLine.length > 2) {
            // Iterate through params and add to HashMap mParams
            int i = 2;
            while (i < firstLine.length) {
                String param = firstLine[i].split(":");
                String name = param[0]
                String typeAsString = param[1]
                VariableType type = VariableType.valueOf(typeAsString);
                mParams.put(name, type); 
                i++;
            }
        }

        // Add code to mCode excluding first and last lines
        mCode = String.join("", Arrays.copyOfRange(code, 1, code.length - 1));
    }

    public void Run() {
        Parser parser = new Parser(mCode);
        parser.RunApp();
    }

    public String getName() {
        return mName;
    }
}