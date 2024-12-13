package adg;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * La classe {@code ControleurCreateProject} est un contrôleur qui gère
 * la création d'un nouveau projet UML. Elle implémente l'interface
 * {@link EventHandler} pour gérer les événements d'action sur un bouton.
 * Lorsqu'un utilisateur clique sur le bouton correspondant, elle appelle
 * la méthode {@link ModelUML#creerProjetVierge()} pour créer un projet vide.
 */
public class ControleurCreateProject implements EventHandler<ActionEvent> {

    /**
     * Le modèle UML associé à ce contrôleur. Il est utilisé pour interagir
     * avec les données du projet UML.
     */
    private ModelUML modelUML;

    /**
     * Constructeur de la classe {@code ControleurCreateProject}.
     * Initialise le contrôleur avec le modèle UML associé.
     *
     * @param modelUML le modèle UML à associer au contrôleur.
     */
    public ControleurCreateProject(ModelUML modelUML) {
        this.modelUML = modelUML;
    }

    /**
     * Gère l'événement d'action lié au clic sur le bouton. Si le texte du
     * bouton est égal à {@code "+"}, un nouveau projet vide est créé en
     * appelant la méthode {@link ModelUML#creerProjetVierge()}.
     *
     * @param event l'événement d'action déclenché par l'utilisateur.
     */
    @Override
    public void handle(ActionEvent event) {
        Button b = (Button) event.getSource();
        if (b.getText().equals("+")) {
            modelUML.creerProjetVierge();
        }
    }
}
