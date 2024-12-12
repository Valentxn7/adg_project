package adg;

import javafx.scene.layout.StackPane;

public class VueDiagramme extends StackPane implements Observateur {
    private ModelUML modelUML;

    public VueDiagramme(ModelUML modelUML) {
        this.modelUML = modelUML;
    }


    @Override
    public void actualiser(Sujet mod) {
        System.out.println("Création d'un projet");
    }
}
