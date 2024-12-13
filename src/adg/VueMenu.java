package adg;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class VueMenu extends MenuBar implements Observateur {
    private ModelUML modelUML;

    public VueMenu(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    @Override
    public void actualiser(Sujet mod) {
    }

    @Override
    public void switchHome2diag() {
        for (Menu m : this.getMenus()){
            for (MenuItem mi : m.getItems()){
                mi.setDisable(false);
            }
        }
        System.out.println("Vue Menu : Switching to diagram");
    }


}
