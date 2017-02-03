package controller;

import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import model.HistoryModel;
import model.Object3D;
import model.SceneModel;
import view.AttributesView;

/**
 * Created by Adrien on 27/10/2016.
 */
public class SelectController {
    private Object3D selected;
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

    public Selection newSelection(Object3D shape) {
        return new Selection(shape);
    }

    public Tools getTools() {
        return tools;
    }

    public void removeSelected() {
        SceneModel.remove(selected);
        selected = null;
    }

    public Object3D getSelected() {
        return selected;
    }

    public class AxisMovement implements EventHandler<MouseEvent> {
        private double x = -1;
        private double y = -1;
        private Point3D axis;
        private double positionX;
        private double positionY;
        private double positionZ;

        public AxisMovement(Point3D axis) {
            this.axis = axis;
        }

        @Override
        public void handle(MouseEvent event) {
            Node innerObject = selected.getInnerObject();
            if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                x = -1;
                y = -1;

                if (positionX != innerObject.getTranslateX() || positionY != innerObject.getTranslateY()
                        || positionZ != innerObject.getTranslateZ()) {
                    HistoryModel.addTranslation(innerObject.getTranslateX() - positionX,
                                                innerObject.getTranslateY() - positionY,
                                                innerObject.getTranslateZ() - positionZ, selected.getName());
                }
                return;
            } else if (x == -1 && y == -1) {
                x = event.getSceneX();
                y = event.getSceneY();

                positionX = innerObject.getTranslateX();
                positionY = innerObject.getTranslateY();
                positionZ = innerObject.getTranslateZ();
                return;
            }

            if (event.isPrimaryButtonDown()) {
                double moveX = axis.getX() * (event.getSceneX() - x) / 100;
                double moveY = axis.getY() * (event.getSceneY() - y) / 100;
                double moveZ = axis.getZ() * (event.getSceneX() - x) / 100;
                selected.translateBy(moveX, moveY, moveZ);
                Node[] nodes = new Node[] { getTools().far, getTools().right, getTools().up };
                for (Node node: nodes) {
                    node.setTranslateX(node.getTranslateX() + moveX);
                    node.setTranslateY(node.getTranslateY() + moveY);
                    node.setTranslateZ(node.getTranslateZ() + moveZ);
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

        private void setScale(Box box, double scale) {
            double newScale = box.getWidth() + scale;
            box.setWidth(newScale);
            box.setHeight(newScale);
            box.setDepth(newScale);
        }

        public AxisScale(Point3D axis) {
            this.axis = axis;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                x = -1;
                y = -1;

                double selectedScale = selected.getScale();

                if (scale != selectedScale) {
                    HistoryModel.addScale(selectedScale / scale, selected.getName());
                }

                return;
            } else if (x == -1 && y == -1) {
                x = event.getSceneX();
                y = event.getSceneY();

                scale = selected.getScale();
                return;
            }

            if (event.isPrimaryButtonDown()) {
                double scale = axis.getX() * (event.getSceneX() - x) / 100;
                selected.setScale(selected.getScale() + scale);
                setScale(getTools().scaler, scale);
            }
            x = event.getSceneX();
            y = event.getSceneY();
        }
    }

    public enum ToolsState {
        TRANSLATING, SCALING, NOTHING
    }

    public class Tools {
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

            Node obj = selected.getInnerObject();

            up = new Cylinder(cylinderRadius, cylinderLen);
            up.setDrawMode(DrawMode.FILL);
            up.setMaterial(new PhongMaterial(Color.GREEN));
            up.getTransforms().addAll(new Translate(obj.getTranslateX(), obj.getTranslateY() - cylinderLen / 2, obj.getTranslateZ()),
                    new Rotate(90, vy));
            AxisMovement axisMovement = new AxisMovement(vy);
            up.addEventFilter(MouseEvent.MOUSE_DRAGGED, axisMovement);
            up.addEventFilter(MouseEvent.MOUSE_RELEASED, axisMovement);

            right = new Cylinder(cylinderRadius, cylinderLen);
            right.setDrawMode(DrawMode.FILL);
            right.setMaterial(new PhongMaterial(Color.RED));
            right.getTransforms().addAll(new Translate(obj.getTranslateX() + cylinderLen / 2, obj.getTranslateY(), obj.getTranslateZ()),
                    new Rotate(90, vz));
            axisMovement = new AxisMovement(vx);
            right.addEventFilter(MouseEvent.MOUSE_DRAGGED, axisMovement);
            right.addEventFilter(MouseEvent.MOUSE_RELEASED, axisMovement);

            far = new Cylinder(cylinderRadius, cylinderLen);
            far.setDrawMode(DrawMode.FILL);
            far.setMaterial(new PhongMaterial(Color.BLUE));
            far.getTransforms().addAll(new Translate(obj.getTranslateX(), obj.getTranslateY(), obj.getTranslateZ() - cylinderLen / 2),
                    new Rotate(90, vx));
            axisMovement = new AxisMovement(vz);
            far.addEventFilter(MouseEvent.MOUSE_DRAGGED, axisMovement);
            far.addEventFilter(MouseEvent.MOUSE_RELEASED, axisMovement);

            root.getChildren().addAll(up, right, far);
        }

        private void addScalers() {
            final double boxLength = 0.5;

            Point3D vx = new Point3D(1, 0, 0);

            Node obj = selected.getInnerObject();

            scaler = new Box(boxLength, boxLength, boxLength);
            scaler.setDrawMode(DrawMode.FILL);
            scaler.setMaterial(new PhongMaterial(Color.YELLOW));
            scaler.setTranslateX(obj.getTranslateX());
            scaler.setTranslateY(obj.getTranslateY());
            scaler.setTranslateZ(obj.getTranslateZ());
            AxisScale axisScale = new AxisScale(vx);
            scaler.addEventFilter(MouseEvent.MOUSE_DRAGGED, axisScale);
            scaler.addEventFilter(MouseEvent.MOUSE_RELEASED, axisScale);

            root.getChildren().addAll(scaler);
        }

        public ToolsState getState() {
            return state;
        }

        public void setTranslate() {
            state = ToolsState.TRANSLATING;
            refreshTools();
        }

        public void setScale() {
            state = ToolsState.SCALING;
            refreshTools();
        }

        private void refreshTools() {
            if (selected == null) {
                return;
            }

            if (state == ToolsState.TRANSLATING) {
                removeRotors();
                addTranslators();
            } else if (state == ToolsState.SCALING) {
                removeRotors();
                addScalers();
            }
        }
    }

    public class Selection implements EventHandler<MouseEvent> {
        Object3D shape3D;

        private Selection(Object3D shape3D) {
            this.shape3D = shape3D;
        }

        @Override
        public void handle(MouseEvent event) {
            if (selected != null) {
                selected.setDrawMode(DrawMode.FILL);
            }
            selected = shape3D;
            AttributesView.getInstance().updateForObject(shape3D);
            shape3D.setDrawMode(DrawMode.LINE);
            if (getTools().getState() != ToolsState.NOTHING) {
                getTools().refreshTools();
            }
        }
    }
}
