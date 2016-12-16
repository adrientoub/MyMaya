package model;

import controller.SelectController;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Adrien on 18/11/2016.
 */
public class SceneModel {
    private static Group scene = new Group();
    private final static Random random = new Random();
    private static List<Object3D> object3DS = new ArrayList<>();
    private static AmbientLight ambientLight;
    private static model.Camera camera;

    private static Color getRandomColor() {
        return new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1);
    }

    public static void addAmbientLight(Color color) {
        if (color == null)
            color = getRandomColor();

        ambientLight = new AmbientLight(color);
    }

    public static void addDirectionalLight(Color color, Point3D direction) {
        Cylinder cylinder = new Cylinder(0.25, 0.5);
        cylinder.setTranslateX(direction.getX());
        cylinder.setTranslateY(direction.getY());
        cylinder.setTranslateZ(direction.getZ());
        if (color == null)
            color = getRandomColor();

        cylinder.setDrawMode(DrawMode.LINE);
        DirectionalLight dl = new model.DirectionalLight(color, cylinder);
        cylinder.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(dl, true));
        object3DS.add(dl);
        scene.getChildren().addAll(cylinder);
    }

    public static void addPointLight(Color color, Point3D position) {
        Sphere pointLight = new Sphere(0.5);
        pointLight.setTranslateX(position.getX());
        pointLight.setTranslateY(position.getY());
        pointLight.setTranslateZ(position.getZ());
        if (color == null)
            color = getRandomColor();

        pointLight.setDrawMode(DrawMode.LINE);
        model.PointLight pl = new model.PointLight(color, pointLight);
        pointLight.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(pl, true));
        object3DS.add(pl);
        scene.getChildren().addAll(pointLight);
    }

    public static Sphere addSphere(double radius, Color color) {
        Sphere sphere = new Sphere(radius);
        if (color == null)
            color = getRandomColor();
        sphere.setMaterial(new PhongMaterial(color));
        sphere.setDrawMode(DrawMode.FILL);
        model.Sphere modelSphere = new model.Sphere(sphere, color);
        sphere.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(modelSphere, false));
        object3DS.add(modelSphere);
        scene.getChildren().addAll(sphere);
        return sphere;
    }

    public static Box addBox(Color color) {
        Box box = new Box(1, 1, 1);
        if (color == null)
            color = getRandomColor();
        box.setMaterial(new PhongMaterial(color));
        box.setDrawMode(DrawMode.FILL);
/*        box.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(box, false));*/
        scene.getChildren().addAll(box);
        return box;
    }

    public static Group getScene() {
        return scene;
    }

    public static List<Object3D> getObject3DS() {
        return object3DS;
    }

    public static AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public static model.Camera getCamera() {
        return camera;
    }

    public static void setCamera(model.Camera camera) {
        SceneModel.camera = camera;
    }
}
