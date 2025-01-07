package adg;

public interface Observateur {

    /**
     * Permet de mettre à jour les adg.vues
     * @param mod le sujet à observer
     */
    void actualiser(Sujet mod);

}
