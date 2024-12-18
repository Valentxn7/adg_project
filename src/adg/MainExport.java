package adg;
import java.util.ArrayList;
import java.util.List;

public class MainExport {

    public static void main(String[] args) throws Exception {
        List<Classe> classes = new ArrayList<>();
        Classe classe = new Classe("adg.Classe");
        Analyser analyser = new Analyser(Class.forName("adg.Classe"));

        classe = analyser.analyse();
        classes.add(classe);

        Export exp = Export.getInstance(classes, null);
        System.out.println("UML : " + exp.getUML());
    }
}
