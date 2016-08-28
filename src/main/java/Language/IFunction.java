package Language;

import java.util.LinkedHashMap;

/**
 * Created by emilyperegrine on 28/08/2016.
 */
public interface IFunction {
    Object call(VariableCollection params) throws Exception;
    String getName();
    public LinkedHashMap<String, String> getParams();
}
