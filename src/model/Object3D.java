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

    public Color getColor() {
        return color;
    }

    public void setName(String name) {
        if (!this.name.equals(name)) {
            HistoryModel.addRename(this.name, name);
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    public void moveBy(double translateX, double translateY, double translateZ) {
        getInnerObject().setTranslateX(getInnerObject().getTranslateX() + translateX);
        getInnerObject().setTranslateY(getInnerObject().getTranslateY() + translateY);
        getInnerObject().setTranslateZ(getInnerObject().getTranslateZ() + translateZ);
        HistoryModel.addTranslation(translateX, translateY, translateZ, name);
    }

    public void setTranslateX(Double translateX) {
        double x = getInnerObject().getTranslateX();
        if (translateX - x != 0) {
            getInnerObject().setTranslateX(translateX);
            HistoryModel.addTranslation(translateX - x, 0, 0, name);
        }
    }

    public void setTranslateY(Double translateY) {
        double y = getInnerObject().getTranslateY();
        if (translateY - y != 0) {
            getInnerObject().setTranslateY(translateY);
            HistoryModel.addTranslation(0, translateY - y, 0, name);
        }
    }

    public void setTranslateZ(Double translateZ) {
        double z = getInnerObject().getTranslateZ();
        if (translateZ - z != 0) {
            getInnerObject().setTranslateZ(translateZ);
            HistoryModel.addTranslation(0, 0, translateZ - z, name);
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Attributes getAttributes() {
        return null;
    }
}
