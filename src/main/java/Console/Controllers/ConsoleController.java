package Console.Controllers;

import Console.IConsole;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.util.Optional;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class ConsoleController {
    @FXML private TextArea ConsoleOutput;
    @FXML private TextField ConsoleInput;

    IConsole c;

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
            c.PrintLn(c.Input());
            ConsoleInput.clear();
        });
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
}
