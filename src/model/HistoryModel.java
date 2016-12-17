package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import view.AttributesView;

/**
 * Created by Adrien on 15/12/2016.
 */
public class HistoryModel {
    public static void addTranslation(double x, double y, double z, String name) {
        AttributesView.getInstance().addLine("translate \"" + name + "\" " + x + " " + y + " " + z);
    }

    public static void addScale(double scale, String name) {
        AttributesView.getInstance().addLine("scale \"" + name + "\" " + scale);
    }

    private static String nodeToString(Node node) {
        return node.getTranslateX() + " " + node.getTranslateY() + " " + node.getTranslateZ();
    }

    private static String colorToString(Color color) {
        return color.getRed() + " " + color.getGreen() + " " + color.getBlue();
    }

    public static void addNewSphere(Sphere sphere) {
        AttributesView.getInstance().addLine("sphere \"" + sphere.getName() + "\" "
                    + nodeToString(sphere.getInnerObject()) + " " + colorToString(sphere.getColor()));
    }

    public static void addNewDirectionalLight(DirectionalLight dl) {
        AttributesView.getInstance().addLine("directional_light \"" + dl.getName() + "\" "
                + nodeToString(dl.getInnerObject()) + " " + colorToString(dl.getColor()));
    }

    public static void addNewPointLight(PointLight pl) {
        AttributesView.getInstance().addLine("point_light \"" + pl.getName() + "\" "
                + nodeToString(pl.getInnerObject()) + " " + colorToString(pl.getColor()));
    }

    public static void addRemove(Object3D object3D) {
        AttributesView.getInstance().addLine("remove \"" + object3D.getName() + "\"");
    }
}
