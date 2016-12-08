package model;

/**
 * Created by Adrien on 08/12/2016.
 */
public class Shape {
    public static String getPositionString(javafx.scene.Node node) {
        return node.getTranslateX() + " " + node.getTranslateY() + " " + node.getTranslateZ();
    }
}
