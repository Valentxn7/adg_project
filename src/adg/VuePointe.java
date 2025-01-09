package adg;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class VuePointe extends Polygon implements Observateur {
    private int decalage;
    private VueFleche fleche;
    public VuePointe(int d, VueFleche f){
            this.fleche = f;
            this.decalage = d;
    }

    @Override
    public void actualiser(Sujet mod) {
        ModelUML model = (ModelUML) mod;
        setArrowHead(fleche);
    }


    public void setArrowHead(VueFleche fleche) {
            Point2D e = new Point2D(fleche.getEndX(), fleche.getEndY());
            double x = e.getX()+decalage;
            double y = e.getY();
            double angle = Math.atan2(fleche.getEndY() - fleche.getStartY(), fleche.getEndX() - fleche.getStartX()) * 180 / Math.PI;

            this.setLayoutX(x);
            this.setLayoutY(y);
            this.setRotate(angle + decalage);
    }
    public void setDecalage(int d){
        decalage = d;
    }
}


