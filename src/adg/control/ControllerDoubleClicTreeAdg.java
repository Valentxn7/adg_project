package adg.control;

import adg.ModelUML;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class ControllerDoubleClicTreeAdg implements EventHandler<MouseEvent> {
    private ModelUML modelUML;

    public ControllerDoubleClicTreeAdg(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {  // Vérifie si c'est un double-clic

            TreeView<String> treeView = (TreeView<String>) mouseEvent.getSource();
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();

            if (selectedItem != null) {
                // Action à réaliser lors du double-clic sur un TreeItem
                System.out.println("Double-clic sur : " + selectedItem.getValue());
                switch (ModelUML.getFileExtensionByName(selectedItem.getValue())) {
                    case "adg":
                        System.out.println("Chargement du fichier ADG");
                        modelUML.loadADGbyName(selectedItem.getValue());
                        break;
                    case "class":
                        try {
                            System.out.println("Analyse du fichier de classe");
                            String path = modelUML.getFolder().getAbsolutePath() + File.separator + selectedItem.getValue();
                            modelUML.analyseFichier(path);
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                        break;
/*                    case "java":
                        break;*/
                    default:
                        System.err.println("Extension non reconnue ou non prise en charge");
                }
            }
        }
    }
}
