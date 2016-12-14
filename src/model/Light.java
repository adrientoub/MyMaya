package model;

import javafx.scene.paint.Color;

/**
 * Created by Adrien on 14/12/2016.
 */
public class Light extends Object3D {
    protected Color color;

    public Light(Color color) {
        this.color = color;
    }
}
