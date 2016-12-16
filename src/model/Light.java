package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;

/**
 * Created by Adrien on 14/12/2016.
 */

public abstract class Light extends Object3D {
    public Light(String name, Color color) {
        super(name, color);
    }

    @Override
    public double getScale() {
        return 1;
    }

    @Override
    public void setScale(double scale) {}

    @Override
    public void setDrawMode(DrawMode drawMode) {}
}
