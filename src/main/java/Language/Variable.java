package Language;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class Variable<T> {
    private String mName;
    private T mValue;
    private String mStringValue;

    public Variable(String name, T value) {
        mName = name;
        mValue = value;
        mStringValue = value.toString();
    }

    public String getName() {
        return mName;
    }

    public String getStringValue() {
        return mStringValue;
    }

    public T getValue() {
        return mValue;
    }

}
