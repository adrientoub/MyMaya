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
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import model.HistoryModel;
import model.SceneModel;

/**
 * Created by Adrien on 27/10/2016.
 */
public class SelectController {
    private Shape3D selected;
    private boolean selectedLight;
    private Group root;
    private Tools tools = new Tools();

    private static SelectController sc;

    private SelectController(Group root) {
        this.root = root;
    }

    public static SelectController initializeSelectController(Group root) {
        sc = new SelectController(root);
        return sc;
    }

    public static SelectController getSelectController() {
        return sc;
    }

    public Selection newSelection(Shape3D shape, boolean light) {
        return new Selection(shape, light);
    }

    public Tools getTools() {
        return tools;
    }

    public class AxisMovement implements EventHandler<MouseEvent> {
        private double x = -1;
        private double y = -1;
        private Point3D axis;
        private double positionX;
        private double positionY;
        private double positionZ;

        public AxisMovement(Point3D axis, double angle) {
            double rad = (angle / 180) * Math.PI;
            this.axis = new Point3D(axis.getX() * Math.cos(rad) + axis.getY() * Math.sin(rad),
                                    -axis.getX() * Math.sin(rad) + axis.getY() * Math.cos(rad),
                                    axis.getZ());
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                x = -1;
                y = -1;

                if (positionX != selected.getTranslateX() || positionY != selected.getTranslateY()
                        || positionZ != selected.getTranslateZ()) {
                    HistoryModel.addTranslation(selected.getTranslateX() - positionX,
                                                selected.getTranslateY() - positionY,
                                                selected.getTranslateZ() - positionZ);
                }
                return;
            } else if (x == -1 && y == -1) {
                x = event.getSceneX();
                y = event.getSceneY();

                positionX = selected.getTranslateX();
                positionY = selected.getTranslateY();
                positionZ = selected.getTranslateZ();
                return;
            }

