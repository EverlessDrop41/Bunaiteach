package Language;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class Variable {
    private VariableType mType;
    private String mName;
    private Object mValue;

    public Variable(VariableType type, String value) {
        mType = type;
        mValue = value;
    }

    public String GetValue() {
        return mValue.toString();
    }

    public <T> T GetNatvieValue(Class<T> clazz) {
        try {
            return clazz.cast(mValue);
        } catch(ClassCastException e) {
            return null;
        }
    }

}
