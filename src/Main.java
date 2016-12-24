import controller.MouseController;
import controller.ScrollController;
import controller.SelectController;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Camera;
import model.SceneModel;
import model.Sphere;
import view.AttributesView;
import view.MenuView;

/**
 * Created by Adrien on 20/10/2016.
 */
public class Main extends Application {
    private void addSpheres() {
        SceneModel.addSphere(3, Color.RED, null, null);
        SceneModel.addSphere(3, Color.BLUE, new Point3D(5, 0, 0), null);
        SceneModel.addSphere(2, Color.GREEN, new Point3D(0, -1, 0), null);
    }

    private void addLights() {
        SceneModel.addAmbientLight(Color.WHITE);
        SceneModel.addDirectionalLight(Color.WHITE, new Point3D(0, 1, 1), null);
        SceneModel.addDirectionalLight(Color.WHITE, new Point3D(0, 1, -1), null);
        SceneModel.addDirectionalLight(Color.WHITE, new Point3D(0, -1, 1), null);
    }

    public Parent createContent(Pane pane) throws Exception {
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
        subScene.setManaged(false);
        SelectController.initializeSelectController(scene);
        addSpheres();
        addLights();
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);
        subScene.setOnMouseClicked(event -> subScene.requestFocus());

        SceneModel.setCamera(myCamera);

        MouseController mc = new MouseController(myCamera, subScene);
        subScene.addEventFilter(MouseEvent.MOUSE_DRAGGED, mc);
        subScene.addEventFilter(MouseEvent.MOUSE_RELEASED, mc);
        subScene.addEventFilter(ScrollEvent.SCROLL, new ScrollController(myCamera));

        Group group = new Group();
        group.getChildren().add(subScene);
        group.requestFocus();

        subScene.heightProperty().bind(pane.heightProperty());
        subScene.widthProperty().bind(pane.widthProperty());
        return group;
    }

    public void addMenu(Pane root) {
        MenuBar menuBar = new MenuView(SceneModel.getScene());

        root.getChildren().addAll(menuBar);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(true);
        primaryStage.setWidth(1024);
        primaryStage.setHeight(768);
        primaryStage.setTitle("MyMaya");

        VBox rootVBox = new VBox();
        addMenu(rootVBox);

        SplitPane attributesEditingPane = AttributesView.getInstance().getPane();
        Pane stackPane3D = new StackPane();
        stackPane3D.prefHeightProperty().bind(primaryStage.heightProperty());
        Parent scene3D = createContent(stackPane3D);
        stackPane3D.getChildren().add(scene3D);

        SplitPane splitPane = new SplitPane(stackPane3D, attributesEditingPane);
        rootVBox.getChildren().add(splitPane);

        Scene scene = new Scene(rootVBox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
