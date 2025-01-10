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

public class ControleurQuitter implements EventHandler<ActionEvent> {

    private ModelUML model;

    public ControleurQuitter(ModelUML model) {
        this.model = model;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Enregistrer le projet");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous enregistrer ?");

        ButtonType saveAsButton = new ButtonType("Enregistrer sous");
        ButtonType saveButton = new ButtonType("Enregistrer");
        ButtonType dontSaveButton = new ButtonType("Ne pas enregistrer");
        ButtonType cancelButton = new ButtonType("Annuler", ButtonType.CANCEL.getButtonData());

        alert.getButtonTypes().setAll(saveAsButton, saveButton, dontSaveButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
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
