import controller.MouseController;
import controller.ScrollController;
import controller.SelectController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 * Created by Adrien on 20/10/2016.
 */
public class Main extends Application {
    public Parent createContent() throws Exception {
        Sphere sphere = new Sphere(3);
        sphere.setMaterial(new PhongMaterial(Color.RED));
        sphere.setDrawMode(DrawMode.FILL);
        SelectController sc = new SelectController();
        sphere.addEventFilter(MouseEvent.MOUSE_CLICKED, sc.newSelection(sphere));

        Sphere secondSphere = new Sphere(3);
        secondSphere.setMaterial(new PhongMaterial(Color.BLUE));
        secondSphere.setDrawMode(DrawMode.FILL);
        secondSphere.getTransforms().addAll(new Translate(5, 0));
        secondSphere.addEventFilter(MouseEvent.MOUSE_CLICKED, sc.newSelection(secondSphere));

        Sphere thirdSphere = new Sphere(2);
        thirdSphere.setMaterial(new PhongMaterial(Color.GREEN));
        thirdSphere.setDrawMode(DrawMode.FILL);
        thirdSphere.getTransforms().addAll(new Translate(0, -1, 0));
        thirdSphere.addEventFilter(MouseEvent.MOUSE_CLICKED, sc.newSelection(thirdSphere));

        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(
                new Rotate(-20, Rotate.Y_AXIS),
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -15));

        // Build the Scene Graph
        Group root = new Group();
        root.getChildren().add(camera);
        root.getChildren().addAll(sphere, secondSphere, thirdSphere);

        // Use a SubScene
        SubScene subScene = new SubScene(root, 1024, 768);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);

        MouseController mc = new MouseController(camera, subScene);
        subScene.addEventFilter(MouseEvent.MOUSE_DRAGGED, mc);
        subScene.addEventFilter(MouseEvent.MOUSE_RELEASED, mc);
        subScene.addEventFilter(ScrollEvent.SCROLL, new ScrollController(camera));

        Group group = new Group();
        group.getChildren().add(subScene);
        return group;
    }

    public void addMenu(VBox root) {
        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        root.getChildren().addAll(menuBar);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        VBox root = new VBox();
        Scene scene = new Scene(root);
        addMenu(root);
        root.getChildren().add(createContent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
