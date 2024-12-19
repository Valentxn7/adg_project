package adg;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

public class MainUML extends Application {
    private ModelUML modelUML;
    private Stage rootStage;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        rootStage = stage;
        modelUML = new ModelUML();
        VBox base = new VBox(0);
        VueTitre titre = new VueTitre(modelUML);
        titre.setText("ADG - Home");
        modelUML.enregistrerObservateur(titre);
        HBox centre = new HBox(0);
        Label fin = new Label("Tous droits réservés");
        fin.setAlignment(javafx.geometry.Pos.CENTER);

        VBox partieGauche = new VBox(0);  // TreeView et MenuBar
        VueDiagramme partieDroite = new VueDiagramme(modelUML);  // bouton add projet
        modelUML.enregistrerObservateur(partieDroite);

//        ControleurCreateProject controleurCreateProject = new ControleurCreateProject(modelUML);
        Button addProjectButton = new Button("+");
        addProjectButton.setAlignment(javafx.geometry.Pos.CENTER);
        addProjectButton.setOnAction(e -> openCreateProjectWindow());

        partieDroite.setAlignment(javafx.geometry.Pos.CENTER);

        VueMenu menuBar = new VueMenu(modelUML);  // barre menu contenante
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
        Menu helpMenu = new Menu("Aide");
        menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);

        nouveau.setOnAction(e -> openCreateProjectWindow());
        ouvrirP.setOnAction(e -> openProject());
        ouvrirS.setOnAction(e -> {
            String path = openSaveFile();
            if (path != null) {
                System.out.println("Ouverture de la sauvegarde : " + path);
            }
        });
        accueil.setOnAction(e -> {
            modelUML.switchDiag2Home();
            rootStage.setTitle("ADG - Home");
        });

        VueArborescence vueArborescence = new VueArborescence(modelUML);  // l'item de base
        modelUML.enregistrerObservateur(vueArborescence);
        vueArborescence.setValue("Projets ADG:");
        vueArborescence.setExpanded(true);
        vueArborescence.actualiser(modelUML);
        TreeView<String> treeView = new TreeView<>(vueArborescence);  // la TreeView affiche les TreeItem

        TreeItem<String> rootRecent = new TreeItem<>();  // l'item de base
        rootRecent.setValue("Projets récents:");
        rootRecent.setExpanded(true);

        VueRecent vueRecent = new VueRecent(modelUML);  // la TreeView affiche les TreeItem
        vueRecent.setRoot(rootRecent);
        modelUML.enregistrerObservateur(vueRecent);
        vueRecent.actualiser(modelUML);

        base.getChildren().addAll(titre, centre, fin);  // VBox
        centre.getChildren().addAll(partieGauche, partieDroite);  // HBox
        partieGauche.getChildren().addAll(menuBar, treeView, vueRecent);  // VBox
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
        vueRecent.getStyleClass().add("treeView");
        fin.getStyleClass().add("label-fin");

        Scene scene = new Scene(base, 922, 420);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("ADG - Home");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Ouvre une fenêtre de dialogue pour la création d'un nouveau projet.
     */
    private void openCreateProjectWindow() {
        Stage createProjetWind = new Stage();
        createProjetWind.initModality(Modality.APPLICATION_MODAL);  // empêche les intéractions avec la grande fenêtre
        createProjetWind.setTitle("Créer un nouveau projet");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label label = new Label("Entrez le nom du projet :");
        TextField projectNameField = new TextField();
        Button createButton = new Button("Créer");

        projectNameField.setOnAction(e -> {
            String projectName = projectNameField.getText().trim();  // on récupère le texte du champ
            if (!projectName.isEmpty()) {  // si le champ n'est pas vide
                if (modelUML.creerProjetVierge(projectName)) {
                    rootStage.setTitle("ADG - " + projectName);
                    modelUML.setWindowsTitle(projectName);
                }
                createProjetWind.close(); // Ferme la fenêtre
            } else {
                showErrorMessage("Le nom du projet ne peut pas être vide.");
            }
        });
        createButton.setOnAction(e -> {
            String projectName = projectNameField.getText().trim();  // on récupère le texte du champ
            if (!projectName.isEmpty()) {  // si le champ n'est pas vide
                if (modelUML.creerProjetVierge(projectName)) {
                    rootStage.setTitle("ADG - " + projectName);
                    modelUML.setWindowsTitle(projectName);
                }
                createProjetWind.close(); // Ferme la fenêtre
            } else {
                showErrorMessage("Le nom du projet ne peut pas être vide.");
            }
        });

        vbox.getChildren().addAll(label, projectNameField, createButton);

        Scene scene = new Scene(vbox, 300, 150);
        createProjetWind.setScene(scene);
        createProjetWind.show();
    }

    /**
     * Ouvre un explorateur pour sélectionner un dossier (projet).
     *
     * @return Le chemin du dossier sélectionné, ou null si aucun dossier n'a été sélectionné.
     */
    private void openProject() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Ouvrir un projet");
        File selectedDirectory = directoryChooser.showDialog(rootStage);
        if (selectedDirectory != null) {
            String path = selectedDirectory.getAbsolutePath();
            System.out.println("Ouverture du projet : " + path);
            rootStage.setTitle("ADG - " + selectedDirectory.getName());
            modelUML.ouvrirProjet(selectedDirectory);
        }
    }

    /**
     * Ouvre un explorateur pour sélectionner un fichier .adg (sauvegarde).
     *
     * @return Le chemin du fichier sélectionné, ou null si aucun fichier n'a été sélectionné.
     */
    private String openSaveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir une sauvegarde");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers ADG", "*.adg"));
        File selectedFile = fileChooser.showOpenDialog(rootStage);
        if (selectedFile != null) {

            return selectedFile.getAbsolutePath();
        }
        return null;
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
