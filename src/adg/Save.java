package adg;

import java.util.List;

public class Save {

    private String path;
    private String project_name;
    private List<Classe> classes;

    public Save (String path, String project_name, List<Classe> classes) {
        this.path = path;
        this.project_name = project_name;
        this.classes = classes;
    }

    public void save() {
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
            java.io.FileWriter writer = new java.io.FileWriter(this.project_name + ".json");
            writer.write(String.valueOf(sb));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
