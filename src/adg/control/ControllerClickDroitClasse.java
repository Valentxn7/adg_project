package adg.control;

import adg.ModelUML;
import adg.data.Classe;
import adg.vues.VueClasse;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControllerClickDroitClasse implements EventHandler<MouseEvent> {
    private ModelUML modelUML;

    public ControllerClickDroitClasse(ModelUML modelUML) {
        this.modelUML = modelUML;
    }


    @Override
    public void handle(MouseEvent mouseEvent) {


        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            int x = (int) mouseEvent.getScreenX();
            int y = (int) mouseEvent.getScreenY();

            int x2 = (int) mouseEvent.getX();
            int y2 = (int) mouseEvent.getY();

            boolean estLibre = modelUML.estLibre(x2, y2);

            VueClasse classe = (VueClasse) mouseEvent.getSource();
            Classe c = classe.getClasse();
            modelUML.setClasseSelectionne(c);

            if (modelUML.getEtatClickDroitClasse() == false) {
                modelUML.afficherClickDroitClasse(x, y);
            } else {
                modelUML.masquerClickDroitClass();
                modelUML.afficherClickDroitClasse(x, y);
            }

        } else {
            modelUML.masquerClickDroit();
            modelUML.masquerClickDroitClass();
        }

    }
}
