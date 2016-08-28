package Console.Controllers;

import Console.IConsole;
import Language.Parser;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.util.Optional;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class ConsoleController {
    @FXML private TextArea CodeInput;
    @FXML private TextArea ConsoleOutput;
    @FXML private TextField ConsoleInput;

    IConsole c;
    Parser parser;

    @FXML
    protected void initialize() {
        System.out.println("start");
        c = new IConsole() {
            @Override
            public void Print(String input) {
                PrintToConsole(input);
            }

            @Override
            public void PrintLn(String input) {
                PrintLineToConsole(input);
            }

            @Override
            public String Input() {
                return GetConsoleInput();
            }
        };

        ConsoleOutput.setEditable(false);

        ConsoleInput.setOnAction(event -> {
            c.PrintLn(ConsoleInput.getText());
            ConsoleInput.clear();
        });

        ContextMenu consoleMenu = new ContextMenu();

        consoleMenu.getItems().add(createItem("Clear Console", e -> {
            ConsoleOutput.setText("");
        }));

        ConsoleOutput.setContextMenu(consoleMenu);

        parser = new Parser(c);
    }

    private MenuItem createItem(String name, EventHandler<ActionEvent> a) {
        final MenuItem menuItem = new MenuItem(name);
        menuItem.setOnAction(a);
        return menuItem;
    }

    public void PrintToConsole(String message) {
        ConsoleOutput.appendText(message);
    }

    public void PrintLineToConsole(String mesage) {
        ConsoleOutput.appendText(mesage + '\n');
    }

    public String GetConsoleInput() {
        TextInputDialog dialog = new TextInputDialog(ConsoleInput.getText());
        dialog.setTitle("Input Request Dialog");
        dialog.setHeaderText("The program requests input");
        dialog.setContentText("");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        return result.isPresent() ? result.get() : "";
    }

    public void showException(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Look, an Exception Dialog");
        alert.setContentText(ex.getMessage());

// Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public void clearCodeInput() {
        CodeInput.setText("");
    }

    public String getCode() {
        return  CodeInput.getText();
    }

    public void submitCode(ActionEvent actionEvent) {
        try {
            String code = getCode();
            parser.Read(code);
            parser.RunApp();
        } catch (Exception e) {
            showException(e);
        }

        clearCodeInput();
    }

    public void loadCodeFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a source code File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Bunaiteach File", "*.bunai", "*.bt", "*.bunaiteach"),
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("Text File", "*.txt")
        );


        File selectedFile = fileChooser.showOpenDialog(null);

        try {
            if (selectedFile != null) {
                c.PrintLn("Loaded File: " + selectedFile.getName());
                try (FileReader reader = new FileReader(selectedFile.getPath())) {
                    char[] chars = new char[(int) selectedFile.length()];
                    reader.read(chars);
                    String content = new String(chars);
                    reader.close();
                    CodeInput.setText(content);
                }
            }
        } catch (Exception e) {
            //TODO Alert error
        }

    }
}
