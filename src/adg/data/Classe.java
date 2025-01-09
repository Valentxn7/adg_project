package adg.data;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Classe {
    private String class_name;
    private String superclass;
    private List<String> interfaces;
    private List<String[]> fields;
    private List<Object[]> constructors;
    private List<Object[]> methods;

    private boolean isInterface;

    private int[] coords = new int[2];
    private int height = 0;
    private int width = 0;

    private boolean showFields = true;
    private boolean showConstructors = true;
    private boolean showMethods = true;

    public Classe(String path) {
        this.class_name = path;

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

    /**
     * Convertit les données de la classe en une chaîne Java
     * @return
     */
    public String toJava() {
        StringBuilder java = new StringBuilder();

        if (!this.isInterface) {
            java.append("public class ").append(this.class_name);
        } else {
            java.append("public interface ").append(this.class_name);
        }
        java.append(" {\n");


        List<String[]> fields = this.fields;
        for (String[] field : fields) {
            java.append("    ").append(field[Analyser.FIELD_MODIFIER]).append(" ")
                    .append(field[Analyser.FIELD_TYPE]).append(" ")
                    .append(field[Analyser.FIELD_NAME]).append(";\n");
        }

        java.append("\n");


        List<Object[]> constructors = this.constructors;
        for (Object[] constructor : constructors) {
            java.append("    ").append(constructor[Analyser.CONSTRUCTOR_MODIFIER]).append(" ")
                    .append(this.class_name).append("(");

            List<String> parameters = (List<String>) constructor[Analyser.CONSTRUCTOR_PARAMETERS];
            java.append(String.join(", ", parameters)).append(") {\n");
            java.append("        // TODO: implementation constructeur\n");
            java.append("    }\n");
        }

        java.append("\n");


        List<Object[]> methods = this.methods;
        for (Object[] method : methods) {
            java.append("    ").append(method[Analyser.METHOD_MODIFIER]).append(" ")
                    .append(method[Analyser.METHOD_RETURN_TYPE]).append(" ")
                    .append(method[Analyser.METHOD_NAME]).append("(");

            List<String> parameters = (List<String>) method[Analyser.METHOD_PARAMETERS];
            java.append(String.join(", ", parameters)).append(") {\n");
            java.append("        // TODO: implementation méthodes\n");
            java.append("    }\n");
        }

        java.append("}\n");

        return java.toString();
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

    public void setInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public void setClassName(String className) {
        this.class_name = className;
    }

    public String getClassName() {
        return this.class_name;
    }

    public String getClassNameWithoutPackages() {
        String[] parts = this.class_name.split("\\.");
        return parts[parts.length - 1];
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


    public List<String[]> getConstructorsInStrings() {
        List<String[]> constructors = new ArrayList<>();

        for (Object[] constructor : this.constructors) {
            String[] new_constructor = new String[3];

            new_constructor[Analyser.CONSTRUCTOR_NAME] = getClassNameWithoutPackages();
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
            new_method[Analyser.METHOD_PARAMETERS] = "";
            for(String s :(List<String>) method[Analyser.METHOD_PARAMETERS]){
                String []temp = s.split("\\.");
                if (temp.length > 0)s = temp[temp.length-1];
                new_method[Analyser.METHOD_PARAMETERS] += s + ", ";
            }
            if (new_method[Analyser.METHOD_PARAMETERS].endsWith(", ")) {
                new_method[Analyser.METHOD_PARAMETERS] =  new_method[Analyser.METHOD_PARAMETERS].substring(0,  new_method[Analyser.METHOD_PARAMETERS].length() - 2);
            }
            methods.add(new_method);
        }
        return methods;
    }

    public boolean equals(Classe classe) {
        return this.class_name.equals(classe.getName());
    }

    public String getName() {
        return this.class_name;
    }

    public void setCoords(int x, int y) {
        this.coords[0] = x;
        this.coords[1] = y;
    }

    public int[] getCoords() {
        return this.coords;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setShowFields(boolean showFields) {
        this.showFields = showFields;
    }

    public void setShowConstructors(boolean showConstructors) {
        this.showConstructors = showConstructors;
    }

    public void setShowMethods(boolean showMethods) {
        this.showMethods = showMethods;
    }

    public boolean getShowFields() {
        return this.showFields;
    }

    public boolean getShowConstructors() {
        return this.showConstructors;
    }

    public boolean getShowMethods() {
        return this.showMethods;
    }

}
