package adg.control;

import adg.MainUML;
import adg.ModelUML;
import adg.data.Export;
import adg.data.Save;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ControllerSaveAs implements EventHandler<ActionEvent> {
    private ModelUML modelUML;
    private Stage rootStage;

    public ControllerSaveAs(ModelUML modelUML, Stage rootStage) {
        this.modelUML = modelUML;
        this.rootStage = rootStage;
    }

    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer sous...");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Sauvegarde ADG", "*.adg")
        );

        fileChooser.setInitialFileName(modelUML.getProjectName());

        File selectedFile = fileChooser.showSaveDialog(rootStage);

        if (selectedFile == null) {
            System.out.println("Aucun fichier sélectionné.");
            return;
        }

        String path = selectedFile.getAbsolutePath();
        Save.save(modelUML.getClasses(), path, modelUML.getProjectName());

        System.out.println("Enregistrer sous vers: " + path);
    }
}
