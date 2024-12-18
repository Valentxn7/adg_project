package adg;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public abstract class Fleche extends Line{
    protected Polygon tete = new Polygon();
    public Fleche(){
        this.setStrokeWidth(2);
        this.setStroke(Color.BLACK);
        this.getStrokeDashArray().addAll(10.0, 5.0);
    }
    public void setPos(VBox start, VBox end){
        Point2D s = start.localToScene(start.getWidth() / 2, start.getHeight()/2);
        Point2D e = end.localToScene(end.getWidth() / 2, end.getHeight()/2);

        this.setStartX(s.getX());
        this.setStartY(s.getY());

        this.setEndX(e.getX());
        this.setEndY(e.getY()+end.getHeight()/2);
        setLine();
        setArrowHead();
    }
    public void setArrowHead(){

        Point2D s = new Point2D(this.getStartX(), this.getStartY());
        Point2D e = new Point2D(this.getEndX(), this.getEndY());
        double x = e.getX()+10;
        double y = e.getY();

        System.out.println(x);
        System.out.println(y);

        tete.setLayoutX(x);
        tete.setLayoutY(y);
        tete.setRotate(-90);
    }
    public abstract void setLine();
    public Polygon getTete(){
        return tete;
    }

}
