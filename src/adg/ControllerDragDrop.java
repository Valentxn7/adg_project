package adg;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.util.List;

public class ControllerDragDrop implements EventHandler<DragEvent> {

    private final ModelUML model;

    public ControllerDragDrop(ModelUML model) {

        this.model = model;
    }

    public void activerDragAndDrop(StackPane root) {
        root.setOnDragOver(this);
        root.setOnDragDropped(this);
    }

    @Override
    public void handle(DragEvent event) {

        if (event.getEventType() == DragEvent.DRAG_OVER) {

            if (event.getGestureSource() != event.getSource() && event.getDragboard().hasFiles()) {

                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();

        } else if (event.getEventType() == DragEvent.DRAG_DROPPED) {

            boolean succes = false;

            if (event.getDragboard().hasFiles()) {

                List<File> files = event.getDragboard().getFiles();

                for (File file : files) {

                    if (file.getName().endsWith(".java")) {
                        model.setFilePath(file.getAbsolutePath());
                        System.out.println("Fichier déposé : " + file.getAbsolutePath());
                        succes = true;
                    }
                }
            }
            event.setDropCompleted(succes);
            event.consume();
        }
    }
}
