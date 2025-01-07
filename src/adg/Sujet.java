package adg;

public interface Sujet {

    /**
     * Enregistre un nouvel observateur pour être notifié des changements
     * d'état du sujet.
     *
     * @param o l'observateur à enregistrer.
     */
    void enregistrerObservateur(Observateur o);

    /**
     * Supprime un observateur de la liste des observateurs du sujet.
     * Cet observateur ne sera plus notifié des changements.
     *
     * @param o l'observateur à supprimer.
     */
    void supprimerObservateur(Observateur o);

    /**
     * Notifie tous les observateurs enregistrés d'un changement d'état
     * du sujet.
     */
    void notifierObservateurs();
}
