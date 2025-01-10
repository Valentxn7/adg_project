package adg.control;

import adg.ModelUML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuItem;

public class ControllerChoixClickDroit implements EventHandler<ActionEvent> {
    private ModelUML modelUML;

    public ControllerChoixClickDroit(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    /**
     * Permet de gérer les actions des items du menu contextuel
     * @param event
     */
    @Override
    public void handle(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        if (item.getId() == "1") {
            modelUML.masquerToutesDependances();
        } else if (item.getId() == "2") {
            modelUML.masquerToutHeritages();
        } else if (item.getId() == "3") {
            modelUML.masquerToutAttributs();
        } else if (item.getId() == "4") {
            modelUML.afficherToutesDependances();
        } else if (item.getId() == "5") {
            modelUML.afficherTousHeritages();
        } else if (item.getId() == "6") {
            modelUML.afficherTousAttributs();
        } else if (item.getId() == "7") {
            modelUML.afficherToutesMethodes();
        } else if (item.getId() == "8") {
            modelUML.masquerToutesMethodes();
        } else if (item.getId() == "A") {
            modelUML.masquerConstructeur();
        } else if (item.getId() == "B") {
            modelUML.afficherConstructeur();
        }
        else if (item.getId() == "C") {
            modelUML.masquerAttributs();
        }  else if (item.getId() == "E") {
            modelUML.afficherHeritages();
        } else if (item.getId() == "F") {
            modelUML.afficherAttributs();
        } else if (item.getId() == "G") {
            modelUML.afficherMethodes();
        } else if (item.getId() == "H") {
            modelUML.masquerMethodes();
        }
    }

}
