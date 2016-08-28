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

import java.io.*;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Scanner;

/**
 * Created by emilyperegrine on 27/08/2016.
 */
public class ConsoleController {
    @FXML private TextArea Console;

    private PrintStream out;
    private InputStream in;

    IConsole c;

    @FXML
    protected void initialize() {
        Console.setWrapText(true);

        TextInputControlStream stream = new TextInputControlStream(Console, Charset.defaultCharset());

        try {
            this.out = new PrintStream(stream.getOut(), true, Charset.defaultCharset().name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.in = stream.getIn();

        System.out.println("start");
        c = new IConsole() {
            @Override
            public void Print(String input) {
                out.print(input);
            }

            @Override
            public void PrintLn(String input) {
                out.println(input);
            }

            @Override
            public String Input() throws Exception {
                Console.requestFocus();
                BufferedReader buffer=new BufferedReader(new InputStreamReader(in));
                return buffer.readLine();
            }
        };

    }

    public PrintStream getOut() {
        return out;
    }

    public InputStream getIn() {
        return in;
    }

    public void runStuff(ActionEvent actionEvent) {
        c.PrintLn("Car");
        try {
            c.PrintLn(c.Input());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
