package adg;

import adg.control.ControleurDeplacerClasse;
import adg.control.ControllerClickDroitClasse;
import adg.data.*;
import adg.vues.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import adg.data.Analyser;
import adg.data.Classe;

import adg.data.PathToClass;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.*;

import java.util.ArrayList;
import java.io.File;

import java.io.IOException;
import java.util.List;

/**
 * Classe représentant le modèle UML. Cette classe gère les classes UML,
 * les chemins de fichiers, et la communication avec les observateurs.
 */
public class ModelUML implements Sujet {
    private VueDiagramme vueDiagramme;
    private ArrayList<Observateur> observateurs; // Liste des observateurs
    private ArrayList<Classe> classes;          // Liste des classes UML
    private ArrayList<String> chemins;          // Liste des chemins de fichiers

    private HashMap<String, VueClasse> vues;  // hashmap qui associe le nom de la classe à sa vue
    private String windowsTitle = "Home";
    private String ADGFolfer;
    private String folderPath = null;
    private File folder = null;
    private static final String DATA_FILE = ".data.json"; // Nom du fichier des données
    private static final String HELP_FILE = "aide.html"; // Nom du fichier des données
    private static final String DIRECTORY_SEPARATOR = FileSystems.getDefault().getSeparator();
    private static final int MAX_RECENT_FOLDERS = 10;     // Limite du nombre de dossiers récents

    List<String> recentFolders;
    private boolean isHome = true;

    private static int PARTIE_GAUCHE_X = 350;  // anciennement 400
    private static final int PARTIE_GAUCHE_X_HOME = 350;
    private static final int PARTIE_GAUCHE_X_DIAG = 250;
    public final static int PARTIE_GAUCHE_Y = 380;
    public final static int MENU_BAR_Y = 20;

    private int vueArbo_x;
    private int vueArbo_y;

    private int vueRecent_x;
    private int vueRecent_y;
    private String vueRecent_style;
    private boolean vueRecent_visible;
    private Stage stage;
    private int vueDiagramme_x;
    private int vueDiagramme_y;
    private int vueDiagramme_bouton_x;
    private int vueDiagramme_bouton_y;
    private String vueDiagramme_bouton_style;
    private boolean vueDiagramme_bouton_visible;
    //coordonner pour les classes
    private Map<VueClasse, int[]> coordonneesClasse;
    //coordonner pour les fleches
    private Map<VueFleche, VueClasse[]> coordonneesFleche;
    private ArrayList<Fleche> fleches = new ArrayList<>();

    private ArrayList<String> menuBar = new ArrayList<>(Arrays.asList("Fichier"));
    // QUAND LE RESTE SERA DEVELOPPE
    // private ArrayList<String> menuBar = new ArrayList<>(Arrays.asList("Fichier", "Affichage", "Aide"));
    private HashMap<String, Boolean> menuFichier = new HashMap<String, Boolean>() {{
        put("Nouveau", true);
        put("Ouvrir un projet", true);
        put("Ouvrir une sauvegarde", true);
        put("Renommer", false);
        put("Supprimer", false);
        put("Enregistrer", false);
        put("Enregistrer sous", false);
        put("Exporter en UML", false);
        put("Exporter en PNG", false);
        put("Exporter en Java", false);
        put("Personnalisation", false);
        put("Accueil", false);
    }};

    private final ControleurDeplacerClasse controleurDeplacerClasse = new ControleurDeplacerClasse(this);

    private boolean etatClickDroit = false;
    private boolean etatClickDroitClasse = false;
    private int[] coordonneesClickDroit = new int[2];
    private ControllerClickDroitClasse controllerClickDroit;
    private Classe classeSelectionne;

    /**
     * Constructeur par défaut. Initialise les listes d'observateurs,
     * de classes UML et de chemins.
     */
    public ModelUML(Stage stage) {
        chemins = new ArrayList<>();
        vues = new HashMap<>();
        observateurs = new ArrayList<Observateur>();
        chemins = new ArrayList<>();
        classes = new ArrayList<Classe>();
        coordonneesClasse = new HashMap<>();
        coordonneesFleche = new HashMap<>();
        this.stage = stage;
        setADGFolder();
        System.out.println("ModelUML initialisé.");
        isHome = true;
        coordonneesClasse = new HashMap<>();
        coordonneesFleche = new HashMap<>();
    }

    /**
     * Ajoute une classe UML au modèle.
     *
     * @param classe la classe à ajouter.
     */
    public void ajouterClasse(Classe classe) {
        if (!verifExistanceClasse(classe)) {
            classes.add(classe);
            this.trouverPlacePourClassess(classe);
            VueClasse vue = new VueClasse(classe);
            vue.setOnMouseClicked(controllerClickDroit);
            observateurs.add(vue);

            vueDiagramme.getChildren().add(vue);
            vues.put(classe.getClassName(), vue);

            vue.addEventHandler(MouseEvent.MOUSE_PRESSED, controleurDeplacerClasse);
            vue.addEventHandler(MouseEvent.MOUSE_DRAGGED, controleurDeplacerClasse);
            this.ajouterFlecheExt(classe, vue);
            this.ajouterFlecheImp(classe, vue);
            this.ajouterFlecheAttri(classe, vue);
            this.ajoutFlecheCorrespondant();
        }
    }


