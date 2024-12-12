package adg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainUML extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Classe maClasse = new Classe("ExempleClasse");
        maClasse.ajouterAttribut("attribut1");
        maClasse.ajouterAttribut("attribut2");
        maClasse.ajouterMethode("methode1");
        maClasse.ajouterMethode("methode2");

        Vue vue = new VueClasse(maClasse);
        vue.actualiser(null);
        StackPane root = new StackPane();
        root.getChildren().add((HBox)vue);
        Scene scene = new Scene(root, 800, 600);

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
