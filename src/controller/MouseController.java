package controller;

import javafx.event.EventHandler;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import view.AttributesView;

/**
 * Created by Adrien on 27/10/2016.
 */
public class MouseController implements EventHandler<MouseEvent> {
    private double x = -1;
    private double y = -1;
    private model.Camera camera;
    private SubScene subScene;

    private final double angleScale = 90;
    private final double translateScale = 5;

    public MouseController(model.Camera camera, SubScene subScene) {
        this.camera = camera;
        this.subScene = subScene;
    }

    private void updateAttributes() {
        if (SelectController.getSelectController().getSelected() == camera) {
            AttributesView.getInstance().getRotateXTextField().setText(Double.toString(camera.getRotateX()));
            AttributesView.getInstance().getRotateYTextField().setText(Double.toString(camera.getRotateY()));
        }
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
            camera.rotateX(angleScale * (y - event.getSceneY()) / subScene.getHeight());
            camera.rotateY(-angleScale * (x - event.getSceneX()) / subScene.getWidth());
            updateAttributes();
        } else if (event.isAltDown() && event.isMiddleButtonDown()) {
            camera.translateX(translateScale * (x - event.getSceneX()) / subScene.getWidth());
            camera.translateY(translateScale * (y - event.getSceneY()) / subScene.getHeight());
        }
        x = event.getSceneX();
        y = event.getSceneY();
    }
}
