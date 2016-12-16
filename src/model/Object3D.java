package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;

/**
 * Created by Adrien on 14/12/2016.
 */
public abstract class Object3D {
    protected String name;
    protected Color color;

    public Object3D(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    protected static String getPositionString(Node node) {
        return node.getTranslateX() + " " + node.getTranslateY() + " " + node.getTranslateZ();
    }

    static String getColorString(Color color) {
        return (int)(color.getRed() * 255) + " " + (int)(color.getGreen() * 255) + " " + (int)(color.getBlue() * 255);
    }

    public abstract Node getInnerObject();
    public abstract double getScale();
    public abstract void setScale(double scale);
    public abstract void setDrawMode(DrawMode drawMode);

    public String getName() {
        return name;
    }
}
