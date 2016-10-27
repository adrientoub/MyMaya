package controller;

import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Created by Adrien on 27/10/2016.
 */
public class SelectController {
    private Sphere selected;
    private Group root;
    private Tools tools = new Tools();
    private SubScene subScene;

    public SelectController(Group root, SubScene subScene) {
        this.root = root;
        this.subScene = subScene;
    }

    public Selection newSelection(Sphere sphere) {
        return new Selection(sphere);
    }

    public Tools getTools() {
        return tools;
    }

    public class AxisMovement implements EventHandler<MouseEvent> {
        private double x = -1;
        private double y = -1;
        private Point3D axis;

        public AxisMovement(Point3D axis, double angle) {
            double rad = (angle / 180) * Math.PI;
            System.out.println(axis);
            this.axis = new Point3D(axis.getX() * Math.cos(rad) + axis.getY() * Math.sin(rad),
                                    -axis.getX() * Math.sin(rad) + axis.getY() * Math.cos(rad),
                                    axis.getZ());
            System.out.println(this.axis);
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                x = -1;
                y = -1;
                return;
            } else if ((x == -1 && y == -1)) {
                x = event.getSceneX();
                y = event.getSceneY();
                return;
            }

            if (event.isPrimaryButtonDown()) {
                selected.setTranslateX(selected.getTranslateX() + axis.getX() * (event.getSceneX() - x) / 100);
                selected.setTranslateY(selected.getTranslateY() + axis.getY() * (event.getSceneY() - y) / 100);
                selected.setTranslateZ(selected.getTranslateZ() + axis.getZ() * (event.getSceneX() - x) / 100);
            }
            x = event.getSceneX();
            y = event.getSceneY();
        }
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

                    Point3D axis = new Point3D(0, 1, 0);

                    up = new Cylinder(0.05, cylinderLen);
                    up.setDrawMode(DrawMode.FILL);
                    up.setMaterial(new PhongMaterial(Color.GREEN));
                    up.getTransforms().addAll(new Translate(selected.getTranslateX(), selected.getTranslateY() - cylinderLen / 2, selected.getTranslateZ()),
                                              new Rotate(90, axis));
                    up.addEventFilter(MouseEvent.MOUSE_DRAGGED, new AxisMovement(axis, 90));

                    axis = new Point3D(0, 0, 1);
                    right = new Cylinder(0.05, cylinderLen);
                    right.setDrawMode(DrawMode.FILL);
                    right.setMaterial(new PhongMaterial(Color.RED));
                    right.getTransforms().addAll(new Translate(selected.getTranslateX() + cylinderLen / 2, selected.getTranslateY(), selected.getTranslateZ()),
                                                 new Rotate(90, axis));
                    right.addEventFilter(MouseEvent.MOUSE_DRAGGED, new AxisMovement(axis, 90));

                    axis = new Point3D(1, 0, 0);
                    far = new Cylinder(0.05, cylinderLen);
                    far.setDrawMode(DrawMode.FILL);
                    far.setMaterial(new PhongMaterial(Color.BLUE));
                    far.getTransforms().addAll(new Translate(selected.getTranslateX(), selected.getTranslateY(), selected.getTranslateZ() - cylinderLen / 2),
                                               new Rotate(90, axis));
                    far.addEventFilter(MouseEvent.MOUSE_DRAGGED, new AxisMovement(axis, 90));

                    root.getChildren().addAll(up, right, far);
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
