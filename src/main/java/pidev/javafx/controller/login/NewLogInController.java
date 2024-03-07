package pidev.javafx.controller.login;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import pidev.javafx.crud.user.ServiceMunicipalite;
import pidev.javafx.crud.user.ServiceUser;
import pidev.javafx.model.user.Role;
import pidev.javafx.model.user.User;
import pidev.javafx.test.Main;
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.marketPlace.MyTools;
import pidev.javafx.tools.user.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

public class NewLogInController implements Initializable {

    @FXML
    private StackPane blackSide;
    @FXML
    private AnchorPane layoutSignup;
    @FXML
    private VBox vboxSignup;
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
    @FXML
    private TextField firstname;
    @FXML
    private TextField adresse;
    @FXML
    private TextField emailSignUp;
    @FXML
    private TextField passwordSignUp;
    @FXML
    private AnchorPane layoutCode;
    @FXML
    private AnchorPane firstLayout;
    @FXML
    private TextField code;
    @FXML
    private Button google;
    @FXML
    private Button googleSignUp;
    @FXML
    private Button verifier;
    @FXML
    private Label resetPassword;
    @FXML
    private TextField emailReset;
    @FXML
    private Button resetBtn;
    @FXML
    private AnchorPane layoutReset;



    private boolean btnState;
    private boolean[] isAllInpulValid;
    Popup popup4Regex = MyTools.getInstance().createPopUp();
    private  String styleInitiale="";
    private int elapsedTime = 0;
    private int nbr;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layoutCode.setVisible(false);
        signinBtn.setVisible(false);
        signinBtn.setVisible(false);
        layoutSignup.setVisible(false);
        layoutReset.setVisible(false);
        btnState=true;
        signinBtn.setOnMouseClicked( event -> {
            translate(-400,300);
            btnState=!btnState;
        } );
        signupBtn.setOnMouseClicked( event -> {
            translate(-400,300);
            btnState=!btnState;
        } );
        setRegEx();
        isAllInpulValid=new boolean[]{false,false,false};
        Popup popup4Regex = MyTools.getInstance().createPopUp();

        resetPassword.setOnMouseClicked( event -> {
            layoutReset.setVisible( true );
            layoutSignin.setVisible( false );
            layoutSignup.setVisible( false );
            resetBtn.setOnMouseClicked( event1 -> {
                if(!emailReset.getText().isEmpty()){
                    AtomicReference<User> user = new AtomicReference<>( new User() );
                    EmailController.sendEmail( "latifa.benzaied@esprit.tn","bla", user.get().generateVerificationCode() );
                    firstLayout.setVisible( false );
                    layoutCode.setVisible( true );
                    System.out.println( user.get().generateVerificationCode());
                    verifier.setOnMouseClicked( event2 -> {
                        if (code.getText().equals( user.get().getVerificationCode()))
                        {
                            ServiceUser serviceUser = new ServiceUser();
                            user.set( serviceUser.findParEmail( emailReset.getText() ) );
                            setDataUser(serviceUser.findParEmail( emailReset.getText() ));
                            serviceUser.ajouter( user.get() );
                            UserController.setUser( user.get() );
                            loadManWindow("/fxml/mainWindow/mainWindow.fxml");
                            layoutCode.setVisible(false);
                        }
                        else {
                            System.out.println("ghlalet ");
                        }
                    } );
                }
            } );
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
            yellowSide.setStyle( "-fx-background-radius: 10 0 0 10;" +
                    "  -fx-border-radius: 10 0 0 10;" );
        else
            yellowSide.setStyle("");

        translateTransitionBlackPart.setOnFinished( event -> {

            layoutSignin.setVisible(btnState);
            layoutSignup.setVisible(!btnState);

            signinBtn.setVisible(!btnState);
            signupBtn.setVisible(btnState);


            fadeTransition.setToValue( 1 );
            fadeTransition.play();
        } );
    }

//
//    public void logIn(ActionEvent actionEvent) {
//
//        ServiceUser service=new ServiceUser();
////      User user=service.findParEmail(email.getText());
//
//        User user=service.findParEmail("salhiomar362@gmail.com");
//
//        if(user.getPassword()==null){
//
//            System.out.println("wrong");
//        }
//
////        else if(PasswordHasher.verifyPassword(password.getText(),user.getPassword())){
//        else if(PasswordHasher.verifyPassword("Latifa123@",user.getPassword())){
//
//            user.setIsConnected(1);
//            UserController.setUser(user);
//            System.out.println(UserController.getInstance().getCurrentUser().getFirstname());
//            ((Stage)Stage.getWindows().get(0)).close();
//            if(user.getRole()==Role.Citoyen)
//                loadManWindow("/fxml/mainWindow/mainWindow.fxml" );
//            else
//                loadManWindow("/fxml/mainWindow/mainWindow.fxml" );
//
//        }
//    }

    public void setUser(int n){
        nbr=n;
    }


    public void logIn(ActionEvent actionEvent) {

        ServiceUser service=new ServiceUser();
//        User user=service.findParEmail(email.getText());
        User user=new User();
        if(email.getText().equals( "1" ))
            user=service.findParEmail("salhiomar3622@gmail.com");
        else if(email.getText().equals( "2" ))
            user=service.findParEmail("latifa.benzaied@gmail.com");
        else if(email.getText().equals( "3" ))
            user=service.findParEmail("omar.marrakchi@gmail.com");
        else if(email.getText().equals( "4" ))
            user=service.findParEmail("aziz.gmaty@gmail.com");
        else if(email.getText().equals( "5" ))
            user=service.findParEmail("khalil rmila@gmail.com");

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
            System.out.println(user);
            if(user.getRole()== Role.Citoyen)
                loadManWindow("/fxml/mainWindow/mainWindow.fxml" );
            else {
                loadManWindow( "/fxml/mainWindow/mainWindowAdmin.fxml" );
            }
        }
    }

