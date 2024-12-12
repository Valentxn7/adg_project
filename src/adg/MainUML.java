package adg;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainUML extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        VBox base = new VBox(0);
        Label titre = new Label("ADG - Home");
        HBox centre = new HBox(0);
        Label fin = new Label("Tous droits réservés");
        fin.setAlignment(javafx.geometry.Pos.CENTER);

        VBox partieGauche = new VBox(0);  // TreeView et MenuBar
        HBox partieDroite = new HBox(0);  // bouton add projet
        Button addProjectButton = new Button("+");
        addProjectButton.setAlignment(javafx.geometry.Pos.CENTER);
        partieDroite.setAlignment(javafx.geometry.Pos.CENTER);

        MenuBar menuBar = new MenuBar();  // barre menu contenante
        Menu fileMenu = new Menu("Fichier");  // contenue
        Menu viewMenu = new Menu("Affichage");
        Menu helpMenu = new Menu("Aide");
        menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);

        TreeItem<String> projetR = new TreeItem<>("Projets récents:");  // l'item de base
        projetR.setExpanded(true);
        projetR.getChildren().addAll(  // ses fils
                new TreeItem<>("Projet 1"),
                new TreeItem<>("Projet 2"),
                new TreeItem<>("Projet 3")
        );

        TreeView<String> treeView = new TreeView<>(projetR);  // la TreeView affiche les TreeItem


        base.getChildren().addAll(titre, centre, fin);  // VBox
        centre.getChildren().addAll(partieGauche, partieDroite);  // HBox
        partieGauche.getChildren().addAll(menuBar, treeView);  // VBox
        partieDroite.getChildren().add(addProjectButton);  // HBox

        base.setPrefSize(900, 400);
        base.setMinSize(400, 200);
        base.setPadding(new Insets(10, 10, 10, 10));

        titre.setPrefSize(900, 20);
        centre.setPrefSize(900, 380);
        fin.setPrefSize(900, 20);

        partieGauche.setPrefSize(400, 380);
        menuBar.setPrefSize(400, 20);
        treeView.setPrefSize(400, 360);

        partieDroite.setPrefSize(500, 380);
        addProjectButton.setPrefSize(370, 270);

        titre.getStyleClass().add("label-titre");
        addProjectButton.getStyleClass().add("addButton");
        menuBar.getStyleClass().add("menuBar");
        partieDroite.getStyleClass().add("menuBar");
        treeView.getStyleClass().add("treeView");
        fin.getStyleClass().add("label-fin");

        Scene scene = new Scene(base, 922, 420);
        //scene.getStylesheets().add(getClass().getResource("/ressources/style.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setTitle("ADG - Home");
        stage.setResizable(false);
        stage.show();
    }
}
