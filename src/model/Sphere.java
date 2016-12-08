package model;

import javafx.scene.paint.Color;

/**
 * Created by Adrien on 08/12/2016.
 */
public class Sphere extends Shape {
    private javafx.scene.shape.Sphere internalSphere;
    private Color color;

    public Sphere(javafx.scene.shape.Sphere internalSphere, Color color) {
        this.internalSphere = internalSphere;
        this.color = color;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("sphere ").append(internalSphere.getRadius()).append(" ")
                .append(Shape.getPositionString(internalSphere)).append(" ").append(attributes.toString()).append(" ")
                .append(getColorString(color)).toString();
    }
}
