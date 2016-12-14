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
    private static List<Shape> shapes = new ArrayList<>();
    private static List<Light> lights = new ArrayList<>();
    private static model.Camera camera;

    private static Color getRandomColor() {
        return new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1);
    }

    public static void addAmbientLight(Color color) {
        if (color == null)
            color = getRandomColor();

        lights.add(new AmbientLight(color));
    }

    public static void addDirectionalLight(Color color, Point3D direction) {
        Cylinder cylinder = new Cylinder(0.25, 0.5);
        cylinder.setTranslateX(direction.getX());
        cylinder.setTranslateY(direction.getY());
        cylinder.setTranslateZ(direction.getZ());
        if (color == null)
            color = getRandomColor();

        cylinder.setDrawMode(DrawMode.LINE);
        cylinder.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(cylinder, true));
        lights.add(new model.DirectionalLight(color, cylinder));
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
        pointLight.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(pointLight, true));
        lights.add(new model.PointLight(color, pointLight));
        scene.getChildren().addAll(pointLight);
    }

    public static Sphere addSphere(double radius, Color color) {
        Sphere sphere = new Sphere(radius);
        if (color == null)
            color = getRandomColor();
        sphere.setMaterial(new PhongMaterial(color));
        sphere.setDrawMode(DrawMode.FILL);
        sphere.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(sphere, false));
        shapes.add(new model.Sphere(sphere, color));
        scene.getChildren().addAll(sphere);
        return sphere;
    }

    public static Box addBox(Color color) {
        Box box = new Box(1, 1, 1);
        if (color == null)
            color = getRandomColor();
        box.setMaterial(new PhongMaterial(color));
        box.setDrawMode(DrawMode.FILL);
        box.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(box, false));
        scene.getChildren().addAll(box);
        return box;
    }

    public static Group getScene() {
        return scene;
    }

    public static List<Shape> getShapes() {
        return shapes;
    }

    public static List<Light> getLights() {
        return lights;
    }

    public static model.Camera getCamera() {
        return camera;
    }

    public static void setCamera(model.Camera camera) {
        SceneModel.camera = camera;
    }
}
