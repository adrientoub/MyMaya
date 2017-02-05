package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import model.HistoryModel;
import view.AttributesView;

/**
 * Created by Adrien on 27/10/2016.
 */
public class MouseController implements EventHandler<MouseEvent> {
    private double x = -1;
    private double y = -1;
    private model.Camera camera;
    private SubScene subScene;

    private double originalX;
    private double originalY;
    private double originalZ;
    private double originalRotateX;
    private double originalRotateY;

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
            if (camera.getRotateX() != originalRotateX || camera.getRotateY() != originalRotateY) {
                HistoryModel.addRotation(camera.getRotateX() - originalRotateX,
                        camera.getRotateY() - originalRotateY, camera.getName());
            }
            Node node = camera.getInnerObject();
            if (node.getTranslateX() != originalX || node.getTranslateY() != originalY ||
                    node.getTranslateZ() != originalZ) {
                HistoryModel.addTranslation(node.getTranslateX() - originalX, node.getTranslateY() - originalY,
                        node.getTranslateZ() - originalZ, camera.getName());
            }
            return;
        } else if ((x == -1 && y == -1)) {
            x = event.getSceneX();
            y = event.getSceneY();
            originalX = camera.getInnerObject().getTranslateX();
            originalY = camera.getInnerObject().getTranslateY();
            originalZ = camera.getInnerObject().getTranslateZ();
            originalRotateX = camera.getRotateX();
            originalRotateY = camera.getRotateY();
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
