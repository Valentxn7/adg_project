package adg;

import java.util.ArrayList;
import java.util.List;

public class MainSave {

    public static void main(String[] args) throws Exception {
        List<Classe> classes = new ArrayList<>();

        Classe classe = new Classe("adg.Classe");
        Analyser analyser = new Analyser(Class.forName("adg.Classe"));

        Classe classe1 = new Classe("adg.MainSave");
        Analyser analyser1 = new Analyser(Class.forName("adg.MainSave"));

        classe = analyser.analyse();
        classe1 = analyser1.analyse();

        classes.add(classe);
        classes.add(classe1);

        Save save = new Save("", "project_name2", classes);
        save.save();
    }
}
