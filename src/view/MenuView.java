package view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import model.*;

/**
 * Created by Adrien on 18/11/2016.
 */
public class MenuView extends MenuBar {
    private Menu createMeshMenu(Group root) {
        Menu menuMesh = new Menu("Mesh");
        MenuItem sphereItem = new MenuItem("Sphere");
        sphereItem.addEventHandler(EventType.ROOT, event -> {
            Sphere sphere = SceneModel.addSphere(1, null);
            HistoryModel.addNewSphere(sphere);
        });
        MenuItem boxItem = new MenuItem("Box");
        boxItem.addEventHandler(EventType.ROOT, event -> {
            SceneModel.addBox(null);
        });
        MenuItem pointLight = new MenuItem("Point Light");
        pointLight.addEventHandler(EventType.ROOT, event -> {
            PointLight pl = SceneModel.addPointLight(null, new Point3D(0, 0, 0));
            HistoryModel.addNewPointLight(pl);
        });
        MenuItem directionalLight = new MenuItem("Directional Light");
        directionalLight.addEventHandler(EventType.ROOT, event -> {
            DirectionalLight dl = SceneModel.addDirectionalLight(null, new Point3D(0, 0, 0));
            HistoryModel.addNewDirectionalLight(dl);
        });

        menuMesh.getItems().addAll(sphereItem, boxItem, pointLight, directionalLight);

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
