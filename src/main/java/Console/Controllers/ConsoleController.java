package Console.Controllers;

import Console.IConsole;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Optional;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class ConsoleController {
    @FXML private TextArea ConsoleInput;
    @FXML private VBox container;
    @FXML private Text ConsoleOutput;

    IConsole c;

    public ConsoleController() {
        c = new IConsole() {
            public void Print(String input) {
                GuiPrint(input);
            }

            public void PrintLn(String input) {
                GuiPrintLn(input);
            }

            public String Input() {
                return GuiGetInput();
            }
        };
    }

    public void setConsoleOutputText(String text) {
         ConsoleOutput.setText(text);
    }

    public String getConsoleText() {
        return ConsoleOutput.getText();
    }

    public void submitCode(ActionEvent actionEvent) {
        c.PrintLn(c.Input());
    }

    public void GuiPrint(String message) {
       setConsoleOutputText(getConsoleText() + message);
    }

    public void GuiPrintLn(String message) {
        GuiPrint(message + "\n");
    }

    public String GuiGetInput() {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Input Request Dialog");
        dialog.setHeaderText("The program requests input");
        dialog.setContentText("");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        return result.isPresent() ? result.get() : "";
    }
}
