package adg.vues;

import adg.ModelUML;
import adg.Observateur;
import adg.Sujet;
import javafx.scene.control.Label;

public class VueTitre extends Label implements Observateur {

    @Override
    public void actualiser(Sujet mod) {
        ModelUML model = (ModelUML) mod;
        this.setText(model.getWindowsTitle());
    }


}
