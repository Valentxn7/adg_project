package adg;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class MainSave {

    public static void main(String[] args) throws ClassNotFoundException {
        File directory = new File("src/adg");
        FilenameFilter filter = (dir, name) -> name.endsWith(".java");
        File[] javaFiles = directory.listFiles(filter);

        if (javaFiles != null) {
            List<Classe> classes = new ArrayList<>();

            for(File file : javaFiles) {
                String className = "adg." + file.getName().substring(0, file.getName().length() - 5);
                Classe classe = new Classe(className);

                classes.add(classe);
            }

            Save save = Save.getInstance(classes, null);
            save.save("exports/", "project_name");

        } else {
            System.out.println("Aucun fichier .java trouvé dans le répertoire spécifié.");
        }
    }
}
