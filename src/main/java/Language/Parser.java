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
import java.util.Iterator;
import java.util.Map.Entry;
import Console.IConsole;

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

    private IConsole mConsole; 


    public Parser() {
        mKeywords = Arrays.asList(KEYWORDS);
        mVariables = new VariableCollection();
        mFunctions = new HashMap<String, Function>();
    }

    public Parser(IConsole console) {
        mConsole = console;
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
                    expression = subInVariablesAndFunctions(expression);
                    
                    // Get evaluations
                    Object evaluation = mEngine.eval(expression);
                    Variable variable = Variable.fromTypeString(name, evaluation, typeAsString);
                    mVariables.add(variable);
                    currentLine++;

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
                    String expression = String.join("", Arrays.copyOfRange(seperated, 1, seperated.length));
                    expression = subInVariablesAndFunctions(expression);
                    Object output = mEngine.eval(expression);
                    mConsole.PrintLn(output.toString());
                    currentLine++;
                    continue;
                } else if (seperated[0].equals("INPUT")) {
                    currentLine++;
                    continue;
                } else if (seperated[0].equals("WHILE")) {

                    // Get lines in loop
                    int c = currentLine + 1;
                    int lineToGoTo = -1;

                    while (c < programLines.length) {
                        if (programLines[c].equals("ENDWHILE")) {
                            lineToGoTo = c + 1;
                            break ;
                        }
                        c++;
                    }

                    if (lineToGoTo == -1) {
                        throw new Exception("While loop not terminated.");
                    }
                    // Get conditions
                    String condition = String.join("", Arrays.copyOfRange(seperated, 1, seperated.length));
                    String conditionSubbed = subInVariablesAndFunctions(condition);

                    Boolean conditionBoolean = (Boolean) mEngine.eval(conditionSubbed);

                    if (conditionBoolean) {
                        do {
                            this.Run(Arrays.copyOfRange(programLines, currentLine + 1, lineToGoTo-1), "");
                            conditionSubbed = subInVariablesAndFunctions(condition);
                            conditionBoolean = (Boolean) mEngine.eval(conditionSubbed);
                        } while (conditionBoolean);
                    }
                    
                    currentLine = lineToGoTo;
                    
                } else if (seperated[0].equals("IF")) {
                    // Get condition
                    String condition = String.join("", Arrays.copyOfRange(seperated, 1, seperated.length));
                    String conditionSubbed = subInVariablesAndFunctions(condition);

                    // Evaluate condition
                    Boolean conditionBoolean = (Boolean) mEngine.eval(conditionSubbed);

                    // Get end line
                    int c = currentLine + 1;
                    int lineToGoTo = -1;
                    int elseLine = -1;

                    while (c < programLines.length) {
                        if (programLines[c].equals("ENDIF")) {
                            lineToGoTo = c + 1;
                            break ;
                        } else if (programLines[c].equals("ELSE")) {
                            elseLine = c;
                        }
                        c++;
                    }

                    if (lineToGoTo == -1) {
                        throw new Exception("If statement not terminated.");
                    }


                    // Get lines to execute
                    int startLine;
                    int endLine;

                    if (conditionBoolean) {
                        startLine = currentLine + 1;
                        if (elseLine != -1) {
                            endLine = elseLine;
                        } else {
                            endLine = lineToGoTo - 1;
                        }
                    } else {
                        startLine = elseLine + 1;
                        endLine = lineToGoTo - 1;
                    }

                    // Run lines
                    String[] lines = Arrays.copyOfRange(programLines, startLine, endLine);
                    this.Run(lines, "");

                    // Update current line
                    currentLine = lineToGoTo;

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
                //System.out.print(object);
            } else {
                throw new Exception("Do not know what to do");
            }
            //currentLine++;
        }

        return new Object();
    }

    public void setVariableCollection(VariableCollection variables) {
        mVariables = variables;
    }

    public String subInVariablesAndFunctions(String expression) throws Exception {
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

                // Split by commas
                // TODO: Split by outer commas as to allow users to nest func calls e.g. A(1, B(2, 3), 4)
                String[] params = args.split("[,]");

                VariableCollection variables = new VariableCollection();
                
                Iterator<Entry<String, String>> itr = function.getParams().entrySet().iterator();
                int i = 0;
                while (itr.hasNext()) {
                    Entry<String, String> entry = itr.next();
                    String variableName = entry.getKey();
                    String type = entry.getValue();
                    Variable variable = Variable.fromTypeString(variableName, mEngine.eval(params[i]), type);
                    variables.add(variable);
                    i++;
                }
                m.appendReplacement(sb, function.call(variables).toString().toString());

            }
            m.appendTail(sb);
            
            expression = sb.toString();
        }

        return expression;
    }
}
