package adg;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class VueClasse extends HBox implements Vue{
    private Classe classe;
    public VueClasse(Classe classe){
        this.classe = classe;
        this.setId("classe-label");
    }
    @Override
    public void actualiser(Sujet mod) {
        // Réinitialiser les éléments visuels
        this.getChildren().clear();

        // Reconstruire l'affichage après modification
        HBox header = new HBox();
        Circle circle = new Circle(15, Color.LIGHTGREEN);
        circle.setStroke(Color.BLACK);
        Text className = new Text(classe.getNom());
        header.getChildren().addAll(circle, className);
        header.setSpacing(10);

        VBox body = new VBox();
        body.setSpacing(5);
        ArrayList<String> attributs = classe.getAttributs();
        for (String attribut : attributs) {
            body.getChildren().add(new Text(attribut));
        }
        ArrayList<String> methodes = classe.getMethodes();
        for (String methode : methodes) {
            body.getChildren().add(new Text(methode));
        }

        this.getChildren().addAll(header, body);
    }
}
