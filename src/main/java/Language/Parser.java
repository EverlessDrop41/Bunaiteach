package Language;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Exchanger;

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
        "ENDWHILE"
    };

    private List<String> mKeywords;
    private HashMap<String, Variable> mVariables;

    private String[] mLines;

    private ScriptEngineManager mManager = new ScriptEngineManager();
    private ScriptEngine mEngine = mManager.getEngineByName("JavaScript");

    public Parser(String program) {
        Read(program);
        mKeywords = Arrays.asList(KEYWORDS);
    }

    public void Read(String program) {
        mLines = program.split("[\n]");
    }

    public void RunApp() throws Exception {
        Run(mLines);
    }

    public void Run(String[] programLines) throws Exception {
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
                    String type = variableDec[1];

                    // Validate equals sign is present
                    if (!seperated[2].equals("=")) {
                        throw new Exception(String.format("Invalid Syntax: Expecting = got %s", seperated[2]));
                    }

                    // Get expression to evaluate
                    String expression = String.join("", Arrays.copyOfRange(seperated, 3, seperated.length));

                    // Sub in variables and functions
                    for (Variable variable: mVariables.values()) {
                        expression = expression.replace(variable.getName(), variable.getStringValue());
                    }


                    // Get evaluations
                    Object evaluation = mEngine.eval(expression);


                } else if (seperated[0].equals("FUNC")) {
                    // Get current line number                    
                    int c = currentLine + 1;
                    // Loop until get end line number
                    while (c < programLines.length) {
                        if (programLines[c].equals("END")) {
                            // Create Function object
                            Function function = new Function(Arrays.copyOfRange(programLines, currentLine, c))
                            functions.put(function.getName(), function);
                            // Set while loop til after function declartion
                            i = c + 1;
                            break;
                        }
                    }
                    
                }
            } else {
                throw new Exception("Do not know what to do");
            }
            currentLine++;
        }
    }
}
