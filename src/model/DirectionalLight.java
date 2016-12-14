package model;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * Created by Adrien on 14/12/2016.
 */
public class DirectionalLight extends Light {
    Node direction;

    public DirectionalLight(Color color, Node direction) {
        super(color);
        this.direction = direction;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("dlight ").append(getPositionString(direction)).append(" ")
                .append(getColorString(color)).toString();
    }
}
