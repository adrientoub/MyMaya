package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;

/**
 * Created by Adrien on 14/12/2016.
 */
public class DirectionalLight extends Light {
    private Shape3D direction;
    private static int directionalLightCount = 0;

    public DirectionalLight(Color color, Shape3D direction) {
        super("directional_light" + directionalLightCount, color);
        directionalLightCount++;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("dlight \"").append(name).append("\" ")
                .append(getPositionString(direction)).append(" ").append(getColorString(color)).toString();
    }

    @Override
    public Node getInnerObject() {
        return direction;
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        PhongMaterial m = (PhongMaterial) direction.getMaterial();
        m.setDiffuseColor(color);
    }
}
