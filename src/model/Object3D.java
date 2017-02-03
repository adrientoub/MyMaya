package model;

import controller.SelectController;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import view.AttributesView;

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

    public void setScale(double scale) {
        if (SelectController.getSelectController().getSelected() == this) {
            AttributesView.getInstance().getScaleTextField().setText(Double.toString(scale));
        }
    }

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

    public boolean isScalable() {
        return true;
    }

    public boolean canRotate() {
        return false;
    }

    public boolean canChangeColor() {
        return true;
    }

    public String getName() {
        return name;
    }

    public void moveBy(double translateX, double translateY, double translateZ) {
        translateBy(translateX, translateY, translateZ);
        HistoryModel.addTranslation(translateX, translateY, translateZ, name);
    }

    public void translateBy(double translateX, double translateY, double translateZ) {
        double x = getInnerObject().getTranslateX();
        double y = getInnerObject().getTranslateY();
        double z = getInnerObject().getTranslateZ();
        if (translateX != 0 || translateY != 0 || translateZ != 0) {
            if (SelectController.getSelectController().getSelected() == this) {
                AttributesView.getInstance().getxTextField().setText(Double.toString(x + translateX));
                AttributesView.getInstance().getyTextField().setText(Double.toString(y + translateY));
                AttributesView.getInstance().getzTextField().setText(Double.toString(z + translateZ));
            }
            getInnerObject().setTranslateX(x + translateX);
            getInnerObject().setTranslateY(y + translateY);
            getInnerObject().setTranslateZ(z + translateZ);
        }
    }

    public void setTranslateX(double translateX) {
        double x = getInnerObject().getTranslateX();
        if (translateX - x != 0) {
            getInnerObject().setTranslateX(translateX);
            HistoryModel.addTranslation(translateX - x, 0, 0, name);
        }
    }

    public void setTranslateY(double translateY) {
        double y = getInnerObject().getTranslateY();
        if (translateY - y != 0) {
            getInnerObject().setTranslateY(translateY);
            HistoryModel.addTranslation(0, translateY - y, 0, name);
        }
    }

    public void setTranslateZ(double translateZ) {
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
