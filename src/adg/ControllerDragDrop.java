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

            // on sauvegarde les coordonnées du drop pour les utiliser dans le diagramme
            // Récupérer les coordonnées dans le StackPane
            StackPane root = (StackPane) event.getSource();

            // Convertir les coordonnées de la scène en coordonnées locales
            double x = root.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
            double y = root.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();
            System.out.println("Position : " + (x) + " " + (y));
            if (event.getDragboard().hasFiles()) {

                List<File> files = event.getDragboard().getFiles();

                for (File file : files) {
                    if (file.getName().endsWith(".java")) {
                        model.setFilePath(file.getAbsolutePath());
                        System.out.println("Fichier déposé : " + file.getAbsolutePath());
                        succes = true;
                        try {
                            model.annalyseFichier(file.getAbsolutePath());
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            event.setDropCompleted(succes);
            event.consume();
        }
    }
}
