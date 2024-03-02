package pidev.javafx.tools;


import pidev.javafx.model.user.Role;
import pidev.javafx.model.user.User;


public class UserController {

    private User user;
    private static UserController instance;

    private UserController(User user) {
        this.user=user;
//        System.out.println(number);
//        if(number==1) {
//            user = new User( 1,
//                    "omar",
//                    "salhi",
//                    "salhiomar362@gmail.com",
//                    "12710434",
//                    22,
//                    29624921,
//                    "beb saadoun",
//                    Role.Citoyen,
//                    "salhi",
//                    "img/marketPlace/me.png"
//                    );
//        }
//        else {
//            user = new User( 2,
//                    "latifa",
//                    "benzaied",
//                    "latifa@gmail.com",
//                    "25251400",
//                    22,
//                    50421001,
//                    "menzah 1",
//                    Role.Citoyen,
//                    "benzaied",
//                    "img/marketPlace/latifa.png");
//        }
    }

    public static void setUser(User user) {
            instance = new UserController(user);
    }


    public static UserController getInstance() {
        return instance;
    }

    public User getCurrentUser(){
        return this.user;
    }
}


//// Simulate a time-consuming process (replace with your actual logic)
//Task<Void> loadingTask = new Task<>() {
//    @Override
//    protected Void call() throws Exception {
//        Thread.sleep(2000); // Simulate work
//        return null;
//    }
//};
//
//        loadingTask.setOnSucceeded(e -> {
//        // Close the loading screen when the task is done
//        loadingStage.close();
//        });
//
//                // Start the task
//                new Thread(loadingTask).start();