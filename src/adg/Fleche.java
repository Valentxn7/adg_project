package adg;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public abstract class Fleche extends Line implements Observateur {
    protected Polygon tete = new Polygon();
    public Fleche(){
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(2);
    }

    public void actualiser(Sujet model){
        ModelUML mod = (ModelUML) model;
        VueClasse[] classe = mod.getCoordonneesFleche(this);
        if(classe!= null){
            setPos(classe[0], classe[1]);
        }
    }
    public void setPos(VBox start, VBox end) {
        // Obtenir les coordonnées centrales des VBoxes dans la scène
        Point2D s = start.localToScene(start.getWidth() / 2, start.getHeight() / 2);
        Point2D e = end.localToScene(end.getWidth() / 2, end.getHeight() / 2);

        // Déterminer les positions de départ
        this.setStartX(s.getX());
        this.setStartY(s.getY());

        // Calculer la direction de la flèche
        double deltaX = e.getX() - s.getX();
        double deltaY = e.getY() - s.getY();
        double angle = Math.atan2(deltaY, deltaX);

        // Ajuster la position finale en fonction des dimensions de la VBox cible
        double arrowLength = Math.sqrt(deltaX * deltaX + deltaY * deltaY); // Longueur totale de la ligne
        double endOffsetX = (end.getWidth() / 2) * Math.cos(angle); // Décalage horizontal
        double endOffsetY = (end.getHeight() / 2) * Math.sin(angle); // Décalage vertical

        // Appliquer le décalage pour que la flèche pointe au bord de la VBox
        this.setEndX(e.getX() - endOffsetX);
        this.setEndY(e.getY() - endOffsetY);

        // Mettre à jour la ligne et la tête de flèche
        setLine();
        setArrowHead();
    }

    public void setArrowHead(){

        Point2D e = new Point2D(this.getEndX(), this.getEndY());
        double x = e.getX()+10;
        double y = e.getY();

        tete.setLayoutX(x);
        tete.setLayoutY(y);
        tete.setRotate(-90);
    }
    public abstract void setLine();
    public Polygon getTete(){
        return tete;
    }

}
