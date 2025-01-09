package adg.control;

import adg.ModelUML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControllerDeleteSave implements EventHandler<ActionEvent> {
    private ModelUML modelUML;

    public ControllerDeleteSave(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    public void handle(ActionEvent event) {
        modelUML.deleteSave();
    }

}
