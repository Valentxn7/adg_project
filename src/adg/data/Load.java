package adg.data;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.EOFException;
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
            if (json.trim().isEmpty()) {
                throw new IOException("Le fichier est vide.");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }


        Gson gson = new Gson();
        Classe[] classesArray = new Classe[0];

        try {
            classesArray = gson.fromJson(json, Classe[].class);
            if (classesArray == null || classesArray.length == 0) {
                throw new JsonSyntaxException("Le fichier JSON est mal formé ou vide.");
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Fichier de sauvegarde incorrect");
            return new ArrayList<>();
        }

        ArrayList<Classe> classesList = new ArrayList<>();
        for (Classe c : classesArray) {
            classesList.add(c);
            System.out.println("LOAD: " + c.getCoords());
        }

        return classesList;
    }

}
