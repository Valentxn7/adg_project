package adg;

import adg.vues.VueClasse;
import adg.vues.VueDiagramme;
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

        VueClasse classe = (VueClasse) event.getSource();
        VueDiagramme partieDroite = model.getVueDiagramme();

        if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            System.out.println("DRAGGED");
            Double x = event.getSceneX() - decalageSouris[0];
            Double y = event.getSceneY() - decalageSouris[1];

            double partieDroiteMaxX = partieDroite.getWidth();
            double partieDroiteMaxY = partieDroite.getHeight();


            if (x >= 0 && x + classe.getWidth() <= partieDroiteMaxX &&
                    y >= 0 && y + classe.getHeight() <= partieDroiteMaxY) {
                model.changerPositionClasse(classe, x, y);
            }

        } else if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("PRESSED");
            decalageSouris[0] = event.getSceneX() - classe.getLayoutX();
            decalageSouris[1] = event.getSceneY() - classe.getLayoutY();
        }
    }
}
