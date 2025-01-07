package adg.data;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Classe utilitaire pour convertir un chemin absolu d'un fichier .class en une classe Java.
 */
public class PathToClass {

    /**
     * Convertit le chemin absolu d'un .class en une classe Java.
     *
     * @param cheminAbsolu Le chemin absolu du fichier .class.
     * @return La classe Java correspondant au chemin absolu.
     * @throws Throwable Si la classe ne peut pas être trouvée ou chargée.
     */
    public static Class<?> convertirCheminEnClasse(String cheminAbsolu) throws Throwable {
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

        return classe;
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
    private static Class<?> chargerClasse(URLClassLoader chargeurClasse, String nomClasse, String cheminAbsolu, int nbslash) throws Throwable {
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
    public static int indiceDernierSlash(String cheminAbsolu) {
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
    public static String extraireNomClasse(String cheminAbsolu) {
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
    public static String remplacerDernierSlashParPoint(String cheminAbsolu) {
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
    public static int nbSlash(String cheminAbsolu) {
        int res = 0;
        for (int i = 0; i < cheminAbsolu.length(); i++) {
            if (cheminAbsolu.charAt(i) == '\\' || cheminAbsolu.charAt(i) == '/') {
                res++;
            }
        }
        return res;
    }


}
