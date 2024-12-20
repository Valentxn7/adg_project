package adg;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;

public class VueArborescence extends TreeView<String> implements Observateur {
    private ModelUML modelUML;

    public VueArborescence(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    @Override
    public void actualiser(Sujet mod) {
        ModelUML model = (ModelUML) mod;
        File root = model.getFolder();
        this.getRoot().setValue(root.getName());
        this.getRoot().getChildren().clear();

        for (File child : root.listFiles()) {
            this.getRoot().getChildren().add(RefreshArboresence(child));
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
    /*
    @Override
    public void switchHome2diag() {
        this.setPrefSize(380, 450);  // (400 - 20)
        this.actualiser(this.modelUML);
    }

    @Override
    public void switchDiag2Home() {
        this.setPrefSize(400, 180);  // (380 - 20) / 2
        this.actualiser(this.modelUML);
    }
    */

}
