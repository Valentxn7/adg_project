package adgtests;

import adg.data.Analyser;
import adg.data.Classe;
import adgtests_t.ExempleClasse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestAnalyser {

    Classe classe;
    String className = ExempleClasse.class.getName();

    @BeforeEach
    public void setUp() throws Exception {
        Analyser analyser = new Analyser(ExempleClasse.class);
        classe = analyser.analyse();
    }

    @Test
    public void test_class_name() throws Exception {
        assertEquals(className, classe.getClassName());
    }

    @Test
    public void test_class_attributes() throws Exception {
        assertEquals(3, classe.getFields().size());

        String[] field1 = classe.getFields().get(0);
        assertEquals("exemple1", field1[Analyser.FIELD_NAME]);
        assertEquals("int", field1[Analyser.FIELD_TYPE]);
        assertEquals("public", field1[Analyser.FIELD_MODIFIER]);

        String[] field2 = classe.getFields().get(1);
        assertEquals("exemple2", field2[Analyser.FIELD_NAME]);
        assertEquals("java.lang.String", field2[Analyser.FIELD_TYPE]);
        assertEquals("private", field2[Analyser.FIELD_MODIFIER]);

        String[] field3 = classe.getFields().get(2);
        assertEquals("exemple3", field3[Analyser.FIELD_NAME]);
        assertEquals("adgtests_t.ExempleClasse", field3[Analyser.FIELD_TYPE]);
        assertEquals("protected", field3[Analyser.FIELD_MODIFIER]);
    }

    @Test
    public void test_class_constructors() throws Exception {
        assertEquals(1, classe.getConstructors().size());

        Object[] constructor1 =  classe.getConstructors().get(0);
        assertEquals("adgtests_t.ExempleClasse", constructor1[Analyser.CONSTRUCTOR_NAME]);
        assertEquals("public", constructor1[Analyser.CONSTRUCTOR_MODIFIER]);
        assertEquals("[int, int]", constructor1[Analyser.CONSTRUCTOR_PARAMETERS].toString());
    }

    @Test
    public void test_class_methods() throws Exception {
        assertEquals(2, classe.getMethods().size());

        List<Object[]> sortedMethods = new ArrayList<>(classe.getMethods());
        sortedMethods.sort((m1, m2) ->
                ((String) m1[Analyser.METHOD_NAME]).compareTo((String) m2[Analyser.METHOD_NAME])
        );

        assertEquals(2, sortedMethods.size());

        // Méthode 1 (ExempleMethode1 après tri)
        Object[] method1 = sortedMethods.get(0);
        assertEquals("ExempleMethode1", method1[Analyser.METHOD_NAME]);
        assertEquals("void", method1[Analyser.METHOD_RETURN_TYPE]);
        assertEquals("public", method1[Analyser.METHOD_MODIFIER]);
        assertEquals("[int]", method1[Analyser.METHOD_PARAMETERS].toString());

        // Méthode 2 (ExempleMethode2 après tri)
        Object[] method2 = sortedMethods.get(1);
        assertEquals("ExempleMethode2", method2[Analyser.METHOD_NAME]);
        assertEquals("java.lang.String", method2[Analyser.METHOD_RETURN_TYPE]);
        assertEquals("private", method2[Analyser.METHOD_MODIFIER]);
        assertEquals("[java.lang.String]", method2[Analyser.METHOD_PARAMETERS].toString());
    }

    @Test
    public void test_class_superclass() throws Exception {
        assertEquals("adgtests_t.ExempleClasse2", classe.getSuperclass());
    }

    @Test
    public void test_class_interfaces() throws Exception {
        assertEquals(1, classe.getInterfaces().size());

        assertEquals("adgtests_t.ExempleInterface", classe.getInterfaces().get(0));
    }
}

