package adg.data;

import adg.Observateur;
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
    public void exportUML(List<Classe> classes, String dir, String file_name) {
        FileWriter writer;
        try {
            writer = new FileWriter(dir + "/" + file_name + ".puml");
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
    public String getUML(List<Classe> classes) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("@startuml\n");
        for (Classe classe : classes) {
            sb.append(classe.UMLString());
        }
        sb.append("@enduml");

        return sb.toString();
    }

    /**
     * @param vues Liste des observateurs à exporter (vues du Model)
     * @param dir Répertoire de destination
     * @param png_name Nom du fichier PNG
     */
    public void exportPNG(ArrayList<Observateur> vues, String dir, String png_name) {
        List<Node> nodes = new ArrayList<>();
        for (Observateur observateur : vues) {
            if (observateur instanceof Node) nodes.add((Node) observateur);
        }

        WritableImage image = nodes.get(0).snapshot(null, null);

        for (int i = 1; i < nodes.size(); i++) {
            image = nodes.get(i).snapshot(null, image);
        }

        File file = new File(dir + "/" + png_name + ".png");
        writePNG(image, file);
    }


    /**
     * @param image Image à exporter
     * @param file Fichier de destination
     */
    private void writePNG(WritableImage image, File file) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

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
    }
}
