package view;

import controller.MouseController;
import controller.ScrollController;
import controller.SelectController;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Camera;
import model.SceneModel;

/**
 * Created by Adrien on 05/02/2017.
 */
public class SceneView extends Group {
    private void addLights() {
        SceneModel.addAmbientLight(Color.WHITE);
        SceneModel.addDirectionalLight(Color.WHITE, new Point3D(0, 1, 1), null);
        SceneModel.addDirectionalLight(Color.WHITE, new Point3D(0, 1, -1), null);
        SceneModel.addDirectionalLight(Color.WHITE, new Point3D(0, -1, 1), null);
    }

    public SceneView(Pane pane) {
        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        model.Camera myCamera = new Camera(camera);

        myCamera.setTranslateX(0);
        myCamera.setTranslateY(0);
        myCamera.setTranslateZ(-15);

        // Build the Scene Graph
        Group scene = SceneModel.getScene();
        scene.getChildren().add(camera);

        // Use a SubScene
        SubScene subScene = new SubScene(scene, 1024, 768);
        subScene.setManaged(false);
        SelectController.initializeSelectController(scene);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);
        subScene.setOnMouseClicked(event -> subScene.requestFocus());

        SceneModel.setCamera(myCamera);

        MouseController mc = new MouseController(myCamera, subScene);
        subScene.addEventFilter(MouseEvent.MOUSE_DRAGGED, mc);
        subScene.addEventFilter(MouseEvent.MOUSE_RELEASED, mc);
        subScene.addEventFilter(ScrollEvent.SCROLL, new ScrollController(myCamera));

        this.getChildren().add(subScene);
        this.requestFocus();
        addLights();

        subScene.heightProperty().bind(pane.heightProperty());
        subScene.widthProperty().bind(pane.widthProperty());

    }
}
