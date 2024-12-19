package adg;

import javafx.scene.layout.StackPane;

/**
 * Classe représentant la vue d'un diagramme dans l'application.
 * Cette classe hérite de {@link StackPane} et implémente l'interface {@link Observateur}.
 * Elle permet de visualiser et gérer les diagrammes liés au modèle UML.
 */
public class VueDiagramme extends StackPane implements Observateur {

    private ModelUML modelUML; // Référence au modèle UML associé

    /**
     * Constructeur de la classe VueDiagramme.
     * Initialise la vue avec le modèle UML donné.
     *
     * @param modelUML Le modèle UML associé à cette vue.
     */
    public VueDiagramme(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    /**
     * Méthode appelée pour actualiser la vue lorsque le modèle est modifié.
     * Implémentation de la méthode `actualiser` définie dans l'interface {@link Observateur}.
     *
     * @param mod Le sujet (modèle) qui a déclenché l'actualisation.
     */
    @Override
    public void actualiser(Sujet mod) {
        System.out.println("Création d'un projet");
    }

    /**
     * Méthode pour passer de l'affichage d'accueil à celui du diagramme.
     * Efface tous les éléments enfants de la vue et ajuste sa taille.
     * Implémentation de la méthode `switchHome2diag` définie dans l'interface {@link Observateur}.
     */
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
