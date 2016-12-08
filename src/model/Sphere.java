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
                .append(getPositionString(internalSphere)).append(" 1 0.5 1 50 1 1 ")
                .append((int)(color.getRed() * 255)).append(" ").append((int)(color.getGreen() * 255)).append(" ")
                .append((int)(color.getBlue() * 255)).toString();
    }
}
