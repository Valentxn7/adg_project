package adg;

import adg.data.Analyser;
import adg.data.Classe;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.io.File;
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
     * @param nomClasse Le nom de la classe à charger.
     * @param cheminAbsolu Le chemin absolu du fichier .class.
     * @param nbslash Le nombre de barres obliques inversées dans le chemin.
     * @return La classe chargée.
     * @throws Throwable Si la classe ne peut pas être trouvée ou chargée.
     */
    private Class<?> chargerClasse(URLClassLoader chargeurClasse, String nomClasse, String cheminAbsolu, int nbslash) throws Throwable {
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
            } catch (ClassNotFoundException | NoClassDefFoundError e) { // Sinon, on attrape l'exception et on modifie le chemin absolu
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