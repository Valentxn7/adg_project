package adg;

/**
 * Interface représentant un Observateur dans le modèle Observer.
 * Permet à un observateur de réagir aux modifications d'un sujet
 * et de basculer entre différents états ou vues.
 */
public interface Observateur {

    /**
     * Méthode appelée pour mettre à jour l'observateur en réponse à une modification du sujet.
     *
     * @param mod le sujet qui a été modifié.
     */
    public void actualiser(Sujet mod);

    /**
     * Méthode appelée pour effectuer une transition de l'écran d'accueil (Home)
     * vers une vue de diagramme (Diagram).
     */
    public void switchHome2diag();
}
