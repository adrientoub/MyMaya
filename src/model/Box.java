package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;

/**
 * Created by Adrien on 08/12/2016.
 */
public class Box extends Shape {
    private javafx.scene.shape.Box internalBox;
    private static int boxCount = 0;

    public Box(javafx.scene.shape.Box internalBox, Color color, String name) {
        this(internalBox, color);
        if (name != null) {
            boxCount--;
            this.name = name;
        }
    }

    public Box(javafx.scene.shape.Box internalBox, Color color) {
        super("box" + boxCount, color);
        boxCount++;
        this.internalBox = internalBox;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("box \"").append(name).append("\" ").append(getScale())
                .append(" ").append(Shape.getPositionString(internalBox)).append(" ").append(attributes.toString())
                .append(" ").append(getColorString(color)).toString();
    }

    @Override
    public Node getInnerObject() {
        return internalBox;
    }

    @Override
    public double getScale() {
        return internalBox.getWidth();
    }

    @Override
    public void setScale(double scale) {
        super.setScale(scale);
        internalBox.setWidth(scale);
        internalBox.setHeight(scale);
        internalBox.setDepth(scale);
    }

    @Override
    public void setDrawMode(DrawMode drawMode) {
        internalBox.setDrawMode(drawMode);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        PhongMaterial m = (PhongMaterial) internalBox.getMaterial();
        m.setDiffuseColor(color);
    }
}
