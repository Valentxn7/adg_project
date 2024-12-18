package adg;

import java.io.*;
import java.util.List;

public class Export {
    private static Export instance = null;
    private List<Classe> classes;
    private List<VueClasse> vues;

    private Export(List<Classe> classes, List<VueClasse> vues) {
        this.classes = classes;
        this.vues = vues;
    }

    public static Export getInstance(List<Classe> classes, List<VueClasse> vues) {
        if (instance == null) {
            instance = new Export(classes, vues);
        }
        return instance;
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

    public String getADG() throws ClassNotFoundException {
        StringBuilder sb = new StringBuilder();

        sb.append("@startadg\n");

        /*
        for (VueClasse vue : vues) {
            sb.append(vue.toADGString());
        }
        */

        sb.append("@endadg\n");

        return sb.toString();
    }

    public void save(String dir, String project_name) {
        FileWriter writer;
        try {
            writer = new FileWriter(dir + "/" + project_name + ".adg");
            writer.write(getUML());
            writer.write(getADG());
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
