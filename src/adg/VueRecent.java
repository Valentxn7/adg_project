package adg;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.ArrayList;

public class VueRecent extends TreeItem<String> implements Observateur {
    private ModelUML modelUML;

    public VueRecent(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    @Override
    public void actualiser(Sujet mod) {
        ModelUML model = (ModelUML) mod;
        if (model.getIsHome()) {  // car la vue des récents ne sert que sur l'écran d'accueil donc pas besoin de l'actualiser si on est sur le diagramme
            ArrayList<String> recentFiles = model.getRecentFolders();
            this.getChildren().clear();

            for (String fold : recentFiles) {
                this.getChildren().add(new TreeItem<String>(fold));
            }
        }
    }

    /**
     * Crée un TreeItem pour un fichier/dossier.
     *
     * @param file le fichier/dossier
     * @return le TreeItem
     */
    private TreeItem<String> RefreshArboresence(File file) {
        // Nœud principal avec le nom du fichier/dossier
        TreeItem<String> treeItem = new TreeItem<>(file.getName());

        if (file.isDirectory()) {
            File[] files = file.listFiles(); // Liste des fichiers/sous-dossiers
            if (files != null) {
                for (File child : files) {
                    treeItem.getChildren().add(RefreshArboresence(child));
                }
            }
        }
        return treeItem;
    }

    @Override
    public void switchHome2diag() {
        // masque cette vue devenue inutile
        this.getChildren().clear();
        this.setValue("");
        this.setExpanded(false);
    }

    @Override
    public void switchDiag2Home() {
        this.setValue("Projets récents:");
        this.setExpanded(true);
        this.actualiser(this.modelUML);
    }


}
