package adg;

public interface Observateur {

    /**
     * Permet de mettre à jour les vues
     * @param mod le sujet à observer
     */
    public void actualiser(Sujet mod);

}