    /**
     * Place la classe aux coordonnées exactes sans vériier si la place est libre
     *
     * @param classe la classe à ajouter.
     */
    public void ajouterClasseSauvegarde(Classe classe) {
        if (classes != null)
            classes.add(classe);
        VueClasse vue = new VueClasse(classe);
        vue.setOnMouseClicked(controllerClickDroit);
        observateurs.add(vue);

        vueDiagramme.getChildren().add(vue);
        vues.put(classe.getClassName(), vue);

        vue.addEventHandler(MouseEvent.MOUSE_PRESSED, controleurDeplacerClasse);
        vue.addEventHandler(MouseEvent.MOUSE_DRAGGED, controleurDeplacerClasse);
        this.ajouterFlecheExt(classe, vue);
        this.ajouterFlecheImp(classe, vue);
        this.ajouterFlecheAttri(classe, vue);
        this.ajoutFlecheCorrespondant();
    }



    private boolean verifExistanceClasse(Classe classe) {
        boolean res = false;
        for (Classe c : classes) {
            if (c.getClassName().equals(classe.getClassName())) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * creez une flèche extends
     *
     * @param classe
     * @param vueClasse
     */
    private void ajouterFlecheExt(Classe classe, VueClasse vueClasse) {
        String s = classe.getSuperclass();
        Classe classeExt = containsClasse(s);
        if (classeExt != null) {
            VueClasse vueClasseExt = vues.get(classeExt.getClassName());
            if (!this.verifExistanceFleche(vueClasse, vues.get(classeExt.getClassName()))) {
                Fleche f = new Fleche(classe, classeExt, Fleche.HERITAGE);
                f.setPos();
                ajouterFleches(f);
                VueFlecheExt fleche = new VueFlecheExt(this,f);

                VuePointe pointe = new PointePleine(fleche);
                fleche.toBack();
                vueDiagramme.getChildren().add(fleche);
                vueDiagramme.getChildren().add(pointe);
                coordonneesFleche.put(fleche, new VueClasse[]{vueClasse, vueClasseExt});
                observateurs.add(fleche);
                observateurs.add(pointe);
            }
        }
    }

    /**
     * Ajoute une flèche d'implémentation à la vue diagramme
     * @param classe
     * @param vueClasse
     */
    private void ajouterFlecheImp(Classe classe, VueClasse vueClasse) {
        List<String> s = classe.getInterfaces();
        for (String i : s) {
            Classe classeImp = containsClasse(i);
            if (classeImp != null) {
                VueClasse vueClasseImp = vues.get(classeImp.getClassName());
                if (!this.verifExistanceFleche(vueClasse, vueClasseImp)) {
                    Fleche f = new Fleche(classe, classeImp, Fleche.HERITAGE);
                    f.setPos();
                    ajouterFleches(f);
                    VueFlecheImp fleche = new VueFlecheImp(this,f);

                    VuePointe pointe = new PointePleine(fleche);
                    vueDiagramme.getChildren().add(fleche);
                    vueDiagramme.getChildren().add(pointe);
                    coordonneesFleche.put(fleche, new VueClasse[]{vueClasse, vueClasseImp});
                    observateurs.add(fleche);
                    observateurs.add(pointe);
                }
            }
        }
    }

    /**
     * creer une flèche d'attribut
     * @param classe
     * @param vueClasse
     */
    private void ajouterFlecheAttri(Classe classe, VueClasse vueClasse) {
        List<String[]> s = classe.getFields();
        for (String[] i : s) {
            String type = i[Analyser.FIELD_TYPE];
            if (type.contains("<")) {
                // Trouver les indices de < et >
                int start = type.indexOf('<');
                int end = type.indexOf('>');

                // Vérifier que < et > existent
                if (start != -1 && end != -1 && start < end) {
                    // Extraire le mot entre < et >
                    String mot = type.substring(start + 1, end);
                    type = mot;
                }
            }
            Classe classeImp = containsClasse(type);

            if (classeImp != null) {
                VueClasse vueClasseImp = vues.get(classeImp.getClassName());
                if (!this.verifExistanceFleche(vueClasse, vueClasseImp)) {
                    Fleche f = new Fleche(classe, classeImp, Fleche.ASSOCIATION);
                    f.setPos();
                    ajouterFleches(f);
                    VueFlecheAttri fleche = new VueFlecheAttri(this,f,new Text(i[Analyser.FIELD_MODIFIER] + i[Analyser.FIELD_NAME]));
                    VuePointe pointe = new PointeCreuse(fleche);
                    vueDiagramme.getChildren().add(fleche);
                    vueDiagramme.getChildren().add(pointe);
                    vueDiagramme.getChildren().add(fleche.getAttribut());
                    coordonneesFleche.put(fleche, new VueClasse[]{vueClasse, vueClasseImp});
                    observateurs.add(fleche);
                    observateurs.add(pointe);
                }
            }
        }
    }

    public void ajouterFleches(Fleche fleche) {
        fleches.add(fleche);
    }

    /**
     * Ajoute les fleches correspondant à chaque classe
     */
    private void ajoutFlecheCorrespondant() {
        for (Classe c : classes) {
            //System.out.println("classe : " + c.getClassName());
            String nameC = c.getClassName();
            VueClasse vueClasse = vues.get(nameC);
            ajouterFlecheExt(c, vueClasse);
            ajouterFlecheImp(c, vueClasse);
            ajouterFlecheAttri(c, vueClasse);
        }

    }

    /**
     * vérifie si une flèche existe déjà
     *
     * @param vueClasse1
     * @param vueClasse2
     * @return
     */
    private boolean verifExistanceFleche(VueClasse vueClasse1, VueClasse vueClasse2) {
        boolean res = false;
        if (vueClasse1 != null && vueClasse2 != null) {
            for (VueFleche f : coordonneesFleche.keySet()) {
                VueClasse[] vues = coordonneesFleche.get(f);
                if (vues[0] == vueClasse1 && vues[1] == vueClasse2) {
                    res = true;
                    break;
                }
            }
        }
        // System.out.println("res : " + res);
        return res;
    }

    /**
     * verifie si une classe existe déjà
     *
     * @param s
     * @return
     */
    private Classe containsClasse(String s) {
        Classe res = null;
        for (Classe classe : classes) {
            if (classe.getClassName().equals(s)) {
                res = classe;
            }
        }
        return res;
    }

    /**
     * Crée un projet vierge et notifie les observateurs pour basculer
     * à la vue diagramme.
     */
    public boolean creerProjetVierge(String nomProjet) {
        System.out.println("Création d'un projet vierge " + nomProjet + "...");

        String userHome = System.getProperty("user.home"); // C:\Users\NomUtilisateur sur Windows par ex
        File appFolder = new File(userHome, "ADGProjects"); // définie emplacement mais ne le créé pas encore
        if (!appFolder.exists()) {  // Si le dossier n'existe pas
            createADGfolder();
        }
        File newProject = new File(appFolder, nomProjet + ".adg");  // on situe un fichier dans le dossier de l'application

        try {
            if (newProject.createNewFile()) {  // si le fichier a bien été créé

                this.folderPath = appFolder.getAbsolutePath();
                this.folder = new File(appFolder.getAbsolutePath());
                this.windowsTitle = nomProjet;
                setWindowsTitle(nomProjet);
                switchState(false);
                addRecentFolder(newProject.getAbsolutePath());
                return true;
            } else {
                /*System.out.println("Le fichier existe déjà.");
                MainUML.showErrorMessage("Le fichier existe déjà.");*/
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
        }
        return false;
    }

    /**
     * @param cont          le contenu du fichier
     * @param directoryPath le path du dossier mais SANS le nom du dossier
     * @param fileName      le nom du fichier à écrire
     */
    public static boolean ecrireFichier(String cont, String directoryPath, String fileName) {
        System.out.println("cont : " + cont);
        System.out.println("directoryPath : " + directoryPath);
        System.out.println("fileName : " + fileName);

        File directory = new File(directoryPath);
        File file = new File(directory, fileName);

        try {
            // Vérifier si le dossier existe, sinon le créer
            if (!directory.exists()) {
                if (directory.mkdirs()) {
                    System.out.println("Dossier créé : " + directoryPath);
                } else {
                    System.err.println("Échec de la création du dossier.");
                    return false;
                }
            }

            // Écrire le contenu dans le fichier
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(cont);
                System.out.println("Fichier enregistré avec succès : " + file.getAbsolutePath());
                return true;
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
        return false;
    }


    public void sauvegarderProjet() {
        if (folder != null) {
            System.out.println("Sauvegarde du projet : " + folder.getName() + "...");
            String path = folder.getAbsolutePath();
            ecrireFichier(Save.save(classes), path, windowsTitle);
            System.out.println("Projet sauvegardé.");
        }
    }

    public void sauvegarderSousProjet(String path) {
        System.out.println("Sauvegarde du projet : " + windowsTitle  + " dans " + path + "...");
        ecrireFichier(Save.save(classes), path, windowsTitle);
        System.out.println("Projet sauvegardé.");
    }

    /**
     * Ouvre un projet existant et notifie les observateurs pour basculer
     *
     * @param folder
     */
    public void ouvrirProjet(File folder) {
        this.folder = folder;
        System.out.println("Ouverture du projet : " + folder.getName() + "...");
        setWindowsTitle(folder.getName());
        addRecentFolder(folder.getAbsolutePath());
        switchState(false);
    }

    /**
     * Enregistre un nouvel observateur.
     *
     * @param o l'observateur à enregistrer.
     */
    @Override
    public void enregistrerObservateur(Observateur o) {
        observateurs.add(o);
    }

    /**
     * Supprime un observateur de la liste.
     *
     * @param o l'observateur à supprimer.
     */
    @Override
    public void supprimerObservateur(Observateur o) {
        observateurs.remove(o);
    }

    /**
     * Notifie tous les observateurs des changements dans le modèle.
     */
    @Override
    public void notifierObservateurs() {
        System.out.println("Notifying Observateurs...");
        for (Observateur o : observateurs) {
            o.actualiser(this);
        }
    }

    /**
     * Change l'état de l'application
     *
     * @param isHome true si on est à l'accueil, false si on est dans un projet
     */
    public void switchState(boolean isHome) {
        this.isHome = isHome;
        if (isHome) {  // home
            PARTIE_GAUCHE_X = PARTIE_GAUCHE_X_HOME;

            this.vueArbo_x = PARTIE_GAUCHE_X; // (400 (taille partie gauche) - 10 (marge) ) / 2
            this.vueArbo_y = (PARTIE_GAUCHE_Y - MENU_BAR_Y) / 2; // 380 /2

            vueRecent_x = PARTIE_GAUCHE_X; // (400 (taille partie gauche) - 10 (marge) ) / 2
            vueRecent_y = (PARTIE_GAUCHE_Y - MENU_BAR_Y) / 2; // 380 /2
            vueRecent_style = "treeView";
            vueRecent_visible = true;

            vueDiagramme_x = 500;
            vueDiagramme_y = 380;
            vueDiagramme_bouton_x = 370;
            vueDiagramme_bouton_y = 270;
            vueDiagramme_bouton_style = "addButton";
            vueDiagramme_bouton_visible = true;

            menuFichier.replaceAll((key, value) -> false);

            String[] entete = {"Nouveau", "Ouvrir un projet", "Ouvrir une sauvegarde"};
            for (String item : entete)
                menuFichier.put(item, true);

            // on supprime les classes
            classes.clear();
            vues.clear();
            coordonneesClasse.clear();
            coordonneesFleche.clear();
            vueDiagramme.getChildren().clear();

            setWindowsTitle("Home");
            stage.setResizable(false);

        } else {  // diagramme
            PARTIE_GAUCHE_X = PARTIE_GAUCHE_X_DIAG;

            this.vueArbo_x = PARTIE_GAUCHE_X; // (400 (taille partie gauche) - 10 (marge) )
            this.vueArbo_y = PARTIE_GAUCHE_Y - MENU_BAR_Y; // 20 = taille menuBar

            vueRecent_x = 0;
            vueRecent_y = 0;
            vueRecent_style = "treeView_hidden";
            vueRecent_visible = false;

            vueDiagramme_x = 900;
            vueDiagramme_y = 400;
            vueDiagramme_bouton_x = 0;
            vueDiagramme_bouton_y = 0;
            vueDiagramme_bouton_style = "addButton_hidden";
            vueDiagramme_bouton_visible = false;

            menuFichier.replaceAll((key, value) -> true);

            stage.setResizable(true);
        }

        notifierObservateurs();
    }

    public String getWindowsTitle() {
        return "ADG - " + windowsTitle;
    }

    public String getProjectName() {
        return windowsTitle;
    }

    /**
     * Définit le titre de la fenêtre.
     *
     * @param titre le nouveau titre.
     */
    public void setWindowsTitle(String titre) {
        this.windowsTitle = titre;
        this.stage.setTitle(getWindowsTitle());
    }

    /**
     * Retourne la liste des classes
     * @return
     */
    public ArrayList<Classe> getClasses() {
        return classes;
    }

    /**
     * Retourne la liste des observateurs
     * @return
     */
    public ArrayList<Observateur> getObservers() {
        return observateurs;
    }

    /**
     * retourne la vue diagramme
     * @return
     */
    public VueDiagramme getVueDiagramme() {
        return vueDiagramme;
    }

    public void setFolderPath(String path) {
        folderPath = path;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }

    /**
     * Retourne le dossier du projet
     * @return
     */
    public File getFolder() {
        return folder;
    }

    /**
     * Retourne l'état de l'application
     * @return
     */
    public boolean getIsHome() {
        return isHome;
    }

    /**
     * Retourne la coordonnée x de la vue arborescence
     * @return
     */
    public int getVueArbo_x() {
        return vueArbo_x;
    }
    /**
     * Retourne la coordonnée y de la vue arborescence
     * @return
     */
    public int getVueArbo_y() {
        return vueArbo_y;
    }
    /**
     * Retourne la coordonnée x de la vue récente
     * @return
     */
    public int getVueRecent_x() {
        return vueRecent_x;
    }
    /**
     * Retourne la coordonnée y de la vue récente
     * @return
     */
    public int getVueRecent_y() {
        return vueRecent_y;
    }
    /**
     * Retourne le style de la vue récente
     * @return
     */
    public String getVueRecent_style() {
        return vueRecent_style;
    }
    /**
     * Retourne la visibilité de la vue récente
     * @return
     */
    public boolean getVueRecentVisibility() {
        return vueRecent_visible;
    }

    /**
     * Retourne la coordonnée x de la vue diagramme
     * @return
     */
    public int getVueDiagramme_x() {
        return vueDiagramme_x;
    }
    /**
     * Retourne la coordonnée y de la vue diagramme
     * @return
     */
    public int getVueDiagramme_y() {
        return vueDiagramme_y;
    }
    /**
     * Retourne la coordonnée x du bouton de la vue diagramme
     * @return
     */
    public int getVueDiagramme_bouton_x() {
        return vueDiagramme_bouton_x;
    }
    /**
     * Retourne la coordonnée y du bouton de la vue diagramme
     * @return
     */
    public int getVueDiagramme_bouton_y() {
        return vueDiagramme_bouton_y;
    }
    /**
     * Retourne le style du bouton de la vue diagramme
     * @return
     */
    public String getVueDiagramme_bouton_style() {
        return vueDiagramme_bouton_style;
    }
    /**
     * Retourne la visibilité du bouton de la vue diagramme
     * @return
     */
    public boolean getVueDiagramme_bouton_visibility() {
        return vueDiagramme_bouton_visible;
    }

    /**
     * Retourne la coordonnée x de la partie gauche
     * @return
     */
    public int getPartieGaucheX() {
        return PARTIE_GAUCHE_X;
    }

    /**
     * Retourne la coordonnée y de la partie gauche
     * @return
     */
    public int getPartieGaucheY() {
        return PARTIE_GAUCHE_Y;
    }

    public double getPartieGaucheYPref() {
        return stage.getMinHeight() - 20 - 20;
    }

    /**
     * Retourne la liste MenuBar
     * @return
     */
    public ArrayList<String> getMenuBar() {
        return menuBar;
    }

    public HashMap<String, Boolean> getMenuFichier() {
        return menuFichier;
    }

    /**
     * Retourne les items du menu
     * @param index
     * @return
     */
    public HashMap<String, Boolean> getMenuItems(int index) {
        String menu = menuBar.get(index);
        //System.out.println("Menu: " + menu);
        if (menu.equals("Fichier")) {
            return menuFichier;
        }
        return null;
    }

    /**
     * creer l'emplacement du projet
     */
    public void setADGFolder() {
        String userHome = System.getProperty("user.home"); // C:\Users\NomUtilisateur sur Windows par ex
        File appFolder = new File(userHome, "ADGProjects"); // définie emplacement mais ne le créé pas encore
        if (!appFolder.exists()) {  // Si le dossier n'existe pas
            createADGfolder();
        }
        this.ADGFolfer = appFolder.getAbsolutePath();
        this.folderPath = appFolder.getAbsolutePath();
        this.folder = new File(appFolder.getAbsolutePath());
    }

    private void ReadRecentProjects() {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".adg")) {
                System.out.println("Fichier : " + file.getName());
            }
        }
    }
    /**
     * Crée le dossier ADGProjects dans le répertoire utilisateur.
     */
    private void createADGfolder() {
        String userHome = System.getProperty("user.home"); // C:\Users\NomUtilisateur sur Windows par ex
        File appFolder = new File(userHome, "ADGProjects"); // définie emplacement mais ne le créé pas encore
        if (!appFolder.exists()) {  // Si le dossier n'existe pas
            if (!appFolder.mkdirs()) { // Crée le dossier s'il n'existe pas
                System.err.println("Erreur lors de la création du dossier.");
                MainUML.showErrorMessage("Erreur lors de la création du dossier.");
            } else {
                System.out.println("Dossier ADG créé avec succès dans " + appFolder.getAbsolutePath() + ".");
            }
        }

        // création du fichier masqué data.json
        try {
            File hiddenFile = new File(userHome, DATA_FILE); // Masqué sur Linux/Mac

            // Créer le fichier
            if (hiddenFile.createNewFile()) {
                System.out.println("Fichier data créé : " + hiddenFile.getAbsolutePath());
            } else {
                System.out.println("Le fichier data existe déjà.");
            }

            // Ajouter l'attribut 'hidden' sur Windows
            if (System.getProperty("os.name").toLowerCase().contains("win")) {  // si sous windows
                Path filePath = hiddenFile.toPath();
                Files.getFileAttributeView(filePath, DosFileAttributeView.class).setHidden(true);  // masqué sous windows
                System.out.println("Le fichier est maintenant masqué sur Windows.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Ajoute un dossier récent à la liste des dossiers récents.
     * @param folderPath
     */
    private static void addRecentFolder(String folderPath) {
        // Lire les dossiers actuels
        List<String> recentFolders = getRecentFolders();

        // Utiliser un Set pour éviter les doublons
        Set<String> folderSet = new LinkedHashSet<>(recentFolders);
        folderSet.add(folderPath); // Ajouter le nouveau dossier

        // Limiter à MAX_RECENT
        while (folderSet.size() > MAX_RECENT_FOLDERS) {
            folderSet.remove(folderSet.iterator().next());
        }

        // Sauvegarder dans le fichier JSON
        saveRecentFolders(new ArrayList<>(folderSet));
    }

    /**
     * Récupère la liste des dossiers récents à partir du fichier JSON.
     * @return
     */
    public static ArrayList<String> getRecentFolders() {
        ArrayList<String> recentFolders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String json = jsonBuilder.toString();

            // Extraire les dossiers récents
            if (json.contains("\"recentFolders\"")) {
                String folderList = json.substring(json.indexOf("[") + 1, json.indexOf("]"));
                String[] folders = folderList.split(",");
                for (String folder : folders) {
                    recentFolders.add(folder.trim().replace("\"", "")); // Supprimer les guillemets
                }
            }
        } catch (IOException e) {
            // Si le fichier n'existe pas, retourner une liste vide
            return new ArrayList<>();
        }
        return recentFolders;
    }

    /**
     * Sauvegarde la liste des dossiers récents dans le fichier JSON.
     * @param folders
     */
    private static void saveRecentFolders(List<String> folders) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            writer.write("{\n");
            writer.write("  \"recentFolders\": [\n");
            for (int i = 0; i < folders.size(); i++) {
                writer.write("    \"" + folders.get(i) + "\"");
                if (i < folders.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("  ]\n");
            writer.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajoute un chemin de fichier au modèle et notifie les observateurs.
     *
     * @param filePath le chemin à ajouter.
     */
    public void setFilePath(String filePath) {
        this.chemins.add(filePath);
        notifierObservateurs();
    }

    /**
     * Retourne tous les chemins de fichiers enregistrés sous forme de chaîne.
     *
     * @return une chaîne contenant tous les chemins, séparés par des sauts de ligne.
     */
    public String getFilePath() {
        StringBuilder res = new StringBuilder();
        for (String chemin : chemins) {
            res.append(chemin).append("\n");
        }
        return res.toString();
    }

    /**
     * Analyse un fichier .class à partir de son chemin absolu et charge la classe correspondante dans le classpath.
     * Effectue également une analyse UML de la classe et notifie les observateurs.
     *
     * @param cheminAbsolu Le chemin absolu du fichier .class à analyser.
     * @throws Throwable Si une exception se produit lors de l'analyse ou du chargement de la classe.
     */
    public void analyseFichier(String cheminAbsolu) throws Throwable {

        // Charge la classe
        Class<?> classe = PathToClass.convertirCheminEnClasse(cheminAbsolu);

        // Analyse la classe
        Analyser analyse = new Analyser(classe);
        Classe classeAnalysee = analyse.analyse();

        // Ajoute la classe au modèle
        ajouterClasse(classeAnalysee);

        // Affiche la représentation UML de la classe
        System.out.println(classeAnalysee.UMLString());

        // Notifie les observateurs
        notifierObservateurs();
    }


    public void setVueDiagramme(VueDiagramme vueDiagramme) {
        this.vueDiagramme = vueDiagramme;
    }

    /**
     * Retourne liste de classes avec leur vue
     *
     * @return vues
     */
    public HashMap<String, VueClasse> getVues() {
        return vues;
    }

    public String getADGFolferPath() {
        return ADGFolfer + DIRECTORY_SEPARATOR;
    }


    /**
     * recupère les classes corréspondant à une fleche
     *
     * @param vueFleche
     */
    public VueClasse[] getFleche(VueFleche vueFleche) {
        VueClasse[] res = new VueClasse[2];
        if (coordonneesFleche.containsKey(vueFleche)) {
            res = coordonneesFleche.get(vueFleche);
        }
        return res;
    }

    /**
     * récupère les coordonnées d'une classe
     *
     * @param vue
     * @return
     */
    public int[] getClassesCoordonnees(VueClasse vue) {
        int[] res = new int[2];
        if (coordonneesClasse.containsKey(vue)) {
            res = coordonneesClasse.get(vue);
        }
        return res;
    }

    /**
     * Trouve une place pour une classe
     * @param classe
     */
    public void trouverPlacePourClassess(Classe classe) {
        // Récupérer les dimensions de la nouvelle classe
        int width = classe.getWidth();
        int height = classe.getHeight();

        // Commencer à tester à partir du coin supérieur gauche avec une marge
        int marge = 10; // marge autour de la classe pour éviter de la placer trop près d'autres
        int x = marge;
        int y = marge;

        // Définir la largeur et la hauteur maximales du pane
        int maxWidth = (int) vueDiagramme.getWidth();
        int maxHeight = (int) vueDiagramme.getHeight();

        boolean placeTrouvee = false;

        // Boucle pour trouver un emplacement libre
        while (y + height <= maxHeight) {
            while (x + width <= maxWidth) {
                // Vérifier si l'emplacement est libre
                if (estLibre(x, y)) {
                    // Si c'est libre, on place la classe ici et on met à jour ses coordonnées
                    classe.setCoords(x, y);
                    placeTrouvee = true;
                    break; // Sortir de la boucle interne si la place a été trouvée
                }
                // Déplacer l'élément sur l'axe X
                x += width + marge;
            }

            if (placeTrouvee) {
                break; // Sortir de la boucle externe si la place a été trouvée
            }

            // Si aucune place trouvée, passer à la ligne suivante
            x = marge;
            y += height + marge;
        }

        // Si aucune place n'a été trouvée, placer la classe à un endroit par défaut
        if (!placeTrouvee) {
            classe.setCoords(0, 0);
            System.err.println("Aucun emplacement libre trouvé. Classe " + classe.getClassName() + " placée en dernier endroit possible.");
        }
    }


    /**
     * Vérifie si une case est libre
     *
     * @param x Coordonnée x du clic
     * @param y Coordonnée y du clic
     * @return true si la case est libre, false sinon
     */
    public boolean estLibre(int x, int y) {
        boolean res = true;
        for (Classe classe : classes) {
            int[] coordonnees = classe.getCoords();
            int width = classe.getWidth();
            int height = classe.getHeight();
            if (x >= coordonnees[0] && x <= coordonnees[0] + width && y >= coordonnees[1] && y <= coordonnees[1] + height) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * Récupère les coordonnées d'une flèche
     *
     * @param vueFleche
     * @return
     */
    public VueClasse[] getCoordonneesFleche(VueFleche vueFleche) {
        VueClasse[] res = null;
        if (coordonneesFleche.containsKey(vueFleche)) {
            res = coordonneesFleche.get(vueFleche);
        }
        return res;
    }

    public List<VueClasse> getVueClasses() {
        System.out.println("taille : " + vues.size());
        List res = new ArrayList<VueClasse>();
        for (String s : vues.keySet()) {
            System.out.println("classe : " + s);
            res.add(vues.get(s));
        }
        return res;
    }

    public void changerPositionClasse(Classe classe, Double x, Double y) {
        int[] coordonnees = new int[2];
        coordonnees[0] = x.intValue();
        coordonnees[1] = y.intValue();
        classe.setCoords(coordonnees[0], coordonnees[1]);
        notifierObservateurs();
    }


    /**
     * Recupère l'état du click droit
     * @return
     */
    public boolean getEtat() {
        return this.etatClickDroit;
    }
    /**
     * Recupère l'état du click droit sur la classe
     */
    public boolean getEtatClickDroitClasse() {
        return etatClickDroitClasse;
    }

    /**
     * Affiche le menu contextuel au click droit
     *
     * @param x
     * @param y
     */
    public void afficherClickDroit(int x, int y) {
        etatClickDroit = true;
        etatClickDroitClasse = false;
        coordonneesClickDroit[0] = x;
        coordonneesClickDroit[1] = y;
        notifierObservateurs();
    }

    public void afficherClickDroitClasse(int x, int y) {
        etatClickDroitClasse = true;
        etatClickDroit = false;
        coordonneesClickDroit[0] = x;
        coordonneesClickDroit[1] = y;
        notifierObservateurs();
    }


    /**
     * Masque le menu contextuel
     */
    public void masquerClickDroit() {
        etatClickDroit = false;
        notifierObservateurs();
    }

    public void masquerClickDroitClass() {
        etatClickDroitClasse = false;
        notifierObservateurs();
    }

    /**
     * Définit le pane du click droit
     *
     * @param pane
     */
    public void setPaneClickDroit(VueDiagramme pane) {
        this.vueDiagramme = pane;
    }

    /**
     * Getter des coordonnées du click droit
     *
     * @return coordonnees x du ClickDroit
     */
    public int getCooXClickDroit() {
        return coordonneesClickDroit[0];
    }

    /**
     * Getter des coordonnées du click droit
     *
     * @return coordonnees y du ClickDroit
     */
    public int getCooYClickDroit() {
        return coordonneesClickDroit[1];
    }

    /**
     * Masque toutes les dépendances.
     */
    public void masquerToutesDependances() {
        //TODO
        System.out.println("masquer toutes les dépendances");
        for (Fleche f : fleches) {
            if(f.getType() == Fleche.ASSOCIATION){
                f.setVisible(false);
            }
        }
        notifierObservateurs();
    }

    /**
     * Masque tous les héritages.
     */
    public void masquerToutHeritages() {
        //TODO
        System.out.println("masquer tous les héritages");
        for(Fleche f : fleches){
            if(f.getType() == Fleche.HERITAGE){
                f.setVisible(false);
            }

        }
        notifierObservateurs();
    }

    /**
     * Masque tous les attributs.
     */
    public void masquerToutAttributs() {
        //TODO
        System.out.println("masquer tous les attributs");
        for (Classe c : classes) {
            c.setShowFields(false);
        }
        notifierObservateurs();
    }

    /**
     * Affiche toutes les dépendances.
     */
    public void afficherToutesDependances() {
        //TODO
        System.out.println("afficher toutes les dépendances");
        for (Fleche f : fleches) {
            if(f.getType() == Fleche.ASSOCIATION){
                f.setVisible(true);
            }

        }
        notifierObservateurs();
    }

    /**
     * Affiche tous les héritages.
     */
    public void afficherTousHeritages() {
        //TODO
        System.out.println("afficher tous les héritages");
        for(Fleche f : fleches){
            System.out.println("----------------------------AFFICHAGE-----------------------------------------------");
            f.setVisible(true);
        }
        notifierObservateurs();
    }

    /**
     * Affiche tous les attributs.
     */
    public void afficherTousAttributs() {
        //TODO
        System.out.println("afficher tous les attributs");
        for (Classe c : classes) {
            c.setShowFields(true);
        }
        notifierObservateurs();
    }


    /**
     * Masque toutes les méthodes.
     */
    public void afficherToutesMethodes() {
        //TODO
        System.out.println("afficher toutes les méthodes");
        for (Classe c : classes) {
            c.setShowMethods(true);
        }
        notifierObservateurs();
    }




    /**
     * Masque les attributs
     */
    public void masquerAttributs() {
        //TODO
        System.out.println("masquer les attributs");
        classeSelectionne.setShowFields(false);
        notifierObservateurs();
    }

    /**

    /**
     * affiche les héritages
     */
    public void afficherHeritages() {
        //TODO
        System.out.println("afficher les héritages");
        notifierObservateurs();
    }

    /**
     * affiche les attributs
     */
    public void afficherAttributs() {
        //TODO
        System.out.println("afficher les attributs");
        classeSelectionne.setShowFields(true);
        notifierObservateurs();
    }

    /**
     * affiche les méthodes
     */
    public void afficherMethodes() {
        //TODO
        classeSelectionne.setShowMethods(true);
        notifierObservateurs();
    }

    public void masquerMethodes() {
        System.out.println("masquer les méthodes");
        classeSelectionne.setShowMethods(false);
        notifierObservateurs();
    }

    public void masquerToutesMethodes() {
        System.out.println("masquer toutes les méthodes");
        for (Classe c : classes) {
            c.setShowMethods(false);
        }
        notifierObservateurs();
    }

    public void setControllerClickDroit(ControllerClickDroitClasse controllerClickDroit) {
        this.controllerClickDroit = controllerClickDroit;
    }

    public void setClasseSelectionne(Classe c) {
        classeSelectionne = c;
    }

    public void setControlleurClickDroit(ControllerClickDroitClasse controllerClickDroitClasse) {
        this.controllerClickDroit = controllerClickDroitClasse;
    }

    /**
     * Ouvre la page d'aide dans le navigateur par défaut.
     * Si le fichier d'aide n'est pas trouvé, affiche un message d'erreur.
     * @param choix le choix de l'aide à ouvrir.
     *              0: web 1: wiki
     */
    public void ouvrirPageAide(int choix) {
        try {
            // Localisation du fichier aide.html
            URI chemin = null;
            boolean BonChoix = true;
            switch (choix) {
                case 0:
                    String path = getADGFolferPath() + HELP_FILE;
                    File aideFile = new File(path); // Ajustez le chemin si nécessaire

                    // Vérifier si le fichier existe
                    if (!aideFile.exists()) {
                        System.err.println("Fichier d'aide introuvable : " + chemin);
                        return;
                    }
                    chemin = aideFile.toURI();
                    break;
                case 1:
                    chemin = new URI("https://github.com/Valentxn7/adg_project/wiki");
                    break;
                default:
                    BonChoix = false;
                    break;
            }

            System.out.println(chemin);
            if (BonChoix) {
                // Ouvrir dans le navigateur par défaut
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(chemin);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadADGbyPath(String path) {
        System.out.println("Ouverture de la sauvegarde : " + path);

        if (windowsTitle.equalsIgnoreCase("Home")) {
            setWindowsTitle(new File(path).getName());
        }

        if (isHome)
            switchState(false);

        ArrayList<Classe> classes = Load.load(path);
        for (Classe c : classes) {
            ajouterClasseSauvegarde(c);
        }
        notifierObservateurs();
    }

    public boolean verifierAttributNonFleche(String[] attribut) {
        boolean res = true;
        for (VueFleche vueFleche : coordonneesFleche.keySet()) {
            if (vueFleche instanceof VueFlecheAttri) {
                VueFlecheAttri flecheAttri = (VueFlecheAttri) vueFleche;
                System.out.println(flecheAttri.getAttribut().getText());
                System.out.println(attribut[Analyser.FIELD_MODIFIER] + attribut[Analyser.FIELD_NAME]);
                if (flecheAttri.getAttribut().getText().equals(attribut[Analyser.FIELD_MODIFIER] + attribut[Analyser.FIELD_NAME])) {
                    res = false;
                    break;
                }
            }
        }
        return res;
    }

    public void deleteSave(){
        String filePath = folderPath + DIRECTORY_SEPARATOR + windowsTitle; // Remplace par le chemin de ton fichier
        System.out.println("Suppression du fichier : " + filePath + "...");

        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Fichier supprimé");
                switchState(true);
            } else {
                System.err.println("Échec de la suppression du fichier.");
            }
        } else {
            System.err.println("Le fichier n'existe pas : " + filePath);
        }
    }

    public static String getFileExtension(String fileName) {
        int indexpoint = fileName.lastIndexOf('.');

        if (indexpoint > 0 && indexpoint < fileName.length() - 1) {
            return fileName.substring(indexpoint + 1); // l'extension
        }
        return ""; // Pas d'extension
    }

    public void masquerConstructeur() {
        System.out.println("masquer les constructeurs");
        classeSelectionne.setShowConstructors(false);
        notifierObservateurs();
    }

    public void afficherConstructeur() {
        System.out.println("afficher les constructeurs");
        classeSelectionne.setShowConstructors(true);
        notifierObservateurs();
    }
}