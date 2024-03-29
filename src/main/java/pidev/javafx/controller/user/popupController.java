package pidev.javafx.controller.user;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pidev.javafx.crud.user.ServiceUser;
import pidev.javafx.model.user.User;
import pidev.javafx.tools.user.windowController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class popupController implements Initializable {
    @FXML
    private Button changerpwd;
    private Stage accountcontroller;
    @FXML
    private Button changeremail;
    private  String email;
    private String password;
    @FXML
    private Button deconnecterbtn;

    public void setAccountcontroller(Stage accountcontroller) {
        this.accountcontroller = accountcontroller;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        changerpwd.setOnMouseClicked(event->{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/User/reglageavancee.fxml"));
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loadReglage(fxmlLoader);

            ReglageController reglage = fxmlLoader.getController();
             reglage.getNouveauemail().setVisible(false);
             reglage.getAncienemail().setVisible(false);
             reglage.getModifieremail().setVisible(false);
             reglage.getResetPassword().setVisible(false);
             reglage.getVerifier().setVisible(false);
             reglage.setPassword(password);
             reglage.setEmail(email);




        });
        changeremail.setOnMouseClicked(event->{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/User/reglageavancee.fxml"));
            loadReglage(fxmlLoader);
            ReglageController reglage = fxmlLoader.getController();
            reglage.setEmail(email);
            reglage.getNouveauemail().setVisible(false);
            reglage.getAncienemail().setVisible(false);
            reglage.getNouveaupass().setVisible(false);
            reglage.getAncienpass().setVisible(false);
            reglage.getNouveaupas().setVisible(false);
            reglage.getNouveauemail().setVisible(true);
            reglage.getAncienemail().setVisible(true);
            reglage.getModifieremail().setVisible(true);
            reglage.getModifierpwd().setVisible(false);

        });



        deconnecterbtn.setOnMouseClicked(event->{
            ServiceUser serviceUser=new ServiceUser();
            User user=new User();
            user=serviceUser.findParEmail(email);
            System.out.println(user.getEmail());
            user.setIsConnected(0);
            serviceUser.isconnected(user);

            windowController.delete();

        });

    }





        public void setData(String pwd, String email){
        this.email=email;
        this.password=pwd;
        }





        public void  loadReglage(FXMLLoader fxmlLoader) {



            Stage newStage;
            try {
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(String.valueOf(getClass().getResource("/style/StyleReglage.css")));
                Stage stage2 = (Stage) changerpwd.getScene().getWindow();
                stage2.setScene(scene);
                stage2.setX(600);
                stage2.setY(200);
                // Affichez le stage
                stage2.show();
                //newStage = new Stage();
                //windowController.deleteLastWindow();
                //windowController.addWindow(newStage);
                // newStage.initOwner(stage2);
                //newStage.setX(600);
                //newStage.setY(200);
                //newStage.setScene(scene);
                //newStage.initModality(Modality.WINDOW_MODAL);
                //newStage.initStyle(StageStyle.UNDECORATED);
                //newStage.show();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }






}

