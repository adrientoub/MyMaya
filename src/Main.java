import controller.MouseController;
import controller.ScrollController;
import controller.SelectController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Camera;
import model.SceneModel;
import view.MenuView;

/**
 * Created by Adrien on 20/10/2016.
 */
public class Main extends Application {
    private void addSpheres() {
        SceneModel.addSphere(3, Color.RED);

        Sphere secondSphere = SceneModel.addSphere(3, Color.BLUE);
        secondSphere.setTranslateX(5);

        Sphere thirdSphere = SceneModel.addSphere(2, Color.GREEN);
        thirdSphere.setTranslateY(-1);
    }

    private void addLights() {
        SceneModel.addAmbientLight(Color.WHITE);
        SceneModel.addDirectionalLight(Color.WHITE, new Point3D(0, 1, 1));
        SceneModel.addDirectionalLight(Color.WHITE, new Point3D(0, 1, -1));
        SceneModel.addDirectionalLight(Color.WHITE, new Point3D(0, -1, 1));
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
        SelectController.initializeSelectController(scene, subScene);
        addSpheres();
        addLights();
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);

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

    public Pane createAttributePane() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Text position = new Text("Position");
        grid.add(position, 0, 0);

        return grid;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(true);
        primaryStage.setWidth(1024);
        primaryStage.setHeight(768);
        primaryStage.setTitle("MyMaya");

        VBox rootVBox = new VBox();
        addMenu(rootVBox);

        Pane attributesEditingPane = createAttributePane();
        Pane stackPane3D = new StackPane();
        stackPane3D.prefHeightProperty().bind(primaryStage.heightProperty());
        Parent scene3D = createContent(stackPane3D);
        stackPane3D.getChildren().add(scene3D);

        SplitPane splitPane = new SplitPane(stackPane3D, attributesEditingPane);
        rootVBox.getChildren().add(splitPane);

        Scene scene = new Scene(rootVBox);
        scene.setOnKeyPressed(SelectController.getSelectController().getTools());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
