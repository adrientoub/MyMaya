package model;

import javafx.scene.paint.Color;

/**
 * Created by Adrien on 08/12/2016.
 */
public abstract class Shape extends Object3D {
    protected Attributes attributes = new Attributes();

    public Shape(String name, Color color) {
        super(name, color);
    }
}
