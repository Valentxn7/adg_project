package adg.vues;

import adg.ModelUML;
import adg.Observateur;
import adg.Sujet;
import adg.control.ControllerDoubleClicTreeAdg;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;

public class VueArborescence extends TreeView<String> implements Observateur {

    public VueArborescence(ControllerDoubleClicTreeAdg cont){
        this.setOnMouseClicked(cont);
    }

    @Override
    public void actualiser(Sujet mod) {
        ModelUML model = (ModelUML) mod;

        // mon pdv = vaux mieux faire 2 test que changer des éléments graphiques (surtout que ça change pas souvent)
        this.setMinWidth(model.getVueArbo_x());
        this.setPrefSize(model.getVueArbo_x(), model.getVueArbo_y());

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
}

