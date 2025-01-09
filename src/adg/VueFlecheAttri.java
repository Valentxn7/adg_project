package adg;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class VueFlecheAttri extends VueFleche {
    private Text  attribut;
    public VueFlecheAttri(Text attribut){
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
