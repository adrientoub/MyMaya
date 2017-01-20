package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ConverterModel;
import model.ExportSceneModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Adrien on 28/12/2016.
 */
public class RenderController implements EventHandler<Event> {
    private File renderer;
    private static final String outputFile = "myout-%d.ppm";
    private static final String inputFile = "temporary-%d.in";
    private static int i = 0;

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
        String inputFilename = String.format(inputFile, i);
        String outputFilename = String.format(outputFile, i++);
        ExportSceneModel.exportScene(inputFilename);
        try {
            ProcessBuilder pb = new ProcessBuilder(renderer.getAbsolutePath(), inputFilename, outputFilename);
            pb.inheritIO();
            long startTime = System.currentTimeMillis();
            Process p = pb.start();
            int outValue = p.waitFor();
            long endTime = System.currentTimeMillis();
            double duration = (endTime - startTime) / 1000.;
            System.out.println("Generated output in " + outputFilename + ", done in " + duration + " seconds. Exit status " + outValue);

            WritableImage image = ConverterModel.openPPM(outputFilename);

            Stage stage = new Stage();
            stage.setTitle("Render");
            VBox vBox = new VBox();
            StackPane secondaryLayout = new StackPane();

            MenuBar menuBar = new MenuBar();
            Menu file = new Menu("File");
            MenuItem save = new MenuItem("Save");
            save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
            save.addEventHandler(EventType.ROOT, event1 -> {
                FileChooser fileChooser = new FileChooser();
                File savedFile = fileChooser.showSaveDialog(stage);
                if (savedFile == null) {
                    return;
                }

                BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
                try {
                    ImageIO.write(bImage, "png", savedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            file.getItems().add(save);
            menuBar.getMenus().addAll(file);
            vBox.getChildren().addAll(menuBar, secondaryLayout);

            secondaryLayout.getChildren().addAll(new ImageView(image));
            stage.setScene(new Scene(vBox, image.getWidth(), image.getHeight()));
            stage.show();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
