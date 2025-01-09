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

public class ControllerSave implements EventHandler<ActionEvent> {
    private ModelUML modelUML;
    private Stage rootStage;

    public ControllerSave(ModelUML modelUML, Stage rootStage) {
        this.modelUML = modelUML;
        this.rootStage = rootStage;
    }

    public void handle(ActionEvent event) {
        String path = modelUML.getFolderPath();

        modelUML.sauvegarderProjet();

        System.out.println("Enregistrer vers: " + path);
    }
}
