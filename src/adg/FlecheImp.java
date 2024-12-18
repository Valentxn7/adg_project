package adg;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class FlecheImp extends Fleche {

    public FlecheImp(){
        super();
        this.tete.getPoints().addAll(new Double[]{
                0.0, 0.0,    // Point de départ (sommet de la flèche)
                -15.0, -10.0, // Point gauche inversé
                -15.0, 10.0   // Point droit inversé
        });
    }
    public void setLine(){
        // Ajouter la cardinalité à l'extrémité de départ
        Text startCardinality = new Text(startX - 20, startY, "1");

        // Ajouter la cardinalité à l'extrémité d'arrivée
        Text endCardinality = new Text(endX + 10, endY, "*");

        // Ajouter le nom de la flèche
        Text arrowName = new Text((startX + endX) / 2, startY - 10, "Nom de la flèche");
        this.setStrokeWidth(2);
        this.setStroke(Color.BLACK);
        this.getStrokeDashArray().addAll(10.0, 5.0);
    }

}
