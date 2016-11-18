package model;

import controller.SelectController;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;

/**
 * Created by Adrien on 18/11/2016.
 */
public class SceneModel {
    private static Group scene = new Group();

    public static Sphere addSphere(double radius, Color color) {
        Sphere sphere = new Sphere(radius);
        sphere.setMaterial(new PhongMaterial(color));
        sphere.setDrawMode(DrawMode.FILL);
        sphere.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(sphere));
        scene.getChildren().addAll(sphere);
        return sphere;
    }

    public static Box addBox(Color color) {
        Box box = new Box(1, 1, 1);
        box.setMaterial(new PhongMaterial(color));
        box.setDrawMode(DrawMode.FILL);
        box.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(box));
        scene.getChildren().addAll(box);
        return box;
    }

    public static Group getScene() {
        return scene;
    }
}
