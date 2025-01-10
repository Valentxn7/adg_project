package adg.control;

import adg.MainUML;
import adg.ModelUML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ControllerNightMode implements EventHandler<ActionEvent> {
    private ModelUML modelUML;
    private Stage rootStage;

    public ControllerNightMode(ModelUML modelUML, Stage rootStage) {
        this.modelUML = modelUML;
        this.rootStage = rootStage;
    }

    public void handle(ActionEvent event) {
        if (event.getSource() instanceof CheckMenuItem) {
            CheckMenuItem checkMenuItem = (CheckMenuItem) event.getSource();

            boolean selected = checkMenuItem.isSelected();

            MainUML.setNightMode(selected, rootStage);
        }
    }
}
