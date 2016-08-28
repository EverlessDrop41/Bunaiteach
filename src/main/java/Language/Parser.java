package Language;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class Parser {
    public static final String[] KEYWORDS = {
        "VAR",
        "PRINT",
        "INPUT",
        "FUNC",
        "END",
        "IF",
        "ENDIF",
        "WHILE",
        "ENDWHILE",
        "RETURN"
    };

    private List<String> mKeywords;
    private VariableCollection mVariables;
    private HashMap<String, Function> mFunctions;

    private String[] mLines;

    private ScriptEngineManager mManager = new ScriptEngineManager();
        private ScriptEngine mEngine = mManager.getEngineByName("JavaScript");


    public Parser() {
        mKeywords = Arrays.asList(KEYWORDS);
        mVariables = new VariableCollection();
        mFunctions = new HashMap<String, Function>();
    }

    public Parser(String program) {
        Read(program);
        mKeywords = Arrays.asList(KEYWORDS);
        mVariables = new VariableCollection();
        mFunctions = new HashMap<String, Function>();
    }

    public void Read(String program) {
        mLines = program.split("[\n]");
    }

    public void RunApp() throws Exception {
        Run(mLines, "");
    }

    public Object Run(String[] programLines, String returnType) throws Exception {
        ArrayList<String> al = new ArrayList<String>();
        al.addAll(al);
        int currentLine = 0;
        while (currentLine < programLines.length) {
            String line = programLines[currentLine];
            String[] seperated = line.split("\\s+");

            if (mKeywords.contains(seperated[0])) {
                //TODO: Handle

                if (seperated[0].equals("VAR")) {
                    // VAR X:INT = 5 + 2 * 10 / 2 ^ 2
                    //TODO: VALIDATE
                    //NAME:TYPE

                    // Get name and type of variable
                    String[] variableDec = seperated[1].split("[:]");
                    String name = variableDec[0];
                    String typeAsString = variableDec[1];

                    // Validate equals sign is present
                    if (!seperated[2].equals("=")) {
                        throw new Exception(String.format("Invalid Syntax: Expecting = got %s", seperated[2]));
                    }

                    // Get expression to evaluate
                    String expression = String.join("", Arrays.copyOfRange(seperated, 3, seperated.length));

                    // Sub in variables and functions
                    for (String varName: mVariables.getNames()) {
                        Variable variable = (Variable) mVariables.get(varName);
                        expression = expression.replace(variable.getName(), variable.getStringValue());
                    }

                    for (Function function: mFunctions.values()) {
                        //Object object = function.call();
                        // Find funcName(1,2,3)
                        Pattern patt = Pattern.compile(function.getName() + "\\((.*)\\)");
                        Matcher m = patt.matcher(expression);
                        StringBuffer sb = new StringBuffer(expression.length());
                        while (m.find()) {
                            String text = m.group(1);

                            // Get function args
                            String args = text.substring(text.indexOf("(") + 1);
                            args = args.substring(0, args.indexOf(")"));

                            // Split by commas
                            // TODO: Split by outer commas as to allow users to nest func calls e.g. A(1, B(2, 3), 4)
                            String[] params = args.split("[,]");

                            VariableCollection variables = new VariableCollection();
                            for (String varName: mVariables.getNames()) {
                                Variable variable = (Variable) mVariables.get(varName);
                                variables.add(variable);
                            }

                            m.appendReplacement(sb, Matcher.quoteReplacement(text));
                        }
                        m.appendTail(sb);
                        // expression = expression.replace(, object.toString());
                    }

                     // Get evaluations
                    Object evaluation = mEngine.eval(expression);
                    Variable variable = Variable.fromTypeString(name, evaluation, typeAsString);
                    mVariables.add(variable);

                } else if (seperated[0].equals("FUNC")) {
                    // Get current line number                    
                    int c = currentLine + 1;
                    // Loop until get end line number
                    while (c < programLines.length) {
                        if (programLines[c].equals("END")) {
                            // Create Function object

                            Function function = new Function(Arrays.copyOfRange(programLines, currentLine, c+1));
                            mFunctions.put(function.getName(), function);
                            // Set while loop til after function declartion
                            currentLine = c + 1;
                            break;
                        }
                        c++;
                    }
                    
                } else if (seperated[0].equals("PRINT")) {
                    continue;
                } else if (seperated[0].equals("INPUT")) {
                    continue;
                } else if (seperated[0].equals("WHILE")) {
                    continue;
                } else if (seperated[0].equals("IF")) {
                    continue;
                } else if (seperated[0].equals("RETURN")) {
                    // Get text of what we are returning
                    // Get expression to evaluate
                    String expression = String.join("", Arrays.copyOfRange(seperated, 1, seperated.length));

                    // Sub in variables and functions
                    for (String varName: mVariables.getNames()) {
                        Variable variable = (Variable) mVariables.get(varName);
                        expression = expression.replace(variable.getName(), variable.getStringValue());
                    }

                     // Get evaluations
                    Object evaluation = mEngine.eval(expression);

                    if (returnType.equals("INT")) {
                         return (Integer) evaluation;
                    } else if (returnType.equals("STRING")) {
                        return (String) evaluation;
                    } else if (returnType.equals("BOOL")) {
                        return (Boolean) evaluation;
                    } else if (returnType.equals("FLOAT")) {
                        return (Float) evaluation;
                    } else if (returnType.equals("CHAR")) {
                        return (Character) evaluation;
                    } else {
                        throw new Exception("Unknown type");
                    }

                }
            } else if (mFunctions.containsKey(seperated[0])) {
                Function function = (Function) mFunctions.get(seperated[0]);
                // TOODO: add arg handling hear
                //Object object = function.call();
                Object object = function.call();
                System.out.print(object);
            } else {
                throw new Exception("Do not know what to do");
            }
            currentLine++;
        }

        return new Object();
    }

    public void setVariableCollection(VariableCollection variables) {
        mVariables = variables;
    }
}
