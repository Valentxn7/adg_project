package adg.data;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class Analyser {
    public static final int FIELD_NAME = 0; //String: Nom de l'attribut
    public static final int FIELD_TYPE = 1; //String: Type de l'attribut (String, int, etc.)
    public static final int FIELD_MODIFIER = 2; //String: Visibilité de l'attribut (public, private, etc.)

    public static final int CONSTRUCTOR_NAME = 0; //String: Nom du constructeur
    public static final int CONSTRUCTOR_MODIFIER = 1; //String: Visibilité du constructeur
    public static final int CONSTRUCTOR_PARAMETERS_TYPE = 2; //String[]: Paramètres du constructeur
    public static final int CONSTRUCTOR_PARAMETERS_NAME = 3; //String[]: Noms des paramètres du constructeur

    public static final int METHOD_NAME = 0; //String: Nom de la méthode
    public static final int METHOD_RETURN_TYPE = 1; //String: Type de retour de la méthode (String, int, etc.)
    public static final int METHOD_MODIFIER = 2; //String: Visibilité de la méthode
    public static final int METHOD_PARAMETERS_TYPE = 3; //String[]: Paramètres de la méthode
    public static final int METHOD_PARAMETERS_NAME = 4; //String[]: Noms des paramètres de la méthode

    private final Class<?> row_class;

    public Analyser(Class<?> c) throws Exception {
        row_class = c;
    }

    /**
     * Analyse the class and returns a map containing class data
     * @return class data map
     */
    public Classe analyse() {
        Classe classe = new Classe(row_class.getName());

        checkInterface(classe);
        buildSuperClass(classe);
        buildInterfaces(classe);
        buildFields(classe);
        buildConstructors(classe);
        buildMethods(classe);

        return classe;
    }

    /** Check if the class is an interface
     * @param classe the class to check if it is an interface
     */
    private void checkInterface(Classe classe) {
        if (row_class.isInterface()) {
            classe.setInterface(true);
        }
    }

    /** Build the superclass for the class
     * @param classe the class to build the superclass for
     */
    private void buildSuperClass(Classe classe) {
        Class<?> superClass = row_class.getSuperclass();
        if(superClass != null)
            classe.setSuperclass(superClass.getName());
    }


    /** Build the interfaces for the class
     * @param classe the class to build the interfaces for
     */
    private void buildInterfaces(Classe classe) {
        List<String> nomsInterfaces = new ArrayList<>();

        for (Class<?> iface : row_class.getInterfaces()) {
            nomsInterfaces.add(iface.getName());
        }
        classe.setInterfaces(nomsInterfaces);
    }

    /** Build the fields for the class
     * @param classe the class to build the fields for
     */
    private void buildFields(Classe classe) {
        List<String[]> fields = new ArrayList<>();

        for (Field field : row_class.getDeclaredFields()) {
            String[] attributInfo = new String[3];

            attributInfo[FIELD_NAME] = field.getName();
            attributInfo[FIELD_TYPE] = getFieldType(field);
            attributInfo[FIELD_MODIFIER] = Modifier.toString(field.getModifiers());

            fields.add(attributInfo);
        }
        classe.setFields(fields);
    }


    /**
     * @param field
     * @return Exemple: "List<String>"
     */
    private String getFieldType(Field field) {
        Type genericType = field.getGenericType();

        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

            StringBuilder sb = new StringBuilder();
            sb.append(field.getType().getSimpleName());
            sb.append("<");
            for (int i = 0; i < actualTypeArguments.length; i++) {
                sb.append(actualTypeArguments[i].getTypeName());
                if (i < actualTypeArguments.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(">");
            return sb.toString();
        }
        return field.getType().getName();
    }

    /** Build the fields for the class
     * @param classe the class to build the constructors for
     */
    private void buildConstructors(Classe classe) {
        List<Object[]> constructors = new ArrayList<>();

        for (Constructor<?> constructor : row_class.getDeclaredConstructors()) {
            Object[] constructeurInfo = new Object[4];
            constructeurInfo[CONSTRUCTOR_NAME] = constructor.getName();
            constructeurInfo[CONSTRUCTOR_MODIFIER] = Modifier.toString(constructor.getModifiers());

            constructeurInfo[CONSTRUCTOR_PARAMETERS_TYPE] = getParameterTypeNames(constructor.getParameterTypes());
            constructeurInfo[CONSTRUCTOR_PARAMETERS_NAME] = getParameterNames(constructor.getParameters());

            constructors.add(constructeurInfo);
        }
        classe.setConstructors(constructors);
    }

    /** Build the methods for the class
     * @param classe the class to build the methods for
     */
    private void buildMethods(Classe classe) {
        List<Object[]> methods = new ArrayList<>();

        for (Method method : row_class.getDeclaredMethods()) {
            Object[] methodeInfo = new Object[5];

            methodeInfo[METHOD_NAME] = method.getName();
            methodeInfo[METHOD_RETURN_TYPE] = method.getReturnType().getName();
            methodeInfo[METHOD_MODIFIER] = Modifier.toString(method.getModifiers());

            methodeInfo[METHOD_PARAMETERS_TYPE] = getParameterTypeNames(method.getParameterTypes());
            methodeInfo[METHOD_PARAMETERS_NAME] = getParameterNames(method.getParameters());

            methods.add(methodeInfo);
        }

        methods.sort((o1, o2) -> {
            String name1 = (String) o1[METHOD_NAME];
            String name2 = (String) o2[METHOD_NAME];
            return name1.compareTo(name2);
        });

        classe.setMethods(methods);
    }

    /**
     * Get the parameter names (type --> name)
     * @param parameters the parameters of methods or constructors
     * @return a list of parameter names
     */
    private List<String> getParameterNames(Parameter[] parameters) {
        List<String> res = new ArrayList<>();
        for (Parameter parameter : parameters) {
            res.add(parameter.getName());
            System.out.println("NOM RECUPERE : " + parameter.getName());
        }
        return res;
    }

    /**
     * Get the parameter type names (type --> name)
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

