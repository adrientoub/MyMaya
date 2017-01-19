package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ConverterModel;
import model.ExportSceneModel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Adrien on 28/12/2016.
 */
public class RenderController implements EventHandler<Event> {
    private File renderer;
    private static final String outputFile = "myout.ppm";
    private static final String inputFile = "temporary.in";

    @Override
    public void handle(Event event) {
        if (renderer == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open executable Raytracer");
            String os = System.getProperty("os.name");
            if (os.startsWith("Windows")) {
                fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Executable", "exe"));
            }
            renderer = fileChooser.showOpenDialog(null);
            if (renderer == null) {
                return;
            }
        }
        ExportSceneModel.exportScene(inputFile);
        try {
            ProcessBuilder pb = new ProcessBuilder(renderer.getAbsolutePath(), inputFile, outputFile);
            pb.inheritIO();
            long startTime = System.currentTimeMillis();
            Process p = pb.start();
            int outValue = p.waitFor();
            long endTime = System.currentTimeMillis();
            double duration = (endTime - startTime) / 1000.;
            System.out.println("Generated output in " + outputFile + ", done in " + duration + " seconds. Exit status " + outValue);

            WritableImage image = ConverterModel.openPPM(outputFile);

            // TODO: add ability to save to usual file format
            Stage stage = new Stage();
            stage.setTitle("Render");
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().addAll(new ImageView(image));
            stage.setScene(new Scene(secondaryLayout, image.getWidth(), image.getHeight()));
            stage.show();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
