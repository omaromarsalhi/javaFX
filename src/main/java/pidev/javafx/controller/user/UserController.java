package pidev.javafx.controller.user;

import pidev.javafx.model.User.Role;
import pidev.javafx.model.User.User;

public class UserController {

    private User user;
    private static UserController instance;

    private UserController(int number) {
        System.out.println(number);
        if(number==1) {
            user = new User( 1,
                    "omar",
                    "salhi",
                    "salhiomar362@gmail.com",
                    "12710434",
                    22,
                    29624921,
                    "beb saadoun",
                    Role.simpleutlisateur,
                    "salhi",
                    "img/me.png" );
        }
        else {
            user = new User( 2,
                    "latifa",
                    "benzaied",
                    "latifa@gmail.com",
                    "25251400",
                    22,
                    50421001,
                    "menzah 1",
                    Role.simpleutlisateur,
                    "benzaied",
                    "img/latifa.png");
        }
    }

    public static void setUser(int number) {
            instance = new UserController(number);
    }


    public static UserController getInstance() {
        return instance;
    }

    public User getCurrentUser(){
        return this.user;
    }
}
