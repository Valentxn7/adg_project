package adg.vues;

import adg.Observateur;
import adg.Sujet;
import adg.data.Classe;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

public class VueClasse extends VBox implements Observateur {
    /**
     * La classe à afficher
     */
    private Classe classe;
    /**
     * Constructeur de la vue
     * @param classe la classe à afficher
     */
    public VueClasse(Classe classe) {
        this.classe = classe;
        this.setSpacing(10); // Espacement entre les éléments
        this.setId("vue-classe");

        // Génération de la vue
        afficherClasse();
    }
    /**
     * Actualise la vue
     * @param mod le sujet à observer
     */
    public void actualiser(Sujet mod) {
            this.getChildren().clear();
            afficherClasse();
    }

    private void afficherClasse() {
        // Affiche le nom de la classe
        this.getChildren().add(new Label(classe.getClassName()));

        // Affichage des attributs
        ajouterElements(classe.getFields(), this::creerAttribut);

        // Affichage des constructeurs
        ajouterElements(classe.getConstructors(), this::creerConstructeur);

        // Affichage des méthodes
        ajouterElements(classe.getMethods(), this::creerMethode);
    }

    /**
     * Ajoute des éléments à la vue principale selon un constructeur d'élément.
     * @param elements Liste des éléments source
     * @param constructeur Fonction pour transformer chaque élément en HBox
     */
    private <T> void ajouterElements(List<T> elements, java.util.function.Function<T, HBox> constructeur) {
        if (!elements.isEmpty()) {
            VBox box = new VBox();// Espacement entre les éléments
            box.setId("separation");
            for (T element : elements) {
                box.getChildren().add(constructeur.apply(element));
            }
            this.getChildren().add(box);
        }
    }

    /**
     * Crée une HBox pour un attribut.
     */
    private HBox creerAttribut(String[] field) {
        return creerHBox(getVisibilityCircle(field[0]), new Label(field[1] + " : " + field[2]));
    }
    /**
     * Crée une HBox pour un constructeur.
     */
    private HBox creerConstructeur(Object[] constructor) {
        List<String> params = (List<String>) constructor[1];
        return creerHBox(getVisibilityCircle(constructor[0].toString()), new Label(classe.getClassName() + "(" + String.join(", ", params) + ")"));
    }

    /**
     * Crée une HBox pour une méthode.
     */
    private HBox creerMethode(Object[] method) {
        List<String> params = (List<String>) method[2];
        return creerHBox(getVisibilityCircle(method[0].toString()), new Label(method[1] + "(" + String.join(", ", params) + ") : " + method[3]));
    }

    /**
     * Crée une HBox contenant un cercle et un label.
     * @param circle Le cercle représentant la visibilité
     * @param label Le label contenant les informations de l'élément
     * @return Une HBox formatée
     */
    private HBox creerHBox(Circle circle, Label label) {
        HBox box = new HBox(5); // Espacement entre les éléments
        box.getChildren().addAll(circle, label);
        return box;
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




