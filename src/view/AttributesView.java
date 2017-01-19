package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Attributes;
import model.Camera;
import model.HistoryModel;
import model.Object3D;
import script.Execution;
import script.Parser;
import script.ast.AstNode;

/**
 * Created by Adrien on 15/12/2016.
 */
public class AttributesView {
    private SplitPane splitPane;
    private TextArea textArea;
    private TextField commandTextField;
    private GridPane grid;

    private static AttributesView instance = new AttributesView();

    private TextField addTextField(String str, double value, int i) {
        return addTextField(str, Double.toString(value), i);
    }

    private TextField addTextField(String str, String value, int i) {
        Text text = new Text(str);
        grid.add(text, 0, i);
        TextField textField = new TextField(value);
        grid.add(textField, 1, i);
        return textField;
    }

    public void addLine(String string) {
        textArea.setText(textArea.getText() + string + "\n");
        textArea.setScrollTop(Double.MAX_VALUE);
    }

    public static AttributesView getInstance() {
        return instance;
    }

    public SplitPane getPane() {
        return splitPane;
    }

    private AttributesView() {
        Pane attributes = addAttributes();
        Pane textField = addTextArea();
        splitPane = new SplitPane(new ScrollPane(attributes), textField);

        splitPane.setOrientation(Orientation.VERTICAL);
    }

