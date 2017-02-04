package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import script.Execution;
import script.Parser;
import script.ast.AstNode;

import java.io.File;
import java.io.IOException;

/**
 * Created by Adrien on 04/02/2017.
 */
public class OpenScriptController implements EventHandler<Event> {
    @Override
    public void handle(Event event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Parser parser = new Parser();
            try {
                AstNode astNode = parser.parse(file);
                if (astNode != null) {
                    astNode.accept(Execution.getInstance());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
