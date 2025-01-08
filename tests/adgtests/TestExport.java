package adgtests;

import adg.data.Analyser;
import adg.data.Classe;
import adg.data.Export;
import adgtests_t.ExempleClasse;
import adgtests_t.ExempleClasse2;
import adgtests_t.ExempleInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestExport {
    Classe classe;
    Classe classe2;
    Classe classe3;

    ArrayList<Classe> classes = new ArrayList<>();

    String className = ExempleClasse.class.getName();
    String className2 = ExempleClasse2.class.getName();
    String className3 = ExempleInterface.class.getName();

    Export exp;

    @BeforeEach
    public void setUp() throws Exception {
        Analyser analyser = new Analyser(ExempleClasse.class);
        classe = analyser.analyse();
        classes.add(classe);

        Analyser analyser2 = new Analyser(ExempleClasse2.class);
        classe2 = analyser2.analyse();
        classes.add(classe2);

        Analyser analyser3 = new Analyser(ExempleInterface.class);
        classe3 = analyser3.analyse();
        classes.add(classe3);
    }

    @Test
    public void test_uml() throws Exception {
        String uml = exp.getUML(classes);

        String expected = """
        @startuml
        class adgtests_t.ExempleClasse {
            + exemple1 : int
            - exemple2 : java.lang.String
            # exemple3 : adgtests_t.ExempleClasse
        '----------------
            + adgtests_t.ExempleClasse(int, int)
            + ExempleMethode1(int) : void
            - ExempleMethode2(java.lang.String) : java.lang.String
        }
        adgtests_t.ExempleClasse --|> adgtests_t.ExempleClasse2
        adgtests_t.ExempleClasse ..|> adgtests_t.ExempleInterface
        class adgtests_t.ExempleClasse2 {
        '----------------
            + adgtests_t.ExempleClasse2()
        }
        class adgtests_t.ExempleInterface {
        '----------------
        }
        @enduml""";

        String normalizedUml = uml.replace("\r\n", "\n").strip();
        String normalizedExpected = expected.replace("\r\n", "\n").strip();

        assertEquals(normalizedExpected, normalizedUml);

    }
}

