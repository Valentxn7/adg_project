package adg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainUML extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();

        ModelUML model = new ModelUML();
        ControllerDragDrop controller = new ControllerDragDrop(model);
        controller.enableDragAndDrop(root);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
