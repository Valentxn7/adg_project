package adg;

import javafx.scene.layout.VBox;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


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
    private boolean isHome;

    /**
     * Constructeur par défaut. Initialise les listes d'observateurs,
     * de classes UML et de chemins.
     */
    public ModelUML() {
        chemins = new ArrayList<>();
        vues = new HashMap<>();
        observateurs = new ArrayList<Observateur>();
        classes = new ArrayList<Classe>();
        setADGFolder();
        isHome = true;
    }

    public void setHome(){
        if (!isHome) {
            isHome = true;
        }else {
            isHome = false;
        }
        notifierObservateurs();
    }

    /**
     * Ajoute une classe UML au modèle.
     *
     * @param classe la classe à ajouter.
     */
    public void ajouterClasse(Classe classe) {
        System.out.println(vueDiagramme == null);
        if (classes != null) {
            classes.add(classe);
            System.out.println(1);
            Observateur vue = new VueClasse(classe);
            System.out.println(2);
            observateurs.add(vue);
            System.out.println(3);
            vueDiagramme.getChildren().add((VBox) vue);
        }
    }

    /**
     * Crée un projet vierge et notifie les observateurs pour basculer
     * à la vue diagramme.
     */
    public boolean creerProjetVierge(String nomProjet) {
        System.out.println("Création d'un projet vierge");

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
                this.windowsTitle = nomProjet;
                addRecentFolder(newProject.getAbsolutePath());
                return true;
            } else {
                System.out.println("Le fichier existe déjà.");
                //MainUML.showErrorMessage("Le fichier existe déjà.");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
        }
        return false;
    }

    public void ouvrirProjet(File folder) {
        this.folder = folder;
        System.out.println("Ouverture du projet : " + folder.getName() + "...");
        this.windowsTitle = folder.getName();
        addRecentFolder(folder.getAbsolutePath());
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


    public String getWindowsTitle() {
        return windowsTitle;
    }

    /**
     * Définit le titre de la fenêtre.
     *
     * @param title le nouveau titre.
     */
    public void setWindowsTitle(String title) {
        windowsTitle = title;
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

    public void setADGFolder(){
        String userHome = System.getProperty("user.home"); // C:\Users\NomUtilisateur sur Windows par ex
        File appFolder = new File(userHome, "ADGProjects"); // définie emplacement mais ne le créé pas encore
        if (!appFolder.exists()) {  // Si le dossier n'existe pas
            createADGfolder();
        }
        this.folderPath = appFolder.getAbsolutePath();
        this.folder = new File(appFolder.getAbsolutePath());
    }

    private void ReadRecentProjects(){
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".adg")) {
                System.out.println("Fichier : " + file.getName());
            }
        }
    }

    private void createADGfolder(){
        String userHome = System.getProperty("user.home"); // C:\Users\NomUtilisateur sur Windows par ex
        File appFolder = new File(userHome, "ADGProjects"); // définie emplacement mais ne le créé pas encore
        if (!appFolder.exists()) {  // Si le dossier n'existe pas
            if (!appFolder.mkdirs()) { // Crée le dossier s'il n'existe pas
                System.err.println("Erreur lors de la création du dossier.");
                //MainUML.showErrorMessage("Erreur lors de la création du dossier.");
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
     * Analyse un fichier UML donné et ajoute la classe analysée au modèle.
     * Notifie ensuite les observateurs.
     *
     * @param cheminAbsolu le chemin absolu du fichier à analyser.
     * @throws ClassNotFoundException si une classe dans le fichier est introuvable.
     */
    public void analyseFichier(String cheminAbsolu) throws Exception {
        // Extrait le nom de la classe à partir du chemin absolu
        System.out.println("Analyse du fichier : " + cheminAbsolu);
        String nomClasse = extraireNomClasse(cheminAbsolu);

        File fichier = new File(cheminAbsolu);
        String cheminClasse = fichier.getParentFile().toURI().toString();

        // Crée un URLClassLoader pour charger la classe
        URLClassLoader chargeurClasse = new URLClassLoader(new URL[]{new URL(cheminClasse)});

        // Charge la classe
        Class<?> classe = chargerClasse(chargeurClasse, nomClasse, cheminAbsolu);

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


    private String extraireNomClasse(String cheminAbsolu) {
        int ind = 0;
        int indbefore = 0;

        for (int i = 0; i < cheminAbsolu.length(); i++) {
            if (cheminAbsolu.charAt(i) == '\\') {
                indbefore = ind;
                ind = i;
            }
        }
        if (indbefore == 0) {
            indbefore = ind;
        }

        // Retourner tout après le deuxième dernier séparateur

        String chemin = cheminAbsolu.substring(indbefore + 1);

        return chemin;
    }

    private Class<?> chargerClasse(URLClassLoader chargeurClasse, String nomClasse, String cheminAbsolu) throws Exception {
        try {
            return chargeurClasse.loadClass(nomClasse);
        } catch (ClassNotFoundException e) {
            // Modifie le chemin absolu pour remplacer le dernier backslash par un point
            cheminAbsolu = remplacerDernierBackslashParPoint(cheminAbsolu);
            System.out.println("Chargement de la classe1 : " + nomClasse);
            File fichier = new File(cheminAbsolu);
            nomClasse = fichier.getName().replace(".class", "");
            String cheminClasse = fichier.getParentFile().toURI().toString();
            chargeurClasse = new URLClassLoader(new URL[]{new URL(cheminClasse)});
            System.out.println("Chargement de la classe : " + nomClasse);
            System.out.println("Chemin de la classe : " + cheminClasse);
            return chargeurClasse.loadClass(nomClasse);
        }
    }

    private String remplacerDernierBackslashParPoint(String cheminAbsolu) {
        int dernierIndexBackslash = cheminAbsolu.lastIndexOf('/');
        System.out.println("dernier index backslash : " + dernierIndexBackslash);
        if (dernierIndexBackslash != -1) {
            String n=  cheminAbsolu.substring(0, dernierIndexBackslash) + '.' + cheminAbsolu.substring(dernierIndexBackslash + 1);
            System.out.println("nouveau chemin : " + n);
            return n;
        }
        return cheminAbsolu;
    }
}
