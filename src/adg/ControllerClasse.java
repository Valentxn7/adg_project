package adg;

import javafx.event.EventHandler;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

public class ControllerClasse implements EventHandler<MouseEvent> {
    private ModelUML modelUML;

    public ControllerClasse(ModelUML modelUML){
        this.modelUML = modelUML;
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED){
            VueClasse classe = (VueClasse) mouseEvent.getSource();

        }
    }
}
