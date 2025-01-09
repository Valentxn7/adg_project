package adg.vues;

import adg.ModelUML;
import adg.Observateur;
import adg.Sujet;
import adg.data.Analyser;
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

    }
    /**
     * Actualise la vue
     * @param mod le sujet à observer
     */
    public void actualiser(Sujet mod) {
        this.getChildren().clear();
        ModelUML model = (ModelUML) mod;
        afficherClasse(model);
        int[] position = classe.getCoords();
        this.setLayoutX(position[0]);
        this.setLayoutY(position[1]);
        this.classe.setCoords(position[0], position[1]);
        this.classe.setWidth((int)this.getWidth());
        this.classe.setHeight((int)this.getHeight());

    }


    private void afficherClasse(ModelUML model) {
        // Affiche le nom de la classe
        this.getChildren().add(new Label(classe.getClassName()));
        // Affichage des attributs
        ajouterElements(classe.getFields(), model);
        // Affichage des constructeurs
        ajouterConstructeur();
        // Affichage des méthodes
        ajouterMethodes();
    }

    /**
     * Ajoute des éléments à la vue principale selon un constructeur d'élément.
     * @param elements Liste des éléments source
     */
    private <T> void ajouterElements(List<String[]> elements, ModelUML modelUML) {
        if (classe.getShowFields()){
            if (!elements.isEmpty()) {
                VBox box = new VBox();// Espacement entre les éléments
                box.setId("separation");
                for (String[] element : elements) {
                    //System.out.println(element);
                    HBox hBox = creerAttribut(element, modelUML);
                    if(hBox != null) box.getChildren().add(hBox);
                }
                this.getChildren().add(box);
            }
        }

    }
    private void ajouterConstructeur(){
        if (classe.getShowConstructors()){
            List<String[]> constructors = classe.getConstructorsInStrings();
            if (!constructors.isEmpty()) {
                VBox box = new VBox();// Espacement entre les éléments
                box.setId("separation");
                for (String[] constructor : constructors) {
                    box.getChildren().add(creerConstructeur(constructor));
                }
                this.getChildren().add(box);
            }
        }

    }

    private void ajouterMethodes(){
        if (classe.getShowMethods()){
            List<String[]>methods = classe.getMethodsInStrings();
            if (!methods.isEmpty()) {
                VBox box = new VBox();// Espacement entre les éléments
                box.setId("separation");
                for (String[] method : methods) {
                    box.getChildren().add(creerMethode(method));
                }
                this.getChildren().add(box);
            }
        }

    }







    /**
     * Crée une HBox pour un attribut.
     */
    private HBox creerAttribut(String[] field, ModelUML model) {
        HBox res = null;
        if(model.verifierAttributNonFleche(field)) res = creerHBox(getVisibilityCircle(field[Analyser.FIELD_MODIFIER]), new Label(field[Analyser.FIELD_TYPE] + " : " + field[Analyser.FIELD_NAME]));
        System.out.println("resultat" + res==null);
    return res;
    }
    /**
     * Crée une HBox pour un constructeur.
     */
    private HBox creerConstructeur(String[] constructor) {
        String params = constructor[Analyser.CONSTRUCTOR_PARAMETERS];
        return creerHBox(getVisibilityCircle(constructor[Analyser.CONSTRUCTOR_MODIFIER]), new Label(classe.getClassName() + "(" + String.join(", ", params) + ")"));
    }

    /**
     * Crée une HBox pour une méthode.
     */
    private HBox creerMethode(String[] method) {
        String params = method[Analyser.METHOD_PARAMETERS];
        return creerHBox(getVisibilityCircle(method[Analyser.METHOD_MODIFIER].toString()), new Label(method[Analyser.METHOD_NAME] + "(" + String.join(", ", params) + ") : " + method[Analyser.METHOD_RETURN_TYPE]));
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

    public Classe getClasse() {
        return classe;
    }
}








