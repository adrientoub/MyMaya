package view;

import controller.RenderController;
import controller.SelectController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import model.*;
import script.Execution;
import script.Parser;
import script.ast.AstNode;

import java.io.File;
import java.io.IOException;

/**
 * Created by Adrien on 18/11/2016.
 */
public class MenuView extends MenuBar {
    private Menu createMeshMenu(Group root) {
        Menu menuMesh = new Menu("Mesh");
        MenuItem sphereItem = new MenuItem("Sphere");
        sphereItem.addEventHandler(EventType.ROOT, event -> {
            SceneModel.addSphere(1, null, null, null);
        });
        MenuItem boxItem = new MenuItem("Box");
        boxItem.addEventHandler(EventType.ROOT, event -> {
            SceneModel.addBox(null);
        });
        MenuItem pointLight = new MenuItem("Point Light");
        pointLight.addEventHandler(EventType.ROOT, event -> {
            SceneModel.addPointLight(null, new Point3D(0, 0, 0), null);
        });
        MenuItem directionalLight = new MenuItem("Directional Light");
        directionalLight.addEventHandler(EventType.ROOT, event -> {
            SceneModel.addDirectionalLight(null, new Point3D(0, 0, 0), null);
        });

        menuMesh.getItems().addAll(sphereItem, boxItem, pointLight, directionalLight);

        return menuMesh;
    }

    private Menu createFileMenu() {
        Menu menuFile = new Menu("File");

        MenuItem open = new MenuItem("Open script");
        open.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    Parser parser = new Parser();
                    try {
                        AstNode astNode = parser.parse(file);
                        if (astNode != null) {
                            astNode.accept(Execution.getInstance());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        MenuItem save = new MenuItem("Save");
        save.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                ExportSceneModel.exportScene("out.in");
            }
        });
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        menuFile.getItems().addAll(save, open);

        return menuFile;
    }

    private Menu createEditMenu() {
        Menu menuEdit = new Menu("Edit");
        MenuItem save = new MenuItem("Remove");
        save.addEventHandler(EventType.ROOT, event -> SelectController.getSelectController().removeSelected());
        save.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));

        MenuItem translate = new MenuItem("Translate");
        translate.addEventHandler(EventType.ROOT, event -> SelectController.getSelectController().getTools().setTranslate());
        translate.setAccelerator(new KeyCodeCombination(KeyCode.W));

        MenuItem scale = new MenuItem("Scale");
        scale.addEventHandler(EventType.ROOT, event -> SelectController.getSelectController().getTools().setScale());
        scale.setAccelerator(new KeyCodeCombination(KeyCode.R));

        menuEdit.getItems().addAll(save, new SeparatorMenuItem(), translate, scale);

        return menuEdit;
    }

    public MenuView(Group root) {
        Menu menuFile = createFileMenu();
        Menu menuEdit = createEditMenu();
        Menu menuView = createViewMenu();
        Menu menuMesh = createMeshMenu(root);

        this.getMenus().addAll(menuFile, menuEdit, menuView, menuMesh);
    }

    private Menu createViewMenu() {
        Menu menuView = new Menu("View");
        MenuItem render = new MenuItem("Render");
        render.addEventHandler(EventType.ROOT, new RenderController());
        render.setAccelerator(new KeyCodeCombination(KeyCode.F5));

        menuView.getItems().addAll(render);

        return menuView;
    }
}
