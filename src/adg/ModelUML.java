package adg;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ModelUML implements Sujet{
    private ArrayList<Observateur> observateurs;
    private ArrayList<Classe> classes;
    private String windowsTitle = "Home";
    private String folderPath = null;
    private File folder = null;
    private static final String DATA_FILE = ".data.json"; // Nom du fichier des données
    private static final int MAX_RECENT_FOLDERS = 10;     // Limite du nombre de dossiers récents
    List<String> recentFolders;
    private boolean isHome = true;

    public ModelUML() {
        observateurs = new ArrayList<Observateur>();
        classes = new ArrayList<Classe>();
        setADGFolder();
    }

    public void ajouterClasse(Classe classe) {
        if(classes!=null)
            classes.add(classe);
    }

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
                this.windowsTitle = nomProjet;
                switchHome2diag();
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
        this.windowsTitle = folder.getName();
        addRecentFolder(folder.getAbsolutePath());
        switchHome2diag();
    }

    @Override
    public void enregistrerObservateur(Observateur o) {
        observateurs.add(o);
    }

    @Override
    public void supprimerObservateur(Observateur o) {
        observateurs.remove(o);
    }

    @Override
    public void notifierObservateurs() {
        for(Observateur o : observateurs) {
            o.actualiser(this);
        }
    }

    public void switchHome2diag() {
        isHome = false;
        for(Observateur o : observateurs) {
            o.switchHome2diag();
        }
    }


    public String getWindowsTitle() {
        return windowsTitle;
    }

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

}
