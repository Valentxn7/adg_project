package adg;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerCreateProject implements EventHandler<ActionEvent> {
    private ModelUML modelUML;

    public ControllerCreateProject(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    /**
     * Ouvre une fenêtre de dialogue pour la création d'un nouveau projet.
     */
    public void handle(ActionEvent event) {
        Stage createProjetWind = new Stage();
        createProjetWind.initModality(Modality.APPLICATION_MODAL);  // empêche les intéractions avec la grande fenêtre
        createProjetWind.setTitle("Créer un nouveau projet");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label label = new Label("Entrez le nom du projet :");
        TextField projectNameField = new TextField();
        projectNameField.setId("projectNameField");
        Button createButton = new Button("Créer");

        projectNameField.setOnAction(new ControllerNewProject(modelUML, createProjetWind));
        createButton.setOnAction(new ControllerNewProject(modelUML, createProjetWind));

        vbox.getChildren().addAll(label, projectNameField, createButton);

        Scene scene = new Scene(vbox, 300, 150);
        createProjetWind.setScene(scene);
        createProjetWind.show();
    }

}
