package pidev.javafx.controller.user;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import pidev.javafx.crud.user.ServiceUser;
import pidev.javafx.model.user.User;
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.marketPlace.MyTools;
import pidev.javafx.tools.user.EmailController;
import pidev.javafx.tools.user.PasswordHasher;

import java.net.URL;
import java.util.ResourceBundle;

public class AdvancedSettingsController implements Initializable {


    @FXML
    private TextField ancienPassword;

    @FXML
    private TextField nouveauPassword1;

    @FXML
    private TextField nouveauPassword2;
    @FXML
    private Button submit;
    @FXML
    private VBox layoutUpadatePassword;
    @FXML
    private VBox layoutUpdateEmail;

    @FXML
    private TextField ancienEmail;

    @FXML
    private TextField newEmail;

    @FXML
    private AnchorPane loadingPane;
    @FXML
    private Button submitEmail;



    private  String password;
    private String email;
    private static String usageOfThisForm;
    String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    Popup popup4Regex = MyTools.getInstance().createPopUp();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadingPane.setVisible( false );
        setRegEx();
        if(UserController.getInstance().getCurrentUser().getPassReseted())
            layoutUpadatePassword.getChildren().remove( 1 );


    }



    public void setUsageOfThisForm(String usage) {
        usageOfThisForm = usage;
        if (usage.equals("updatePassword")) {
           layoutUpdateEmail.setVisible(false);
           layoutUpadatePassword.setVisible(true);
            submit.setOnAction( event ->{
                updatePassword();
                loadingPane.setVisible( true );
                sleepThread(usage).start();
            } );
        }
        else {
            layoutUpdateEmail.setVisible(true);
            layoutUpadatePassword.setVisible(false);
            submit.setOnAction( event -> {
                updateEmail();
                loadingPane.setVisible( true );
                sleepThread(usage).start();
            } );
        }
    }




    private Thread sleepThread(String usage) {
        Task<Void> myTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(3000);
                return null;
            }
        };
        myTask.setOnSucceeded(e -> {
            loadingPane.setVisible( false );
            EventBus.getInstance().publish( "loadBlog",e );
            if(usage.equals("updatePassword"))
                MyTools.getInstance().getTextNotif().setText( "Password Has Been Modified Successfully" );
            else
                MyTools.getInstance().getTextNotif().setText( "Email Has Been Modified Successfully" );
            MyTools.getInstance().showNotif();
        });
        return new Thread(myTask);
    }


    private Thread sendMailThread() {
        Task<Void> myTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                EmailController.sendEmail("latifa.benzaied@esprit.tn","Mot de passe "," mot de passe a ete change");
                return null;
            }
        };
        myTask.setOnSucceeded(e -> {
            System.out.println("sent succfull");
        });
        return new Thread(myTask);
    }

    public void setData(User user) {
        password=user.getPassword();
        System.out.println(user.getEmail());
//        email.setText(user.getFirstname());
    }

    public void updatePassword() {
        ServiceUser serviceUser = new ServiceUser();
        if(UserController.getInstance().getCurrentUser().getPassReseted()){
            System.out.println(nouveauPassword1.getText());
            serviceUser.modifierPassword( UserController.getInstance().getCurrentUser().getEmail(), PasswordHasher.hashPassword( nouveauPassword1.getText() ) );
            sendMailThread().start();
        }
        else {
            String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            String ancien = ancienPassword.getText();
            if (PasswordHasher.verifyPassword( ancien, password ) && nouveauPassword1.getText().matches( passwordRegex ) && nouveauPassword2.getText().equals( nouveauPassword1.getText() )) {
                serviceUser.modifierPassword( UserController.getInstance().getCurrentUser().getEmail(), PasswordHasher.hashPassword( nouveauPassword1.getText() ) );
                sendMailThread().start();
            } else {

            }
        }
    }
    public void updateEmail() {

            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

            ServiceUser service =new ServiceUser();

            User user=new User();

            user=service.findParEmail(ancienEmail.getText());

            if(user!=null && newEmail.getText().matches(emailRegex) && !newEmail.getText().equals(user.getEmail())){
                service.modifierEmail(ancienEmail.getText(),newEmail.getText());
            }
            else {


                if(user==null)//anciem email ghalet
                {

                }
                if(!user.getEmail().equals(newEmail.getText())){

                    System.out.println("hi2");
                }

                if(!newEmail.getText().matches(emailRegex))
                {
                    System.out.println("hi3");
                }
            }

    }

    public void setRegEx() {

        ancienEmail.setOnMouseEntered( event -> {
            if(!nouveauPassword1.getText().matches(passwordRegex)) {

                ((Label) popup4Regex.getContent().get(0)).setText("ANCIEM PASSWORD");
                popup4Regex.getContent().get(0).setStyle(popup4Regex.getContent().get(0).getStyle() + "-fx-background-color: #ed1c27;");
                popup4Regex.show(Stage.getWindows().get(0), event.getScreenX() + 40, event.getScreenY() - 40);
            }
        } );
        ancienEmail.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );


        nouveauPassword1.setOnMouseEntered( event -> {
            if(!nouveauPassword1.getText().matches(passwordRegex)) {
                System.out.println("2");
                ((Label) popup4Regex.getContent().get(0)).setText("REGEX AAAAAAAAAAAAAAAAAAAAAAAAAA ");
                popup4Regex.getContent().get(0).setStyle(popup4Regex.getContent().get(0).getStyle() + "-fx-background-color: #ed1c27;");
                popup4Regex.show(Stage.getWindows().get(0), event.getScreenX() + 40, event.getScreenY() - 40);
            }
        } );
        nouveauPassword1.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );


        nouveauPassword2.setOnMouseEntered( event -> {
            if(!nouveauPassword1.getText().matches(passwordRegex)) {
                System.out.println("2");
                ((Label) popup4Regex.getContent().get(0)).setText("PPPPPPPPP ");
                popup4Regex.getContent().get(0).setStyle(popup4Regex.getContent().get(0).getStyle() + "-fx-background-color: #ed1c27;");
                popup4Regex.show(Stage.getWindows().get(0), event.getScreenX() + 40, event.getScreenY() - 40);
            }
        } );

        nouveauPassword2.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );



    }






}
