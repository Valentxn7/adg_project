package adgtests;

import adg.Classe;
import adg.Load;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestLoad {
    List<Classe> classes = new ArrayList<>();

    String ppath = Paths.get("").toAbsolutePath().toString();
    String path1 = ppath + "\\tests\\adgtests\\";
    String pn1 = "exemple_project_load";

    List<String> list = List.of(new String[]{"adgtests_t.ExempleClasse", "adgtests_t.ExempleClasse2", "adgtests_t.ExempleInterface"});

    @Test
    public void load1() throws Exception {

        Load load = new Load(path1, pn1);
        classes = load.load();

        for (Classe c : classes) {
            System.out.println(c.getClassName());
            assertTrue(list.contains(c.getClassName()));
        }
    }
}


