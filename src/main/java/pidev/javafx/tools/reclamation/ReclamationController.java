package pidev.javafx.tools.reclamation;


import pidev.javafx.model.Reclamation.Reclamation;

public class ReclamationController {
    private Reclamation rec;
    private static ReclamationController instance;

    private ReclamationController() {}


    public static ReclamationController getInstance(){
        if(instance==null)
            instance=new ReclamationController();
        return instance;
    }

    public Reclamation getCurrentUser(){
        return instance.rec;
    }

    public static void setUser(Reclamation user){
        getInstance().rec=user;
    }

}
