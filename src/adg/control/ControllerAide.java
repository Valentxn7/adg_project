package adg.control;

import adg.ModelUML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;


public class ControllerAide implements EventHandler<ActionEvent> {
    private ModelUML modelUML;

    public ControllerAide(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    public void handle(ActionEvent event) {
        MenuItem source = (MenuItem) event.getSource();
        String text = source.getText();

        if (text.equals("Aide en ligne")) {
            modelUML.ouvrirPageAide(0);
        }
        if (text.equals("Aide sur le wiki")) {
            modelUML.ouvrirPageAide(1);
        }
    }
}
