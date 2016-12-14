package model;

import javafx.scene.paint.Color;

/**
 * Created by Adrien on 14/12/2016.
 */
public class AmbientLight extends Light {
    public AmbientLight(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return "alight " + getColorString(color);
    }
}
