package adg;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class FlecheAttri extends Fleche {
    private Text  attribut;
    public FlecheAttri( Text attribut){
        super();
        this.tete.setFill(Color.BLUE);
        this.attribut = attribut;
        this.tete.getPoints().addAll(new Double[]{
                -1.0, -10.0, // Point gauche bas
                -15.0, 10.0,  // Point gauche haut
                0.0, 0.0,     // Sommet (pointe de la flèche)
                15.0, 10.0,   // Point droit haut
                1.0, -10.0,// Point droit bas
        });
    }
    public void setLine(){
        this.setStrokeWidth(2);
        this.setStroke(Color.BLACK);
        setPosAttribut();
    }
    private void setPosAttribut(){
        attribut.setLayoutX((this.getStartX()+this.getEndX())/2);
        attribut.setLayoutY((this.getStartY()+this.getEndY())/2);
    }
}
