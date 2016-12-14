package model;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * Created by Adrien on 14/12/2016.
 */
public class Object3D {
    protected static String getPositionString(Node node) {
        return node.getTranslateX() + " " + node.getTranslateY() + " " + node.getTranslateZ();
    }

    protected String getColorString(Color color) {
        return (int)(color.getRed() * 255) + " " + (int)(color.getGreen() * 255) + " " + (int)(color.getBlue() * 255);
    }
}
