package adg;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Save {
    private static Save instance = null;
    private List<Classe> classes;
    private List<VueClasse> vues;

    private Save(List<Classe> classes, List<VueClasse> vues) {
        this.classes = classes;
        this.vues = vues;
    }

    public static Save getInstance(List<Classe> classes, List<VueClasse> vues) {
        if (instance == null) {
            instance = new Save(classes, vues);
        }
        return instance;
    }

    public String getUML() throws ClassNotFoundException {
        StringBuilder sb = new StringBuilder();

        sb.append("@startuml\n");

        for (Classe classe : classes) {
            Analyser analyser = new Analyser(classe.getClassName());
            sb.append(analyser.analyse().UMLString());
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