            if (event.isPrimaryButtonDown()) {
                Shape3D[] shapes = new Shape3D[] { selected, getTools().far, getTools().right, getTools().up };
                for (Shape3D shape: shapes) {
                    shape.setTranslateX(shape.getTranslateX() + axis.getX() * (event.getSceneX() - x) / 100);
                    shape.setTranslateY(shape.getTranslateY() + axis.getY() * (event.getSceneY() - y) / 100);
                    shape.setTranslateZ(shape.getTranslateZ() + axis.getZ() * (event.getSceneX() - x) / 100);
                }
            }
            x = event.getSceneX();
            y = event.getSceneY();
        }
    }

    public class AxisScale implements EventHandler<MouseEvent> {
        private double x = -1;
        private double y = -1;
        private Point3D axis;
        private double scale;

        private double getScale() {
            if (selected instanceof Sphere) {
                return ((Sphere) selected).getRadius();
            } else {
                return selected.getScaleX();
            }
        }

        private void setScale(Shape3D shape, double scale) {
            if (shape instanceof Sphere) {
                Sphere sphere = (Sphere) shape;
                sphere.setRadius(sphere.getRadius() + scale);
            } else {
                double newScale = shape.getScaleX() + scale;
                shape.setScaleX(newScale);
                shape.setScaleY(newScale);
                shape.setScaleZ(newScale);
            }
        }

        public AxisScale(Point3D axis, double angle) {
            double rad = (angle / 180) * Math.PI;
            this.axis = new Point3D(axis.getX() * Math.cos(rad) + axis.getY() * Math.sin(rad),
                    -axis.getX() * Math.sin(rad) + axis.getY() * Math.cos(rad),
                    axis.getZ());
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                x = -1;
                y = -1;

                double selectedScale = getScale();

                if (scale != selectedScale) {
                    HistoryModel.addScale(selectedScale / scale);
                }

                return;
            } else if (x == -1 && y == -1) {
                x = event.getSceneX();
                y = event.getSceneY();

                scale = getScale();
                return;
            }

            if (event.isPrimaryButtonDown()) {
                Shape3D[] shapes = new Shape3D[] { selected, getTools().scaler };
                double scale = axis.getX() * (event.getSceneX() - x) / 100;
                for (Shape3D shape: shapes) {
                    setScale(shape, scale);
                }
            }
            x = event.getSceneX();
            y = event.getSceneY();
        }
    }

    public enum ToolsState {
        TRANSLATING, SCALING, NOTHING
    }

    public class Tools implements EventHandler<KeyEvent> {
        private Cylinder up;
        private Cylinder right;
        private Cylinder far;
        private Box scaler;
        private ToolsState state = ToolsState.NOTHING;

        private void removeRotors() {
            if (up != null) {
                root.getChildren().remove(up);
                up = null;
            }
            if (right != null) {
                root.getChildren().remove(right);
                right = null;
            }
            if (far != null) {
                root.getChildren().remove(far);
                far = null;
            }
            if (scaler != null) {
                root.getChildren().remove(scaler);
                scaler = null;
            }
        }

        private void addTranslators() {
            final double cylinderLen = 0.75;
            final double cylinderRadius = 0.05;

            Point3D vx = new Point3D(1, 0, 0);
            Point3D vy = new Point3D(0, 1, 0);
            Point3D vz = new Point3D(0, 0, 1);

            up = new Cylinder(cylinderRadius, cylinderLen);
            up.setDrawMode(DrawMode.FILL);
            up.setMaterial(new PhongMaterial(Color.GREEN));
            up.getTransforms().addAll(new Translate(selected.getTranslateX(), selected.getTranslateY() - cylinderLen / 2, selected.getTranslateZ()),
                    new Rotate(90, vy));
            AxisMovement axisMovement = new AxisMovement(vy, 0);
            up.addEventFilter(MouseEvent.MOUSE_DRAGGED, axisMovement);
            up.addEventFilter(MouseEvent.MOUSE_RELEASED, axisMovement);

            right = new Cylinder(cylinderRadius, cylinderLen);
            right.setDrawMode(DrawMode.FILL);
            right.setMaterial(new PhongMaterial(Color.RED));
            right.getTransforms().addAll(new Translate(selected.getTranslateX() + cylinderLen / 2, selected.getTranslateY(), selected.getTranslateZ()),
                    new Rotate(90, vz));
            axisMovement = new AxisMovement(vx, 0);
            right.addEventFilter(MouseEvent.MOUSE_DRAGGED, axisMovement);
            right.addEventFilter(MouseEvent.MOUSE_RELEASED, axisMovement);

            far = new Cylinder(cylinderRadius, cylinderLen);
            far.setDrawMode(DrawMode.FILL);
            far.setMaterial(new PhongMaterial(Color.BLUE));
            far.getTransforms().addAll(new Translate(selected.getTranslateX(), selected.getTranslateY(), selected.getTranslateZ() - cylinderLen / 2),
                    new Rotate(90, vx));
            axisMovement = new AxisMovement(vz, 0);
            far.addEventFilter(MouseEvent.MOUSE_DRAGGED, axisMovement);
            far.addEventFilter(MouseEvent.MOUSE_RELEASED, axisMovement);

            root.getChildren().addAll(up, right, far);
        }

        private void addScalers() {
            final double boxLength = 0.5;

            Point3D vx = new Point3D(1, 0, 0);

            scaler = new Box(boxLength, boxLength, boxLength);
            scaler.setDrawMode(DrawMode.FILL);
            scaler.setMaterial(new PhongMaterial(Color.YELLOW));
            scaler.setTranslateX(selected.getTranslateX());
            scaler.setTranslateY(selected.getTranslateY() - boxLength / 2);
            scaler.setTranslateZ(selected.getTranslateZ());
            AxisScale axisScale = new AxisScale(vx, 0);
            scaler.addEventFilter(MouseEvent.MOUSE_DRAGGED, axisScale);
            scaler.addEventFilter(MouseEvent.MOUSE_RELEASED, axisScale);

            root.getChildren().addAll(scaler);
        }

        public ToolsState getState() {
            return state;
        }

        @Override
        public void handle(KeyEvent event) {
            if (selected == null) {
                return;
            }
            if (event != null && event.getCode() == KeyCode.W) {
                event = null;
                state = ToolsState.TRANSLATING;
            } else if (event != null && event.getCode() == KeyCode.R) {
                event = null;
                state = ToolsState.SCALING;
            }
            if (event == null) {
                if (state == ToolsState.TRANSLATING) {
                    removeRotors();
                    addTranslators();
                } else if (state == ToolsState.SCALING) {
                    removeRotors();
                    addScalers();
                }
            }
        }
    }

    public class Selection implements EventHandler<MouseEvent> {
        Shape3D shape3D;
        boolean light;

        private Selection(Shape3D shape3D, boolean light) {
            this.shape3D = shape3D;
            this.light = light;
        }

        @Override
        public void handle(MouseEvent event) {
            if (selected != null && !selectedLight) {
                selected.setDrawMode(DrawMode.FILL);
            }
            selected = shape3D;
            selectedLight = light;
            shape3D.setDrawMode(DrawMode.LINE);
            if (getTools().getState() != ToolsState.NOTHING)
                getTools().handle(null);
        }
    }
}
