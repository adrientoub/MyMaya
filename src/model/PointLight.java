package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * Created by Adrien on 14/12/2016.
 */
public class PointLight extends Light {
    private Node position;
    private static int pointLightCount = 0;

    public PointLight(Color color, Node position) {
        super("point_light" + pointLightCount, color);
        pointLightCount++;
        this.position = position;
    }

    @Override
    public String toString() {
        return "plight \"" + name + "\" " + getPositionString(position) + " " + getColorString(color);
    }

    @Override
    public Node getInnerObject() {
        return position;
    }
}
