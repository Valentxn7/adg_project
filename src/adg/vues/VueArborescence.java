package adg.vues;

import adg.ModelUML;
import adg.Observateur;
import adg.Sujet;
import adg.control.ControllerDoubleClicTreeAdg;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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

        String[] list = {"adg", "class"};
        ArrayList<String> extension = new ArrayList<String>(Arrays.asList(list));

        //RefreshArboresencev2(this.getRoot(), root, new ArrayList<>(Arrays.asList("adg", "class", "java")));

        /*for (File child : root.listFiles()) {
            if (extension.contains(ModelUML.getFileExtension(child)) || child.isDirectory()) {
                this.getRoot().getChildren().add(RefreshArboresence(child));
            }
        }*/

        for (File child : root.listFiles()) {
            RefreshArboresencev2(this.getRoot(), child, extension);
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

    /**
     * Remplit un TreeItem de manière récursive en ne gardant que les fichiers et dossiers pertinents.
     *
     * @param base     le TreeItem parent
     * @param file       le fichier/dossier à analyser
     * @param extensions les extensions valides
     * @return true si le fichier/dossier contient des éléments valides, false sinon
     */
    private boolean RefreshArboresencev2(TreeItem<String> base, File file, ArrayList<String> extensions) {
        if (file.isFile()) {  // si on a une feuille
            if (extensions.contains(ModelUML.getFileExtensionByName(file.getName()))) { // vérifier si le fichier a une extension valide
                base.getChildren().add(new TreeItem<>(file.getName()));
                return true;
            }
            return false;

        } else if (file.isDirectory()) {
            TreeItem<String> dossier = new TreeItem<>(file.getName());
            boolean isOK = false;

            for (File child : file.listFiles()) {
                if (RefreshArboresencev2(dossier, child, extensions)) {
                    isOK = true;
                }
            }

            if (isOK) {
                base.getChildren().add(dossier);
                return true;
            }
        }
        return false;
    }
}

