package view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import model.ExportSceneModel;
import model.SceneModel;

/**
 * Created by Adrien on 18/11/2016.
 */
public class MenuView extends MenuBar {
    private Menu createMeshMenu(Group root) {
        Menu menuMesh = new Menu("Mesh");
        MenuItem sphere = new MenuItem("Sphere");
        sphere.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                SceneModel.addSphere(1, null);
            }
        });
        MenuItem box = new MenuItem("Box");
        box.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                SceneModel.addBox(null);
            }
        });

        menuMesh.getItems().addAll(sphere, box);

        return menuMesh;
    }

    private Menu createFileMenu() {
        Menu menuFile = new Menu("File");
        MenuItem save = new MenuItem("Save");
        save.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                ExportSceneModel.exportScene("out.in");
            }
        });
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        menuFile.getItems().addAll(save);

        return menuFile;
    }


    public MenuView(Group root) {
        Menu menuFile = createFileMenu();
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        Menu menuMesh = createMeshMenu(root);

        this.getMenus().addAll(menuFile, menuEdit, menuView, menuMesh);
    }
}
