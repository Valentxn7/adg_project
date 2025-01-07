package vues;

import adg.ModelUML;
import adg.Observateur;
import adg.Sujet;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import java.util.ArrayList;

public class VueRecent extends TreeView<String> implements Observateur {

    @Override
    public void actualiser(Sujet mod) {
        ModelUML model = (ModelUML) mod;

        if (model.getVueRecentVisibility() != this.isVisible()) {  // valeur de check pour éviter de faire des opérations inutiles si la vue est déjà comme on veut qu'elle soit
            System.out.println("VueRecent: " + model.getVueRecentVisibility());
            this.setVisible(model.getVueRecentVisibility());
            this.getStyleClass().clear();
            this.getStyleClass().add(model.getVueRecent_style());
            this.setPrefSize(model.getVueRecent_x(), model.getVueRecent_y());
        }

        if (model.getIsHome()) {  // car la vue des récents ne sert que sur l'écran d'accueil donc pas besoin de l'actualiser si on est sur le diagramme
            ArrayList<String> recentFiles = ModelUML.getRecentFolders();
            this.getChildren().clear();

            for (String recentfold : recentFiles) { // on parcours la liste des fichiers récents
                this.getRoot().getChildren().add(new TreeItem<String>(recentfold));
            }

        }
    }

}
