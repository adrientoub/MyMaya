package model;

import javafx.scene.paint.Color;

/**
 * Created by Adrien on 08/12/2016.
 */
public class Shape {
    protected Attributes attributes = new Attributes();

    public static String getPositionString(javafx.scene.Node node) {
        return node.getTranslateX() + " " + node.getTranslateY() + " " + node.getTranslateZ();
    }

    protected String getColorString(Color color) {
        return (int)(color.getRed() * 255) + " " + (int)(color.getGreen() * 255) + " " + (int)(color.getBlue() * 255);
    }
}
