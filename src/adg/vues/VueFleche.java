package adg.vues;

import adg.ModelUML;
import adg.Observateur;
import adg.Sujet;
import adg.data.Fleche;
import javafx.scene.shape.Line;

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
        System.out.println("Actualisation de la flèche");
        System.out.println("Visibilité : " +fleche.getVisible() );
        if(fleche.getVisible()){
            this.setVisible(true);
            this.setStartX(fleche.getStartIntersection().getX());
            this.setStartY(fleche.getStartIntersection().getY());
            this.setEndX(fleche.getEndIntersection().getX());
            this.setEndY(fleche.getEndIntersection().getY());
            this.setLine();
        } else{
            this.setVisible(false);
        }



    }



    public abstract void setLine();

    public Fleche getFleche(){
        return fleche;
    }
}