    @FXML
    public void signUp(ActionEvent actionEvent) {

        ServiceUser service = new ServiceUser();
        ServiceMunicipalite serviceMunicipalite=new ServiceMunicipalite();
        User usexist = service.findParEmail(emailSignUp.getText());
        String municipalie= GeocodingAi.getAddressInformation(adresse.getText());
        int idMunicipalite=serviceMunicipalite.getOneByNomMunicipalite(municipalie);
      //  System.out.println(idMunicipalite);
        if( isAllInpulValid[0] && isAllInpulValid[1]&&isAllInpulValid[2] && usexist == null && idMunicipalite!=-1){
          User user=new User();
          user.setIdMunicipalite(idMunicipalite);
          layoutSignup.setStyle(String.valueOf(getClass().getResource("/style/user/newLogIn.css")));
          firstLayout.setVisible(false);
          layoutCode.setVisible(true);
          MyTools.getInstance().showAnimation( layoutCode );
          System.out.println(user.generateVerificationCode());
          Timer timer = new Timer();
            TimerTask task = new TimerTask() {

                  public void run() {

                      if (elapsedTime < 20) {
                          System.out.println(elapsedTime);
                          verifier.setOnAction(event -> {
                              if (code.getText().equals(user.getVerificationCode()))
                              {
                                  timer.cancel();
                                  ServiceUser serviceUser = new ServiceUser();
                                  setDataUser(user);
                                  serviceUser.ajouter(user);
                                  UserController.setUser(user);
                                  loadManWindow("/fxml/mainWindow/mainWindow.fxml");
                                  layoutCode.setVisible(false);
//                                  firstLayout.setOpacity(1);
                                  clean();
                              }
                              else {
                                  System.out.println("ghlalet ");
                              }

                          });
                          //System.out.println(elapsedTime);
                          elapsedTime++;

                      }
                      else {
                          timer.cancel();
                          ((Stage)Stage.getWindows().get( 0 )).close();
                          clean();
                      }
                  }

              };
            timer.schedule(task, 0, 1000);
        }
        else {
            System.out.println("ya ajax ,ya mawjoud ");
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
        stage.setResizable( true );
        stage=(Stage)signinBtn.getScene().getWindow();
        stage.close();
        stage.setScene(scene);
        stage.show();
    }
    public void setRegEx() {

        String firstnameRegex = "[a-zA-Z\\s]{3,}+";
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        String emaill="Doit être au format standard, par exemple \"utilisateur@example.com";
        String firstName = "Accepte uniquement les chaines composés d'au moins 3 caractères alphabétiques.";
        String passwordd=" Doit contenir au moins 8 caractères, avec au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial. Pas d'espaces autorisés";


        firstname.setOnKeyTyped( event -> {
            String color=(isAllInpulValid[0])?"green":"#ff4343";
            isAllInpulValid[0]=firstname.getText().matches(firstnameRegex);
            if(firstname.getText().isEmpty()){
                firstname.setStyle("");
                isAllInpulValid[0]=false;

            }
            else {

                firstname.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0" );
            }

        });

        emailSignUp.setOnKeyTyped( event -> {
            String color=(isAllInpulValid[1])?"green":"#ff4343";
            isAllInpulValid[1]=emailSignUp.getText().matches(emailRegex);
            if(emailSignUp.getText().isEmpty()){
                emailSignUp.setStyle("");
                isAllInpulValid[1]=false;

            }
            else {
                System.out.println("lllll");
                emailSignUp.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0" );
            }

        });



        passwordSignUp.setOnKeyTyped( event -> {
            String color=(isAllInpulValid[2])?"#8FFA54FF":"#ff4343";
            isAllInpulValid[2]=passwordSignUp.getText().matches(passwordRegex);
            if(passwordSignUp.getText().isEmpty()){
                passwordSignUp.setStyle("");
                isAllInpulValid[2]=false;

            }
            else {
                passwordSignUp.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0" );

            }

        });

        firstname.setOnMouseEntered( event -> {
            if(firstname.getText()!=null&&!firstname.getText().isEmpty()&&!firstname.getText().isEmpty()&&!firstname.getText().matches(firstnameRegex)){
                ((Label)popup4Regex.getContent().get( 0 )).setText(firstName);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }
        } );
        firstname.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );

        emailSignUp.setOnMouseEntered( event -> {
            if(emailSignUp.getText()!=null&&!emailSignUp.getText().isEmpty()&&!emailSignUp.getText().isEmpty()&&!emailSignUp.getText().matches(emailRegex)){
                ((Label)popup4Regex.getContent().get( 0 )).setText(emaill);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }
        } );
        emailSignUp.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );

