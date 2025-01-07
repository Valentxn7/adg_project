package adg;

import javafx.stage.Stage;
import adg.data.Analyser;
import adg.data.Classe;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class ModelUML implements Sujet {
    private ArrayList<Observateur> observateurs;
    private ArrayList<String> chemins;
    private ArrayList<Classe> classes;
    private String windowsTitle = "Home";
    private String folderPath = null;
    private File folder = null;
    private static final String DATA_FILE = ".data.json"; // Nom du fichier des données
    private static final int MAX_RECENT_FOLDERS = 10;     // Limite du nombre de dossiers récents
    List<String> recentFolders;
    private boolean isHome = true;
    private final Stage stage;

    public final static int PARTIE_GAUCHE_X = 400;
    public final static int PARTIE_GAUCHE_Y = 380;
    public final static int MENU_BAR_Y = 20;

    private int vueArbo_x;
    private int vueArbo_y;

    private int vueRecent_x;
    private int vueRecent_y;
    private String vueRecent_style;
    private boolean vueRecent_visible;

    private int vueDiagramme_x;
    private int vueDiagramme_y;
    private int vueDiagramme_bouton_x;
    private int vueDiagramme_bouton_y;
    private String vueDiagramme_bouton_style;
    private boolean vueDiagramme_bouton_visible;

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


    public ModelUML(Stage stage) {
        observateurs = new ArrayList<Observateur>();
        chemins = new ArrayList<>();
        classes = new ArrayList<Classe>();
        this.stage = stage;
        this.setADGFolder();
        this.switchState(true);
        System.out.flush();
        System.out.println("ModelUML initialisé.");
    }

    /**
     * Ajoute une classe UML au modèle.
     *
     * @param classe la classe à ajouter.
     */
    public void ajouterClasse(Classe classe) {
        if (classes != null)
            classes.add(classe);
    }

    /**
     * Crée un projet vierge, survient quand on clique sur Nouveau Projet
     *
     * @param nomProjet nom du projet
     * @return true si le projet a été créé, false sinon
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
                System.out.println("Le fichier a été créé avec succès dans " + newProject.getAbsolutePath() + ".");
                this.folderPath = appFolder.getAbsolutePath();
                this.folder = new File(appFolder.getAbsolutePath());
                setWindowsTitle(nomProjet);
                switchState(false);
                addRecentFolder(newProject.getAbsolutePath());
                return true;
            } else {
                System.out.println("Le fichier existe déjà.");
                MainUML.showErrorMessage("Le fichier existe déjà.");
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

        } else {  // diagramme
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
        }

        notifierObservateurs();
    }

    public String getWindowsTitle() {
        return "ADG - " + windowsTitle;
    }

    public void setWindowsTitle(String titre) {
        this.windowsTitle = titre;
        this.stage.setTitle(titre);
        notifierObservateurs();
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
        // Extrait le nom de la classe à partir du chemin absolu
        int nbslash = nbSlash(cheminAbsolu);
        String nomClasse = extraireNomClasse(cheminAbsolu);
        System.out.println("Nom de la classe : " + nomClasse);

        File fichier = new File(cheminAbsolu);
        String cheminClasse = fichier.getParentFile().toURI().toString();

        // Crée un URLClassLoader pour charger la classe
        URLClassLoader chargeurClasse = new URLClassLoader(new URL[]{new URL(cheminClasse)});

        // Charge la classe
        Class<?> classe = chargerClasse(chargeurClasse, nomClasse, cheminAbsolu, nbslash);

        // Analyse la classe
        Analyser analyse = new Analyser(classe);
        Classe classeAnalysée = analyse.analyse();

        // Ajoute la classe au modèle
        ajouterClasse(classeAnalysée);

        // Affiche la représentation UML de la classe
        System.out.println(classeAnalysée.UMLString());

        // Notifie les observateurs
        notifierObservateurs();
    }


    /**
     * Charge une classe en utilisant un URLClassLoader. Tente de charger la classe en ajustant le chemin absolu
     * en remplaçant les backslashes par des points pour correspondre à la structure des packages.
     *
     * @param chargeurClasse Le URLClassLoader utilisé pour charger la classe.
     * @param nomClasse      Le nom de la classe à charger.
     * @param cheminAbsolu   Le chemin absolu du fichier .class.
     * @param nbslash        Le nombre de barres obliques inversées dans le chemin.
     * @return La classe chargée.
     * @throws Throwable Si la classe ne peut pas être trouvée ou chargée.
     */
    private Class<?> chargerClasse(URLClassLoader chargeurClasse, String nomClasse, String cheminAbsolu,
                                   int nbslash) throws Throwable {
        // On initialise la valeur de la classe à null et on initialise un booléen qui sert à savoir si le chargement a réussi
        Class<?> c = null;
        boolean succes = false;

        // On essaie de charger la classe, si une exception est levée, on modifie le chemin absolu tant que c'est possible
        while (!succes && nbslash > 0) { // Au cas où il y a des packages dans des packages
            try {
                // Charge la classe
                c = chargeurClasse.loadClass(nomClasse);
                // Si la classe est chargée, on met le booléen à true
                succes = true;
            } catch (ClassNotFoundException |
                     NoClassDefFoundError e) { // Sinon, on attrape l'exception et on modifie le chemin absolu
                // Modifie le chemin absolu pour remplacer le dernier backslash par un point
                cheminAbsolu = remplacerDernierSlashParPoint(cheminAbsolu);
                nbslash--;

                File fichier = new File(cheminAbsolu);
                nomClasse = fichier.getName().replace(".class", "");
                String cheminClasse = fichier.getParentFile().toURI().toString();
                chargeurClasse = new URLClassLoader(new URL[]{new URL(cheminClasse)});
            }
        }
        // Si la classe n'a pas été chargée, cela veut dire que le .class n'est pas dans le bon package
        if (!succes) {
            throw new ClassNotFoundException("La classe n'est pas dans le bon package");
        }

        return c;
    }


    /**
     * Trouve l'indice du dernier slash ('\\') ou barre oblique ('/') dans le chemin absolu.
     *
     * @param cheminAbsolu Le chemin absolu à analyser.
     * @return L'indice du dernier slash ou barre oblique dans le chemin, ou -1 si aucun n'est trouvé.
     */
    private int indiceDernierSlash(String cheminAbsolu) {
        int dernierIndex = cheminAbsolu.lastIndexOf('\\');

        // Sur Mac et Linux, le backslash est un slash, on vérifie donc si c'est le cas
        if (dernierIndex == -1) {
            dernierIndex = cheminAbsolu.lastIndexOf('/');
        }

        return dernierIndex;
    }

    /**
     * Extrait le nom de la classe à partir du chemin absolu du fichier .class.
     *
     * @param cheminAbsolu Le chemin absolu du fichier .class.
     * @return Le nom de la classe sans l'extension ".class".
     */
    private String extraireNomClasse(String cheminAbsolu) {
        int dernierIndex = indiceDernierSlash(cheminAbsolu);

        // Si un slash ou un backslash est trouvé, on extrait le nom de la classe
        if (dernierIndex != -1) {
            cheminAbsolu = cheminAbsolu.substring(dernierIndex + 1);
        }

        return cheminAbsolu.replace(".class", "");
    }

    /**
     * Remplace le dernier backslash ('\\') ou slash ('\') par un point ('.') dans le chemin absolu.
     *
     * @param cheminAbsolu Le chemin absolu à modifier.
     * @return Le chemin avec le dernier backslash remplacé par un point.
     */
    private String remplacerDernierSlashParPoint(String cheminAbsolu) {
        int dernierIndex = indiceDernierSlash(cheminAbsolu);

        // Si un slash ou un backslash est trouvé, on remplace le dernier backslash par un point
        if (dernierIndex != -1) {
            return cheminAbsolu.substring(0, dernierIndex) + '.' + cheminAbsolu.substring(dernierIndex + 1);
        }

        return cheminAbsolu;
    }

    /**
     * Compte le nombre de barres obliques inversées ('\\') dans le chemin absolu.
     *
     * @param cheminAbsolu Le chemin absolu à analyser.
     * @return Le nombre de barres obliques inversées dans le chemin.
     */
    private int nbSlash(String cheminAbsolu) {
        int res = 0;
        for (int i = 0; i < cheminAbsolu.length(); i++) {
            if (cheminAbsolu.charAt(i) == '\\') {
                res++;
            }
        }
        return res;
    }


}