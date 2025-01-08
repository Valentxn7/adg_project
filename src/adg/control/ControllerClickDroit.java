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

public class ControllerClickDroit implements EventHandler<MouseEvent> {
    private ModelUML modelUML;

    public ControllerClickDroit(ModelUML modelUML) {
        this.modelUML = modelUML;
    }


    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getTarget() == modelUML.getPaneCLickDroit()) { // Vérifie si le clic est sur le Pane

            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                int x = (int) mouseEvent.getScreenX();
                int y = (int) mouseEvent.getScreenY();

                int x2 = (int) mouseEvent.getX();
                int y2 = (int) mouseEvent.getY();

                if (x2 < 0) {
                    x2 = x2 * -1;
                }
                if (y2 < 0) {
                    y2 = y2 * -1;
                }
                if (modelUML.getEtat() == false) {
                    modelUML.afficherClickDroit(x, y);
                } else {
                    modelUML.masquerClickDroit();
                    modelUML.afficherClickDroit(x, y);
                }

            } else {
                modelUML.masquerClickDroit();
                modelUML.masquerClickDroitClass();
            }
        }
    }
}