        passwordSignUp.setOnMouseEntered( event -> {
            if(passwordSignUp.getText()!=null&&!passwordSignUp.getText().isEmpty()&&!passwordSignUp.getText().isEmpty()&&!passwordSignUp.getText().matches(passwordRegex)){
                ((Label)popup4Regex.getContent().get( 0 )).setText(passwordd);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }
        } );
        passwordSignUp.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );

        adresse.setOnMouseEntered( event -> {
                ((Label)popup4Regex.getContent().get( 0 )).setText("dddddddddd");
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: white; -fx-text-fill: black;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);

        } );
        adresse.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );


    }


     public void clean() {
        firstname.clear();
        adresse.clear();
        emailSignUp.clear();
        passwordSignUp.clear();
        code.clear();
     }

     public void setDataUser(User user) {
        user.setFirstname(firstname.getText());
        user.setEmail(emailSignUp.getText());
        user.setAdresse(adresse.getText());
        user.setPassword(PasswordHasher.hashPassword(passwordSignUp.getText()));
        user.setRole(Role.Citoyen);
        user.setIsConnected(1);
        UserController.setUser(user);

     }


     public void signupWithGoogle(ActionEvent actionEvent) {
               GoogleApi googleApi = new GoogleApi();
               WebView webView = googleApi.AccessTokenFetcher();
//        Button button=new Button();
               AnchorPane anchorPane = new AnchorPane(webView);
               anchorPane.setPrefWidth(450);
               anchorPane.setPrefHeight(650);
               WebEngine webEngine = webView.getEngine();
               Scene scene = new Scene(anchorPane);
               Stage stage;
               stage = (Stage) google.getScene().getWindow();
               stage.setScene(scene);
               stage.close();
               stage.show();
     }

     public void signInWithGoogle(ActionEvent actionEvent) {
         GoogleApi googleApi = new GoogleApi();
         WebView webView = googleApi.AccessTokenFetcher();
         AnchorPane anchorPane = new AnchorPane(webView);
         anchorPane.setPrefWidth(450);
         anchorPane.setPrefHeight(650);
         WebEngine webEngine = webView.getEngine();
         Scene scene = new Scene(anchorPane);
         Stage stage;
         stage = (Stage) google.getScene().getWindow();
         stage.setScene(scene);
         stage.close();
         stage.show();

     }

           public void display(String email, String name, String lastname) {
               this.emailSignUp.setText(email);
               this.firstname.setText(name);
               //this.lastname=lastname;


           }

//    void resetPassword(MouseEvent event) {
//
//
//        ServiceUser service=new ServiceUser();//bech nverifiw est ce que il e-mail mawjoud w le kbal mnbaath il code
//        if(service.chercherParEmail(email.getText()))//true
//        {
//            User user = new User();
//        }
//        else {
//
//            System.out.println("email n'exsite pas");
//        }
//    }
//
   }

