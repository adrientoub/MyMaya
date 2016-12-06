package controller;

import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Translate;

/**
 * Created by Adrien on 27/10/2016.
 */
public class ScrollController implements EventHandler<ScrollEvent> {
    private final double scrollScale = 0.05;
    private Camera camera;

    public ScrollController(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void handle(ScrollEvent event) {
        camera.setTranslateZ(camera.getTranslateZ() + event.getDeltaY() * scrollScale);
    }
}
