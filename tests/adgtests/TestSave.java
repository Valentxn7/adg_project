package adgtests;

import adg.ModelUML;
import adg.data.Analyser;
import adg.data.Classe;
import adg.data.Save;
import adgtests_t.ExempleClasse;
import adgtests_t.ExempleClasse2;
import adgtests_t.ExempleInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestSave {
    ArrayList<Classe> classes = new ArrayList<>();

    String ppath = Paths.get("").toAbsolutePath().toString();
    String path1 = ppath + "\\tests\\adgtests\\";
    String pn1 = "exemple_project_save";

    File file;

    @BeforeEach
    public void setUp() throws Exception {
        Analyser analyser = new Analyser(ExempleClasse.class);
        Classe classe = analyser.analyse();
        classes.add(classe);

        Analyser analyser2 = new Analyser(ExempleClasse2.class);
        Classe classe2 = analyser2.analyse();
        classes.add(classe2);

        Analyser analyser3 = new Analyser(ExempleInterface.class);
        Classe classe3 = analyser3.analyse();
        classes.add(classe3);

        file = new File(path1 + pn1 + ".adg");
        if (file.exists()) {
            file.delete();
            System.out.println("File deleted");
        }
    }

    @Test
    public void save1() throws Exception {
        ModelUML.ecrireFichier(Save.save(classes), path1, pn1 + ".adg");

        File file = new File(path1 + pn1 + ".adg");
        assertTrue(file.exists());
    }
}


