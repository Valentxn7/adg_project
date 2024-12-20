package adg;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControllerAccueil implements EventHandler<ActionEvent> {
    private ModelUML modelUML;

    public ControllerAccueil(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    public void handle(ActionEvent event) {
        modelUML.switchState(true);
        modelUML.setWindowsTitle("ADG - Home");
    }

}
