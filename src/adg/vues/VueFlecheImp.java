package adg.vues;

import adg.ModelUML;
import adg.data.Fleche;
import javafx.scene.paint.Color;

public class VueFlecheImp extends VueFleche {
    public VueFlecheImp(ModelUML modelUML, Fleche fleche) {
        super(modelUML, fleche);
        setLine();

    }

    public void setLine(){
        this.setStrokeWidth(2);
        this.setStroke(Color.BLACK);
        this.getStrokeDashArray().addAll(10.0, 5.0);
    }
}
