package adg;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe singleton responsable de la sauvegarde des données UML et ADG.
 * Gère la génération des représentations textuelles des diagrammes UML et ADG
 * ainsi que leur sauvegarde dans des fichiers.
 */
public class Save {

    private static Save instance = null; // Instance unique du singleton
    private List<Classe> classes;        // Liste des classes du projet
    private List<VueClasse> vues;        // Liste des vues associées aux classes

    /**
     * Constructeur privé pour implémenter le pattern singleton.
     * Initialise les listes de classes et de vues.
     *
     * @param classes Liste des classes à inclure dans le projet.
     * @param vues    Liste des vues associées aux classes.
     */
    private Save(List<Classe> classes, List<VueClasse> vues) {
        this.classes = classes;
        this.vues = vues;
    }

    /**
     * Méthode statique pour obtenir l'instance unique de la classe Save.
     * Si l'instance n'existe pas, elle est créée.
     *
     * @param classes Liste des classes à inclure dans le projet.
     * @param vues    Liste des vues associées aux classes.
     * @return L'instance unique de la classe Save.
     */
    public static Save getInstance(List<Classe> classes, List<VueClasse> vues) {
        if (instance == null) {
            instance = new Save(classes, vues);
        }
        return instance;
    }

    /**
     * Génère une représentation textuelle du diagramme UML au format PlantUML.
     *
     * @return Une chaîne contenant la description UML au format PlantUML.
     * @throws ClassNotFoundException Si une classe ne peut pas être analysée.
     */
    public String getUML() throws ClassNotFoundException {
        StringBuilder sb = new StringBuilder();

        sb.append("@startuml\n");

        for (Classe classe : classes) {
            Analyser analyser = new Analyser(classe.getClassName());
            sb.append(analyser.analyse().UMLString());
        }

        sb.append("@enduml");

        return sb.toString();
    }

    /**
     * Génère une représentation textuelle du diagramme ADG.
     *
     * @return Une chaîne contenant la description ADG.
     * @throws ClassNotFoundException Si une classe ou vue ne peut pas être analysée.
     */
    public String getADG() throws ClassNotFoundException {
        StringBuilder sb = new StringBuilder();

        sb.append("@startadg\n");

        /*
        for (VueClasse vue : vues) {
            sb.append(vue.toADGString());
        }
        */

        sb.append("@endadg\n");

        return sb.toString();
    }

    /**
     * Sauvegarde les données UML et ADG dans un fichier.
     * Les données sont sauvegardées dans le répertoire spécifié sous le nom de projet donné.
     *
     * @param dir          Le répertoire dans lequel enregistrer le fichier.
     * @param project_name Le nom du projet utilisé pour nommer le fichier.
     */
    public void save(String dir, String project_name) {
        FileWriter writer;
        try {
            writer = new FileWriter(dir + "/" + project_name + ".adg");
            writer.write(getUML());
            writer.write(getADG());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
