package adg;

import java.util.ArrayList;

public class ModelUML implements Sujet{
    private ArrayList<Observateur> observateurs;
    private ArrayList<Classe> classes;
    private ArrayList<String> chemins;
    private String WindowsTitle = "Home";

    public ModelUML() {
        observateurs = new ArrayList<Observateur>();
        classes = new ArrayList<Classe>();
        chemins = new ArrayList<String>();
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

    public String getWindowsTitle() {
        return WindowsTitle;
    }

    public void setWindowsTitle(String title) {
        WindowsTitle = title;
    }

    public void setFilePath(String filePath) {
        this.chemins.add(filePath);
        notifierObservateurs();
    }

    public String getFilePath() {
        StringBuilder res = new StringBuilder();
        for (String chemin : chemins) {
            res.append(chemin).append("\n");
        }
        return res.toString();
    }
}
