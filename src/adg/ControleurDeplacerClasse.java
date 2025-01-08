package adg;

import adg.vues.VueClasse;
import adg.vues.VueDiagramme;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Classe ControleurDeplacerClasse
 */

public class ControleurDeplacerClasse implements EventHandler<MouseEvent> {

    private double[] decalageSouris = new double[2];
    private ModelUML model;

    /**
     * @param m
     * Constructeur de la classe ControleurDeplacerClasse
     */
    public ControleurDeplacerClasse(ModelUML m) {
        this.model = m;
    }


    /**
     * @param event
     * Méthode qui permet de déplacer une classe
     */
    @Override
    public void handle(MouseEvent event) {

        VueClasse classe = (VueClasse) event.getSource();
        VueDiagramme partieDroite = model.getVueDiagramme();

        if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) { // Quand la souris est en train de bouger

            Double x = event.getSceneX() - decalageSouris[0]; //On récupère les coordonnées de la souris
            Double y = event.getSceneY() - decalageSouris[1];

            double partieDroiteMaxX = partieDroite.getWidth(); //On récupère les dimensions de la partie droite
            double partieDroiteMaxY = partieDroite.getHeight();

            if (x >= 0 && x + classe.getWidth() <= partieDroiteMaxX &&
                    y >= 0 && y + classe.getHeight() <= partieDroiteMaxY) { //Si la classe est dans la partie droite
                model.changerPositionClasse(classe, x, y); //On change la position de la classe
            }

        } else if (event.getEventType() == MouseEvent.MOUSE_PRESSED) { // Quand on appuie sur la souris
            decalageSouris[0] = event.getSceneX() - classe.getLayoutX(); //On récupère les coordonnées de la souris
            decalageSouris[1] = event.getSceneY() - classe.getLayoutY();
        }
    }
}
