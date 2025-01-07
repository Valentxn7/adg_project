package adg.vues;

import adg.ModelUML;
import adg.Observateur;
import adg.Sujet;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

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
        ModelUML modelUML = (ModelUML) mod;

        ArrayList<String> menuBar = modelUML.getMenuBar();
        System.out.println("liste menu a parcourir: " + menuBar);
        for (Menu m : this.getMenus()) {  // on parcours les menus (Fichier, Affichage, ...)

            if (menuBar.contains(m.getText())) {  // Si le menu est dans la liste des menus à afficher

                HashMap<String, Boolean> menuItems = modelUML.getMenuItems(menuBar.indexOf(m.getText()));  // on récupère les items du menu correspondant

                for (MenuItem mi : m.getItems()) {  // on parcours les items du menu
                    if (!(mi.getText() == null)) {
                        if (menuItems.containsKey(mi.getText())) {  // si l'item est dans la liste des items à afficher
                            System.out.println("Item: " + mi.getText() + " | " + menuItems.get(mi.getText()));
                            mi.setDisable(!menuItems.get(mi.getText()));  // on active ou désactive l'item
                        }
                    }
                }

            }
        }
    }

}
