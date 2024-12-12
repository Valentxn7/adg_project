package adg;

import java.util.ArrayList;

public class ModelUML implements Sujet{
    private ArrayList<Observateur> observateurs;
    private ArrayList<Classe> classes;

    public ModelUML() {
        observateurs = new ArrayList<Observateur>();
        classes = new ArrayList<Classe>();
    }

    public void ajouterClasse(Classe classe) {
        if(classes!=null)
            classes.add(classe);
    }

    public void creerProjetVierge() {
        System.out.println("Création d'un projet vierge");
        for(Observateur o : observateurs) {
            o.switchHome2diag();
        }
    }

    @Override
    public void enregistrerObservateur(Observateur o) {
        observateurs.add(o);
    }

    @Override
    public void supprimerObservateur(Observateur o) {
        observateurs.remove(o);
    }

    @Override
    public void notifierObservateurs() {
        for(Observateur o : observateurs) {
            o.actualiser(this);
        }
    }

}
