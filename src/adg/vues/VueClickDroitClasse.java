package adg.vues;

import adg.MainUML;
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

        MenuItem item3 = MainUML.createMenuItem("Masquer attributs", "a_gray");
        MenuItem item8 = MainUML.createMenuItem("Masquer méthodes", "m_gray");
        MenuItem item1 = MainUML.createMenuItem("Masquer Constructeur", "c_gray");

        MenuItem item6 = MainUML.createMenuItem("Afficher attributs", "a");
        MenuItem item7 = MainUML.createMenuItem("Afficher méthodes", "m");
        MenuItem item2 = MainUML.createMenuItem("Afficher Constructeur", "c");

        // Ajout des items au menu
        this.getItems().addAll(item3, item8, item1,item6,item7,item2);

        // Ajouter des actions aux éléments
        item1.setOnAction(controllerChoixClickDroit);
        item1.setId("A");
        item2.setOnAction(controllerChoixClickDroit);
        item2.setId("B");
        item3.setOnAction(controllerChoixClickDroit);
        item3.setId("C");
        item6.setOnAction(controllerChoixClickDroit);
        item6.setId("F");
        item7.setOnAction(controllerChoixClickDroit);
        item7.setId("G");
        item8.setOnAction(controllerChoixClickDroit);
        item8.setId("H");
    }

    /**
     * Permet de mettre à jour les vues
     * @param mod le sujet à observer
     */
    @Override
    public void actualiser(Sujet mod) {
        // Mise à jour du menu contextuel selon l'état pour eviter les doublons
        if (modelUML.getEtatClickDroitClasse()) {

            VueDiagramme pane = modelUML.getVueDiagramme();
            this.show(pane, modelUML.getCooXClickDroit(), modelUML.getCooYClickDroit());
        } else {
            this.hide();
        }
    }


}
