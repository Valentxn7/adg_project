package adg.vues;

import adg.ModelUML;
import adg.data.Fleche;
import javafx.scene.paint.Color;

public class VueFlecheExt extends VueFleche {

    public VueFlecheExt(ModelUML modelUML, Fleche fleche) {
        super(modelUML, fleche);
    }
    public void setLine(){
        this.setStroke(Color.BLUE);

    }

}
