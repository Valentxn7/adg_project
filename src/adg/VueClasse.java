package adg;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
        nomClasse.setId("separation");
        this.getChildren().add(nomClasse);


        // Affichage des attributs
        List<String[]> fields = classe.getFields();
        if (!fields.isEmpty()) {
            VBox attributsBox = new VBox();
            attributsBox.setId("separation");
            for (String[] field : fields) {
                HBox attributBox = new HBox(5);// Espacement entre le cercle et le texte
                Circle visibilite = getVisibilityCircle(field[0]);
                Label attribut = new Label(field[1] + " : " + field[2]);
                attributBox.getChildren().addAll(visibilite, attribut);
                attributsBox.getChildren().add(attributBox);
            }
            this.getChildren().add(attributsBox);
        }

        // Affichage des constructeurs
        List<Object[]> constructors = classe.getConstructors();
        if (!constructors.isEmpty()) {
            VBox constructeursBox = new VBox();
            constructeursBox.setId("separation");
            for (Object[] constructor : constructors) {
                HBox constructeurBox = new HBox(5);
                Circle visibilite = getVisibilityCircle(constructor[0].toString());
                List<String> params = (List<String>) constructor[1];
                Label constructeur = new Label(classe.getClassName() + "(" + String.join(", ", params) + ")");
                constructeurBox.getChildren().addAll(visibilite, constructeur);
                constructeursBox.getChildren().add(constructeurBox);
            }
            this.getChildren().add(constructeursBox);
        }

        // Affichage des méthodes
        List<Object[]> methods = classe.getMethods();
        if (!methods.isEmpty()) {
            VBox methodesBox = new VBox();
            for (Object[] method : methods) {
                HBox methodeBox = new HBox(5);
                Circle visibilite = getVisibilityCircle(method[0].toString());
                List<String> params = (List<String>) method[2];
                Label methode = new Label(method[1] + "(" + String.join(", ", params) + ") : " + method[3]);
                methodeBox.getChildren().addAll(visibilite, methode);
                methodesBox.getChildren().add(methodeBox);
            }
            this.getChildren().add(methodesBox);
        }
    }

    /**
     * Crée un cercle de couleur correspondant au modificateur de visibilité
     * @param modifier le modificateur sous forme de chaîne
     * @return un cercle coloré correspondant
     */
    private Circle getVisibilityCircle(String modifier) {
        Circle circle = new Circle(5); // Rayon du cercle
        switch (modifier) {
            case "public":
                circle.setFill(Color.GREEN); // Vert pour public
                break;
            case "private":
                circle.setFill(Color.RED); // Rouge pour private
                break;
            case "protected":
                circle.setFill(Color.BLUE); // Bleu pour protected
                break;
            default:
                circle.setFill(Color.GRAY); // Gris pour package-private
                break;
        }
        return circle;
    }
}




