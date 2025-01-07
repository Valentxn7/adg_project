package adg;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class ControllerOpenFolder implements EventHandler<ActionEvent> {
    private ModelUML modelUML;
    private Stage rootStage;

    public ControllerOpenFolder(ModelUML modelUML, Stage rootStage) {
        this.modelUML = modelUML;
        this.rootStage = rootStage;
    }

    /**
     * Ouvre un explorateur pour sélectionner un dossier (projet).
     *
     * @return Le chemin du dossier sélectionné, ou null si aucun dossier n'a été sélectionné.
     */
    public void handle(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Ouvrir un projet");
        File selectedDirectory = directoryChooser.showDialog(rootStage);

        if (selectedDirectory != null) {
            String path = selectedDirectory.getAbsolutePath();
            System.out.println("Ouverture du projet : " + path);
            modelUML.ouvrirProjet(selectedDirectory);
        }
    }


}
