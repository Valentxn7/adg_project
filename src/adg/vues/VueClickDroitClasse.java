package adg.vues;

import adg.ModelUML;
import adg.Observateur;
import adg.Sujet;
import adg.control.ControllerChoixClickDroit;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class VueClickDroitClasse extends ContextMenu implements Observateur {

    private ModelUML modelUML;
    private ControllerChoixClickDroit controllerChoixClickDroit;

    public VueClickDroitClasse(ModelUML modelUML,ControllerChoixClickDroit controllerChoixClickDroit) {

        this.modelUML = modelUML;
        this.controllerChoixClickDroit = controllerChoixClickDroit;

        // Création des items
        MenuItem item1 = new MenuItem("Masquer dépendences");
        MenuItem item2 = new MenuItem("Masquer héritages");
        MenuItem item3 = new MenuItem("Masquer attributs");
        MenuItem item4 = new MenuItem("Afficher dépendences");
        MenuItem item5 = new MenuItem("Afficher héritages");
        MenuItem item6 = new MenuItem("Afficher attribut");
        MenuItem item7 = new MenuItem("Afficher méthodes");

        // Ajout des items au menu
        this.getItems().addAll(item1, item2, item3,item4,item5,item6,item7);

        // Ajouter des actions aux éléments
        item1.setOnAction(controllerChoixClickDroit);
        item1.setId("A");
        item2.setOnAction(controllerChoixClickDroit);
        item2.setId("B");
        item3.setOnAction(controllerChoixClickDroit);
        item3.setId("C");
        item4.setOnAction(controllerChoixClickDroit);
        item4.setId("D");
        item5.setOnAction(controllerChoixClickDroit);
        item5.setId("E");
        item6.setOnAction(controllerChoixClickDroit);
        item6.setId("F");
        item7.setOnAction(controllerChoixClickDroit);
        item7.setId("G");
    }

    /**
     * Permet de mettre à jour les vues
     * @param mod le sujet à observer
     */
    @Override
    public void actualiser(Sujet mod) {
        // Mise à jour du menu contextuel selon l'état pour eviter les doublons
        if (modelUML.getEtatClickDroitClasse()) {

            VueDiagramme pane = modelUML.getPaneCLickDroit();
            this.show(pane, modelUML.getCooXClickDroit(), modelUML.getCooYClickDroit());
        } else {
            this.hide();
        }
    }


}
