package adg;

/**
 * L'interface {@code Sujet} représente un objet observable dans le cadre du
 * patron de conception Observateur (Observer Design Pattern). Elle définit
 * les méthodes nécessaires pour gérer les observateurs et les notifier
 * des changements d'état.
 */
public interface Sujet {

    /**
     * Enregistre un nouvel observateur pour être notifié des changements
     * d'état du sujet.
     *
     * @param o l'observateur à enregistrer.
     */
    public void enregistrerObservateur(Observateur o);

    /**
     * Supprime un observateur de la liste des observateurs du sujet.
     * Cet observateur ne sera plus notifié des changements.
     *
     * @param o l'observateur à supprimer.
     */
    public void supprimerObservateur(Observateur o);

    /**
     * Notifie tous les observateurs enregistrés d'un changement d'état
     * du sujet.
     */
    public void notifierObservateurs();
}
