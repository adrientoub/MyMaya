package controller;

import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.awt.*;

/**
 * Created by Adrien on 27/10/2016.
 */
public class SelectController {
    private Sphere selected;
    private Group root;
    private Tools tools = new Tools();

    public SelectController(Group root) {
        this.root = root;
    }

    public Selection newSelection(Sphere sphere) {
        return new Selection(sphere);
    }

    public Tools getTools() {
        return tools;
    }

    public class Tools implements EventHandler<KeyEvent> {
        private Cylinder up;
        private Cylinder right;
        private Cylinder far;

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.W) {
                System.out.println("Pressed W");
                if (selected == null) {
                    return;
                } else {
                    if (up != null) {
                        root.getChildren().remove(up);
                    }
                    if (right != null) {
                        root.getChildren().remove(right);
                    }
                    if (far != null) {
                        root.getChildren().remove(far);
                    }
                    final double cylinderLen = 0.75;
                    up = new Cylinder(0.05, cylinderLen);
                    up.setDrawMode(DrawMode.FILL);
                    up.setMaterial(new PhongMaterial(Color.RED));
                    up.getTransforms().addAll(new Translate(selected.getTranslateX(), selected.getTranslateY() - cylinderLen / 2, selected.getTranslateZ()),
                                              new Rotate(selected.getRotate(), selected.getRotationAxis()));

                    right = new Cylinder(0.05, cylinderLen);
                    right.setDrawMode(DrawMode.FILL);
                    right.setMaterial(new PhongMaterial(Color.GREEN));
                    right.getTransforms().addAll(new Translate(selected.getTranslateX() + cylinderLen / 2, selected.getTranslateY(), selected.getTranslateZ()),
                                                 new Rotate(selected.getRotate() + 90, selected.getRotationAxis()));

                    root.getChildren().addAll(up, right);//, far);
                    selected.getTranslateX();
                }
            }
        }
    }

    public class Selection implements EventHandler<MouseEvent> {
        Sphere sphere;

        private Selection(Sphere sphere) {
            this.sphere = sphere;
        }

        @Override
        public void handle(MouseEvent event) {
            if (selected != null)
                selected.setDrawMode(DrawMode.FILL);
            selected = sphere;
            sphere.setDrawMode(DrawMode.LINE);
        }
    }
}
