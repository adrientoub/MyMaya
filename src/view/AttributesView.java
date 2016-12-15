package view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by Adrien on 15/12/2016.
 */
public class AttributesView {
    private SplitPane splitPane;
    private TextArea textArea;
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

    private Pane addAttributes() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Text position = new Text("Position");
        grid.add(position, 0, 0);

        return grid;
    }
}
