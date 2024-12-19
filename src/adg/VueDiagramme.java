package adg;

import javafx.scene.Scene;
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
        System.out.println("VueDiagramme : Switching to diagram");
    }

    @Override
    public void switchDiag2Home() {
        this.getChildren().clear();
        this.setPrefSize(500, 380);
        System.out.println("VueDiagramme : Switching to home");
    }


}
