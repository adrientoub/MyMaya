package model;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * Created by Adrien on 14/12/2016.
 */
public class PointLight extends Light {
    Node position;

    public PointLight(Color color, Node position) {
        super(color);
        this.position = position;
    }

    @Override
    public String toString() {
        return "plight " + getPositionString(position) + " " + getColorString(color);
    }
}
