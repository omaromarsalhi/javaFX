package pidev.javafx.controller.user;

import pidev.javafx.model.User.Role;
import pidev.javafx.model.User.User;

public class UserController {

    private User user;
    private static UserController instance;

    private UserController() {

        user=new User(1,
                "omar",
                "salhi",
                "salhiomar362@gmail.com",
                "12710434",
                22,
                29624921,
                "beb saadoun",
                Role.simpleutlisateur);

//        user=new User(2,
//                "latifa",
//                "benzaied",
//                "latifa@gmail.com",
//                "25251400",
//                22,
//                50421001,
//                "menzah 1",
//                Role.simpleutlisateur
//        );
    }

    public static UserController getInstance() {
        if (instance == null)
            instance = new UserController();
        return instance;
    }

    public User getCurrentUser(){
        return this.user;
    }
}
