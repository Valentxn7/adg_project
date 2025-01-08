package adg;

import adg.data.PathToClass;
import adg.vues.VueClasse;
import adg.vues.VueDiagramme;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import adg.data.Analyser;
import adg.data.Classe;

import adg.data.PathToClass;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.*;

import java.util.*;

import java.util.ArrayList;
import java.io.File;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Classe représentant le modèle UML. Cette classe gère les classes UML,
 * les chemins de fichiers, et la communication avec les observateurs.
 */
public class ModelUML implements Sujet {
    private VueDiagramme vueDiagramme;
    private ArrayList<Observateur> observateurs; // Liste des observateurs
    private ArrayList<Classe> classes;          // Liste des classes UML
    private ArrayList<String> chemins;          // Liste des chemins de fichiers
    private String WindowsTitle = "Home";       // Titre de la fenêtre
    private HashMap<String, VueClasse> vues;  // hashmap qui associe le nom de la classe à sa vue
    private String windowsTitle = "Home";
    private String folderPath = null;
    private File folder = null;
    private static final String DATA_FILE = ".data.json"; // Nom du fichier des données
    private static final int MAX_RECENT_FOLDERS = 10;     // Limite du nombre de dossiers récents
    List<String> recentFolders;
    private boolean isHome = true;

    public static int PARTIE_GAUCHE_X = 300;  // anciennement 400
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
    private Map<Fleche, VueClasse[]> coordonneesFleche;

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
        put("Personnalisation", false);
        put("Accueil", false);
    }};

    private final ControleurDeplacerClasse controleurDeplacerClasse = new ControleurDeplacerClasse(this);

    private boolean etatClickDroit = false;
    private int[] coordonneesClickDroit = new int[2];
    private VueDiagramme partieDroite;

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
        this.stage = stage;
        this.setADGFolder();
        this.switchState(true);
        System.out.flush();
        System.out.println("ModelUML initialisé.");
        setADGFolder();
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
        if (classes != null)
            classes.add(classe);
        VueClasse vue = new VueClasse(classe);
        observateurs.add(vue);
        vueDiagramme.getChildren().add(vue);
        vues.put(classe.getClassName(), vue);
        this.trouverPlacePourClassess(vue);
        vue.addEventHandler(MouseEvent.MOUSE_PRESSED, controleurDeplacerClasse);
        vue.addEventHandler(MouseEvent.MOUSE_DRAGGED, controleurDeplacerClasse);
        this.ajouterFlecheExt(classe, vue);
        this.ajouterFlecheImp(classe, vue);
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

    private void ajouterFlecheExt(Classe classe, VueClasse vueClasse) {
        String s = classe.getSuperclass();
        Classe classeExt = containsClasse(s);
        if (classeExt != null) {
            VueClasse vueClasseExt = vues.get(classeExt.getClassName());
            if (!this.verifExistanceFleche(vueClasse, vues.get(classeExt.getClassName()))) {
                FlecheExt fleche = new FlecheExt();
                fleche.toBack();
                vueDiagramme.getChildren().add(fleche);
                vueDiagramme.getChildren().add(fleche.getTete());
                coordonneesFleche.put(fleche, new VueClasse[]{vueClasse, vueClasseExt});
                observateurs.add(fleche);
            }
        }
    }

    /**
     * Crée un projet vierge, survient quand on clique sur Nouveau Projet
     *
     * @param nomProjet nom du projet
     * @return true si le projet a été créé, false sinon
     */
    private void ajouterFlecheImp(Classe classe, VueClasse vueClasse) {
        List<String> s = classe.getInterfaces();
        for (String i : s) {
            Classe classeImp = containsClasse(i);
            if (classeImp != null) {
                VueClasse vueClasseImp = vues.get(classeImp.getClassName());
                if (!this.verifExistanceFleche(vueClasse, vueClasseImp)) {
                    FlecheImp fleche = new FlecheImp();
                    vueDiagramme.getChildren().add(fleche);
                    vueDiagramme.getChildren().add(fleche.getTete());
                    coordonneesFleche.put(fleche, new VueClasse[]{vueClasse, vueClasseImp});
                    observateurs.add(fleche);
                }
            }
        }
    }

    private void ajoutFlecheCorrespondant() {
        for (Classe c : classes) {
            System.out.println("classe : " + c.getClassName());
            String nameC = c.getClassName();
            VueClasse vueClasse = vues.get(nameC);
            ajouterFlecheExt(c, vueClasse);
            ajouterFlecheImp(c, vueClasse);
        }

    }

    private boolean verifExistanceFleche(VueClasse vueClasse1, VueClasse vueClasse2) {
        boolean res = false;
        if (vueClasse1 != null && vueClasse2 != null) {
            for (Fleche f : coordonneesFleche.keySet()) {
                VueClasse[] vues = coordonneesFleche.get(f);
                if (vues[0] == vueClasse1 && vues[1] == vueClasse2) {
                    res = true;
                    break;
                }
            }
        }
        System.out.println("res : " + res);
        return res;
    }

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
            PARTIE_GAUCHE_X = 350;

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

            stage.setResizable(false);

        } else {  // diagramme
            PARTIE_GAUCHE_X = 250;

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

    /**
     * Définit le titre de la fenêtre.
     *
     * @param titre le nouveau titre.
     */
    public void setWindowsTitle(String titre) {
        this.windowsTitle = titre;
        this.stage.setTitle(titre);
    }

    public ArrayList<Classe> getClasses() {
        return classes;
    }

    public ArrayList<Observateur> getObservers() {
        return observateurs;
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

    public File getFolder() {
        return folder;
    }

    public boolean getIsHome() {
        return isHome;
    }

    public int getVueArbo_x() {
        return vueArbo_x;
    }

    public int getVueArbo_y() {
        return vueArbo_y;
    }

    public int getVueRecent_x() {
        return vueRecent_x;
    }

    public int getVueRecent_y() {
        return vueRecent_y;
    }

    public String getVueRecent_style() {
        return vueRecent_style;
    }

    public boolean getVueRecentVisibility() {
        return vueRecent_visible;
    }

    public int getVueDiagramme_x() {
        return vueDiagramme_x;
    }

    public int getVueDiagramme_y() {
        return vueDiagramme_y;
    }

    public int getVueDiagramme_bouton_x() {
        return vueDiagramme_bouton_x;
    }

    public int getVueDiagramme_bouton_y() {
        return vueDiagramme_bouton_y;
    }

    public String getVueDiagramme_bouton_style() {
        return vueDiagramme_bouton_style;
    }

    public boolean getVueDiagramme_bouton_visibility() {
        return vueDiagramme_bouton_visible;
    }

    public int getPartieGaucheX() {
        return PARTIE_GAUCHE_X;
    }

    public int getPartieGaucheY() {
        return PARTIE_GAUCHE_Y;
    }

    public double getPartieGaucheYPref() {
        return stage.getMinHeight() - 20 - 20;
    }

    public ArrayList<String> getMenuBar() {
        return menuBar;
    }

    public HashMap<String, Boolean> getMenuFichier() {
        return menuFichier;
    }

    public HashMap<String, Boolean> getMenuItems(int index) {
        String menu = menuBar.get(index);
        System.out.println("Menu: " + menu);
        if (menu.equals("Fichier")) {
            return menuFichier;
        }
        return null;
    }

    public void setADGFolder() {
        String userHome = System.getProperty("user.home"); // C:\Users\NomUtilisateur sur Windows par ex
        File appFolder = new File(userHome, "ADGProjects"); // définie emplacement mais ne le créé pas encore
        if (!appFolder.exists()) {  // Si le dossier n'existe pas
            createADGfolder();
        }
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


    // Ajoute un dossier récent au fichier JSON
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

    // Récupère la liste des dossiers récents
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

    // Sauvegarde la liste des dossiers récents dans un fichier JSON
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


    /**
     * recupère les classes corréspondant à une fleche
     *
     * @param fleche
     */
    public VueClasse[] getFleche(Fleche fleche) {
        VueClasse[] res = new VueClasse[2];
        if (coordonneesFleche.containsKey(fleche)) {
            res = coordonneesFleche.get(fleche);
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


    public void trouverPlacePourClassess(VueClasse vue) {
        int[] coordonnees = new int[2];
        for (int y = 40; y < vueDiagramme.getHeight(); y++) {
            boolean b = true;
            for (int x = 0; x < vueDiagramme.getWidth(); x += 200) {
                if (estLibre(x, y)) {
                    coordonnees[0] = x;
                    coordonnees[1] = y;
                    coordonneesClasse.put(vue, coordonnees);
                    b = false;
                    break;
                }
            }
            if (!b) {
                break;
            }
        }
    }

    private boolean estLibre(int x, int y) {
        int x2, y2;
        boolean res = true;
        for (VueClasse classe : coordonneesClasse.keySet()) {
            {
                x2 = coordonneesClasse.get(classe)[0];
                y2 = coordonneesClasse.get(classe)[1];
                if ((y >= y2 && y <= y2 + classe.getHeight()) && (x >= x2 && x <= x2 + classe.getWidth())) {
                    res = false;
                }
            }
        }
        return res;
    }

    public VueClasse[] getCoordonneesFleche(Fleche fleche) {
        VueClasse[] res = null;
        if (coordonneesFleche.containsKey(fleche)) {
            res = coordonneesFleche.get(fleche);
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

    public void changerPositionClasse(VueClasse classe, Double x, Double y) {
        int[] coordonnees = new int[2];
        coordonnees[0] = x.intValue();
        coordonnees[1] = y.intValue();
        coordonneesClasse.put(classe, coordonnees);
        notifierObservateurs();
    }


    public VueDiagramme getVueDiagramme() {
        return vueDiagramme;
    }


    public boolean getEtat() {
        return this.etatClickDroit;
    }

    /**
     * Affiche le menu contextuel au click droit
     *
     * @param x
     * @param y
     */
    public void afficherClickDroit(int x, int y) {
        etatClickDroit = true;
        coordonneesClickDroit[0] = x;
        coordonneesClickDroit[1] = y;
        System.out.println("click droit : " + x + " " + y);
        System.out.println("etat click droit : " + etatClickDroit);
        notifierObservateurs();
    }


    /**
     * Masque le menu contextuel
     */
    public void masquerClickDroit() {
        etatClickDroit = false;
        notifierObservateurs();
    }

    /**
     * Getter la partie droite de l'application
     *
     * @return
     */
    public VueDiagramme getPaneCLickDroit() {
        return partieDroite;
    }

    /**
     * Définit le pane du click droit
     *
     * @param pane
     */
    public void setPaneClickDroit(VueDiagramme pane) {
        this.partieDroite = pane;
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
    }

    /**
     * Masque tous les héritages.
     */
    public void masquerToutHeritages() {
        //TODO
        System.out.println("masquer tous les héritages");
    }

    /**
     * Masque tous les attributs.
     */
    public void masquerToutAttributs() {
        //TODO
        System.out.println("masquer tous les attributs");
    }

    /**
     * Affiche toutes les dépendances.
     */
    public void afficherToutesDependances() {
        //TODO
        System.out.println("afficher toutes les dépendances");
    }

    /**
     * Affiche tous les héritages.
     */
    public void afficherTousHeritages() {
        //TODO
        System.out.println("afficher tous les héritages");
    }

    /**
     * Affiche tous les attributs.
     */
    public void afficherTousAttributs() {
        //TODO
        System.out.println("afficher tous les attributs");
    }


    /**
     * Masque toutes les méthodes.
     */
    public void afficherToutesMethodes() {
        //TODO
        System.out.println("afficher toutes les méthodes");
    }
}