package adg.control;

import adg.ModelUML;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.util.List;

/**
 * Contrôleur gérant le drag-and-drop dans l'interface utilisateur.
 * Permet de déposer des fichiers sur une zone spécifique pour qu'ils
 * soient analysés et ajoutés au modèle UML.
 */
public class ControllerDragDrop implements EventHandler<DragEvent> {

    private final ModelUML model; // Référence au modèle UML

    /**
     * Constructeur qui initialise le contrôleur avec le modèle donné.
     *
     * @param model le modèle UML à utiliser pour le traitement des fichiers déposés.
     */
    public ControllerDragDrop(ModelUML model) {
        this.model = model;
    }

    /**
     * Active les fonctionnalités de drag-and-drop sur un élément graphique.
     *
     * @param root le conteneur graphique (StackPane) sur lequel le drag-and-drop
     *             est activé.
     */
    public void activerDragAndDrop(StackPane root) {
        root.setOnDragOver(this);
        root.setOnDragDropped(this);
    }

    /**
     * Gère les événements de drag-and-drop.
     * Accepte les fichiers déposés et les analyse si ce sont des fichiers Java.
     *
     * @param event l'événement de drag-and-drop.
     */
    @Override
    public void handle(DragEvent event) {

        if (event.getEventType() == DragEvent.DRAG_OVER) {

            // Vérifie si le contenu peut être accepté (fichiers uniquement)
            if (event.getGestureSource() != event.getSource() && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();

        } else if (event.getEventType() == DragEvent.DRAG_DROPPED) {

            boolean succes = false;

            // Sauvegarde les coordonnées du drop pour les utiliser dans le diagramme
            StackPane root = (StackPane) event.getSource();
            double x = root.sceneToLocal(event.getSceneX(), event.getSceneY()).getX();
            double y = root.sceneToLocal(event.getSceneX(), event.getSceneY()).getY();
            System.out.println("Position : " + x + " " + y);

            if (event.getDragboard().hasFiles()) {

                // Récupère la liste des fichiers déposés
                List<File> files = event.getDragboard().getFiles();

                for (File file : files) {
                    // Vérifie si le fichier est un fichier Java
                    if (file.getName().endsWith(".class")) {
                        model.setFilePath(file.getAbsolutePath());
                        System.out.println("Fichier déposé : " + file.getAbsolutePath());
                        succes = true;
                        try {
                            model.analyseFichier(file.getAbsolutePath());
                        } catch (ClassNotFoundException e) {
                            System.out.println("Erreur lors de l'analyse du fichier");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            // Indique si le dépôt a été complété avec succès
            event.setDropCompleted(succes);
            event.consume();
        }
    }
}
