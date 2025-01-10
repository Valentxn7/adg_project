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
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;

public class VueClickDroit extends ContextMenu implements Observateur {

    private ModelUML modelUML;
    private ControllerChoixClickDroit controllerChoixClickDroit;

    public VueClickDroit(ModelUML modelUML,ControllerChoixClickDroit controllerChoixClickDroit) {

        this.modelUML = modelUML;


        // Création des items
        MenuItem item1 = MainUML.createMenuItem("Masquer dépendances pour tous", "d_gray");
        MenuItem item2 = MainUML.createMenuItem("Masquer héritages pour tous", "h_gray");
        MenuItem item3 = MainUML.createMenuItem("Masquer attributs pour tous", "a_gray");
        MenuItem item8 = MainUML.createMenuItem("Masquer méthodes pour tous", "m_gray");
        MenuItem item9 = MainUML.createMenuItem("Masquer constructeurs pour tous", "c_gray");
        MenuItem item4 = MainUML.createMenuItem("Afficher dépendances pour tous", "d");
        MenuItem item5 = MainUML.createMenuItem("Afficher héritages pour tous", "h");
        MenuItem item6 = MainUML.createMenuItem("Afficher attributs pour tous", "a");
        MenuItem item7 = MainUML.createMenuItem("Afficher méthodes pour tous", "m");
        MenuItem item10 = MainUML.createMenuItem("Afficher constructeurs pour tous", "c");

        // Ajout des items au menu
        this.getItems().addAll(item1, item2, item3,item8,item9,item4,item5,item6,item7,item10);

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
        item8.setOnAction(controllerChoixClickDroit);
        item8.setId("8");
        item9.setOnAction(controllerChoixClickDroit);
        item9.setId("9");
        item10.setOnAction(controllerChoixClickDroit);
        item10.setId("10");
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
