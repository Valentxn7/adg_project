package adg;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * Classe représentant la vue d'un diagramme dans l'application.
 * Cette classe hérite de {@link StackPane} et implémente l'interface {@link Observateur}.
 * Elle permet de visualiser et gérer les diagrammes liés au modèle UML.
 */
public class VueDiagramme extends StackPane implements Observateur {





    /**
     * Méthode appelée pour actualiser la vue lorsque le modèle est modifié.
     * Implémentation de la méthode `actualiser` définie dans l'interface {@link Observateur}.
     *
     * @param mod Le sujet (modèle) qui a déclenché l'actualisation.
     */
    @Override
    public void actualiser(Sujet mod) {

        ModelUML model = (ModelUML) mod;
        System.out.println(model.getIsHome());
        if(model.getIsHome()) {
            this.switchDiag2Home();
        } else {
            this.switchHome2diag();
        }
    }

    /**
     * Méthode pour passer de l'affichage d'accueil à celui du diagramme.
     * Efface tous les éléments enfants de la vue et ajuste sa taille.
     * Implémentation de la méthode `switchHome2diag` définie dans l'interface {@link Observateur}.
     */

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

    public void switchDiag2Home() {
        for (Node fils : this.getChildren()) {  // le bouton +
            if (fils.getId().equalsIgnoreCase("bouton")) {
                Button b = (Button) fils;
                b.getStyleClass().remove("addButton_hidden");
                b.getStyleClass().add("addButton");
                b.setVisible(true);
                b.setPrefSize(370, 270);
            }
        }
        this.setPrefSize(500, 380);
        System.out.println("VueDiagramme : Switching to home");
    }


}
