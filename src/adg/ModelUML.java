package adg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ModelUML implements Sujet{
    private ArrayList<Observateur> observateurs;
    private ArrayList<Classe> classes;
    private String windowsTitle = "Home";
    private String folderPath = null;
    private File folder = null;

    public ModelUML() {
        observateurs = new ArrayList<Observateur>();
        classes = new ArrayList<Classe>();
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
            if (!appFolder.mkdirs()) { // Crée le dossier s'il n'existe pas
                System.err.println("Erreur lors de la création du dossier.");
                MainUML.showErrorMessage("Erreur lors de la création du dossier.");
            } else {
                System.out.println("Dossier ADG créé avec succès dans " + appFolder.getAbsolutePath() + ".");
            }
        }
        File newProject = new File(appFolder, nomProjet + ".adg");  // on situe un fichier dans le dossier de l'application

        try {
            if (newProject.createNewFile()) {  // si le fichier a bien été créé
                System.out.println("Le fichier a été créé avec succès dans " + newProject.getAbsolutePath() + ".");
                this.folderPath = appFolder.getAbsolutePath();
                this.folder = new File(appFolder.getAbsolutePath());
                this.windowsTitle = nomProjet;
                switchHome2diag();
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

}
