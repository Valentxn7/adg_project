package adg;

/**
 * Interface représentant un Observateur dans le modèle Observer.
 * Permet à un observateur de réagir aux modifications d'un sujet
 * et de basculer entre différents états ou vues.
 */
public interface Observateur {

    /**
     * Permet de mettre à jour les vues
     * @param mod le sujet à observer
     */
    public void actualiser(Sujet mod);

    /**
     * Permet le changement des vues de l'état d'accueil à l'état d'affichage diagramme
     */
    public void switchHome2diag();

    /**
     * Permet le changement des vues de l'état d'affichage diagramme à l'état d'accueil
     */
    public void switchDiag2Home();

}
