package adg;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class VueMenu extends MenuBar implements Observateur {
    private ModelUML modelUML;

    public VueMenu(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    @Override
    public void actualiser(Sujet mod) {
    }

    @Override
    public void switchHome2diag() {
        for (Menu m : this.getMenus()) {
            for (MenuItem mi : m.getItems()) {
                mi.setDisable(false);
            }
            System.out.println("Vue Menu : Switching to diagram");
        }
    }

    @Override
    public void switchDiag2Home() {
        for (Menu m : this.getMenus()) {  // On parcourt les menus (Fichier, Affichage, ...)
            if (m.getText().equals("Fichier")) {
                for (MenuItem mi : m.getItems()) {  // On parcourt les items du menu Fichier

                    // On désactive tous les items sauf Nouveau, Ouvrir un projet et Ouvrir une sauvegarde
                    if (mi.getText() != null) { // nécessaire car les barre de séparation ont leur getText = null et ça fait crash les .equals
                        if (mi.getText().equals("Nouveau") || mi.getText().equals("Ouvrir un projet") || mi.getText().equals("Ouvrir une sauvegarde"))
                            mi.setDisable(false);
                        else
                            mi.setDisable(true);
                    }
                }
            }
            System.out.println("Vue Menu : Switching to diagram");
        }
    }
}
