package pidev.javafx.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pidev.javafx.tools.user.PasswordHasher;
import pidev.javafx.model.user.User;
import pidev.javafx.crud.user.ServiceUser;

import java.net.URL;
import java.util.ResourceBundle;
import pidev.javafx.tools.user.EmailController;
import pidev.javafx.tools.user.windowController;

public class ReglageController implements Initializable {
    @FXML
    private PasswordField ancienpass;

    @FXML
    private PasswordField nouveaupass;

    @FXML
    private PasswordField nouveaupas;

    @FXML
    private TextField ancienemail;

    @FXML
    private TextField nouveauemail;
    @FXML
    private Button Modifierpwd;
    @FXML
    private Button Modifieremail;
    @FXML
    private Button deconnecter;
    @FXML
    private Button close;
    private String password;
    private String email;
    private String nouveauEmail;
    @FXML
    private ImageView valider;

    @FXML
    private Label label1;

    @FXML
    private Button alerteAncienemail;

    @FXML
    private Button alertenouveaupwd;

    @FXML
    private Button alertenouveauemail;

    @FXML
    private Button alertenouveaupwd2;

    @FXML
    private Button alerteAncienpwd;

    @FXML
    private Button showpassword;
    @FXML
    private TextField showpass;
    @FXML
    private Button hidepwd;
    @FXML
    private TextField showpass2;
    @FXML
    private Button hidepwd2;

    @FXML
    private TextField showpass3;

    @FXML
    private Button hidepwd3;
    @FXML
    private Button showpassword2;
    @FXML
    private Button showpassword3;
    @FXML
    private Button verifier;
    private String code;
    @FXML
    private Button resetPassword;
    @FXML
    private Label label2;
    @FXML
    private ImageView valider2;


    public ImageView getValider2() {
        return valider2;
    }

    public void setValider2(ImageView valider2) {
        this.valider2 = valider2;
    }

    public Button getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(Button resetPassword) {
        this.resetPassword = resetPassword;
    }

    public Button getVerifier() {
        return verifier;
    }

    public void setVerifier(Button verifier) {
        this.verifier = verifier;
    }

    public ImageView getValider() {
        return valider;
    }

    public void setValider(ImageView valider) {
        this.valider = valider;
    }

    public Button getAlerteAncienemail() {
        return alerteAncienemail;
    }

    public void setAlerteAncienemail(Button alerteAncienemail) {
        this.alerteAncienemail = alerteAncienemail;
    }

    public Button getAlertenouveaupwd() {
        return alertenouveaupwd;
    }

    public void setAlertenouveaupwd(Button alertenouveaupwd) {
        this.alertenouveaupwd = alertenouveaupwd;
    }

    public Button getAlertenouveauemail() {
        return alertenouveauemail;
    }

    public void setAlertenouveauemail(Button alertenouveauemail) {
        this.alertenouveauemail = alertenouveauemail;
    }

    public Button getAlertenouveaupwd2() {
        return alertenouveaupwd2;
    }

    public void setAlertenouveaupwd2(Button alertenouveaupwd2) {
        this.alertenouveaupwd2 = alertenouveaupwd2;
    }

    public Button getAlerteAncienpwd() {
        return alerteAncienpwd;
    }

    public void setAlerteAncienpwd(Button alerteAncienpwd) {
        this.alerteAncienpwd = alerteAncienpwd;
    }

    public Button getShowpassword() {
        return showpassword;
    }

    public void setShowpassword(Button showpassword) {
        this.showpassword = showpassword;
    }

    public TextField getShowpass() {
        return showpass;
    }

    public void setShowpass(TextField showpass) {
        this.showpass = showpass;
    }

    public Button getHidepwd() {
        return hidepwd;
    }

    public void setHidepwd(Button hidepwd) {
        this.hidepwd = hidepwd;
    }

    public TextField getShowpass2() {
        return showpass2;
    }

    public void setShowpass2(TextField showpass2) {
        this.showpass2 = showpass2;
    }

    public Button getHidepwd2() {
        return hidepwd2;
    }

    public void setHidepwd2(Button hidepwd2) {
        this.hidepwd2 = hidepwd2;
    }

    public TextField getShowpass3() {
        return showpass3;
    }

    public void setShowpass3(TextField showpass3) {
        this.showpass3 = showpass3;
    }

    public Button getHidepwd3() {
        return hidepwd3;
    }

    public void setHidepwd3(Button hidepwd3) {
        this.hidepwd3 = hidepwd3;
    }

    public Button getShowpassword2() {
        return showpassword2;
    }

    public void setShowpassword2(Button showpassword2) {
        this.showpassword2 = showpassword2;
    }

    public Button getShowpassword3() {
        return showpassword3;
    }

    public void setShowpassword3(Button showpassword3) {
        this.showpassword3 = showpassword3;
    }






    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PasswordField getAncienpass() {
        return ancienpass;
    }

    public void setAncienpasss(PasswordField ancienpass) {
        this.ancienpass = ancienpass;
    }

