package adg;

import javafx.scene.paint.Color;

public class PointeCreuse extends VuePointe {
    public PointeCreuse(VueFleche f) {
        super(90, f);
        this.setFill(Color.AQUA);
        this.getPoints().addAll(new Double[]{
                -1.0, -10.0, // Point gauche bas
                -15.0, 10.0,  // Point gauche haut
                0.0, 0.0,     // Sommet (pointe de la flèche)
                15.0, 10.0,   // Point droit haut
                1.0, -10.0,// Point droit bas
        });
    }
}