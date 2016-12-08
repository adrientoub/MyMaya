package controller;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;

/**
 * Created by Adrien on 27/10/2016.
 */
public class ScrollController implements EventHandler<ScrollEvent> {
    private final double scrollScale = 0.05;
    private model.Camera camera;

    public ScrollController(model.Camera camera) {
        this.camera = camera;
    }

    @Override
    public void handle(ScrollEvent event) {
        camera.translateZ(event.getDeltaY() * scrollScale);
    }
}
