package adg;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ControleurDeplacerClasse implements EventHandler<MouseEvent> {

    private double[] decalageSouris = new double[2];
    private ModelUML model;

    public ControleurDeplacerClasse(ModelUML m) {
        this.model = m;
    }

    @Override
    public void handle(MouseEvent event) {

        VBox classe = (VBox) event.getSource();

        if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            classe.setLayoutX(event.getSceneX() - decalageSouris[0]);
            classe.setLayoutY(event.getSceneY() - decalageSouris[1]);
        } else if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            decalageSouris[0] = event.getSceneX() - classe.getLayoutX();
            decalageSouris[1] = event.getSceneY() - classe.getLayoutY();
        }
    }
}
