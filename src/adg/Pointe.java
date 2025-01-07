package adg;

public class Pointe implements Observateur{



    public void actualiser(Sujet model){
        ModelUML mod = (ModelUML) model;
        System.out.println(mod.getIsHome());
        if(mod.getIsHome()) {
            System.out.println("Pointe : Switching to diagram");
        } else {
            System.out.println("Pointe : Switching to home");
        }
    }
}
