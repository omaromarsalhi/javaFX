package pidev.javafx.controller.Municipalite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pidev.javafx.controller.Municipalite.municipaliteitemController;
import pidev.javafx.tools.user.PasswordHasher;
import pidev.javafx.model.user.Municipalite;
import pidev.javafx.model.user.Role;
import pidev.javafx.model.user.User;
import pidev.javafx.crud.user.ServiceMunicipalite;
import pidev.javafx.crud.user.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Admin2Controller implements Initializable {
    @FXML
    HBox munilayout;

    @FXML
    private VBox vboxinfo;

    @FXML
    private Label role;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField cin;

    @FXML
    private TextField adresse;

    @FXML
    private TextField age;

    @FXML
    private TextField email;

    @FXML
    private TextField status;

    @FXML
    private TextField phone;

    @FXML
    private DatePicker dob;

    @FXML
    private DatePicker date;
    int test;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //vboxinfo.setVisible(false);
          ServiceMunicipalite service=new ServiceMunicipalite();
          List<Municipalite> muni = new ArrayList<>(service.getAll());
             for (int i = 0; i < muni.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/User/municiplaite_item.fxml"));
                try {
                   HBox cardhBox = fxmlLoader.load();
                   municipaliteitemController muniitem = fxmlLoader.getController();
                   muniitem.setData(muni.get(i));
                   HBox.setMargin(cardhBox, new Insets(10, 10, 0, 0));
                   munilayout.getChildren().add(cardhBox);
                    int finalI = i;
                    cardhBox.setOnMouseClicked(event->{
                        System.out.println(muni.get(finalI).getId());
                        test=muni.get(finalI).getId();
                    });
                 }
                    catch (IOException e) {
                    throw new RuntimeException(e);
                     }


    }}


   

    public void onAjoutResponsable(ActionEvent actionEvent) {

        vboxinfo.setVisible(true);

    }




    public void ajouterResponsable(ActionEvent actionEvent) {

            Random random = new Random();
            ServiceUser serviceUser = new ServiceUser();
            User user = new User();

            user.setFirstname(firstname.getText());
            user.setLastname(lastname.getText());
            user.setEmail(email.getText());
            user.setAge(Integer.parseInt(age.getText()));
            user.setNum(Integer.parseInt(phone.getText()));
            user.setPassword(PasswordHasher.hashPassword("latifa"));
            user.setAdresse(adresse.getText());
            user.setCin(cin.getText());
            user.setRole(Role.responsable);
            user.setStatus(status.getText());
            user.setIdMunicipalite(test);
            serviceUser.ajouterResponsable(user);


        }

    }

