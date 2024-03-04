package pidev.javafx.tools;

import pidev.javafx.model.user.User;


public class UserController {

    private User user;
    private static UserController instance;

    private UserController() {}


    public static UserController getInstance(){
        if(instance==null)
            instance=new UserController();
        return instance;
    }

    public User getCurrentUser(){
        return instance.user;
    }

    public static void setUser(User user){
        getInstance().user=user;
    }
}

