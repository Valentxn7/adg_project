package adg;

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



}
