package adg;

import adg.data.Fleche;
import adg.vues.VueClasse;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.awt.*;

public abstract class VueFleche extends Line implements Observateur {
    private Fleche fleche;
    private ModelUML mod;
    public VueFleche(ModelUML modelUML, Fleche fleche){
        this.fleche = fleche;
        this.mod = modelUML;

    }



    public void actualiser(Sujet model) {

        fleche.setPos();

        // Appliquer les positions


        // Mettre à jour la ligne et la tête de flèche
        System.out.println("Visibilité : " +fleche.getVisible() );
        if(fleche.getVisible()){
            this.setVisible(true);
            this.setStartX(fleche.getStartIntersection().getX());
            this.setStartY(fleche.getStartIntersection().getY());
            this.setEndX(fleche.getEndIntersection().getX());
            this.setEndY(fleche.getEndIntersection().getY());
        } else{
            this.setVisible(false);
        }



    }



    public abstract void setLine();

    public Fleche getFleche(){
        return fleche;
    }

}
