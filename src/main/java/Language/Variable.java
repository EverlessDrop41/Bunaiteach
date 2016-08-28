package Language;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class Variable<T> {
    private String mName;
    private T mValue;
    private String mStringValue;
    private String mTypeString;

    public Variable(String name, T value, String typeString) {
        mName = name;
        mValue = value;
        mStringValue = value.toString();
        mTypeString = typeString;
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

    public static Variable fromTypeString(String name, Object value, String typeString) throws Exception {
        Variable variable;
        if (typeString.equals("INT")) {
            variable = new Variable<Integer>(name, (Integer) value, typeString);
        } else if (typeString.equals("STRING")) {
            variable = new Variable<String>(name, (String) value, typeString);
        } else if (typeString.equals("BOOL")) {
            variable = new Variable<Boolean>(name, (Boolean) value, typeString);
        } else if (typeString.equals("FLOAT")) {
            variable = new Variable<Float>(name, (Float) value, typeString);
        } else if (typeString.equals("CHAR")) {
            variable = new Variable<Character>(name, (Character) value, typeString);
        } else {
            throw new Exception("Unknown type");
        }
        return variable;
    }

    public String getTypeString() {
        return mTypeString;
    }
}
