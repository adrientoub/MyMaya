package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import model.ImportSceneModel;
import model.ObjReader;

import java.io.File;
import java.io.IOException;

/**
 * Created by Adrien on 04/02/2017.
 */
public class OpenController implements EventHandler<Event> {
    @Override
    public void handle(Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Object files", "*.obj"),
                new FileChooser.ExtensionFilter("All files", "*")
                );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            if (file.getName().endsWith(".obj")) {
                try {
                    ObjReader.openObj(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                ImportSceneModel.importScene(file);
            }
        }
    }
}
