package adg.control;
import adg.ModelUML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControllerClickDroit implements EventHandler<MouseEvent> {
    private ModelUML modelUML;

    public ControllerClickDroit(ModelUML modelUML) {
        this.modelUML = modelUML;
    }


    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            System.out.println("------------------------click droit");
            int x = (int)mouseEvent.getScreenX();
            int y = (int)mouseEvent.getScreenY();
            System.out.println("x: " + x + " y: " + y);
            if (modelUML.getEtat() == false) {
                modelUML.afficherClickDroit(x,y);
            } else {
                modelUML.masquerClickDroit();
                modelUML.afficherClickDroit(x,y);
            }
        } else {
            modelUML.masquerClickDroit();
        }

    }
}
