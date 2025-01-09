package adg.vues;

import adg.ModelUML;
import adg.data.Fleche;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class VueFlecheAttri extends VueFleche {
    private Text  attribut;
    public VueFlecheAttri(ModelUML modelUML, Fleche fleche, Text attribut){
        super(modelUML, fleche);
        this.attribut = attribut;
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

    public Text getAttribut(){
        return attribut;
    }
}
