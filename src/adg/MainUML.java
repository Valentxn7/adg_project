package adg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainUML extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Création de l'objet Classe
        Classe maClasse = new Classe("Person");

        // Configuration des champs (attributs)
        List<String[]> fields = new ArrayList<>();
        fields.add(new String[]{"private", "name", "String"});
        fields.add(new String[]{"private", "age", "int"});
        maClasse.setFields(fields);

        // Configuration des constructeurs
        List<Object[]> constructors = new ArrayList<>();
        constructors.add(new Object[]{"public", Arrays.asList("String name", "int age")});
        maClasse.setConstructors(constructors);

        // Configuration des méthodes
        List<Object[]> methods = new ArrayList<>();
        methods.add(new Object[]{"public", "getName", Arrays.asList(), "String"});
        methods.add(new Object[]{"public", "setName", Arrays.asList("String name"), "void"});
        methods.add(new Object[]{"public", "getAge", Arrays.asList(), "int"});
        methods.add(new Object[]{"public", "setAge", Arrays.asList("int age"), "void"});
        maClasse.setMethods(methods);

        // Création de la vue associée
        VueClasse vueClasse = new VueClasse(maClasse);

        // Mise en place de la scène et du conteneur
        VBox root = new VBox();
        root.getChildren().add(vueClasse);

        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm()); // Optionnel : ajout d'un CSS
        primaryStage.setScene(scene);
        primaryStage.setTitle("Représentation UML de la classe");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

