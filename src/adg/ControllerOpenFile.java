package adg;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ControllerOpenFile implements EventHandler<ActionEvent> {
    private ModelUML modelUML;
    private Stage rootStage;

    public ControllerOpenFile(ModelUML modelUML, Stage rootStage) {
        this.modelUML = modelUML;
        this.rootStage = rootStage;
    }

    /**
     * Ouvre un explorateur pour sélectionner un fichier .adg (sauvegarde).
     *
     * @return Le chemin du fichier sélectionné, ou null si aucun fichier n'a été sélectionné.
     */
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir une sauvegarde");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers ADG", "*.adg"));

        File selectedFile = fileChooser.showOpenDialog(rootStage);
        if (selectedFile != null) {
            System.out.println("Ouverture de la sauvegarde : " + selectedFile.getAbsolutePath());
            /**      ENDROIT OU TRAITER LES FICHIERS PLUS TARDS VOIR AVEC RYAN    **/
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }

}
