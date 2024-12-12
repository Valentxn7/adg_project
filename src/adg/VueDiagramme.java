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

    @Override
    public void switchHome2diag() {
        this.getChildren().clear();
        this.setPrefSize(900, 400);
        System.out.println("Switching to diagram");
    }


}
