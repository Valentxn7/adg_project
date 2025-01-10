package adg.control;

import adg.ModelUML;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * Controleur pour quitter l'application
 */

public class ControleurQuitter implements EventHandler<ActionEvent> {

    private ModelUML model;

    /**
     * Constructeur du controleur
     * @param model
     */
    public ControleurQuitter(ModelUML model) {
        this.model = model;
    }

    /**
     * Affiche une boîte de dialogue pour demander à l'utilisateur s'il veut enregistrer le projet avant de quitter
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // Boîte de dialogue pour demander
        alert.setTitle("Enregistrer le projet");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous enregistrer ?");

        ButtonType saveAsButton = new ButtonType("Enregistrer sous"); // Bouton pour enregistrer sous
        ButtonType saveButton = new ButtonType("Enregistrer"); //bouton pour enregistrer
        ButtonType dontSaveButton = new ButtonType("Ne pas enregistrer"); // bouton pour ne pas enregistrer
        ButtonType cancelButton = new ButtonType("Annuler", ButtonType.CANCEL.getButtonData()); // bouton pour annuler

        alert.getButtonTypes().setAll(saveAsButton, saveButton, dontSaveButton, cancelButton); // Ajout des boutons

        Optional<ButtonType> result = alert.showAndWait(); // Affichage de la boîte de dialogue
        if (result.isPresent()) {
            switch (result.get().getText()) {
                case "Enregistrer sous":
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Enregistrer le projet sous");
                    File file = fileChooser.showSaveDialog(model.getStage());
                    if (file != null) {
                        model.sauvegarderSousProjet(file.getAbsolutePath());
                        Platform.exit();
                    }
                    break;
                case "Enregistrer":
                    model.sauvegarderProjet();
                    Platform.exit();
                    break;
                case "Ne pas enregistrer":
                    Platform.exit();
                    break;
                default:
                    break;
            }
        }
    }
}
