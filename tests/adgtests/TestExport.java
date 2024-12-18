package adgtests;

import adg.Analyser;
import adg.Classe;
import adg.Export;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestExport {
    Classe classe;
    Classe classe2;
    Classe classe3;

    String className = ExempleClasse.class.getName();
    String className2 = ExempleClasse2.class.getName();
    String className3 = ExempleInterface.class.getName();

    Export exp;

    @BeforeEach
    public void setUp() throws Exception {
        ArrayList<Classe> classes = new ArrayList<>();

        Analyser analyser = new Analyser(ExempleClasse.class);
        classe = analyser.analyse();
        classes.add(classe);

        Analyser analyser2 = new Analyser(ExempleClasse2.class);
        classe2 = analyser2.analyse();
        classes.add(classe2);

        Analyser analyser3 = new Analyser(ExempleInterface.class);
        classe3 = analyser3.analyse();
        classes.add(classe3);

        exp = Export.getInstance(classes, null);
    }

    @Test
    public void test_uml() throws Exception {
        String uml = exp.getUML();

        String expected = """
        @startuml
        class adgtests.ExempleClasse {
            + exemple1 : int
            - exemple2 : java.lang.String
            # exemple3 : adgtests.ExempleClasse
        '----------------
            + adgtests.ExempleClasse(int, int)
            + ExempleMethode1(int) : void
            - ExempleMethode2(java.lang.String) : java.lang.String
        }
        adgtests.ExempleClasse --|> adgtests.ExempleClasse2
        adgtests.ExempleClasse ..|> adgtests.ExempleInterface
        class adgtests.ExempleClasse2 {
        '----------------
            + adgtests.ExempleClasse2()
        }
        class adgtests.ExempleInterface {
        '----------------
        }
        @enduml""";

        String normalizedUml = uml.replace("\r\n", "\n").strip();
        String normalizedExpected = expected.replace("\r\n", "\n").strip();

        assertEquals(normalizedExpected, normalizedUml);

    }
}

