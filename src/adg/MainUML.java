package adg;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

public class MainUML extends Application {
    private ModelUML modelUML;
    private Stage rootStage;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        rootStage = stage;
        modelUML = new ModelUML();
        VBox base = new VBox(0);
        VueTitre titre = new VueTitre(modelUML);
        titre.setText("ADG - Home");
        modelUML.enregistrerObservateur(titre);
        HBox centre = new HBox(0);
        Label fin = new Label("Tous droits réservés");
        fin.setAlignment(javafx.geometry.Pos.CENTER);

        //VBox partieGauche = new VBox(0);  // TreeView et MenuBar
        VueDiagramme partieDroite = new VueDiagramme();  // bouton add projet


        modelUML.setVueDiagramme(partieDroite);
        modelUML.enregistrerObservateur(partieDroite);

        ControllerDragDrop controller = new ControllerDragDrop(modelUML);
        controller.activerDragAndDrop(partieDroite);
        Scene scene = new Scene(partieDroite, 922, 420);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("ADG - Home");
        stage.setResizable(false);
        stage.show();
    }

}
