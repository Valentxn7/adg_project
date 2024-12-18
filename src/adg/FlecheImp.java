package adg;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
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
        this.setStrokeWidth(2);
        this.setStroke(Color.BLACK);
        this.getStrokeDashArray().addAll(10.0, 5.0);
    }
}
