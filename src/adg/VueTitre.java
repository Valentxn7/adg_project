package adg;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Classe représentant un titre dans l'interface utilisateur.
 * Cette classe hérite de {@link Label} et implémente l'interface {@link Observateur}.
 * Elle est utilisée pour afficher et mettre à jour le titre de la fenêtre en fonction de l'état du modèle.
 */
public class VueTitre extends Label implements Observateur {

    private ModelUML modelUML; // Référence au modèle UML associé

    /**
     * Constructeur de la classe VueTitre.
     * Initialise le titre avec le modèle UML donné.
     *
     * @param modelUML Le modèle UML associé à cette vue.
     */
    public VueTitre(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    /**
     * Méthode appelée pour actualiser le titre lorsque le modèle est modifié.
     * Implémentation de la méthode `actualiser` définie dans l'interface {@link Observateur}.
     * Met à jour le texte du titre en utilisant les informations du modèle.
     *
     * @param mod Le sujet (modèle) qui a déclenché l'actualisation.
     */
    @Override
    public void actualiser(Sujet mod) {
        this.setText("ADG - " + modelUML.getWindowsTitle());
    }
    /**
     * Méthode pour mettre à jour le titre lors de la transition
     * de l'affichage d'accueil à celui du diagramme.
     * Implémentation de la méthode `switchHome2diag` définie dans l'interface {@link Observateur}.
     * Cette méthode appelle `actualiser` pour synchroniser le titre avec le modèle.
     */
}
