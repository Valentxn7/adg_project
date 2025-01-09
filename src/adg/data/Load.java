package adg.data;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Load {
    public static ArrayList<Classe> load(String path) {
        String json = "";
        try {
            File file = new File(path);
            if (!file.exists()) {
                throw new IOException("Le fichier n'existe pas : " + file.getAbsolutePath());
            }

            json = Files.readString(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();

        Classe[] classesArray = gson.fromJson(json, Classe[].class);

        ArrayList<Classe> classesList = new ArrayList<>();
        for (Classe c : classesArray) {
            classesList.add(c);
            System.out.println("LOAD: " + c.getCoords());
        }

        return classesList;
    }
}