    public PasswordField getNouveaupass() {
        return nouveaupass;
    }

    public void setNouveaupass(PasswordField nouveaupass) {
        this.nouveaupass = nouveaupass;
    }



    public TextField getAncienemail() {
        return ancienemail;
    }

    public void setAncienemail(TextField ancienemail) {
        this.ancienemail = ancienemail;
    }

    public TextField getNouveauemail() {
        return nouveauemail;
    }

    public void setNouveauemail(TextField nouveauemail) {
        this.nouveauemail = nouveauemail;
    }
    public PasswordField getNouveaupas() {
        return nouveaupas;
    }

    public void setNouveaupas(PasswordField nouveaupas) {
        this.nouveaupas = nouveaupas;
    }

    public Button getModifierpwd() {
        return Modifierpwd;
    }

    public void setModifierpwd(Button modifierpwd) {
        Modifierpwd = modifierpwd;
    }

    public Button getModifieremail() {
        return Modifieremail;
    }

    public void setModifieremail(Button modifieremail) {
        Modifieremail = modifieremail;
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

         label1.setVisible(false);
         valider.setVisible(false);
         valider2.setVisible(false);
         alertenouveauemail.setVisible(false);
         alertenouveaupwd.setVisible(false);
         alertenouveaupwd2.setVisible(false);
         alerteAncienpwd.setVisible(false);
         alerteAncienemail.setVisible(false);
         showpass.setVisible(false);
         hidepwd.setVisible(false);
         showpass2.setVisible(false);
         hidepwd2.setVisible(false);
         showpass3.setVisible(false);
         hidepwd3.setVisible(false);

        showpassword.setOnMouseClicked(e -> {

                showpass.setText(ancienpass.getText());
                showpass.setVisible(true);
                hidepwd.setVisible(true);



        });
        hidepwd.setOnMouseClicked(e -> {

            hidepwd.setVisible(false);
            showpass.setVisible(false);




        });
        showpassword2.setOnMouseClicked(e -> {

            showpass2.setText(nouveaupas.getText());
            showpass2.setVisible(true);
            hidepwd2.setVisible(true);



        });
        hidepwd2.setOnMouseClicked(e -> {

            hidepwd2.setVisible(false);
            showpass2.setVisible(false);




        });
        showpassword3.setOnMouseClicked(e -> {

            showpass3.setText(nouveaupass.getText());
            showpass3.setVisible(true);
            hidepwd3.setVisible(true);



        });
        hidepwd3.setOnMouseClicked(e -> {

            hidepwd3.setVisible(false);
            showpass3.setVisible(false);




        });


    }
    public void btnClose(ActionEvent actionEvent) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void modifierPwd(ActionEvent actionEvent) {

        ServiceUser serviceUser=new ServiceUser();

        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        String ancien=ancienpass.getText();


        if(PasswordHasher.verifyPassword(ancien,password))//keteb nafess il mot de passe lkdim
        {

            if(nouveaupas.getText().matches(passwordRegex))//foreme shiha
            {
                if(nouveaupass.getText().equals(nouveaupas.getText())){//kteb nafess il mot de passe
                     valider.setVisible(true);

                     label1.setVisible(true);

                     nouveaupass.setVisible(false);

                     ancienpass.setVisible(false);

                     nouveaupas.setVisible(false);

                     Modifierpwd.setVisible(false);

                     serviceUser.modifierPassword(email,PasswordHasher.hashPassword(nouveaupas.getText()));

                    EmailController.sendEmail("latifa.benzaied@esprit.tn","Mot de passe "," mot de passe a ete change");
                    valider2.setVisible(true);
                    nouveaupass.setVisible(false);
                    ancienpass.setVisible(false);
                    nouveaupas.setVisible(false);
                    showpass.setVisible(false);
                    showpass2.setVisible(false);
                    showpass3.setVisible(false);
                    showpassword.setVisible(false);
                    showpassword2.setVisible(false);
                    showpassword3.setVisible(false);
                    hidepwd.setVisible(false);
                    hidepwd2.setVisible(false);
                    hidepwd3.setVisible(false);
                    valider.setVisible(false);

                }
                else {
                    Modifieremail.setStyle("-fx-border-color: #FF0000 ;-fx-background-color:#000000;");

                    alertenouveaupwd2.setVisible(true);
                    alertenouveaupwd2.setOnMouseClicked(event -> {
                        Alert alert;
                        alert=showAlert("mouch nafess il password","aaaaaaaaa");
                        alert.show();
                    });

                    label1.setText("mouch nafess il password");

                    label1.setVisible(true);
                }
            }
            else {
                   Modifieremail.setStyle("-fx-border-color: #FF0000 ;-fx-background-color:#000000;");

                   alertenouveaupwd.setVisible(true);
                   alertenouveaupwd.setOnMouseClicked(event -> {
                    Alert alert;
                    alert=showAlert("mouch nafess il password","aaaaaaaaa");
                    alert.show();
                     });

                   label1.setText("il forme ya rajel");

                   label1.setVisible(true);
            }

        }
        else {
               Modifieremail.setStyle("-fx-border-color: #FF0000 ;-fx-background-color:#000000;");

               alerteAncienpwd.setVisible(false);
               alerteAncienpwd.setOnMouseClicked(event -> {
                Alert alert;
                alert=showAlert("il ancien mot de passe ghalet ","aaaaaaaaa");
                alert.show();
                });

               label1.setText("il ancien mot de passe ghalet ");

               label1.setVisible(true);

        }


    }

