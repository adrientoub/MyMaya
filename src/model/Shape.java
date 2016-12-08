package model;

/**
 * Created by Adrien on 08/12/2016.
 */
public class Shape {
    protected String getPositionString(javafx.scene.shape.Shape3D shape) {
        return shape.getTranslateX() + " " + shape.getTranslateY() + " " + shape.getTranslateZ();
    }
}
