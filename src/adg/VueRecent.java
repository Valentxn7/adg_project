package adg;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;

public class VueRecent extends TreeView<String> implements Observateur {
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

            for (String recentfold : recentFiles) { // on parcours la liste des fichiers récents
                this.getRoot().getChildren().add(new TreeItem<String>(recentfold));
            }
        }
    }

    @Override
    public void switchHome2diag() {
        this.setVisible(false);
        this.getStyleClass().add("treeView_hidden");
        this.setPrefSize(0, 0);
    }

    @Override
    public void switchDiag2Home() {
        this.setVisible(true);
        this.getStyleClass().remove("treeView_hidden");
        this.getStyleClass().add("treeView");
        this.setPrefSize(400, 180);  // (380 - 20) / 2
    }


}
