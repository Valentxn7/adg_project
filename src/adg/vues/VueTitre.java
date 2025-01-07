package adg.vues;

import adg.ModelUML;
import adg.Observateur;
import adg.Sujet;
import javafx.scene.control.Label;

public class VueTitre extends Label implements Observateur {
    private ModelUML modelUML;

    public VueTitre(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    @Override
    public void actualiser(Sujet mod) {
        this.setText(modelUML.getWindowsTitle());
    }


}
