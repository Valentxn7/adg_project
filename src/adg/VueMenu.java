package adg;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * Classe représentant la barre de menu de l'application.
 * Cette classe hérite de {@link MenuBar} et implémente l'interface {@link Observateur}.
 * Elle est utilisée pour gérer et mettre à jour les options de menu en fonction de l'état de l'application.
 */
public class VueMenu extends MenuBar implements Observateur {

    private ModelUML modelUML; // Référence au modèle UML associé

    /**
     * Constructeur de la classe VueMenu.
     * Initialise la barre de menu avec le modèle UML donné.
     *
     * @param modelUML Le modèle UML associé à cette vue.
     */
    public VueMenu(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    /**
     * Méthode appelée pour actualiser la vue lorsque le modèle est modifié.
     * Implémentation de la méthode `actualiser` définie dans l'interface {@link Observateur}.
     * <p>
     * Cette méthode est actuellement vide mais peut être étendue
     * pour réagir aux changements du modèle.
     *
     * @param mod Le sujet (modèle) qui a déclenché l'actualisation.
     */
    @Override
    public void actualiser(Sujet mod) {
        // Actuellement sans implémentation.
    }

    /**
     * Méthode pour activer toutes les options du menu lors de la transition
     * de l'affichage d'accueil à celui du diagramme.
     * Implémentation de la méthode `switchHome2diag` définie dans l'interface {@link Observateur}.
     * <p>
     * Cette méthode parcourt tous les menus et réactive leurs éléments.
     */
    @Override
    public void switchHome2diag() {
        for (Menu m : this.getMenus()) {
            for (MenuItem mi : m.getItems()) {
                mi.setDisable(false);
            }
            if (m.getText().equals("Fichier")) {
                for (MenuItem mi : m.getItems()) {
                    // On remet en Menu pour trouver Personnalisation
                    if (mi instanceof Menu sousMenu) {
                        if (sousMenu.getText().equals("Personnalisation")) {
                            sousMenu.getItems().addAll(
                                    new MenuItem("Masquer les dépendances pour tous"),
                                    new MenuItem("Masquer les héritages pour tous"),
                                    new MenuItem("Masquer les attributs pour tous"),
                                    new MenuItem("Masquer les méthodes pour tous"),
                                    new SeparatorMenuItem(),
                                    new MenuItem("Afficher les dépendances pour tous"),
                                    new MenuItem("Afficher les héritages pour tous"),
                                    new MenuItem("Afficher les attributs pour tous"),
                                    new MenuItem("Afficher les méthodes pour tous")
                            );
                        }
                    }
                }
            }
            System.out.println("Vue Menu : Switching to diagram");
        }


    }
}
