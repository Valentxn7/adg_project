package adg.data;

import java.io.*;
import java.util.List;

public class Export {
    private List<Classe> classes;

    /**
     * @param classes List of classes to export
     */
    public Export(List<Classe> classes) {
        this.classes = classes;
    }

    public String getUML() throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("@startuml\n");
        for (Classe classe : classes) {
            sb.append(classe.UMLString());
        }
        sb.append("@enduml");

        return sb.toString();
    }

    public void export(String dir, String project_name) {
        FileWriter writer;
        try {
            writer = new FileWriter(dir + "/" + project_name + ".puml");
            writer.write(getUML());
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