    private Pane addTextArea() {
        textArea = new TextArea();
        textArea.setFocusTraversable(false);
        textArea.setEditable(false);

        commandTextField = new TextField();
        commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    Parser p = new Parser();
                    AstNode parsed = p.parse(commandTextField.getText());
                    if (parsed != null) {
                        parsed.accept(Execution.getInstance());
                    }
                    commandTextField.setText("");
                }
            }
        });

        return new VBox(textArea, commandTextField);
    }

    public int addPositionPanel(Object3D shape3D, int i) {
        Text position = new Text("Position");
        grid.add(position, 0, i++);

        Node inner = shape3D.getInnerObject();

        TextField xTextField = addTextField("x", inner.getTranslateX(), i++);
        xTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                shape3D.setTranslateX(Double.valueOf(tf.getText()));
            } catch (NumberFormatException ignored) {}
        });

        TextField yTextField = addTextField("y", inner.getTranslateY(), i++);
        yTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                shape3D.setTranslateY(Double.valueOf(tf.getText()));
            } catch (NumberFormatException ignored) {}
        });

        TextField zTextField = addTextField("z", inner.getTranslateZ(), i++);
        zTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                shape3D.setTranslateZ(Double.valueOf(tf.getText()));
            } catch (NumberFormatException ignored) {}
        });

        return i;
    }

    public void updateForObject(Object3D shape3D) {
        grid.getChildren().clear();
        int i = addNamePanel(shape3D, 0);
        i = addPositionPanel(shape3D, i);
        i = addScalePanel(shape3D, i);
        i = addRotatePanel(shape3D, i);
        i = addColorPanel(shape3D, i);
        i = addAttributesPanel(shape3D, i);
    }

    private int addRotatePanel(Object3D shape3D, int i) {
        if (!shape3D.canRotate()) {
            return i;
        }
        // TODO: add to all shapes
        Camera c = (Camera) shape3D;
        Text rotate = new Text("Rotate");
        grid.add(rotate, 0, i++);

        TextField rotateXTextField = addTextField("X", c.getRotateX(), i++);
        rotateXTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                double newRotate = Double.valueOf(tf.getText());
                if (newRotate != c.getRotateX()) {
                    HistoryModel.addRotation(newRotate - c.getRotateX(), 0, shape3D.getName());
                    c.rotateX(newRotate - c.getRotateX());
                }
            } catch (NumberFormatException ignored) {}
        });
        TextField rotateYTextField = addTextField("Y", c.getRotateY(), i++);
        rotateYTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                double newRotate = Double.valueOf(tf.getText());
                if (newRotate != c.getRotateY()) {
                    HistoryModel.addRotation(0, newRotate - c.getRotateY(), shape3D.getName());
                    c.rotateY(newRotate - c.getRotateY());
                }
            } catch (NumberFormatException ignored) {}
        });

        return i;

    }

    private int addScalePanel(Object3D shape3D, int i) {
        if (!shape3D.isScalable()) {
            return i;
        }
        Text scale = new Text("Scale");
        grid.add(scale, 0, i++);

        TextField scaleTextField = addTextField("Scale", shape3D.getScale(), i++);
        scaleTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                double newScale = Double.valueOf(tf.getText());
                if (newScale != 0 && newScale != shape3D.getScale()) {
                    HistoryModel.addScale(newScale / shape3D.getScale(), shape3D.getName());
                    shape3D.setScale(newScale);
                }
            } catch (NumberFormatException ignored) {}
        });

        return i;
    }

    private int addNamePanel(Object3D shape3D, int i) {
        Text position = new Text("Base");
        grid.add(position, 0, i++);

        TextField nameTextField = addTextField("Name", shape3D.getName(), i++);
        nameTextField.setOnKeyReleased(event -> {
            TextField tf = (TextField) event.getSource();
            if (tf.getText().length() > 0) {
                shape3D.setName(tf.getText());
            }
        });

        return i;
    }

    private int addAttributesPanel(Object3D shape3D, Integer i) {
        Attributes attributes = shape3D.getAttributes();
        if (attributes == null) {
            return i;
        }

        Text colorText = new Text("Attributes");
        grid.add(colorText, 0, i++);

        addTextField("diffuse", attributes.getDiff(), i++).setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                attributes.setDiff(Double.valueOf(tf.getText()));
            } catch (IllegalArgumentException ignored) {}
        });

        addTextField("reflection", attributes.getRefl(), i++).setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                attributes.setRefl(Double.valueOf(tf.getText()));
            } catch (IllegalArgumentException ignored) {}
        });

        addTextField("specular", attributes.getSpec(), i++).setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                attributes.setSpec(Double.valueOf(tf.getText()));
            } catch (IllegalArgumentException ignored) {}
        });

        addTextField("shininess", attributes.getShin(), i++).setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                attributes.setShin(Double.valueOf(tf.getText()));
            } catch (IllegalArgumentException ignored) {}
        });

        addTextField("refraction", attributes.getRefr(), i++).setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                attributes.setRefr(Double.valueOf(tf.getText()));
            } catch (IllegalArgumentException ignored) {}
        });

        addTextField("opacity", attributes.getOpac(), i++).setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                attributes.setOpac(Double.valueOf(tf.getText()));
            } catch (IllegalArgumentException ignored) {}
        });

        return i;
    }

    private int addColorPanel(Object3D shape3D, int i) {
        if (!shape3D.canChangeColor()) {
            return i;
        }
        Text colorText = new Text("Color");
        grid.add(colorText, 0, i++);

        Color color = shape3D.getColor();

        TextField redTextField = addTextField("red", color.getRed(), i++);
        redTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                Color shapeColor = shape3D.getColor();
                Color new_color = Color.color(Double.valueOf(tf.getText()), shapeColor.getGreen(), shapeColor.getBlue());
                shape3D.setColor(new_color);
            } catch (IllegalArgumentException ignored) {}
        });

        TextField greenTextField = addTextField("green", color.getGreen(), i++);
        greenTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                Color shapeColor = shape3D.getColor();
                Color new_color = Color.color(shapeColor.getRed(), Double.valueOf(tf.getText()), shapeColor.getBlue());
                shape3D.setColor(new_color);
            } catch (IllegalArgumentException ignored) {}
        });

        TextField blueTextField = addTextField("blue", color.getBlue(), i++);
        blueTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                Color shapeColor = shape3D.getColor();
                Color new_color = Color.color(shapeColor.getRed(), shapeColor.getGreen(), Double.valueOf(tf.getText()));
                shape3D.setColor(new_color);
            } catch (IllegalArgumentException ignored) {}
        });

        return i;
    }

    private Pane addAttributes() {
        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        return grid;
    }
}
