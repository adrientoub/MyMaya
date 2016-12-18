package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import view.AttributesView;

/**
 * Created by Adrien on 15/12/2016.
 */
public class HistoryModel {
    private static void addLine(String line) {
        AttributesView.getInstance().addLine(line);
    }

    public static void addTranslation(double x, double y, double z, String name) {
        addLine("translate \"" + name + "\" " + x + " " + y + " " + z);
    }

    public static void addScale(double scale, String name) {
        addLine("scale \"" + name + "\" " + scale);
    }

    private static String nodeToString(Node node) {
        return node.getTranslateX() + " " + node.getTranslateY() + " " + node.getTranslateZ();
    }

    private static String colorToString(Color color) {
        return color.getRed() + " " + color.getGreen() + " " + color.getBlue();
    }

    public static void addNewSphere(Sphere sphere) {
        addLine("sphere \"" + sphere.getName() + "\" " + nodeToString(sphere.getInnerObject()) + " "
                + colorToString(sphere.getColor()));
    }

    public static void addNewDirectionalLight(DirectionalLight dl) {
        addLine("directional_light \"" + dl.getName() + "\" " + nodeToString(dl.getInnerObject()) + " "
                + colorToString(dl.getColor()));
    }

    public static void addNewPointLight(PointLight pl) {
        addLine("point_light \"" + pl.getName() + "\" " + nodeToString(pl.getInnerObject()) + " "
                + colorToString(pl.getColor()));
    }

    public static void addRemove(Object3D object3D) {
        addLine("remove \"" + object3D.getName() + "\"");
    }

    public static void addSave(String filename) {
        addLine("save \"" + filename + "\"");
    }

    public static void addRename(String name, String new_name) {
        addLine("rename \"" + name + "\" \"" + new_name + "\"");
    }
}
