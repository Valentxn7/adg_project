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

}
