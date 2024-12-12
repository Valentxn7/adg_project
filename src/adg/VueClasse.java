package adg;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.List;

public class VueClasse extends VBox {
    private Classe classe;

    public VueClasse(Classe classe) {
        this.classe = classe;
        this.setSpacing(10); // Espacement entre les éléments
        this.setId("vue-classe");

        // Génération de la vue
        afficherClasse();
    }

    private void afficherClasse() {
        // Nom de la classe
        Label nomClasse = new Label(classe.getClassName());
        nomClasse.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-alignment: center;");
        this.getChildren().add(nomClasse);

        // Séparation
        this.getChildren().add(new Label("----------------"));

        // Affichage des attributs
        List<String[]> fields = classe.getFields();
        if (!fields.isEmpty()) {
            for (String[] field : fields) {
                String visibilite = getUMLVisibility(field[0]);
                Label attribut = new Label(visibilite + " " + field[1] + " : " + field[2]);
                this.getChildren().add(attribut);
            }
            this.getChildren().add(new Label("----------------"));
        }

        // Affichage des constructeurs
        List<Object[]> constructors = classe.getConstructors();
        if (!constructors.isEmpty()) {
            for (Object[] constructor : constructors) {
                String visibilite = getUMLVisibility(constructor[0].toString());
                @SuppressWarnings("unchecked")
                List<String> params = (List<String>) constructor[1];
                Label constructeur = new Label(visibilite + " " + classe.getClassName() + "(" + String.join(", ", params) + ")");
                this.getChildren().add(constructeur);
            }
            this.getChildren().add(new Label("----------------"));
        }

        // Affichage des méthodes
        List<Object[]> methods = classe.getMethods();
        if (!methods.isEmpty()) {
            for (Object[] method : methods) {
                String visibilite = getUMLVisibility(method[0].toString());
                @SuppressWarnings("unchecked")
                List<String> params = (List<String>) method[2];
                Label methode = new Label(visibilite + " " + method[1] + "(" + String.join(", ", params) + ") : " + method[3]);
                this.getChildren().add(methode);
            }
        }
    }

    /**
     * Convertit les modificateurs en symboles UML (+, -, #, ~)
     * @param modifier le modificateur sous forme de chaîne
     * @return un symbole UML correspondant
     */
    private String getUMLVisibility(String modifier) {
        switch (modifier) {
            case "public":
                return "+";
            case "private":
                return "-";
            case "protected":
                return "#";
            default:
                return "~";
        }
    }
}



