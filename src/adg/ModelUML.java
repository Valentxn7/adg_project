package adg;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
/**
 * Classe représentant le modèle UML. Cette classe gère les classes UML,
 * les chemins de fichiers, et la communication avec les observateurs.
 */
public class ModelUML implements Sujet {

    private ArrayList<Observateur> observateurs; // Liste des observateurs
    private ArrayList<Classe> classes;          // Liste des classes UML
    private ArrayList<String> chemins;          // Liste des chemins de fichiers
    private String WindowsTitle = "Home";       // Titre de la fenêtre

    /**
     * Constructeur par défaut. Initialise les listes d'observateurs,
     * de classes UML et de chemins.
     */
    public ModelUML() {
        observateurs = new ArrayList<>();
        classes = new ArrayList<>();
        chemins = new ArrayList<>();
    }

    /**
     * Ajoute une classe UML au modèle.
     *
     * @param classe la classe à ajouter.
     */
    public void ajouterClasse(Classe classe) {
        if (classes != null) {
            classes.add(classe);
        }
    }

    /**
     * Crée un projet vierge et notifie les observateurs pour basculer
     * à la vue diagramme.
     */
    public void creerProjetVierge() {
        System.out.println("Création d'un projet vierge");
        for (Observateur o : observateurs) {
            o.switchHome2diag();
        }
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
     * Retourne le titre de la fenêtre.
     *
     * @return le titre de la fenêtre.
     */
    public String getWindowsTitle() {
        return WindowsTitle;
    }

    /**
     * Définit le titre de la fenêtre.
     *
     * @param title le nouveau titre.
     */
    public void setWindowsTitle(String title) {
        WindowsTitle = title;
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

    private String extraireNomClasse(String cheminAbsolu) {
        String res =  cheminAbsolu
                .substring(cheminAbsolu.lastIndexOf("C:") + 3) // +8 pour ignorer "Desktop\\"
                .replace(".class", "")
                .replace("\\", ".");
        System.out.println(res);
        return res;
    }

    private Class<?> chargerClasse(URLClassLoader chargeurClasse, String nomClasse, String cheminAbsolu) throws Exception {
        try {
            return chargeurClasse.loadClass(nomClasse);
        } catch (ClassNotFoundException e) {
            // Modifie le chemin absolu pour remplacer le dernier backslash par un point
            cheminAbsolu = remplacerDernierBackslashParPoint(cheminAbsolu);

            File fichier = new File(cheminAbsolu);
            nomClasse = fichier.getName().replace(".class", "");
            String cheminClasse = fichier.getParentFile().toURI().toString();
            chargeurClasse = new URLClassLoader(new URL[]{new URL(cheminClasse)});

            return chargeurClasse.loadClass(nomClasse);
        }
    }

    private String remplacerDernierBackslashParPoint(String cheminAbsolu) {
        int dernierIndexBackslash = cheminAbsolu.lastIndexOf('\\');
        if (dernierIndexBackslash != -1) {
            return cheminAbsolu.substring(0, dernierIndexBackslash) + '.' + cheminAbsolu.substring(dernierIndexBackslash + 1);
        }
        return cheminAbsolu;
    }




}