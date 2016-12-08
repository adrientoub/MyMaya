package model;

import javafx.scene.transform.Rotate;

/**
 * Created by Adrien on 08/12/2016.
 */
public class Camera {
    private javafx.scene.Camera camera;
    private Rotate rotateX;
    private Rotate rotateY;

    public Camera(javafx.scene.Camera camera) {
        this.camera = camera;
        rotateX = new Rotate(0,  Rotate.X_AXIS);
        rotateY = new Rotate(0,  Rotate.Y_AXIS);
        camera.getTransforms().addAll(rotateX, rotateY);
    }

    public void rotateX(double angle) {
        rotateX.setAngle(rotateX.getAngle() + angle);
    }

    public void rotateY(double angle) {
        rotateY.setAngle(rotateY.getAngle() + angle);
    }

    public void translateX(double movement) {
        camera.setTranslateX(camera.getTranslateX() + movement);
    }

    public void translateY(double movement) {
        camera.setTranslateY(camera.getTranslateY() + movement);
    }

    public void translateZ(double movement) {
        camera.setTranslateZ(camera.getTranslateZ() + movement);
    }

    @Override
    public String toString() {
        // TODO: Find u and v vectors
        return "camera " + camera.getTranslateX() + " " + camera.getTranslateY() + " " + camera.getTranslateZ() + " 1 0 0 0 -1 0";
    }
}
