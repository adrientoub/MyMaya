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

    public static void scaleBy(String name, double scale) {
        Object3D object3D = findObject(name);
        if (object3D != null) {
            object3D.setScale(object3D.getScale() * scale);
            HistoryModel.addScale(scale, name);
        }
    }

    public static void moveBy(String name, double x, double y, double z) {
        Object3D object3D = findObject(name);
        if (object3D != null) {
            object3D.moveBy(x, y, z);
        }
    }

    public static void rename(String from, String to) {
        Object3D object3D = findObject(from);
        if (object3D != null) {
            object3D.setName(to);
        }
    }

    private static Object3D findObject(String objectName) {
        Object3D object3D = null;
        for (Object3D obj: object3DS) {
            if (obj.getName().equals(objectName)) {
                object3D = obj;
                break;
            }
        }
        if (object3D == null) {
            System.err.println("No object with name " + objectName + " found in scene.");
            return null;
        }
        return object3D;
    }

    public static void remove(String objectName) {
        Object3D object3D = findObject(objectName);
        if (object3D != null) {
            remove(object3D);
        }
    }

    public static void remove(Object3D object3D) {
        object3DS.remove(object3D);
        scene.getChildren().remove(object3D.getInnerObject());
        HistoryModel.addRemove(object3D);
    }

    public static void addAmbientLight(Color color) {
        if (color == null)
            color = getRandomColor();

        ambientLight = new AmbientLight(color);
    }

    public static DirectionalLight addDirectionalLight(Color color, Point3D direction, String name) {
        Cylinder cylinder = new Cylinder(0.25, 0.5);
        cylinder.setTranslateX(direction.getX());
        cylinder.setTranslateY(direction.getY());
        cylinder.setTranslateZ(direction.getZ());
        if (color == null)
            color = getRandomColor();

        cylinder.setMaterial(new PhongMaterial(color));
        cylinder.setDrawMode(DrawMode.LINE);
        DirectionalLight dl = new model.DirectionalLight(color, cylinder, name);
        cylinder.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(dl));
        object3DS.add(dl);
        scene.getChildren().addAll(cylinder);
        HistoryModel.addNewDirectionalLight(dl);
        return dl;
    }

    public static PointLight addPointLight(Color color, Point3D position, String name) {
        Sphere pointLight = new Sphere(0.5);
        pointLight.setTranslateX(position.getX());
        pointLight.setTranslateY(position.getY());
        pointLight.setTranslateZ(position.getZ());
        if (color == null)
            color = getRandomColor();

        pointLight.setMaterial(new PhongMaterial(color));
        pointLight.setDrawMode(DrawMode.LINE);
        PointLight pl = new PointLight(color, pointLight, name);
        pointLight.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(pl));
        object3DS.add(pl);
        scene.getChildren().addAll(pointLight);
        HistoryModel.addNewPointLight(pl);
        return pl;
    }

    public static model.Sphere addSphere(double radius, Color color, Point3D pos, String name) {
        Sphere sphere = new Sphere(radius);
        if (color == null)
            color = getRandomColor();
        sphere.setMaterial(new PhongMaterial(color));
        sphere.setDrawMode(DrawMode.FILL);
        model.Sphere modelSphere = new model.Sphere(sphere, color, name);
        sphere.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(modelSphere));
        object3DS.add(modelSphere);
        scene.getChildren().addAll(sphere);
        if (pos != null) {
            sphere.setTranslateX(pos.getX());
            sphere.setTranslateY(pos.getY());
            sphere.setTranslateZ(pos.getZ());
        }

        HistoryModel.addNewSphere(modelSphere);
        return modelSphere;
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
