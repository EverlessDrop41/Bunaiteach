package Language;

import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class VariableCollection {

    private HashMap<String, Variable<Boolean>> mBools;
    private HashMap<String, Variable<Integer>> mInts;
    private HashMap<String, Variable<Float>> mFloats;

    private HashMap<String, Variable<Character>> mChars;
    private HashMap<String, Variable<String>> mStrings;
    
    private TreeSet<String> mNames;

    public VariableCollection() {
        mNames = new TreeSet<String>();
        mBools = new HashMap<String, Variable<Boolean>>();
        mInts = new HashMap<String, Variable<Integer>>();
        mFloats = new HashMap<String, Variable<Float>>();
        mChars = new HashMap<String, Variable<Character>>();
        mStrings = new HashMap<String, Variable<String>>();
    }

    public HashMap<String, Variable<Boolean>> getBools() {
        return mBools;
    }

    public HashMap<String, Variable<Integer>> getInts() {
        return mInts;
    }

    public HashMap<String, Variable<Float>> getFloats() {
        return mFloats;
    }

    public HashMap<String, Variable<Character>> getChars() {
        return mChars;
    }

    public HashMap<String, Variable<String>> getStrings() {
        return mStrings;
    }

    public boolean Contains(String name) {
        return  mBools.containsKey(name)  ||
                mInts.containsKey(name)   ||
                mFloats.containsKey(name) ||
                mChars.containsKey(name)  ||
                mStrings.containsKey(name);
    }

    public Object get(String name) throws Exception {
        if (mBools.containsKey(name)) {
            return mBools.get(name);
        } else if (mInts.containsKey(name)) {
            return mInts.get(name);
        } else if (mFloats.containsKey(name)) {
            return mFloats.get(name);
        } else if (mChars.containsKey(name)) {
            return mChars.get(name);
        } else if (mStrings.containsKey(name)) {
            return mStrings.get(name);
        } else {
            throw new Exception("Variable not found");
        }
    }

    public Variable<Boolean> getBool(String name) {
        return mBools.get(name);
    }

    public Variable<Integer> getInt(String name) {
        return mInts.get(name);
    }

    public Variable<Float> getFloat(String name) {
        return mFloats.get(name);
    }

    public Variable<Character> getChar(String name) {
        return mChars.get(name);
    }

    public TreeSet<String> getNames() {
        return mNames;
    }

    public String getString(String name) {
        return mStrings.get(name).getValue();
    }

    public void addBool(Variable<Boolean> var) {
        mNames.add(var.getName());
        mBools.put(var.getName(), var);
    }

    public void addInt(Variable<Integer> var) {
        mNames.add(var.getName());
        mInts.put(var.getName(), var);
    }

    public void addFloat(Variable<Float> var) {
        mNames.add(var.getName());
        mFloats.put(var.getName(), var);
    }

    public void addChar(Variable<Character> var) {
        mNames.add(var.getName());
        mChars.put(var.getName(), var);
    }

    public void addString (Variable<String> var) {
        mNames.add(var.getName());
        mStrings.put(var.getName(), var);
    }
}
