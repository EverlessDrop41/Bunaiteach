package Language;

import java.util.LinkedHashMap;
import java.util.Arrays;

public class Function {
    private String[] mCode;
    private String mName;
    private String mReturnType;
    private LinkedHashMap<String, String> mParams;

    public Function(String[] code) {
        mParams = new LinkedHashMap<String, String>();

        // Split first line to get name and params
        String[] firstLine = code[0].split("\\s+");
        String[] declaration = firstLine[1].split(":");
        mName = declaration[0];
        mReturnType = declaration[1];

        // Check function takes parms
        if (firstLine.length > 2) {
            // Iterate through params and add to LinkedHashMap mParams
            int i = 2;
            while (i < firstLine.length) {
                String[] param = firstLine[i].split(":");
                String name = param[0];
                String type = param[1];
                mParams.put(name, type); 
                i++;
            }
        }

        // Add code to mCode excluding first and last lines
        mCode = Arrays.copyOfRange(code, 1, code.length - 1);
    }

    public Object call(VariableCollection params) throws Exception {
        Parser parser = new Parser();
        
        // Update variable collection
        parser.setVariableCollection(params);
        
        return parser.Run(mCode, mReturnType);
    }

    public String getName() {
        return mName;
    }

    public LinkedHashMap<String, String> getParams() {
    	return mParams;
    }
}