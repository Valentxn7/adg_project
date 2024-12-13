import adg.Classe;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClasseTest {

    @org.junit.jupiter.api.Test
    void ajouterAttribut() {
        Classe classe = new Classe("TestClasse");
        classe.ajouterAttribut("attribut1");
        assertTrue(classe.getAttributs().contains("attribut1"));
    }

    @org.junit.jupiter.api.Test
    void ajouterMethode() {
        Classe classe = new Classe("TestClasse");
        classe.ajouterMethode("methode1");
        assertTrue(classe.getMethodes().contains("methode1"));
    }

    @org.junit.jupiter.api.Test
    void ajouterListeAttributs() {
        Classe classe = new Classe("TestClasse");
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("attribut1");
        attributs.add("attribut2");
        classe.ajouterListeAttributs(attributs);
        assertTrue(classe.getAttributs().contains("attribut1"));
        assertTrue(classe.getAttributs().contains("attribut2"));
    }

    @org.junit.jupiter.api.Test
    void ajouterListeMethodes() {
        Classe classe = new Classe("TestClasse");
        ArrayList<String> methodes = new ArrayList<>();
        methodes.add("methode1");
        methodes.add("methode2");
        classe.ajouterListeMethodes(methodes);
        assertTrue(classe.getMethodes().contains("methode1"));
        assertTrue(classe.getMethodes().contains("methode2"));
    }
}