package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import model.ObjReader;

import java.io.File;
import java.io.IOException;

/**
 * Created by Adrien on 15/01/2017.
 */
public class ObjController implements EventHandler<Event> {
    @Override
    public void handle(Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open executable Raytracer");
        File file = fileChooser.showOpenDialog(null);
        if (file == null) {
            return;
        }
        try {
            ObjReader.openObj(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
