package adg;

import adg.control.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import adg.vues.*;

import java.io.File;

public class MainUML extends Application {
    private ModelUML modelUML;
    private Stage rootStage;

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

        VuePartieGauche partieGauche = new VuePartieGauche(0);  // TreeView et MenuBar
        VueDiagramme partieDroite = new VueDiagramme();  // bouton add projet
        modelUML.enregistrerObservateur(partieDroite);
        modelUML.setVueDiagramme(partieDroite);
        ControllerDragDrop controllerDragDrop = new ControllerDragDrop(modelUML);

        controllerDragDrop.activerDragAndDrop(partieDroite);
        Button addProjectButton = new Button("+");
        addProjectButton.setId("bouton");
        addProjectButton.setAlignment(javafx.geometry.Pos.CENTER);
        addProjectButton.setOnAction(new ControllerCreateProject(modelUML));



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

        Scene scene = new Scene(base, 922, 470);
        scene.getStylesheets().add(new File("ressource/style.css").toURI().toString());
        stage.setScene(scene);
        stage.setTitle("ADG - Home");
        stage.setResizable(true);
        stage.setMinHeight(460);
        stage.setMinWidth(940);

        base.setMinSize(900, 400);
        //base.setPrefSize(stage.getMaxWidth(), stage.getMaxHeight());
        base.setPadding(new Insets(10, 10, 10, 10));

//        titre.setPrefSize(900, 20);
//        centre.setPrefSize(900, 380);
//        fin.setPrefSize(900, 20);
        titre.setPrefSize(stage.getMaxWidth(), 20);
        centre.setPrefSize(stage.getMaxWidth(), 380);
        fin.setPrefSize(stage.getMaxWidth(), 20);

        partieGauche.setMinSize(ModelUML.PARTIE_GAUCHE_X, ModelUML.PARTIE_GAUCHE_Y);
        partieGauche.setPrefSize(ModelUML.PARTIE_GAUCHE_X, stage.getMinHeight() - 20 - 20);

        menuBar.setPrefHeight(ModelUML.MENU_BAR_Y);

        vueArborescence.setMinSize(ModelUML.PARTIE_GAUCHE_X, (double) (stage.getMinHeight() - 100) / 2);  // (380 - 20) / 2
        vueArborescence.setPrefSize(ModelUML.PARTIE_GAUCHE_X, (double) (stage.getMinHeight() - 100) / 2);  // (380 - 20) / 2
        //vueRecent.setMinSize(ModelUML.PARTIE_GAUCHE_X, 180);  // (380 - 20) / 2
        System.out.println("h: " + stage.getWidth() + " " + stage.getHeight());
        System.out.println("h: " + stage.getMaxWidth() + " " + stage.getMaxHeight());
        System.out.println("h: " + stage.getMinWidth() + " " + stage.getMinHeight());  // le bon truc à mettre
        System.out.println("h: " + stage.getScene().getWidth() + " " + stage.getScene().getHeight());
        System.out.println("h: " + partieGauche.getPrefWidth() + " " + partieGauche.getPrefHeight());



        //partieDroite.setPrefSize(500, 380);
        partieDroite.setPrefSize(stage.getWidth(), stage.getMaxHeight() - 20 - 20);
        addProjectButton.setPrefSize(370, 270);

        // Permettre à centre de prendre toute la hauteur restante
        VBox.setVgrow(centre, Priority.ALWAYS);
        VBox.setVgrow(partieGauche, Priority.ALWAYS);
        VBox.setVgrow(vueArborescence, Priority.ALWAYS);
        VBox.setVgrow(vueRecent, Priority.SOMETIMES);

        // Permettre à partieDroite de s'élargir
        HBox.setHgrow(partieDroite, Priority.ALWAYS);


        /*       STYLE       **/

        titre.getStyleClass().add("label-titre");
        addProjectButton.getStyleClass().add("addButton");
        menuBar.getStyleClass().add("menuBar");
        partieDroite.getStyleClass().add("menuBar");
        vueArborescence.getStyleClass().add("treeView");
        vueRecent.getStyleClass().add("treeView");
        fin.getStyleClass().add("label-fin");

        /*       lancement       **/
        modelUML.switchState(true);
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
