package adg;

import adg.data.Fleche;
import adg.vues.VueClasse;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public abstract class VueFleche extends Line implements Observateur {


    private Fleche fleche;
    private ModelUML mod;
    public VueFleche(ModelUML modelUML, Fleche fleche){
        this.fleche = fleche;
        this.mod = modelUML;

    }



    public void actualiser(Sujet model) {

        VueClasse[] classe = mod.getCoordonneesFleche(this);
        if (classe != null) {
            setPos(classe[0], classe[1]);
        }
        setLine();
    }


    public void setPos(VBox start, VBox end) {
        // Obtenir les coordonnées centrales des VBoxes dans le parent (Pane)

        // Calculer la direction de la flèche

//        double deltaX = eCenter.getX() - sCenter.getX();
//        double deltaY = eCenter.getY() - sCenter.getY();
//
//        double deltaX2 = sCenter.getX() - eCenter.getX();
//        double deltaY2 = sCenter.getY() - eCenter.getY();


        // Calculer l'angle de la flèche
//        double angle = Math.atan2(deltaY, deltaX);
//        double angle2 = Math.atan2(deltaY2, deltaX2);
        // Calculer les points d'intersection pour le début (start)


//        Point2D startIntersection = getIntersectionPoint(
//                sCenter.getX(), sCenter.getY(), angle, start.getWidth(), start.getHeight()
//        );

        // Calculer les points d'intersection pour la fin (end)
        // Utiliser l'angle opposé pour calculer l'intersection à l'autre extrémité

//        Point2D endIntersection = getIntersectionPoint(
//                eCenter.getX(), eCenter.getY(), angle2, end.getWidth(), end.getHeight()
//        );

        fleche.setPos(start,end);

        // Appliquer les positions
        this.setStartX(fleche.getStartIntersection().getX());
        this.setStartY(fleche.getStartIntersection().getY());
        this.setEndX(fleche.getEndIntersection().getX());
        this.setEndY(fleche.getEndIntersection().getY());

        // Mettre à jour la ligne et la tête de flèche
        setLine();
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

    public abstract void setLine();

}
