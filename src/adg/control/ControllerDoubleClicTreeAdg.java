package adg.control;

import adg.ModelUML;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

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
                modelUML.loadADGbyPath(modelUML.getADGFolferPath() + selectedItem.getValue());

            }
        }
    }
}
