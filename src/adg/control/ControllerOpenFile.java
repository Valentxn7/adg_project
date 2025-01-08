package adg.control;

import adg.ModelUML;
import adg.data.Classe;
import adg.data.Load;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

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
     */
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir une sauvegarde");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers ADG", "*.adg"));

        File selectedFile = fileChooser.showOpenDialog(rootStage);
        if (selectedFile != null) {
            System.out.println("Ouverture de la sauvegarde : " + selectedFile.getAbsolutePath());


            ArrayList<Classe> classes = Load.load(selectedFile.getAbsolutePath());

            for(Classe c : classes){
                modelUML.ajouterClasse(c);
            }
            modelUML.switchState(false);







        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }

}
