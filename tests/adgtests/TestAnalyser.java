package adgtests;

import adg.Analyser;
import adg.Classe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestAnalyser {

    Classe classe;
    String className = ExempleClasse.class.getName();

    @BeforeEach
    public void setUp() throws Exception {
        Analyser analyser = new Analyser(className);
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
        assertEquals("adgtests.ExempleClasse", field3[Analyser.FIELD_TYPE]);
        assertEquals("protected", field3[Analyser.FIELD_MODIFIER]);
    }

    @Test
    public void test_class_constructors() throws Exception {
        assertEquals(1, classe.getConstructors().size());

        Object[] constructor1 =  classe.getConstructors().get(0);
        assertEquals("adgtests.ExempleClasse", constructor1[Analyser.CONSTRUCTOR_NAME]);
        assertEquals("public", constructor1[Analyser.CONSTRUCTOR_MODIFIER]);
        assertEquals("[int, int]", constructor1[Analyser.CONSTRUCTOR_PARAMETERS].toString());
    }

    @Test
    public void test_class_methods() throws Exception {
        assertEquals(2, classe.getMethods().size());

        Object[] method1 = classe.getMethods().get(1);
        assertEquals("ExempleMethode1", method1[Analyser.METHOD_NAME]);
        assertEquals("void", method1[Analyser.METHOD_RETURN_TYPE]);
        assertEquals("public", method1[Analyser.METHOD_MODIFIER]);
        assertEquals("[int]", method1[Analyser.METHOD_PARAMETERS].toString());

        Object[] method2 = classe.getMethods().get(0);
        assertEquals("ExempleMethode2", method2[Analyser.METHOD_NAME]);
        assertEquals("java.lang.String", method2[Analyser.METHOD_RETURN_TYPE]);
        assertEquals("private", method2[Analyser.METHOD_MODIFIER]);
        assertEquals("[java.lang.String]", method2[Analyser.METHOD_PARAMETERS].toString());
    }

    @Test
    public void test_class_superclass() throws Exception {
        assertEquals("adgtests.ExempleClasse2", classe.getSuperclass());
    }

    @Test
    public void test_class_interfaces() throws Exception {
        assertEquals(1, classe.getInterfaces().size());

        assertEquals("adgtests.ExempleInterface", classe.getInterfaces().get(0));
    }
}

