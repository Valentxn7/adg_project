package adg.control;

import adg.MainUML;
import adg.ModelUML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ControllerPolice implements EventHandler<ActionEvent> {
    private ModelUML modelUML;
    private Stage rootStage;

    public ControllerPolice(ModelUML modelUML, Stage rootStage) {
        this.modelUML = modelUML;
        this.rootStage = rootStage;
    }

    public void handle(ActionEvent event) {
        if (event.getSource() instanceof ComboBox) {
            ComboBox<String> comboBox = (ComboBox<String>) event.getSource();

            String selectedFont = comboBox.getValue();

            modelUML.setFont(selectedFont, rootStage);
        }
    }
}
