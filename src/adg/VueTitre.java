package adg;

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
