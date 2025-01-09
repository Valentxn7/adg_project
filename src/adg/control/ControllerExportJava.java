package adg.control;

import adg.MainUML;
import adg.ModelUML;
import adg.data.Export;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * ControllerExportJava
 * Exporter le diagramme UML en Java
 */

public class ControllerExportJava implements EventHandler<ActionEvent> {

    private ModelUML modelUML;
    private Stage rootStage;

    public ControllerExportJava(ModelUML modelUML, Stage rootStage) {
        this.modelUML = modelUML;
        this.rootStage = rootStage;
    }

    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter le diagramme UML en Java");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers Java", "*.java")
        );

        fileChooser.setInitialFileName("export_squelette");

        File selectedFile = fileChooser.showSaveDialog(rootStage);

        if (selectedFile == null) {
            System.out.println("Aucun fichier sélectionné.");
            return;
        }

        String path = selectedFile.getAbsolutePath();

        System.out.println("Export du fichier vers : " + path);
        Export.exportJava(this.modelUML.getClasses(), new File(path).getParent(), new File(path).getName());
    }
}