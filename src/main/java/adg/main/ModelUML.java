package main.java.adg.main;

import java.util.ArrayList;

public class ModelUML implements Sujet{
    private ArrayList<Vue> vues;
    private ArrayList<Classe> classes;

    public ModelUML() {
        vues = new ArrayList<Vue>();
        classes = new ArrayList<Classe>();
    }

    public void ajouterClasse(Classe classe) {
        if(classes!=null)classes.add(classe);
        notifierVue();
    }

    @Override
    public void notifierVue() {
        for (Vue vue : vues) {
            vue.actualiser(this);
        }
    }

    @Override
    public void ajouterVue(Vue vue) {
        if(vue!=null)vues.add(vue);
    }

    @Override
    public void supprimerVue(Vue vue) {

    }
}
