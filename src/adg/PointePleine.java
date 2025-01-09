package adg;

import javafx.scene.paint.Color;

public class PointePleine extends VuePointe{
    public PointePleine(VueFleche f){
        super(0,f, 10);
        this.setFill(Color.BLUE);
        this.getPoints().addAll(new Double[]{
                0.0, 0.0,    // Point de départ (sommet de la flèche)
                -15.0, -10.0, // Point gauche inversé
                -15.0, 10.0   // Point droit inversé
        });
    }
}