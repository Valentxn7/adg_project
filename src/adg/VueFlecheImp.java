package adg;

import javafx.scene.paint.Color;

public class VueFlecheImp extends VueFleche {
    public void setLine(){
        this.setStrokeWidth(2);
        this.setStroke(Color.BLACK);
        this.getStrokeDashArray().addAll(10.0, 5.0);
    }
}
