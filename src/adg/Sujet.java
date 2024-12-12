package adg;

public interface Sujet {
    public void enregistrerObservateur(Observateur observateur);
    public void supprimerObservateur(Observateur observateur);
    public void notifierObservateurs();

}
