package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;

/**
 * Created by Adrien on 14/12/2016.
 */
public class PointLight extends Light {
    private Shape3D position;
    private static int pointLightCount = 0;

    public PointLight(Color color, Shape3D position, String name) {
        this(color, position);
        if (name != null) {
            this.name = name;
            pointLightCount--;
        }
    }

    public PointLight(Color color, Shape3D position) {
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

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        PhongMaterial m = (PhongMaterial) position.getMaterial();
        m.setDiffuseColor(color);
    }
}
