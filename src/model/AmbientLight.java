package model;

import javafx.scene.paint.Color;

/**
 * Created by Adrien on 14/12/2016.
 */
public class AmbientLight {
    private Color color;

    public AmbientLight(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "alight " + Object3D.getColorString(color);
    }
}
