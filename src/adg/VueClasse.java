package adg;

import javafx.scene.control.Label;

import java.util.ArrayList;

public class VueClasse extends Label implements Vue{
    private Classe classe;

    public VueClasse(Classe classe){
        this.classe = classe;
        this.setId("classe-label");
    }

    @Override
    public void actualiser(Sujet mod) {
        ModelUML m = (ModelUML) mod;
        String text = classe.getNom()+"\n";
        ArrayList attributs = classe.getAttributs();
        ArrayList methodes = classe.getMethodes();

        for(int i = 0; i < attributs.size(); i++)
            text += attributs.get(i)+"\n";

        for(int i = 0; i < methodes.size(); i++)
            text += methodes.get(i)+"\n";

        this.setText(text);
    }
}
