package controller;

import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Created by Adrien on 27/10/2016.
 */
public class MouseController implements EventHandler<MouseEvent> {
    private double x = -1;
    private double y = -1;
    private Camera camera;
    private SubScene subScene;

    private final double angleScale = 90;
    private final double translateScale = 5;

    public MouseController(Camera camera, SubScene subScene) {
        this.camera = camera;
        this.subScene = subScene;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            x = -1;
            y = -1;
            return;
        } else if ((x == -1 && y == -1)) {
            x = event.getSceneX();
            y = event.getSceneY();
            return;
        }
        if (event.isAltDown() && event.isPrimaryButtonDown()) {
            camera.getTransforms().addAll(
                    new Rotate(angleScale * (x - event.getSceneX()) / subScene.getWidth(),  Rotate.X_AXIS),
                    new Rotate(angleScale * (y - event.getSceneY()) / subScene.getHeight(), Rotate.Y_AXIS));
        } else if (event.isAltDown() && event.isMiddleButtonDown()) {
            camera.getTransforms().addAll(new Translate(translateScale * (x - event.getSceneX()) / subScene.getWidth(),
                                                        translateScale * (y - event.getSceneY()) / subScene.getHeight()));
        }
        x = event.getSceneX();
        y = event.getSceneY();
    }
}
