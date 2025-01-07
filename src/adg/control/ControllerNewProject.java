package adg.control;

import adg.MainUML;
import adg.ModelUML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerNewProject implements EventHandler<ActionEvent> {
    private ModelUML modelUML;
    private Stage createProjetWind;

    public ControllerNewProject(ModelUML modelUML, Stage createWind) {
        this.modelUML = modelUML;
        this.createProjetWind = createWind;
    }

    public void handle(ActionEvent event) {
        TextField projectNameField = (TextField) createProjetWind.getScene().lookup("#projectNameField");
        // TextField projectNameField = (TextField) event.getSource();  // le label où on a entré le nom du projet
        String projectName = projectNameField.getText().trim();  // on récupère le texte du champ

        if (!projectName.isEmpty()) {  // si le champ n'est pas vide
            if (modelUML.creerProjetVierge(projectName)) {
                createProjetWind.close(); // Ferme la fenêtre
            }
        } else {
            MainUML.showErrorMessage("Le nom du projet ne peut pas être vide.");
        }
    }
}
