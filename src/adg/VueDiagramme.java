package adg;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Classe représentant la vue d'un diagramme dans l'application.
 * Cette classe hérite de {@link StackPane} et implémente l'interface {@link Observateur}.
 * Elle permet de visualiser et gérer les diagrammes liés au modèle UML.
 */
public class VueDiagramme extends Pane implements Observateur {





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
    }




}
