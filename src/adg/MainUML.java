package adg;

import adg.control.ControllerAccueil;
import adg.control.ControllerCreateProject;
import adg.control.ControllerOpenFile;
import adg.control.ControllerOpenFolder;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import adg.vues.*;

import java.io.File;

public class MainUML extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        ModelUML modelUML = new ModelUML(stage);
        VBox base = new VBox(0);
        VueTitre titre = new VueTitre();
        titre.setText("ADG - Home");
        modelUML.enregistrerObservateur(titre);
        HBox centre = new HBox(0);
        Label fin = new Label("Tous droits réservés");
        fin.setAlignment(javafx.geometry.Pos.CENTER);

        VBox partieGauche = new VBox(0);  // TreeView et MenuBar
        VueDiagramme partieDroite = new VueDiagramme();  // bouton add projet
        modelUML.enregistrerObservateur(partieDroite);

        Button addProjectButton = new Button("+");
        addProjectButton.setId("bouton");
        addProjectButton.setAlignment(javafx.geometry.Pos.CENTER);
        addProjectButton.setOnAction(new ControllerCreateProject(modelUML));

        partieDroite.setAlignment(javafx.geometry.Pos.CENTER);

        /*     MENU       **/

        VueMenu menuBar = new VueMenu();  // barre menu contenante
        modelUML.enregistrerObservateur(menuBar);

        Menu fileMenu = new Menu("Fichier");  // contenue

        MenuItem nouveau = new MenuItem("Nouveau");
        MenuItem ouvrirP = new MenuItem("Ouvrir un projet");
        MenuItem ouvrirS = new MenuItem("Ouvrir une sauvegarde");
        MenuItem renommer = new MenuItem("Renommer");
        MenuItem supprimer = new MenuItem("Supprimer");
        MenuItem enregistrer = new MenuItem("Enregistrer");
        MenuItem enregistrerSous = new MenuItem("Enregistrer sous");
        MenuItem exporterUml = new MenuItem("Exporter en UML");
        MenuItem exporterPng = new MenuItem("Exporter en PNG");
        Menu personnalisation = new Menu("Personnalisation");
        MenuItem accueil = new MenuItem("Accueil");

        renommer.setDisable(true);
        supprimer.setDisable(true);
        enregistrer.setDisable(true);
        enregistrerSous.setDisable(true);
        exporterUml.setDisable(true);
        exporterPng.setDisable(true);
        personnalisation.setDisable(true);
        accueil.setDisable(true);

        fileMenu.getItems().addAll(
                nouveau, ouvrirP, ouvrirS, new SeparatorMenuItem(),
                renommer, supprimer, new SeparatorMenuItem(),
                enregistrer, enregistrerSous, new SeparatorMenuItem(),
                exporterUml, exporterPng, new SeparatorMenuItem(),
                personnalisation, accueil);

        personnalisation.getItems().addAll(
                new MenuItem("Masquer les dépendances pour tous"),
                new MenuItem("Masquer les héritages pour tous"),
                new MenuItem("Masquer les attributs pour tous"),
                new MenuItem("Masquer les méthodes pour tous"),
                new SeparatorMenuItem(),
                new MenuItem("Afficher les dépendances pour tous"),
                new MenuItem("Afficher les héritages pour tous"),
                new MenuItem("Afficher les attributs pour tous"),
                new MenuItem("Afficher les méthodes pour tous")
        );

        Menu viewMenu = new Menu("Affichage");

        CheckMenuItem modeNuit = new CheckMenuItem("Mode nuit");

        //MenuItem changerPolice = new MenuItem("Changer la police");

        Label comboBoxLabel = new Label("Police :");
        comboBoxLabel.setStyle("-fx-text-fill: black;"); // Définit la couleur du texte
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Lexend", "Sans Serif", "...");
        comboBox.setValue("Lexend");
        comboBoxLabel.setLabelFor(comboBox);


        HBox labeledComboBox = new HBox(10, comboBoxLabel, comboBox); // Espacement de 10px
        labeledComboBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        labeledComboBox.setStyle("-fx-padding: 5;"); // Ajout de marges internes

        // Créer un CustomMenuItem pour contenir le HBox
        CustomMenuItem customMenuItem = new CustomMenuItem(labeledComboBox);
        customMenuItem.setHideOnClick(false); // Empêche la fermeture du menu lors de l'interaction

        viewMenu.getItems().addAll(modeNuit, customMenuItem);


        Menu helpMenu = new Menu("Aide");
        menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);

        nouveau.setOnAction(new ControllerCreateProject(modelUML));
        ouvrirP.setOnAction(new ControllerOpenFolder(modelUML, stage));
        ouvrirS.setOnAction(new ControllerOpenFile(modelUML, stage));

        accueil.setOnAction(new ControllerAccueil(modelUML));

        /*     ARBORESCENCE       **/

        TreeItem<String> rootArborescence = new TreeItem<String>();  // l'item de base
        rootArborescence.setValue("Projets ADG:");
        rootArborescence.setExpanded(true);

        VueArborescence vueArborescence = new VueArborescence();  // l'item de base
        vueArborescence.setRoot(rootArborescence);
        modelUML.enregistrerObservateur(vueArborescence);
        vueArborescence.actualiser(modelUML);

        /*     RECENTS       **/

        TreeItem<String> rootRecent = new TreeItem<String>();  // l'item de base
        rootRecent.setValue("Projets récents:");
        rootRecent.setExpanded(true);

        VueRecent vueRecent = new VueRecent();  // la TreeView affiche les TreeItem
        vueRecent.setRoot(rootRecent);
        modelUML.enregistrerObservateur(vueRecent);
        vueRecent.actualiser(modelUML);

        /*     ORGANISATION       **/

        base.getChildren().addAll(titre, centre, fin);  // VBox
        centre.getChildren().addAll(partieGauche, partieDroite);  // HBox
        partieGauche.getChildren().addAll(menuBar, vueArborescence, vueRecent);  // VBox
        partieDroite.getChildren().add(addProjectButton);  // HBox

        /*       SIZE       **/

        base.setPrefSize(900, 400);
        base.setMinSize(400, 200);
        base.setPadding(new Insets(10, 10, 10, 10));

        titre.setPrefSize(900, 20);
        centre.setPrefSize(900, 380);
        fin.setPrefSize(900, 20);

        partieGauche.setPrefSize(ModelUML.PARTIE_GAUCHE_X, ModelUML.PARTIE_GAUCHE_Y);
        menuBar.setPrefSize(400, ModelUML.MENU_BAR_Y);
        vueArborescence.setPrefSize(400, 180);  // (380 - 20) / 2
        vueRecent.setPrefSize(400, 180);  // (380 - 20) / 2

        partieDroite.setPrefSize(500, 380);
        addProjectButton.setPrefSize(370, 270);

        /*       STYLE       **/

        titre.getStyleClass().add("label-titre");
        addProjectButton.getStyleClass().add("addButton");
        menuBar.getStyleClass().add("menuBar");
        partieDroite.getStyleClass().add("menuBar");
        vueArborescence.getStyleClass().add("treeView");
        vueRecent.getStyleClass().add("treeView");
        fin.getStyleClass().add("label-fin");

        /*       lancement       **/

        Scene scene = new Scene(base, 922, 420);
        scene.getStylesheets().add(new File("ressource/style.css").toURI().toString());
        stage.setScene(scene);
        stage.setTitle("ADG - Home");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Permet l'affichage d'un message d'erreur par une fenêtre de dialogue.
     *
     * @param message le message d'erreur
     */
    public static void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
