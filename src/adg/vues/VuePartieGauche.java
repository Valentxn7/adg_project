package adg.vues;

import adg.ModelUML;
import adg.Observateur;
import adg.Sujet;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import java.io.File;

public class VuePartieGauche extends VBox implements Observateur {

    public VuePartieGauche(int i) {
        super(i);
    }

    @Override
    public void actualiser(Sujet mod) {
        ModelUML model = (ModelUML) mod;

        this.setMinSize(model.getPartieGaucheX(), model.getPartieGaucheY());
        this.setPrefSize(model.getPartieGaucheX(), model.getPartieGaucheYPref());
    }
}
