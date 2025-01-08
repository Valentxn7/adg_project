package adg.vues;

import adg.ModelUML;
import adg.Observateur;
import adg.Sujet;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VueClickDroit extends ContextMenu implements Observateur {

    private ModelUML modelUML;

    public VueClickDroit(ModelUML modelUML) {

        this.modelUML = modelUML;

        MenuItem item1 = new MenuItem("Masquer dépendences pour tous");
        MenuItem item2 = new MenuItem("Masquer héritages pour tous");
        MenuItem item3 = new MenuItem("Masquer héritages pour tous");
        MenuItem item4 = new MenuItem("afficher dépendences pour tous");
        MenuItem item5 = new MenuItem("afficher héritages pour tous");
        MenuItem item6 = new MenuItem("afficher attributs pour tous");
        MenuItem item7 = new MenuItem("afficher méthodes pour tous");

        this.getItems().addAll(item1, item2, item3);

        // Ajouter des actions aux éléments
        item1.setOnAction(event -> System.out.println("Option 1 sélectionnée"));
        item2.setOnAction(event -> System.out.println("Option 2 sélectionnée"));
        item3.setOnAction(event -> System.out.println("Option 3 sélectionnée"));
    }
    @Override
    public void actualiser(Sujet mod) {
        if (modelUML.getEtat()) {
            System.out.println("Menu contextuel mis à jour : État actif.");
            VueDiagramme pane = modelUML.getPaneCLickDroit();
            this.show(pane, modelUML.getCooXClickDroit(), modelUML.getCooYClickDroit());

        } else {
            System.out.println("Menu contextuel mis à jour : État inactif.");
        }
    }


}
