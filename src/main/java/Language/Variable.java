package Language;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class Variable {
    private VariableType mType;
    private String mName;
    private Object mValue;
    private String mStringValue;

    public Variable(VariableType type, String value) {
        mType = type;
        mValue = value;
        mStringValue = value;
    }

    public String getStringValue() {
        return mStringValue;
    }

    public String getName() {
        return mName;
    }

    public String GetValue() {
        return mValue.toString();
    }

    public <T> T GetNativeValue(Class<T> clazz) {
        try {
            return clazz.cast(mValue);
        } catch(ClassCastException e) {
            return null;
        }
    }

}
