package adg;

import java.util.List;

public class Save {

    private String path;
    private String project_name;
    private List<Classe> classes;

    /**
     * @param p Path to save the file
     * @param pn Project name
     * @param classes List of classes to save
     */
    public Save (String p, String pn, List<Classe> classes) {
        this.path = p;
        this.project_name = pn;
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
            java.io.FileWriter writer = new java.io.FileWriter(path + project_name + ".adg");
            writer.write(String.valueOf(sb));
            writer.close();

            System.out.println("File saved");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
