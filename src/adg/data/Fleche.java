package adg.data;

import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;

public class Fleche {

    private Point2D sCenter;
    private Point2D eCenter;
    private boolean visible;
    private int[] coordsDebut = new int[2];
    private int[] coordsFin = new int[2];

    private double deltaX;
    private double deltaY;
    private double deltaX2;
    private double deltaY2;

    private double angle;
    private double angle2;

    private Point2D startIntersection;
    private Point2D endIntersection;

    public Fleche(){
        this.visible = true;
        this.coordsDebut[0] = 0;
        this.coordsDebut[1] = 0;
    }

    public Fleche(double deltaX,double deltaY, double deltaX2, double deltaY2, double angle, double angle2, Point2D startIntersection, Point2D endIntersection, Point2D sCenter, Point2D eCenter){
        this.visible = true;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaX2 = deltaX2;
        this.deltaY2 = deltaY2;
        this.angle = angle;
        this.angle2 = angle2;
        this.startIntersection = startIntersection;
        this.endIntersection = endIntersection;
        this.sCenter = sCenter;
        this.eCenter = eCenter;
    }

    public void setSCenter(Point2D sCenter){
        this.sCenter = sCenter;
    }

    public void setECenter(Point2D eCenter){
        this.eCenter = eCenter;
    }

    public void claculerDirectionFleche(){
        if(this.sCenter != null && this.eCenter != null){
            this.deltaX = this.eCenter.getX() - this.sCenter.getX();
            this.deltaY = this.eCenter.getY() - this.sCenter.getY();
            this.deltaX2 = this.sCenter.getX() - this.eCenter.getX();
            this.deltaY2 = this.sCenter.getY() - this.eCenter.getY();
        }
    }

    public void calculerAngle(){
        this.angle = Math.atan2(this.deltaY, this.deltaX);
        this.angle2 = Math.atan2(this.deltaY2, this.deltaX2);
    }

    public void calculerIntersectionDebut(double width, double height){
        this.startIntersection = getIntersectionPoint(sCenter.getX(),sCenter.getY(),angle,width,height);
    }

    public void calculerIntersectionFin(double width, double height){
        this.endIntersection = getIntersectionPoint(eCenter.getX(),eCenter.getY(),angle2,width,height);
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
    public Point2D getIntersectionPoint(double centerX, double centerY, double angle, double width, double height) {
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

    public void setPos(VBox start, VBox end){
        // Obtenir les coordonnées centrales des VBoxes dans le parent (Pane)
        this.setSCenter(start.localToParent(start.getWidth() / 2, start.getHeight() / 2));
        this.setECenter(end.localToParent(end.getWidth() / 2, end.getHeight() / 2));

        // Calculer la direction de la flèche
        this.claculerDirectionFleche();

        // Calculer l'angle de la flèche
        this.calculerAngle();

        // Calculer les points d'intersection pour le début (start)

        this.calculerIntersectionDebut(start.getWidth(), start.getHeight());

        // Calculer les points d'intersection pour la fin (end)
        // Utiliser l'angle opposé pour calculer l'intersection à l'autre extrémité
        this.calculerIntersectionFin(end.getWidth(), end.getHeight());


    }

    public void setCoordsFin(int x, int y){
        this.coordsFin[0] = x;
        this.coordsFin[1] = y;
    }

    public int[] getCoordsFin(){
        return this.coordsFin;
    }


    public Point2D getSCenter(){
        return this.sCenter;
    }

    public Point2D getECenter(){
        return this.eCenter;
    }

    public void setVisible(boolean v){
        this.visible = v;
    }

    public boolean getVisible(){
        return this.visible;
    }

    public void setCoords(int x, int y){
        this.coordsDebut[0] = x;
        this.coordsDebut[1] = y;
    }

    public int[] getCoordsDebut(){
        return this.coordsDebut;
    }

    public void setDeltaX(double deltaX){
        this.deltaX = deltaX;
    }

    public void setDeltaY(double deltaY){
        this.deltaY = deltaY;
    }

    public void setDeltaX2(double deltaX2){
        this.deltaX2 = deltaX2;
    }

    public void setDeltaY2(double deltaY2){
        this.deltaY2 = deltaY2;
    }

    public void setAngle(double angle){
        this.angle = angle;
    }

    public void setAngle2(double angle2){
        this.angle2 = angle2;
    }

    public void setStartIntersection(Point2D startIntersection){
        this.startIntersection = startIntersection;
    }

    public void setEndIntersection(Point2D endIntersection){
        this.endIntersection = endIntersection;
    }

    public double getDeltaX(){
        return this.deltaX;
    }

    public double getDeltaY(){
        return this.deltaY;
    }

    public double getDeltaX2(){
        return this.deltaX2;
    }

    public double getDeltaY2(){
        return this.deltaY2;
    }

    public double getAngle(){
        return this.angle;
    }

    public double getAngle2(){
        return this.angle2;
    }

    public Point2D getStartIntersection(){
        return this.startIntersection;
    }

    public Point2D getEndIntersection(){
        return this.endIntersection;
    }





}
