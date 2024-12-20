package adg;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

public class VueMenu extends MenuBar implements Observateur {
    private ModelUML modelUML;

    public VueMenu(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    @Override
    public void actualiser(Sujet mod) {
        ModelUML modelUML = (ModelUML) mod;

        ArrayList<String> menuBar = modelUML.getMenuBar();
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
