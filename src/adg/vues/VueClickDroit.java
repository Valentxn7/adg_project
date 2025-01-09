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
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;

public class VueClickDroit extends ContextMenu implements Observateur {

    private ModelUML modelUML;
    private ControllerChoixClickDroit controllerChoixClickDroit;

    public VueClickDroit(ModelUML modelUML,ControllerChoixClickDroit controllerChoixClickDroit) {

        this.modelUML = modelUML;


        // Création des items
        MenuItem item1 = new MenuItem("Masquer dépendences pour tous");
        MenuItem item2 = new MenuItem("Masquer héritages pour tous");
        MenuItem item3 = new MenuItem("Masquer attributs pour tous");
        MenuItem item4 = new MenuItem("Afficher dépendences pour tous");
        MenuItem item5 = new MenuItem("Afficher héritages pour tous");
        MenuItem item6 = new MenuItem("Afficher attributs pour tous");
        MenuItem item7 = new MenuItem("Afficher méthodes pour tous");

        // Ajout des items au menu
        this.getItems().addAll(item1, item2, item3,item4,item5,item6,item7);

        // Ajouter des actions aux éléments
        item1.setOnAction(controllerChoixClickDroit);
        item1.setId("1");
        item2.setOnAction(controllerChoixClickDroit);
        item2.setId("2");
        item3.setOnAction(controllerChoixClickDroit);
        item3.setId("3");
        item4.setOnAction(controllerChoixClickDroit);
        item4.setId("4");
        item5.setOnAction(controllerChoixClickDroit);
        item5.setId("5");
        item6.setOnAction(controllerChoixClickDroit);
        item6.setId("6");
        item7.setOnAction(controllerChoixClickDroit);
        item7.setId("7");
    }

    /**
     * Permet de mettre à jour les vues
     * @param mod le sujet à observer
     */
    @Override
    public void actualiser(Sujet mod) {
        // Mise à jour du menu contextuel selon l'état pour eviter les doublons
        if (modelUML.getEtat()) {
            VueDiagramme pane = modelUML.getVueDiagramme();
            this.show(pane, modelUML.getCooXClickDroit(), modelUML.getCooYClickDroit());
        } else {

            this.hide();
        }
    }


}
