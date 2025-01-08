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

            int x2 = (int)mouseEvent.getX();
            int y2 = (int)mouseEvent.getY();

            boolean estLibre = modelUML.estLibre(x2,y2);
            if (estLibre) {
                System.out.println("x: " + x2 + " y: " + y2);
                if (modelUML.getEtat() == false) {
                    modelUML.afficherClickDroit(x,y);
                } else {
                    modelUML.masquerClickDroit();
                    modelUML.afficherClickDroit(x,y);
                }
            }else {
                if (modelUML.getEtatClickDroitClasse() == false) {
                    modelUML.afficherClickDroitClasse(x,y);
                } else {
                    modelUML.masquerClickDroit();
                    modelUML.afficherClickDroitClasse(x, y);
                }
            }


        } else {
            modelUML.masquerClickDroit();
        }

    }
}
