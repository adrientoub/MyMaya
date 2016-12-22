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

    public static void remove(String objectName) {
        Object3D object3D = null;
        for (Object3D obj: object3DS) {
            if (obj.getName().equals(objectName)) {
                object3D = obj;
                break;
            }
        }
        if (object3D == null) {
            System.err.println("No object with name " + objectName + " found in scene.");
            return;
        }
        remove(object3D);
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

    public static DirectionalLight addDirectionalLight(Color color, Point3D direction) {
        Cylinder cylinder = new Cylinder(0.25, 0.5);
        cylinder.setTranslateX(direction.getX());
        cylinder.setTranslateY(direction.getY());
        cylinder.setTranslateZ(direction.getZ());
        if (color == null)
            color = getRandomColor();

        cylinder.setMaterial(new PhongMaterial(color));
        cylinder.setDrawMode(DrawMode.LINE);
        DirectionalLight dl = new model.DirectionalLight(color, cylinder);
        cylinder.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(dl));
        object3DS.add(dl);
        scene.getChildren().addAll(cylinder);
        return dl;
    }

    public static PointLight addPointLight(Color color, Point3D position) {
        Sphere pointLight = new Sphere(0.5);
        pointLight.setTranslateX(position.getX());
        pointLight.setTranslateY(position.getY());
        pointLight.setTranslateZ(position.getZ());
        if (color == null)
            color = getRandomColor();

        pointLight.setMaterial(new PhongMaterial(color));
        pointLight.setDrawMode(DrawMode.LINE);
        PointLight pl = new PointLight(color, pointLight);
        pointLight.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(pl));
        object3DS.add(pl);
        scene.getChildren().addAll(pointLight);
        return pl;
    }

    public static model.Sphere addSphere(double radius, Color color) {
        Sphere sphere = new Sphere(radius);
        if (color == null)
            color = getRandomColor();
        sphere.setMaterial(new PhongMaterial(color));
        sphere.setDrawMode(DrawMode.FILL);
        model.Sphere modelSphere = new model.Sphere(sphere, color);
        sphere.addEventFilter(MouseEvent.MOUSE_CLICKED, SelectController.getSelectController().newSelection(modelSphere));
        object3DS.add(modelSphere);
        scene.getChildren().addAll(sphere);
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
