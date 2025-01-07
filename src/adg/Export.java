package adg;

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

    public String getJava() throws ClassNotFoundException, IOException {

        StringBuilder source = new StringBuilder();

        source.append("package adg;\n\n");

        for (Classe classe : classes) {
            source.append(classe.toJava());
        }

        return source.toString();

    }

    private void exportToJavaFile(String dir, String nomFicher) throws IOException {
        File file = new File(dir + nomFicher);
        if (file.canWrite()) {
            try {
                System.out.println("File created: " + file.getName() + " location: " + file.getAbsolutePath());
                FileWriter writer = new FileWriter(file);
                writer.write(getJava());
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Cannot write to file: " + file.getAbsolutePath());
        }
    }

    public void export(String dir, String project_name) {
        FileWriter writer;
        try {
            writer = new FileWriter(dir + "/" + project_name + ".adg");
            writer.write(getUML());
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
