package adg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

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
        Label fin = new Label("Tous droits réservés");
        fin.setAlignment(javafx.geometry.Pos.CENTER);

        VueDiagramme partieDroite = new VueDiagramme();  // bouton add projet


        modelUML.setVueDiagramme(partieDroite);
        modelUML.enregistrerObservateur(partieDroite);

        ControllerDragDrop controller = new ControllerDragDrop(modelUML);
        controller.activerDragAndDrop(partieDroite);
        ControleurDeplacerClasse controleurDeplacerClasse = new ControleurDeplacerClasse(modelUML);
        List<VueClasse> vueClasses = modelUML.getVueClasses();
        Scene scene = new Scene(partieDroite, 922, 420);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("ADG - Home");
        stage.show();
    }

}
