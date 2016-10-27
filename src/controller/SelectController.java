package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;

/**
 * Created by Adrien on 27/10/2016.
 */
public class SelectController {
    private Sphere selected;

    public Selection newSelection(Sphere sphere) {
        return new Selection(sphere);
    }

    public class Selection implements EventHandler<MouseEvent> {
        Sphere sphere;

        private Selection(Sphere sphere) {
            this.sphere = sphere;
        }

        @Override
        public void handle(MouseEvent event) {
            if (selected != null)
                selected.setDrawMode(DrawMode.FILL);
            selected = sphere;
            sphere.setDrawMode(DrawMode.LINE);
        }
    }
}
