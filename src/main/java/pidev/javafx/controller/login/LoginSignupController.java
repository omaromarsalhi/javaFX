package pidev.javafx.controller.login;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.user.PasswordHasher;
import pidev.javafx.controller.user.AccountController;
import pidev.javafx.controller.user.ReglageController;
import pidev.javafx.tools.user.EmailController;
import pidev.javafx.crud.user.ServiceUser;
import pidev.javafx.model.user.Role;
import pidev.javafx.model.user.User;
import pidev.javafx.tools.user.GoogleApi;
import pidev.javafx.tools.user.windowController;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class LoginSignupController implements Initializable {
    @FXML
    private AnchorPane layer2;
    @FXML
    private AnchorPane layer1;

    @FXML
    private Label welcome;

    @FXML
    private Label hello;

    @FXML
    private Button signin;

    @FXML
    private Button signup;

    @FXML
    private TextField username;



    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;
    @FXML
    private PasswordField verifierpassword2;
    @FXML
    private Button forget;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Button signin2;

    @FXML
    private Button signup2;
    @FXML
    private TextField adresse;
    @FXML
    private Button alertename;

    @FXML
    private Button alerteemail;

    @FXML
    private Button alerteadress;
    @FXML
    private Button alertepassword2;

    @FXML
    private Button alertepassword;
    @FXML
    private TextField code;

    @FXML
    private Button verif;
    @FXML
    private Label time;
    @FXML
    private ImageView image;
    private Timeline timeline;
    private int elapsedTime = 0;
    @FXML
    private AnchorPane mainBorderPain;
    ActionEvent event1;

    @FXML
    private Label champsVide;
    @FXML
    Timeline timeline1 ;
    @FXML
    ImageView probleme;
    @FXML
    PasswordField password2;
    @FXML
    Button google;
    private  String lastname;
    String nameRegex = "[a-zA-Z]{3,}+";
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    String nom=" Doit contenir au moins 3 lettres, sans caractères spéciaux ni espaces.";
    String emaill="Doit être au format standard, par exemple \"utilisateur@example.com";
    String passwordd=" Doit contenir au moins 8 caractères, avec au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial. Pas d'espaces autorisés";


    public PasswordField getPassword2() {
        return password2;
    }

    public void setPassword2(PasswordField password2) {
        this.password2 = password2;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label2.setText("To keep connected with us please  \n" +
                " login with your personal info ");
        welcome.setVisible(true);
        hello.setVisible(false);
        signin.setVisible(true);
        signup.setVisible(false);
        username.setVisible(false);
        password.setVisible(true);
        forget.setVisible(false);
        signin2.setVisible(false);
        email.setVisible(true);
        name.setVisible(true);
        signup2.setVisible(true);
        alertename.setVisible(false);
        alerteadress.setVisible(false);
        alerteemail.setVisible(false);
        alertepassword.setVisible(false);
        alertepassword2.setVisible(false);
        code.setVisible(false);
        verif.setVisible(false);
        time.setVisible(false);
        image.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(true);
        champsVide.setVisible(false);
        probleme.setVisible(false);
        password2.setVisible(false);

        forget.setOnAction(event -> {
            onClickForget(event);
        });

        email.textProperty().addListener((observable, oldValue, newValue) -> {
            champsVide.setVisible(false);
            if(timeline1!=null)
             timeline1.stop();
          //  String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            System.out.println("Nouvelle valeur du TextField : " + newValue);
            if(newValue.matches(emailRegex)){

                System.out.println(newValue);
                alerteemail.setVisible(false);
                if(champsVide.isVisible()){
                    champsVide.setVisible(true);}
                else champsVide.setVisible(false);
            }
            else {
                probleme.setVisible(true);
                alerteemail.setVisible(true);
                alerteemail.setOnMouseClicked(event -> {
                    showAlerteButton(alerteemail,"sss",emaill);

                });
            }

        });

        name.textProperty().addListener((observable, oldValue, newValue) -> {
            champsVide.setVisible(false);
            if(timeline1!=null)
            timeline1.stop();
            String nameRegex = "[a-zA-Z]{3,}+";

            if(newValue.matches(nameRegex)){
                System.out.println(newValue);
                alertename.setVisible(false);
                if(champsVide.isVisible()){
                    champsVide.setVisible(true);}
                else champsVide.setVisible(false);
            }
            else{
                probleme.setVisible(true);
                alertename.setVisible(true);
                alertename.setOnMouseClicked(event -> {
                    System.out.println("hiii");
                    showAlerteButton(alertename,"sss",nom);

                });


            }

        });


        password.textProperty().addListener((observable, oldValue, newValue) -> {
            champsVide.setVisible(false);
            if(timeline1!=null)
            timeline1.stop();
        //    String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            System.out.println(newValue);
            if(newValue.matches(passwordRegex)){
                alertepassword.setVisible(false);
                if(champsVide.isVisible()){
                    champsVide.setVisible(true);}
                else champsVide.setVisible(false);
            }
            else{
                     probleme.setVisible(true);
                     password.setVisible(true);
                     alertepassword.setVisible(true);
                     alertepassword.setOnMouseClicked(event -> {

                         showAlerteButton(alertepassword,"sss",passwordd);


                });
            }

        });


        verifierpassword2.textProperty().addListener((observable, oldValue, newValue) -> {
            champsVide.setVisible(false);
            if(timeline1!=null)
                timeline1.stop();
           // String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            System.out.println(newValue);
            if(newValue.matches(passwordRegex)){
                alertepassword2.setVisible(false);
                if(champsVide.isVisible()){
                    champsVide.setVisible(true);}
                else champsVide.setVisible(false);
            }
            else{
                probleme.setVisible(true);
                verifierpassword2.setVisible(true);
                alertepassword2.setVisible(true);
                alertepassword2.setOnMouseClicked(event -> {

                    showAlerteButton(alertepassword2,"sss","Veuillez répéter exactement le même mot de passe que vous avez saisi précédemment");


                });
            }

        });




        google.setOnAction(event -> {

            GoogleApi googleApi=new GoogleApi();
            WebView webView = googleApi.AccessTokenFetcher();
            AnchorPane anchorPane = new AnchorPane(webView);
            anchorPane.setPrefWidth(500);
            anchorPane.setPrefHeight(650);
            WebEngine webEngine = webView.getEngine();
            Scene scene=new Scene(anchorPane);
            Stage stage;
            stage = (Stage) google.getScene().getWindow();
            stage.setScene(scene);
            stage.close();
            stage.show();

        });




    }




    @FXML
    void onSigninClicked(MouseEvent event) {
        TranslateTransition slide =new TranslateTransition();
        slide.setDuration(Duration.seconds(0.7));
        slide.setNode(layer2);
        slide.setToX(470);
        slide.play();
        layer1.setTranslateX(-233);
        welcome.setVisible(false);
        hello.setVisible(true);
        signin.setVisible(false);
        signup.setVisible(true);
        username.setVisible(true);
        password.setVisible(true);
        forget.setVisible(true);
        signin2.setVisible(true);
        email.setVisible(false);
        name.setVisible(false);
        signup2.setVisible(false);
        adresse.setVisible(false);
        image.setVisible(true);
        label1.setVisible(true);
        label2.setVisible(false);
        probleme.setVisible(false);
        alertepassword.setVisible(false);
        alertename.setVisible(false);
        alerteemail.setVisible(false);
        password2.setVisible(true);
        verifierpassword2.setVisible(false);
    }



    @FXML
    void onSignupClicked(MouseEvent event) {
        TranslateTransition slide =new TranslateTransition();
        slide.setDuration(Duration.seconds(0.5));

        slide.setNode(layer2);
        slide.setToX(0);
        slide.play();
        layer1.setTranslateX(0);
        welcome.setVisible(true);
        hello.setVisible(false);
        signin.setVisible(true);
        signup.setVisible(false);
        username.setVisible(false);
        password.setVisible(true);
        forget.setVisible(false);
        signin2.setVisible(false);
        email.setVisible(true);
        name.setVisible(true);
        signup2.setVisible(true);
        adresse.setVisible(true);
        image.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(true);
        password2.setVisible(false);
        verifierpassword2.setVisible(true);

        slide.setOnFinished((e->{


        }));

    }



    @FXML
    public void CreateAccount(ActionEvent actionEvent) throws IOException {


     User user=new User();
     alerteemail.setVisible(false);
     alertename.setVisible(false);
     alertepassword.setVisible(false);
     alertepassword2.setVisible(false);
     time.setVisible(true);


//        String nom=" Doit contenir au moins 3 lettres, sans caractères spéciaux ni espaces.";
//        String emaill="Doit être au format standard, par exemple \"utilisateur@example.com";
//        String passwordd=" Doit contenir au moins 8 caractères, avec au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial. Pas d'espaces autorisés";

     if (name.getText() != "" && email.getText() != "" && password.getText() != "" && verifierpassword2.getText() != "") {
      if(champsVide.isVisible()){
         champsVide.setVisible(true);
      }
       else champsVide.setVisible(false);
      if (name.getText().matches(nameRegex) && email.getText().matches(emailRegex) && password.getText().matches(passwordRegex) &&verifierpassword2.getText().equals(password.getText())) {
        ServiceUser service = new ServiceUser();
        User usexist = new User();
        usexist = service.findParEmail(email.getText());
        if (usexist == null) {
                    //ken l email mouch mawjoud w jawou bhyy
          code.setVisible(true);
          verif.setVisible(true);
          name.setVisible(false);
          email.setVisible(false);
          adresse.setVisible(false);
          password.setVisible(false);
          signup2.setVisible(false);
          signin.setVisible(false);
          verifierpassword2.setVisible(false);
     //     String filePath = "C:\\Users\\Latifa\\Desktop\\test\\index.html";
//          try {
            //  String htmlContent = new String(Files.readAllBytes(Paths.get(filePath)));
              user.generateVerificationCode();
              System.out.println(user.generateVerificationCode());
              System.out.println(user.getVerificationCode());
              String dynamicText=user.getVerificationCode();
             // String body=EmailController.addDynamicText(htmlContent,dynamicText);
              EmailController.sendEmail("latifa.benzaied@esprit.tn", "Subject",user.getVerificationCode() );

//          } catch (IOException e) {
//             e.printStackTrace();
//          }
                   // EmailController.sendEmail("latifa.benzaied@esprit.tn","verifier",user.getVerificationCode());
           Timer timer = new Timer();

            TimerTask task = new TimerTask() {
                public void run() {

                  if (elapsedTime <60) {//wa9et mawfech
                    Platform.runLater(() -> updateTimeLabel());
                    verif.setOnMouseClicked(event -> {
                    if(code.getText().equals(user.getVerificationCode())){

                        //bech iverif il compte kbal 60 secandes
                        timer.cancel();
                        user.setFirstname(name.getText());
                        user.setEmail(email.getText());
                        user.setAdresse(adresse.getText());
                        user.setPassword(PasswordHasher.hashPassword(password.getText()));
                        user.setIdMunicipalite(1);
                        user.setRole(Role.Citoyen);
                        user.setIsConnected(1);
                        ServiceUser serviceUser = new ServiceUser();
                        serviceUser.ajouter(user);
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainWindow/mainWindow.fxml"));
                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                        throw new RuntimeException(e);
                        }
                        if(lastname!=null) {
                            user.setLastname(lastname);
                        }

                        UserController.setUser(user);
//                            AccountController account = fxmlLoader.getController();
//                            account.display(user);
                          //  scene.getStylesheets().add(String.valueOf(getClass().getResource("/style/styleAccount.css")));
                            Stage stage;
                            stage = (Stage) signup2.getScene().getWindow();
                            stage.initStyle( StageStyle.TRANSPARENT);
                            stage.setScene(scene);
                            stage.close();
                            stage.show();
                            //windowController.addWindow(stage);

                    }
                    else {
                        System.out.println("mouch shih");
                    }
                    });


                    elapsedTime++;
                  }

                  else {//wa9et oufe
                  Platform.runLater(() -> {
                   AnchorPane successPane;
                   try {
                   successPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/User/oops.fxml")));
                   } catch (IOException e) {
                   throw new RuntimeException(e);
                   }

                                        // Set content
                   successPane.setPrefHeight(mainBorderPain.getPrefHeight());
                   successPane.setPrefWidth(mainBorderPain.getPrefWidth());
                   mainBorderPain.getChildren().add(successPane);
                   Timeline timeline = new Timeline(
                   new KeyFrame(Duration.ZERO, event1 -> {
                                                    // Afficher successPane
                   successPane.setVisible(true);
                   }),
                    new KeyFrame(Duration.seconds(10), event1 -> {
                        successPane.setVisible(false);
                        code.setVisible(false);
                        verif.setVisible(false);
                        name.setVisible(true);
                        email.setVisible(true);
                        adresse.setVisible(true);
                        password.setVisible(true);
                        signup2.setVisible(true);
                        time.setVisible(false);
                        name.clear();
                        email.clear();
                        adresse.clear();
                        password.clear();
                        signin.setVisible(true);
                        alertename.setVisible(false);
                        alerteadress.setVisible(false);
                        alerteemail.setVisible(false);
                        alertepassword.setVisible(false);
                        alertepassword2.setVisible(false);
                    })
                    );// Start the PauseTransition
                      timeline.play();
                      cancel();
                                });
                           }

                        };
                    };
                    timer.schedule(task, 0,1000);
                }
              }

            else{
                probleme.setVisible(true);
                if(!name.getText().matches(nameRegex)){
                    alertename.setVisible(true);
                    probleme.setVisible(true);

                    alertename.setOnMouseClicked(event -> {

                        showAlerteButton(alertename,"sss",nom);

                    });
                }


                if(!email.getText().matches(emailRegex)){
                    alerteemail.setVisible(true);
                    probleme.setVisible(true);
                      alerteemail.setOnMouseClicked(event -> {
                          showAlerteButton(alerteemail,"sss",emaill);
                        });

                }


                if(!password.getText().matches(passwordRegex)){

                    alertepassword.setVisible(true);
                    probleme.setVisible(true);
                    alertepassword.setOnMouseClicked(event -> {
//
//
//
                        showAlerteButton(alertepassword,"sss",passwordd);
                    });
                }
                  if(!verifierpassword2.getText().equals(password.getText())){

                      alertepassword2.setVisible(true);
                      probleme.setVisible(true);
                      alertepassword2.setOnMouseClicked(event -> {
                          showAlerteButton(alertepassword2,"sss","Veuillez répéter exactement le même mot de passe que vous avez saisi précédemment");


                      });
                  }



            }
    }
        else {
          champsVide.setFont(new Font(20));
            champsVide.setVisible(true);
            probleme.setVisible(true);
            timeline1 =LabelAnimation(champsVide);
            timeline1.play();
        }

    }





    public void Authentifier(ActionEvent actionEvent) {

        User user = new User();
        user.setEmail(username.getText());
        user.setPassword(password2.getText());

        ServiceUser service=new ServiceUser();
        String pass=service.RetriveHashedPassword(user.getEmail());
        if(pass==null){

            Alert alert=showAlert("utlisateur n'existe pas ","il faut s'inscrire");
            alert.show();


            username.clear();
            password.clear();
        }


        else
        {
            if(PasswordHasher.verifyPassword(user.getPassword(),pass)){

                user=service.findParEmail(user.getEmail());

                user.setIsConnected(1);

//                service.isconnected(user);

                UserController.setUser(user);

                if(user.getRole()==Role.Citoyen) {

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainWindow/mainWindow.fxml"));

                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                     //   AccountController account = fxmlLoader.getController();

//                  account.display(user);

                        scene.getStylesheets().add(String.valueOf(getClass().getResource("/style/styleAccount.css")));

                        Stage stage;

                        stage = (Stage) signup2.getScene().getWindow();

                        stage.setScene(scene);

                        stage.close();

                        stage.show();

                        windowController.addWindow(stage);


                    }
                    if(user.getRole()==Role.responsable){

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/User/ListeUser.fxml"));

                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                        scene.getStylesheets().add(String.valueOf(getClass().getResource("/style/StylelisteUsers.css")));
                        Stage stage;
                        stage = (Stage) signup2.getScene().getWindow();
                        stage.setScene(scene);
                        stage.close();
                        stage.show();
                    }

                }
                else{

                    System.out.println("ce utlistateur n'existe pas ");
                   showAlert("utlisateur n'existe pas ","il faut s'inscrire").show();


                    username.clear();
                    password.clear();

                }
            }






    }




    private void updateTimeLabel() {
        int minutes = elapsedTime / 60;
        int seconds = 60 - elapsedTime % 60;

        if (seconds == 60) {

            minutes=0;
            seconds = 0;
        }

        time.setText(String.format("%02d:%02d", minutes, seconds));
    }


    public void showAlerteButton(Button alerte,String titile,String message){

        alerte.setOnMouseClicked(event -> {

            Alert alert;
            alert=showAlert(titile,message);
            Region root = (Region) alert.getDialogPane().getChildren().get(0);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), root);
            fadeTransition.setFromValue(0.0); // Opacité initiale
            fadeTransition.setToValue(1.0); // Opacité finale
            alert.show();
            fadeTransition.play();


        });

    }
    public Alert showAlert(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setPrefWidth(350);
        alert.getDialogPane().setPrefHeight(150);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/style/user/styleSignup.css").toExternalForm());
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        //stage.getIcons().add(new Image("/resources/img/gmail.png")); // Remplacez le chemin par votre propre chemin
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/img/userImg/logo.png"),60,32,true,true));
        alert.setGraphic(icon);
        return alert;
    }

    public  Timeline LabelAnimation(Label label) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> label.setVisible(true)),
                new KeyFrame(Duration.seconds(1), e -> label.setVisible(false)),
                new KeyFrame(Duration.seconds(1.5), e -> label.setVisible(true))
        );
        timeline.setCycleCount(Animation.INDEFINITE);

        return timeline;
    }
    public  void display(String email,String name,String lastname) {
        this.email.setText(email);
        this.name.setText(name);
        this.lastname=lastname;


    }
    public void onClickForget(ActionEvent event ){
        User user=new User();
        ServiceUser service=new ServiceUser();//bech nverifiw est ce que il e-mail mawjoud w le kbal mnbaath il code
        if(service.chercherParEmail(username.getText()))//true
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/User/reglageavancee.fxml"));

            try {

                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();


                Stage popupStage = new Stage();

                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(String.valueOf(getClass().getResource("/style/user/StyleReglage.css")));

                scene.setFill(Color.TRANSPARENT);

                ReglageController reglageController = fxmlLoader.getController();
                reglageController.getModifierpwd().setVisible(false);
                reglageController.getAncienemail().setVisible(false);
                reglageController.getModifieremail().setVisible(false);
                reglageController.getNouveauemail().setVisible(true);
                reglageController.getNouveaupas().setVisible(false);
                reglageController.getNouveaupass().setVisible(false);
                reglageController.getAncienpass().setVisible(false);
                reglageController.getAlerteAncienemail().setVisible(false);
                reglageController.getAlertenouveauemail().setVisible(false);
                reglageController.getAlertenouveaupwd2().setVisible(false);
                reglageController.getAlertenouveaupwd().setVisible(false);
                reglageController.getAlerteAncienpwd().setVisible(false);
                reglageController.getShowpass().setVisible(false);
                reglageController.getShowpass2().setVisible(false);
                reglageController.getShowpass3().setVisible(false);
                reglageController.getShowpassword().setVisible(false);
                reglageController.getShowpassword2().setVisible(false);
                reglageController.getShowpassword3().setVisible(false);
                reglageController.getVerifier().setVisible(true);
                reglageController.getNouveauemail().setPromptText("Code");
                reglageController.getResetPassword().setVisible(false);
                reglageController.getValider().setLayoutY(5);

                // Image image = new Image("../../img/e-mail.gif");
                //reglageController.getValider().setImage(image);
                reglageController.getValider().setVisible(true);
                user.generateVerificationCode();
//                reglageController.setData(username.getText(),user.getVerificationCode());
//                EmailController.sendEmail("latifa.benzaied@esprit.tn", "verifier", user.getVerificationCode());
                popupStage.setScene(scene);
                windowController.addWindow(popupStage);
                popupStage.initOwner(primaryStage);
                popupStage.initStyle(StageStyle.UNDECORATED);
                popupStage.show();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        else {

            System.out.println("email n'exsite pas");
        }

    }


}
