package adg.control;

import adg.ModelUML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControllerAccueil implements EventHandler<ActionEvent> {
    private ModelUML modelUML;

    public ControllerAccueil(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    public void handle(ActionEvent event) {
        modelUML.setWindowsTitle("Home");
        modelUML.switchState(true);
    }

}
