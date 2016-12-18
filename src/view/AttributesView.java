package view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Object3D;

/**
 * Created by Adrien on 15/12/2016.
 */
public class AttributesView {
    private SplitPane splitPane;
    private TextArea textArea;
    private GridPane grid;

    private static AttributesView instance = new AttributesView();

    public void addLine(String string) {
        textArea.setText(textArea.getText() + string + "\n");
    }

    public static AttributesView getInstance() {
        return instance;
    }

    public SplitPane getPane() {
        return splitPane;
    }

    private AttributesView() {
        Pane attributes = addAttributes();
        Pane textField = addTextField();
        splitPane = new SplitPane(attributes, textField);

        splitPane.setOrientation(Orientation.VERTICAL);
    }

    private Pane addTextField() {
        textArea = new TextArea();
        textArea.setFocusTraversable(false);
        textArea.setEditable(false);

        TextField textField = new TextField();

        return new VBox(textArea, textField);
    }

    public int addPositionPanel(Object3D shape3D, int i) {
        Text position = new Text("Position");
        grid.add(position, 0, i++);

        Text x = new Text("x");
        grid.add(x, 0, i);
        TextField xTextField = new TextField(Double.toString(shape3D.getInnerObject().getTranslateX()));
        xTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                shape3D.setTranslateX(Double.valueOf(tf.getText()));
            } catch (NumberFormatException ignored) {}
        });
        grid.add(xTextField, 1, i++);

        Text y = new Text("y");
        grid.add(y, 0, i);
        TextField yTextField = new TextField(Double.toString(shape3D.getInnerObject().getTranslateY()));
        yTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                shape3D.setTranslateY(Double.valueOf(tf.getText()));
            } catch (NumberFormatException ignored) {}
        });
        grid.add(yTextField, 1, i++);

        Text z = new Text("z");
        grid.add(z, 0, i);
        TextField zTextField = new TextField(Double.toString(shape3D.getInnerObject().getTranslateZ()));
        zTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                shape3D.setTranslateZ(Double.valueOf(tf.getText()));
            } catch (NumberFormatException ignored) {}
        });
        grid.add(zTextField, 1, i++);

        return i;
    }

    public void updateForObject(Object3D shape3D) {
        int i = addPositionPanel(shape3D, 0);
        i = addColorPanel(shape3D, i);
    }

    private int addColorPanel(Object3D shape3D, int i) {
        Text colorText = new Text("Color");
        grid.add(colorText, 0, i++);

        Color color = shape3D.getColor();

        Text red = new Text("red");
        grid.add(red, 0, i);
        TextField redTextField = new TextField(Double.toString(color.getRed()));
        redTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                Color shapeColor = shape3D.getColor();
                Color new_color = Color.color(Double.valueOf(tf.getText()), shapeColor.getGreen(), shapeColor.getBlue());
                shape3D.setColor(new_color);
            } catch (IllegalArgumentException ignored) {}
        });
        grid.add(redTextField, 1, i++);

        Text green = new Text("green");
        grid.add(green, 0, i);
        TextField greenTextField = new TextField(Double.toString(color.getGreen()));
        greenTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                Color shapeColor = shape3D.getColor();
                Color new_color = Color.color(shapeColor.getRed(), Double.valueOf(tf.getText()), shapeColor.getBlue());
                shape3D.setColor(new_color);
            } catch (IllegalArgumentException ignored) {}
        });
        grid.add(greenTextField, 1, i++);

        Text blue = new Text("blue");
        grid.add(blue, 0, i);
        TextField blueTextField = new TextField(Double.toString(color.getBlue()));
        blueTextField.setOnKeyReleased(event -> {
            try {
                TextField tf = (TextField) event.getSource();
                Color shapeColor = shape3D.getColor();
                Color new_color = Color.color(shapeColor.getRed(), shapeColor.getGreen(), Double.valueOf(tf.getText()));
                shape3D.setColor(new_color);
            } catch (IllegalArgumentException ignored) {}
        });
        grid.add(blueTextField, 1, i++);

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
