package pidev.javafx.controller.login;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import pidev.javafx.crud.user.ServiceUser;
import pidev.javafx.model.user.Role;
import pidev.javafx.model.user.User;
import pidev.javafx.test.Main;
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.user.PasswordHasher;
import pidev.javafx.tools.user.windowController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewLogInController implements Initializable {

    @FXML
    private StackPane blackSide;
    @FXML
    private AnchorPane layoutSignup;
    @FXML
    private Button signin;
    @FXML
    private Button signup;
    @FXML
    private AnchorPane layoutSignin;
    @FXML
    private AnchorPane yellowSide;
    @FXML
    private Button signupBtn;
    @FXML
    private Button signinBtn;
    @FXML
    private TextField password;
    @FXML
    private TextField email;




    private boolean btnState;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        signin.setVisible(false);
        signupBtn.setVisible(false);
        layoutSignin.setVisible(false);
        btnState=true;
        signinBtn.setOnMouseClicked( event -> {
            translate(400,-300);
            btnState=!btnState;
        } );

        signupBtn.setOnMouseClicked( event -> {
            translate(400,-300);
            btnState=!btnState;
        } );
    }

    public void translate(int yellowX,int blackX){
        TranslateTransition translateTransitionYellowPart=new TranslateTransition( Duration.seconds( 0.6 ),yellowSide);
        FadeTransition fadeTransition=new FadeTransition(Duration.seconds( 0.3 ),blackSide);
        TranslateTransition translateTransitionBlackPart=new TranslateTransition( Duration.seconds( 0.6 ),blackSide);

        translateTransitionYellowPart.setToX((btnState)?yellowX:0);
        translateTransitionBlackPart.setToX( (btnState)?blackX:0 );

        fadeTransition.setToValue( 0 );
        fadeTransition.play();

        translateTransitionYellowPart.play();
        translateTransitionBlackPart.play();

        if(btnState)
            yellowSide.setStyle( "-fx-background-radius: 0 10 10 0;" +
                    "  -fx-border-radius: 0 10 10 0;" );
        else
            yellowSide.setStyle("");

        translateTransitionBlackPart.setOnFinished( event -> {

            layoutSignin.setVisible(!btnState);
            layoutSignup.setVisible(btnState);

            fadeTransition.setToValue( 1 );
            fadeTransition.play();
        } );
    }


    public void logIn(ActionEvent actionEvent) {


        ServiceUser service=new ServiceUser();
//        User user=service.findParEmail(email.getText());

        User user=service.findParEmail("salhiomar362@gmail.com");

        if(user.getPassword()==null){
//            Alert alert=showAlert("utlisateur n'existe pas ","il faut s'inscrire");
//            alert.show();
//            username.clear();
//            password.clear();
            System.out.println("wrong");
        }
//        else if(PasswordHasher.verifyPassword(password.getText(),user.getPassword())){
        else if(PasswordHasher.verifyPassword("Latifa123@",user.getPassword())){
            user.setIsConnected(1);
            UserController.setUser(user);
            ((Stage)Stage.getWindows().get(0)).close();
            if(user.getRole()== Role.Citoyen)
                loadManWindow("/fxml/mainWindow/mainWindow.fxml" );
            else
                loadManWindow("/fxml/mainWindow/mainWindow.fxml" );

        }
    }

    public void loadManWindow(String fileName){
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource(fileName));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), Color.TRANSPARENT);
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        stage.initStyle( StageStyle.TRANSPARENT);
        stage.setTitle("CitizenHub");
        UserController.setUser( null );
        stage.setResizable( true );
        stage.setScene(scene);
        stage.show();
    }
}
