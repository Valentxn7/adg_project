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

        if (modelUML.getVueDiagramme_bouton_visibility() != this.isVisible()) {
            System.out.println("VueDiagramme_bouton: " + modelUML.getVueDiagramme_bouton_visibility());
            for (Node fils : this.getChildren()) {  // le bouton +
                if (fils.getId().equalsIgnoreCase("bouton")) {
                    Button b = (Button) fils;
                    b.getStyleClass().clear();
                    b.getStyleClass().add(modelUML.getVueDiagramme_bouton_style());
                    b.setVisible(modelUML.getVueDiagramme_bouton_visibility());
                    b.setPrefSize(modelUML.getVueDiagramme_bouton_x(), modelUML.getVueDiagramme_bouton_y());
                }
            }
            this.setPrefSize(modelUML.getVueDiagramme_x(), modelUML.getVueDiagramme_y());
        }

    }
}


