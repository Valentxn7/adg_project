package adg.data;

import adg.Observateur;
import adg.vues.VueDiagramme;
import javafx.scene.Node;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Export {
    /**
     * @param classes Liste des classes à exporter
     * @param dir Répertoire de destination
     * @param file_name Nom du fichier
     */
    public static void exportUML(List<Classe> classes, String dir, String file_name) {
        FileWriter writer;
        try {
            writer = new FileWriter(dir + "/" + file_name);
            writer.write(getUML(classes));
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param classes Liste des classes à exporter
     * @return String UML
     * @throws Exception
     */
    public static String getUML(List<Classe> classes) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("@startuml\n");
        for (Classe classe : classes) {
            sb.append(classe.UMLString());
        }
        sb.append("@enduml");

        return sb.toString();
    }

    /**
     * @param v VueDiagramme à exporter
     * @param dir Répertoire de destination
     * @param png_name Nom du fichier PNG
     */
    public static void exportPNG(VueDiagramme v, String dir, String png_name) {
        WritableImage image = v.snapshot(null, null);

        File file = new File(dir + "/" + png_name);
        writePNG(image, file);
    }


    /**
     * @param image Image à exporter
     * @param file Fichier de destination
     */
    private static void writePNG(WritableImage image, File file) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        System.out.println("width: " + width + " height: " + height);

        int[] pixels = new int[width * height];

        PixelReader reader = image.getPixelReader();
        reader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.setRGB(0, 0, width, height, pixels, 0, width);

        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Image saved to " + file.getAbsolutePath());
    }

    /**
     * @param classes Liste des classes à exporter
     * @return String Java
     * @throws Exception
     */
    public static String getJava(List<Classe> classes) throws Exception {

        StringBuilder source = new StringBuilder();

        source.append("package adg;\n\n");

        for (Classe classe : classes) {
            source.append(classe.toJava());
        }

        return source.toString();
    }

    /**
     * @param classes Liste des classes à exporter
     * @param dir Répertoire de destination
     * @param nomFicher Nom du fichier
     */
    public static void exportJava(List<Classe> classes, String dir, String nomFicher) {
        File file = new File(dir, nomFicher);
        try {
            if (file.createNewFile() || file.canWrite()) {
                System.out.println("File created: " + file.getName() + " location: " + file.getAbsolutePath());
                FileWriter writer = new FileWriter(file);
                writer.write(getJava(classes));
                writer.close();
            } else {
                System.out.println("Cannot write to file: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
