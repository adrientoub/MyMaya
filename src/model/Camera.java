package model;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;

/**
 * Created by Adrien on 08/12/2016.
 */
public class Camera extends Object3D {
    private javafx.scene.Camera camera;
    private Rotate rotateX;
    private Rotate rotateY;

    public Camera(javafx.scene.Camera camera) {
        super("camera1", Color.WHITE);
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

    private Point3D rotate(Point3D point, Point3D axis, double angle) {
        double costheta = Math.cos(Math.toRadians(angle));
        double sintheta = Math.sin(Math.toRadians(angle));

        double r00 = costheta + Math.pow(axis.getX(), 2) * (1 - costheta);
        double r01 = axis.getX() * axis.getY() * (1 - costheta) - axis.getZ() * sintheta;
        double r02 = axis.getX() * axis.getZ() * (1 - costheta) + axis.getY() * sintheta;

        double r10 = axis.getX() * axis.getY() * (1 - costheta) + axis.getZ() * sintheta;
        double r11 = costheta + Math.pow(axis.getY(), 2) * (1 - costheta);
        double r12 = axis.getY() * axis.getZ() * (1 - costheta) - axis.getX() * sintheta;

        double r20 = axis.getX() * axis.getZ() * (1 - costheta) - axis.getY() * sintheta;
        double r21 = axis.getY() * axis.getZ() * (1 - costheta) + axis.getX() * sintheta;
        double r22 = costheta + Math.pow(axis.getZ(), 2) * (1 - costheta);

        return new Point3D(r00 * point.getX() + r01 * point.getY() + r02 * point.getZ(),
                r10 * point.getX() + r11 * point.getY() + r12 * point.getZ(),
                r20 * point.getX() + r21 * point.getY() + r22 * point.getZ());
    }

    @Override
    public String toString() {
        Point3D u = new Point3D(1, 0, 0);
        u = rotate(u, rotateX.getAxis(), rotateX.getAngle());
        u = rotate(u, rotateY.getAxis(), rotateY.getAngle());

        Point3D v = new Point3D(0, 1, 0);
        v = rotate(v, rotateX.getAxis(), rotateX.getAngle());
        v = rotate(v, rotateY.getAxis(), rotateY.getAngle());

        return "camera " + Shape.getPositionString(camera) + " " + u.getX() + " " + u.getY() + " " + u.getZ() + " " + v.getX() + " " + v.getY() + " " + v.getZ();
    }

    @Override
    public Node getInnerObject() {
        return camera;
    }

    @Override
    public double getScale() {
        return 0;
    }

    @Override
    public void setScale(double scale) {}

    @Override
    public void setDrawMode(DrawMode drawMode) {}
}