    public void modifierEmail(ActionEvent actionEvent) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        ServiceUser service =new ServiceUser();

        User user=new User();

         user=service.findParEmail(ancienemail.getText());


         if(user!=null && nouveauemail.getText().matches(emailRegex) && !nouveauemail.getText().equals(user.getEmail())){

             nouveauEmail=nouveauemail.getText();
             service.modifierEmail(ancienemail.getText(),nouveauemail.getText());

             valider2.setVisible(true);

             label1.setText("l'E-mail a ete changee");

             label1.setVisible(true);

             ancienemail.setVisible(false);

             nouveauemail.setVisible(false);

             Modifieremail.setVisible(false);

             alerteAncienemail.setVisible(false);

             alertenouveauemail.setVisible(false);
         }
         else {

             Modifieremail.setStyle("-fx-border-color: #FF0000 ;-fx-background-color:#000000;");

             if(user==null)//anciem email ghalet
             {

                 alerteAncienemail.setVisible(true);
                 alerteAncienemail.setOnMouseClicked(event -> {
                     Alert alert;
                     alert=showAlert("anciem email ghalet","anciem email ghalet");
                     alert.show();
                 });
             }
             if(user.getEmail().equals(nouveauemail.getText())){
                 alerteAncienemail.setVisible(true);
                 alerteAncienemail.setOnMouseClicked(event -> {
                     Alert alert;
                     alert=showAlert("nafssou","nafssou");
                     alert.show();
                 });

             }

             if(!nouveauemail.getText().matches(emailRegex))//forme ghalta
             {
                 alertenouveauemail.setVisible(true);
                 alertenouveauemail.setOnMouseClicked(event -> {
                     Alert alert;
                     alert=showAlert("formes ghalta","formes ghalta fel forme il jdida");
                     alert.show();
                 });
             }
         }

    }


    public void deconnecter(ActionEvent actionEvent) {
        ServiceUser serviceUser=new ServiceUser();
        User user;
        //System.out.println(nouveauEmail);
        //user=serviceUser.findParEmail(nouveauEmail);
        //System.out.println(user.getEmail());
        //user.setIsConnected(0);
        //serviceUser.isconnected(user);
        windowController.delete();

    }
    public static Alert showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setPrefWidth(290);
        alert.getDialogPane().setPrefHeight(200);
        alert.getDialogPane().getStylesheets().add(ReglageController.class.getResource("/style/styleSignup.css").toExternalForm());
        return alert;
    }

    public void verifierCode(ActionEvent actionEvent) {
          if(nouveauemail.getText().equals(code)){

    System.out.println("heloooo");
    getShowpass2().setVisible(true);
    getShowpass3().setVisible(true);
    nouveaupass.setVisible(true);
    nouveaupas.setVisible(true);
    resetPassword.setVisible(true);
    nouveauemail.setVisible(false);
    verifier.setVisible(false);
    valider.setVisible(false);
    hidepwd2.setVisible(true);
    hidepwd3.setVisible(true);
    label2.setVisible(false);
    deconnecter.setVisible(false);

    getShowpassword2().setVisible(true);
    getShowpassword3().setVisible(true);
//    showpass.setVisible(true);
//    hidepwd.setVisible(true);
//    showpass2.setVisible(true);
//    hidepwd2.setVisible(true);
//    showpass3.setVisible(true);
//    hidepwd.setVisible(true);
}
else {
    System.out.println("byeeeeeeeeeee");
}
    }

    public void resetPassword(ActionEvent actionEvent) {

        ServiceUser serviceUser=new ServiceUser();

        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        String ancien=ancienpass.getText();
        System.out.println(ancien);
        System.out.println(nouveaupas);

         if(nouveaupas.getText().matches(passwordRegex))//forme shiha
            {
                System.out.println("forme shih");
                if (nouveaupass.getText().equals(nouveaupas.getText())) {//kteb nafess il mot de passe

                    valider.setVisible(true);

                    label1.setVisible(true);

                    nouveaupass.setVisible(false);

                    ancienpass.setVisible(false);

                    nouveaupas.setVisible(false);

                    Modifierpwd.setVisible(false);

                    serviceUser.modifierPassword(email, PasswordHasher.hashPassword(nouveaupas.getText()));

                    EmailController.sendEmail("latifa.benzaied@esprit.tn", "Mot de passe ", " mot de passe a ete change");


                }

            }
         else {
             System.out.println("ytytytytt");
         }

    }
    public void setData(String email,String code ){
        this.email=email;
        this.code=code;
    }

}
