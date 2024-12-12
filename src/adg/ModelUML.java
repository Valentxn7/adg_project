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
        if(classes!=null)classes.add(classe);
        notifierObservateurs();
    }



    @Override
    public void enregistrerObservateur(Observateur observateur) {

    }

    @Override
    public void supprimerObservateur(Observateur observateur) {

    }

    @Override
    public void notifierObservateurs() {
        for (Observateur observateur : observateurs) {
            observateur.actualiser(this);
        }
    }
}
