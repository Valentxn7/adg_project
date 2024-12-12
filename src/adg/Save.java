package adg;

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

    public void saveUML() throws ClassNotFoundException {
        StringBuilder sb = new StringBuilder();

        sb.append("@startuml\n");

        for (Classe classe : classes) {
            Analyser analyser = new Analyser(classe.getClassName());
            sb.append(analyser.analyse().UMLString());
        }

        sb.append("@enduml");

        System.out.println(sb.toString());
    }

    public void saveADG() throws ClassNotFoundException {
        StringBuilder sb = new StringBuilder();

        sb.append("@startadg\n");

        for (VueClasse vue : vues) {
            sb.append(vue.toADGString());

        }

        sb.append("@endadg\n");




    }


}
