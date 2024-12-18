package adg;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.net.URLClassLoader;

public class Analyser {
    public static final int FIELD_NAME = 0; // Nom de l'attribut
    public static final int FIELD_TYPE = 1; // Type de l'attribut (String, int, etc.)
    public static final int FIELD_MODIFIER = 2; // Visibilité de l'attribut (public, private, etc.)

    public static final int CONSTRUCTOR_NAME = 0; // Nom du constructeur
    public static final int CONSTRUCTOR_MODIFIER = 1; // Visibilité du constructeur
    public static final int CONSTRUCTOR_PARAMETERS = 2; // Paramètres du constructeur (

    public static final int METHOD_NAME = 0; // Nom de la méthode
    public static final int METHOD_RETURN_TYPE = 1; // Type de retour de la méthode (String, int, etc.)
    public static final int METHOD_MODIFIER = 2; // Visibilité de la méthode
    public static final int METHOD_PARAMETERS = 3; // Paramètres de la méthode

    private Class<?> row_class;

    public Analyser(Class<?> c) throws Exception {
        row_class = c;
    }

    /**
     * Analyse the class and returns a map containing class data
     *
     * @return class data map
     */
    public Classe analyse() {
        Classe classe = new Classe(row_class.getName());

        // Class Name
        classe.setClassName(row_class.getName());

        // Super Class
        Class<?> superClass = row_class.getSuperclass();
        if(superClass != null)
            classe.setSuperclass(superClass.getName());

        // Interfaces
        Class<?>[] interfaces = row_class.getInterfaces();
        List<String> nomsInterfaces = new ArrayList<>();
        for (Class<?> iface : interfaces) {
            nomsInterfaces.add(iface.getName());
        }
        classe.setInterfaces(nomsInterfaces);

        // Fields
        List<String[]> fields = new ArrayList<>();
        for (Field field : row_class.getDeclaredFields()) {
            String[] attributInfo = new String[3];
            attributInfo[FIELD_NAME] = field.getName();
            attributInfo[FIELD_TYPE] = field.getType().getName();
            attributInfo[FIELD_MODIFIER] = Modifier.toString(field.getModifiers());

            fields.add(attributInfo);
        }
        classe.setFields(fields);

        // Constructors
        List<Object[]> constructors = new ArrayList<>();
        for (Constructor<?> constructor : row_class.getDeclaredConstructors()) {
            Object[] constructeurInfo = new Object[3];
            constructeurInfo[CONSTRUCTOR_NAME] = constructor.getName();
            constructeurInfo[CONSTRUCTOR_MODIFIER] = Modifier.toString(constructor.getModifiers());
            constructeurInfo[CONSTRUCTOR_PARAMETERS] = getParameterTypeNames(constructor.getParameterTypes());

            constructors.add(constructeurInfo);
        }
        classe.setConstructors(constructors);

        // Methods
        List<Object[]> methods = new ArrayList<>();
        for (Method method : row_class.getDeclaredMethods()) {
            Object[] methodeInfo = new Object[4];
            methodeInfo[METHOD_NAME] = method.getName();
            methodeInfo[METHOD_RETURN_TYPE] = method.getReturnType().getName();
            methodeInfo[METHOD_MODIFIER] = Modifier.toString(method.getModifiers());
            methodeInfo[METHOD_PARAMETERS] = getParameterTypeNames(method.getParameterTypes());

            methods.add(methodeInfo);
        }
        classe.setMethods(methods);

        return classe;
    }

    /**
     * Get the parameter type names (type --> name)
     *
     * @param parameterTypes the parameter types
     * @return a list of parameter type names
     */
    private List<String> getParameterTypeNames(Class<?>[] parameterTypes) {
        List<String> res = new ArrayList<>();

        for (Class<?> paramType : parameterTypes) {
            res.add(paramType.getName());
        }
        return res;
    }
}

