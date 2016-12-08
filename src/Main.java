import controller.MouseController;
import controller.ScrollController;
import controller.SelectController;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import model.Camera;
import model.SceneModel;
import view.MenuView;

/**
 * Created by Adrien on 20/10/2016.
 */
public class Main extends Application {
    public void addSpheres() {
        SceneModel.addSphere(3, Color.RED);

        Sphere secondSphere = SceneModel.addSphere(3, Color.BLUE);
        secondSphere.setTranslateX(5);

        Sphere thirdSphere = SceneModel.addSphere(2, Color.GREEN);
        thirdSphere.setTranslateY(-1);
    }

    public Parent createContent() throws Exception {
        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        model.Camera myCamera = new Camera(camera);
        myCamera.rotateX(-20);
        myCamera.rotateY(-20);

        myCamera.translateX(0);
        myCamera.translateY(0);
        myCamera.translateZ(-15);

        // Build the Scene Graph
        Group scene = SceneModel.getScene();
        scene.getChildren().add(camera);

        // Use a SubScene
        SubScene subScene = new SubScene(scene, 1024, 768);
        SelectController.initializeSelectController(scene, subScene);
        addSpheres();
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);


        SceneModel.setCamera(myCamera);

        MouseController mc = new MouseController(myCamera, subScene);
        subScene.addEventFilter(MouseEvent.MOUSE_DRAGGED, mc);
        subScene.addEventFilter(MouseEvent.MOUSE_RELEASED, mc);
        subScene.addEventFilter(ScrollEvent.SCROLL, new ScrollController(myCamera));

        Group group = new Group();
        group.getChildren().add(subScene);
        return group;
    }

    public void addMenu(VBox root) {
        MenuBar menuBar = new MenuView(SceneModel.getScene());

        root.getChildren().addAll(menuBar);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        VBox root = new VBox();
        Scene scene = new Scene(root);
        addMenu(root);
        root.getChildren().add(createContent());
        scene.setOnKeyPressed(SelectController.getSelectController().getTools());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
