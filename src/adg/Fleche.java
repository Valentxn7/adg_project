package adg;
import adg.vues.VueClasse;
import javafx.geometry.Bounds;
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
        // Obtenir les coordonnées centrales des VBoxes dans le parent (Pane)
        Point2D sCenter = start.localToParent(start.getWidth() / 2, start.getHeight() / 2);
        Point2D eCenter = end.localToParent(end.getWidth() / 2, end.getHeight() / 2);

        // Calculer la direction de la flèche
        double deltaX = eCenter.getX() - sCenter.getX();
        double deltaY = eCenter.getY() - sCenter.getY();

        double deltaX2 = sCenter.getX() - eCenter.getX();
        double deltaY2 = sCenter.getY() - eCenter.getY();

        // Calculer l'angle de la flèche
        double angle = Math.atan2(deltaY, deltaX);
        double angle2= Math.atan2(deltaY2, deltaX2);
        // Calculer les points d'intersection pour le début (start)
        Point2D startIntersection = getIntersectionPoint(
                sCenter.getX(), sCenter.getY(), angle, start.getWidth(), start.getHeight()
        );

        // Calculer les points d'intersection pour la fin (end)
        // Utiliser l'angle opposé pour calculer l'intersection à l'autre extrémité
        Point2D endIntersection = getIntersectionPoint(
                eCenter.getX(), eCenter.getY(), angle2 , end.getWidth(), end.getHeight()
        );



        // Appliquer les positions
        this.setStartX(startIntersection.getX());
        this.setStartY(startIntersection.getY());
        this.setEndX(endIntersection.getX());
        this.setEndY(endIntersection.getY());

        // Mettre à jour la ligne et la tête de flèche
        setLine();
        setArrowHead();
    }


    /**
     * Calcule le point d'intersection entre une ligne qui part du centre d'une VBox
     * avec un angle donné et les bords de la VBox.
     *
     * @param centerX La coordonnée X du centre de la VBox
     * @param centerY La coordonnée Y du centre de la VBox
     * @param angle   L'angle de la ligne en radians
     * @param width   La largeur de la VBox
     * @param height  La hauteur de la VBox
     * @return Le point d'intersection avec le bord de la VBox
     */
    private Point2D getIntersectionPoint(double centerX, double centerY, double angle, double width, double height) {
        // Les demi-dimensions de la VBox
        double halfWidth = width / 2;
        double halfHeight = height / 2;

        // Calculer les distances en fonction des proportions
        double tanAngle = Math.tan(angle);

        // Vérifier quel bord est atteint en premier
        if (Math.abs(tanAngle) <= halfHeight / halfWidth) {
            // Intersection avec les côtés gauche/droit
            double x = angle > -Math.PI / 2 && angle < Math.PI / 2 ? halfWidth : -halfWidth;
            double y = x * tanAngle;
            return new Point2D(centerX + x, centerY + y);
        } else {
            // Intersection avec le haut/bas
            double y = angle > 0 ? halfHeight : -halfHeight;
            double x = y / tanAngle;
            return new Point2D(centerX + x, centerY + y);
        }
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
