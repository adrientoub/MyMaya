package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;

/**
 * Created by Adrien on 08/12/2016.
 */
public class Sphere extends Shape {
    private javafx.scene.shape.Sphere internalSphere;
    private static int sphereCount = 0;

    public Sphere(javafx.scene.shape.Sphere internalSphere, Color color) {
        super("sphere" + sphereCount, color);
        sphereCount++;
        this.internalSphere = internalSphere;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("sphere \"").append(name).append("\" ").append(internalSphere.getRadius())
                .append(" ").append(Shape.getPositionString(internalSphere)).append(" ").append(attributes.toString())
                .append(" ").append(getColorString(color)).toString();
    }

    @Override
    public Node getInnerObject() {
        return internalSphere;
    }

    @Override
    public double getScale() {
        return internalSphere.getRadius();
    }

    @Override
    public void setScale(double radius) {
        internalSphere.setRadius(radius);
    }

    @Override
    public void setDrawMode(DrawMode drawMode) {
        internalSphere.setDrawMode(drawMode);
    }
}
