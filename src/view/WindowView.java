package view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Adrien on 05/02/2017.
 */
public class WindowView {
    private SplitPane splitPane;
    private Stage primaryStage;

    public WindowView(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setResizable(true);
        primaryStage.setWidth(1024);
        primaryStage.setHeight(768);
        primaryStage.setTitle("MyMaya");

        VBox rootVBox = new VBox();

        rootVBox.getChildren().addAll(new MenuView());

        SplitPane attributesEditingPane = AttributesView.getInstance().getPane();
        Pane stackPane3D = new StackPane();
        stackPane3D.prefHeightProperty().bind(primaryStage.heightProperty());
        Parent scene3D = new SceneView(stackPane3D);
        stackPane3D.getChildren().add(scene3D);

        splitPane = new SplitPane(stackPane3D, attributesEditingPane);
        rootVBox.getChildren().add(splitPane);

        Scene scene = new Scene(rootVBox);

        primaryStage.setScene(scene);
    }

    public void show() {
        primaryStage.show();
        splitPane.setDividerPosition(0, 0.8);
    }
}
