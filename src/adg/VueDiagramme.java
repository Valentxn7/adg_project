package adg;

import javafx.scene.Node;
import javafx.scene.control.Button;
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
        for (Node fils : this.getChildren()) {  // le bouton +
            if (fils.getId().equalsIgnoreCase("bouton")) {
                Button b = (Button) fils;
                b.getStyleClass().add("addButton_hidden");
                b.setVisible(false);
                b.setPrefSize(0, 0);
            }
        }
        this.setPrefSize(900, 400);
        System.out.println("VueDiagramme : Switching to diagram");
    }

    @Override
    public void switchDiag2Home() {
        for (Node fils : this.getChildren()) {  // le bouton +
            if (fils.getId().equalsIgnoreCase("bouton")) {
                Button b = (Button) fils;
                b.getStyleClass().remove("addButton_hidden");
                b.getStyleClass().add("addButton");
                b.setVisible(true);
                b.setPrefSize(370, 270);
            } else {
                this.getChildren().clear();
            }
        }
        this.setPrefSize(500, 380);
        System.out.println("VueDiagramme : Switching to home");
    }


}
