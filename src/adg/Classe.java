package adg;

import java.util.ArrayList;

public class Classe {
    private String nom;
    private ArrayList attributs;
    private ArrayList methodes;

    public Classe(String nom) {
        this.nom = nom;
        attributs = new ArrayList();
        methodes = new ArrayList();
    }
    public void ajouterAttribut(String attribut) {
        attributs.add(attribut);
    }
    public void ajouterMethode(String methode) {
        methodes.add(methode);
    }
    public void ajouterListeAttributs(ArrayList attributs) {
        this.attributs = attributs;
    }
    public void ajouterListeMethodes(ArrayList methodes) {
        this.methodes = methodes;
    }
}
