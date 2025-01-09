package adg.data;

import java.util.List;

public class Save {
    public static String save(List<Classe> classes) {
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

        return sb.toString();
    }
}
