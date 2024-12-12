package adg;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControleurCreateProject implements EventHandler<ActionEvent> {
    private ModelUML modelUML;

    public ControleurCreateProject(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    @Override
    public void handle(ActionEvent event) {
        Button b = (Button) event.getSource();
        if (b.getText().equals("+")) {
            modelUML.creerProjetVierge();
        }
    }
}
