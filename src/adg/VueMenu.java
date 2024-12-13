package adg;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

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
