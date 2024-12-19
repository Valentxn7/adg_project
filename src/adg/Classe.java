package adg;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Classe {
    private String class_name;
    private String class_path;
    private String superclass;
    private List<String> interfaces;
    private List<String[]>  fields;
    private List<Object[]> constructors;
    private List<Object[]> methods;

    private double[] coords = new double[2];

    public Classe(String path) {
        this.class_path = path;
        this.class_name = null;

        this.superclass = null;
        this.interfaces = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.constructors = new ArrayList<>();
        this.methods = new ArrayList<>();

        this.coords[0] = 0; // x
        this.coords[1] = 0; // y
    }

    /**
     * Convert the class data to a PlantUML string representation
     * @return PlantUML string representation of the class
     */
    public String UMLString() {
        StringBuilder uml = new StringBuilder();

        // Class Name
        uml.append("class ").append(this.class_name).append(" {\n");

        // Fields
        List<String[]> fields = this.fields;
        for (String[] field : fields) {
            String vis = getUMLVisibility(field[Analyser.FIELD_MODIFIER]);

            uml.append("    ").append(vis).append(" ")
                    .append(field[Analyser.FIELD_NAME]).append(" : ")
                    .append(field[Analyser.FIELD_TYPE]).append("\n");
        }

        uml.append("'----------------\n");

        // Constructors
        List<Object[]> constructors = this.constructors;
        for (Object[] constructor : constructors) {
            String vis = getUMLVisibility(constructor[Analyser.CONSTRUCTOR_MODIFIER].toString());

            uml.append("    ").append(vis).append(" ").append(this.class_name).append("(");

            List<String> parameters = (List<String>) constructor[Analyser.CONSTRUCTOR_PARAMETERS];
            uml.append(String.join(", ", parameters)).append(")\n");
        }


        // Methods
        List<Object[]> methods = this.methods;
        for (Object[] method : methods) {
            String vis = getUMLVisibility(method[Analyser.METHOD_MODIFIER].toString());

            uml.append("    ").append(vis).append(" ").append(method[Analyser.METHOD_NAME]).append("(");

            List<String> parameters = (List<String>) method[Analyser.METHOD_PARAMETERS];
            uml.append(String.join(", ", parameters)).append(") : ")
                    .append(method[Analyser.METHOD_RETURN_TYPE]).append("\n");
        }
        uml.append("}\n");

        // Super Class
        String superClass = this.superclass;
        if (superClass != null) {
            if (!superClass.contains("java.lang"))
                uml.append(this.class_name).append(" --|> ").append(superClass).append("\n");
        }

        // Interfaces
        List<String> interfaces = this.interfaces;
        for (String iface : interfaces) {
            uml.append(this.class_name).append(" ..|> ").append(iface).append("\n");
        }

        return uml.toString();
    }

    public String save() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Convertit les modificateurs en symboles UML (+, -, #, ~)
     *
     * @param modifier le modificateur sous forme de chaîne
     * @return un symbole UML correspondant
     */
    private String getUMLVisibility(String modifier) {
        if (modifier.contains("public")) {
            return "+";
        } else if (modifier.contains("private")) {
            return "-";
        } else if (modifier.contains("protected")) {
            return "#";
        } else {
            return "~";
        }
    }

    public void setClassPath(String path) {
        this.class_path = path;
    }

    public String getClassPath() {
        return this.class_path;
    }
    public void setClassName(String className) {
        this.class_name = className;
    }

    public String getClassName() {
        return this.class_name;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    public String getSuperclass() {
        return this.superclass;
    }

    public void setInterfaces(List<String> interfaces) {
        this.interfaces = interfaces;
    }

    public List<String> getInterfaces() {
        return this.interfaces;
    }

    public void setFields(List<String[]> fields) {
        this.fields = fields;
    }

    public List<String[]> getFields() {
        return this.fields;
    }

    public void setConstructors(List<Object[]> constructors) {
        this.constructors = constructors;
    }

    public List<Object[]> getConstructors() {
        return this.constructors;
    }

    public void setMethods(List<Object[]> methods) {
        this.methods = methods;
    }

    public List<Object[]> getMethods() {
        return this.methods;
    }


    public List<String[]> getConstrutorsInStrings() {
        List<String[]> constructors = new ArrayList<>();

        for (Object[] constructor : this.constructors) {
            String[] new_constructor = new String[3];
            new_constructor[Analyser.CONSTRUCTOR_NAME] = (String) constructor[Analyser.CONSTRUCTOR_NAME];
            new_constructor[Analyser.CONSTRUCTOR_MODIFIER] = (String) constructor[Analyser.CONSTRUCTOR_MODIFIER];
            new_constructor[Analyser.CONSTRUCTOR_PARAMETERS] = String.join(", ", (List<String>) constructor[Analyser.CONSTRUCTOR_PARAMETERS]);

            constructors.add(new_constructor);
        }
        return constructors;
    }

    public List<String[]> getMethodsInStrings() {
        List<String[]> methods = new ArrayList<>();

        for (Object[] method : this.methods) {
            String[] new_method = new String[4];
            new_method[Analyser.METHOD_NAME] = (String) method[Analyser.METHOD_NAME];
            new_method[Analyser.METHOD_RETURN_TYPE] = (String) method[Analyser.METHOD_RETURN_TYPE];
            new_method[Analyser.METHOD_MODIFIER] = (String) method[Analyser.METHOD_MODIFIER];
            new_method[Analyser.METHOD_PARAMETERS] = String.join(", ", (List<String>) method[Analyser.METHOD_PARAMETERS]);

            methods.add(new_method);
        }
        return methods;
    }
}
