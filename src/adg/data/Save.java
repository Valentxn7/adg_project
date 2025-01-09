package adg.data;

import java.util.List;

public class Save {
    public static void save(List<Classe> classes, String path) {
        StringBuilder sb = new StringBuilder();

        sb.append("[\n");

        for (Classe classe : classes) {
            sb.append(classe.save());

            if(classes.indexOf(classe) != classes.size() - 1) {
                sb.append(",\n");
            } else {
                sb.append("\n]");
            }
        }

        try {
            java.io.FileWriter writer = new java.io.FileWriter(path);
            writer.write(String.valueOf(sb));
            writer.close();

            System.out.println("File saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
