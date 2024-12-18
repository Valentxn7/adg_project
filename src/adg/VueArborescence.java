package adg;

import javafx.scene.control.TreeItem;
import javafx.scene.layout.StackPane;

import java.io.File;

public class VueArborescence extends TreeItem<String> implements Observateur {
    private ModelUML modelUML;

    public VueArborescence(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    @Override
    public void actualiser(Sujet mod) {
        ModelUML model = (ModelUML) mod;
        File root = model.getFolder();
        this.setValue(root.getName());
        this.getChildren().clear();

        for (File child : root.listFiles()) {
            this.getChildren().add(RefreshArboresence(child));
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
        this.actualiser(this.modelUML);
    }


}
