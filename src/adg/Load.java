package adg;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Load {
    private String path;
    private String project_name;

    /**
     * @param p Path to load the file
     * @param pn Project name
     */
    public Load(String p, String pn) {
        this.path = p;
        this.project_name = pn;
    }

    public List<Classe> load() {
        String json = "";
        try {
            File file = new File(this.path + "/" + this.project_name + ".adg");
            if (!file.exists()) {
                throw new IOException("Le fichier n'existe pas : " + file.getAbsolutePath());
            }

            json = Files.readString(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();

        Classe[] classesArray = gson.fromJson(json, Classe[].class);

        List<Classe> classesList = new ArrayList<>();
        for (Classe c : classesArray) {
            classesList.add(c);
        }

        return classesList;
    }
}
