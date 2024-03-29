package pidev.javafx.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pidev.javafx.model.user.User;


import java.net.URL;
import java.util.*;

public class UseritemController implements Initializable

{
    @FXML
    private ImageView image;
    @FXML
    private Label firstname;

    @FXML
    private Label lastname;

    @FXML
    private Label email;
    @FXML
    private HBox hbox ;




    public void setData(User user1){


        firstname.setText(user1.getFirstname());

        lastname.setText(user1.getLastname());

        email.setText(user1.getEmail());
        Image image1 = new Image("file:src/main/resources"+user1.getPhotos(),32,32,true,true);
        image.setImage(image1);


    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }



@FXML
    public void btnmodifier(ActionEvent actionEvent) {
        System.out.println("helooooooooooooooooo");
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Details.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setScene(scene);

            stage.showAndWait();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }



}
