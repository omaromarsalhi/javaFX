package pidev.javafx.controller.user;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.net.URL;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import pidev.javafx.tools.user.PasswordHasher;
import pidev.javafx.model.user.Role;
import pidev.javafx.model.user.User;
import pidev.javafx.crud.user.ServiceUser;


public class listUserController implements Initializable {
    @FXML
    private VBox Userlayout;
    @FXML
    private VBox vboxinfo;
    @FXML
    private TextField firstname;

    @FXML
    private
    TextField email;
    @FXML
    private TextField lastname;

    @FXML
    private TextField cin;

    @FXML
    private TextField adresse;

    @FXML
    private TextField age;

    @FXML
    private TextField status;

    @FXML
    private TextField phone;

    @FXML
    private Label role;

    @FXML
    private DatePicker date;

    @FXML
    private DatePicker dob;

    @FXML
    private Button clean;

    @FXML
    private Button btn_supp;

    @FXML
    private Button btn_bloq;

    @FXML
    private Button btn_ajouter;
    @FXML
    private ImageView photos;
    @FXML
    private Pane pane;
    private HBox selectedBox;
    private  int selectedUser;

    List<User> users;
    List<User> test;
    int j = 1;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        test = new ArrayList<>();
        ServiceUser service = new ServiceUser();
        int employee = 0;
        int utlis = 0;
        users = new ArrayList<>(service.getAll());
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getIdMunicipalite() == service.chercherParIsconnected()) {//hne bech ncouf ichkoun il responsable il connectee

                if (users.get(i).getRole() == Role.employe) {

                    employee++;
                }
                if (users.get(i).getRole() == Role.Citoyen) {
                    utlis++;
                }
                if (users.get(i).getRole() != Role.responsable) {
                    test.add(users.get(i));

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/User/user_item.fxml"));
                    try {
                        HBox hBox = fxmlLoader.load();

                        UseritemController useritem = fxmlLoader.getController();

                        useritem.setData(users.get(i));

                        int finalI = i;// a comprendre

                        Button suppButton = (Button) hBox.lookup("#supp"); // La fonction lookup(String selector) est une méthode de la classe Node qui cherche par id dans l' hiérarchie

                        if (suppButton != null) {
                            suppButton.setOnAction(event -> {
                                ServiceUser serviceUser = new ServiceUser();
                                serviceUser.supprimerByEmail(users.get(finalI).getEmail());
                                Userlayout.getChildren().remove(hBox);
                            });

                        }

                        final HBox finalHBox = hBox;
                        int finalI1 = i;
                        hBox.setOnMouseClicked(event -> {
                            clean.setVisible(true);
                            clean.setDisable(false);
                            btn_bloq.setVisible(true);
                            btn_bloq.setDisable(false);
                            btn_supp.setVisible(true);
                            btn_supp.setDisable(false);
                            btn_ajouter.setVisible(true);
                            btn_ajouter.setDisable(true);
                            User user = new User();
                            ServiceUser serviceUser = new ServiceUser();
                            display(serviceUser.findParEmail(users.get(finalI).getEmail()));
                            this.j = test.indexOf(users.get(finalI));
                            if (selectedBox != null) {
                                selectedBox.getStyleClass().remove("hboxselected");
                            }
                            hBox.getStyleClass().add("hboxselected");
                            selectedUser=finalI1;
                            selectedBox = hBox;


                        });

                        btn_supp.setOnMouseClicked(event -> {
                            System.out.println(selectedBox);
                            System.out.println(selectedUser);

                          //  users.remove(selectedUser);
                            ServiceUser serviceUser = new ServiceUser();
                            serviceUser.supprimerByEmail(email.getText());
                            Userlayout.getChildren().remove(selectedBox);
                            users.remove(selectedUser);
                            clearVbox();
                            System.out.println(hBox);


                        });

                        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), hBox);
                        scaleTransition.setFromX(1);
                        scaleTransition.setFromY(1);
                        scaleTransition.setToX(1.02);
                        scaleTransition.setToY(1.02);

                        hBox.setOnMouseEntered(event -> {
                            scaleTransition.playFromStart();
                        });

                        hBox.setOnMouseExited(event -> {
                            scaleTransition.setRate(-1);
                            scaleTransition.playFrom(Duration.millis(200));

                        });
                        HBox.setMargin(hBox, new Insets(70, 0, 0, 0));
                        Userlayout.getChildren().add(hBox);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }
        for (int i = 0; i < Userlayout.getChildren().size(); i++) {
            HBox hbox = (HBox) Userlayout.getChildren().get(i);
            // hbox.setOnMouseClicked(createClickHandler(hbox));
            selectedBox = hbox;
        }


        firstname.setText(test.get(0).getFirstname());
        lastname.setText(test.get(0).getLastname());
        phone.setText(String.valueOf(test.get(0).getNum()));
        adresse.setText(test.get(0).getAdresse());
        age.setText(String.valueOf(test.get(0).getAge()));
        role.setText(String.valueOf(test.get(0).getRole()));
        cin.setText(String.valueOf(test.get(0).getCin()));
        status.setText(String.valueOf(test.get(0).getStatus()));
        String dateString = test.get(0).getDate();
        date.setValue(LocalDate.parse(test.get(0).getDate()));
        dob.setValue(LocalDate.parse(test.get(0).getDob()));
        //labelusers.setText(String.valueOf(utlis));
        // labelemploye.setText(String.valueOf(employee));
        Image image = new Image("file:" +
                test.get(0).getPhotos());
        photos.setImage(image);


    }


    private void fadeIn(TextField node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), node);

        fadeTransition.setFromValue(0);

        fadeTransition.setToValue(1);

        fadeTransition.play();

        node.setVisible(true);
    }

    private void fadeOut(TextField node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), node);

        fadeTransition.setFromValue(1);

        fadeTransition.setToValue(0);

        fadeTransition.setOnFinished(event -> node.setVisible(false));

        fadeTransition.play();
    }


    @FXML
    public void onclick(ActionEvent actionEvent) {

        showDetails();
    }

    private void showDetails() {


        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/User/Details.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);

            scene.getRoot().setStyle("-fx-padding: 5px;");

            scene.getStylesheets().add(String.valueOf(getClass().getResource("/style/styleDetails.css")));

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), root);

            fadeIn.setFromValue(0.0);

            fadeIn.setToValue(1.0);

            fadeIn.play();

            Stage dialogStage = new Stage();

            dialogStage.setScene(scene);

            dialogStage.setTitle("Détails");

            dialogStage.initModality(Modality.APPLICATION_MODAL);

            dialogStage.initStyle(StageStyle.UNDECORATED);

            DetailsController detailsController = loader.getController();

            detailsController.setScene(scene);

            dialogStage.showAndWait();
        } catch (Exception e) {

            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de l'affichage de la boîte de dialogue.");
            alert.showAndWait();
        }
    }


    public void display(User user) {
        this.firstname.setText(user.getFirstname());
        this.email.setText(user.getEmail());
        this.lastname.setText(user.getLastname());
        this.age.setText(String.valueOf(user.getAge()));
        this.adresse.setText(user.getAdresse());
        this.cin.setText(user.getCin());
        this.phone.setText(String.valueOf(user.getNum()));
        this.status.setText(user.getStatus());
        this.role.setText(String.valueOf(user.getRole()));
        this.date.setValue(LocalDate.parse(user.getDate()));
        Image image1 = new Image(user.getPhotos());
        photos.setImage(image1);
        vboxinfo.setVisible(true);


        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), vboxinfo);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

    }


    @FXML
    public void btnAjouterEmploye(ActionEvent actionEvent) {
        System.out.println("HIIIIII");
        clearVbox();
        btn_supp.setVisible(true);
        photos.setImage(new Image("file:src/main/resources/img/image.png",140,122,true,true));
        btn_bloq.setVisible(true);
        clean.setVisible(true);
        btn_supp.setDisable(true);
        btn_bloq.setDisable(true);
        clean.setDisable(true);
        btn_ajouter.setVisible(true);
        btn_ajouter.setDisable(false);
        vboxinfo.setVisible(true);
        //TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), vboxinfo);
        //transition.setToX(-20);
        //transition.setAutoReverse(true); // Répéter en sens inverse
        //transition.setCycleCount(TranslateTransition.INDEFINITE); // Nombre de répétitions
        //transition.play();

    }


    public void ajouterEmploye(ActionEvent actionEvent) {
        System.out.println("dggggggggggg");
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
        user.setRole(Role.employe);
        user.setStatus(status.getText());
        user.setIdMunicipalite(serviceUser.chercherParIsconnected());
        serviceUser.ajouteremploye(user);


        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/User/user_item.fxml"));
            HBox hBox = fxmlLoader.load();
            UseritemController useritem = fxmlLoader.getController();
            useritem.setData(user);

            Userlayout.getChildren().add(hBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    private void clearTextFields(VBox mainVBox) {
        mainVBox.getChildren().forEach(hBox -> {
            if (hBox instanceof HBox) {
                ((HBox) hBox).getChildren().forEach(vBox -> {
                    if (vBox instanceof VBox) {
                        ((VBox) vBox).getChildren().forEach(node -> {
                            if (node instanceof TextField) {
                                ((TextField) node).clear();
                            }
                        });
                    }
                });
            }
        });
    }

    public void onUsersCllik(ActionEvent actionEvent) {
        ServiceUser service = new ServiceUser();

        Userlayout.getChildren().clear();
        System.out.println(users.size());
        for (int i = 0; i < users.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/User/user_item.fxml"));
            HBox hBox;


            try {


                hBox = fxmlLoader.load();

            } catch (IOException e) {


                throw new RuntimeException(e);
            }
            System.out.println(users.get(i).getIdMunicipalite());

            if (users.get(i).getRole().equals(Role.Citoyen)) {

                if (users.get(i).getIdMunicipalite() == service.chercherParIsconnected()) {

                    UseritemController useritem = fxmlLoader.getController();

                    Userlayout.getChildren().add(hBox);

                    useritem.setData(users.get(i));
                }
            }
            int finalI = i;
            final HBox finalHBox = hBox;
            hBox.setOnMouseClicked(event -> {
                clean.setVisible(true);
                clean.setDisable(false);
                btn_bloq.setVisible(true);
                btn_bloq.setDisable(false);
                btn_supp.setVisible(true);
                btn_supp.setDisable(false);
                btn_ajouter.setVisible(true);
                btn_ajouter.setDisable(true);
                User user = new User();
                ServiceUser serviceUser = new ServiceUser();

                display(serviceUser.findParEmail(users.get(finalI).getEmail()));
                if (selectedBox != null) {
                    System.out.println("heloooo");
                    selectedBox.getStyleClass().remove("hboxselected");
                }
                hBox.getStyleClass().add("hboxselected");
                System.out.println("ddddddddddddd");
                selectedBox = hBox;


            });
            Button suppButton = (Button) hBox.lookup("#supp"); // La fonction lookup(String selector) est une méthode de la classe Node qui cherche par id dans l' hiérarchie

            if (suppButton != null) {
                suppButton.setOnAction(event -> {
                    ServiceUser serviceUser = new ServiceUser();

                    serviceUser.supprimerByEmail(users.get(finalI).getEmail());
                    Userlayout.getChildren().remove(hBox);
                    users.remove(finalI);
                });

            }


        }
        for (int i = 0; i < Userlayout.getChildren().size(); i++) {
            HBox hbox = (HBox) Userlayout.getChildren().get(i);
            //hbox.setOnMouseClicked(createClickHandler(hbox));
            selectedBox = hbox;
        }


    }


    public void onEmployeeClick(ActionEvent actionEvent) {
        ServiceUser service = new ServiceUser();
        Userlayout.getChildren().clear();
        for (int i = 0; i < users.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/User/user_item.fxml"));
            HBox hBox;

            try {


                hBox = fxmlLoader.load();

            } catch (IOException e) {


                throw new RuntimeException(e);
            }

            if (users.get(i).getRole().equals(Role.employe)) {
                if (users.get(i).getIdMunicipalite() == service.chercherParIsconnected()) {


                    UseritemController useritem = fxmlLoader.getController();
                    useritem.setData(users.get(i));
                    Userlayout.getChildren().add(hBox);
                }
            }
            int finalI = i;
            final HBox finalHBox = hBox;
            hBox.setOnMouseClicked(event -> {
                clean.setVisible(true);
                clean.setDisable(false);
                btn_bloq.setVisible(true);
                btn_bloq.setDisable(false);
                btn_supp.setVisible(true);
                btn_supp.setDisable(false);
                btn_ajouter.setVisible(true);
                btn_ajouter.setDisable(true);
                User user = new User();
                ServiceUser serviceUser = new ServiceUser();

                display(serviceUser.findParEmail(users.get(finalI).getEmail()));
                if (selectedBox != null) {
                    System.out.println("heloooo");
                    selectedBox.getStyleClass().remove("hboxselected");
                }
                hBox.getStyleClass().add("hboxselected");
                System.out.println("ddddddddddddd");
                selectedBox = hBox;


            });

            Button suppButton = (Button) hBox.lookup("#supp"); // La fonction lookup(String selector) est une méthode de la classe Node qui cherche par id dans l' hiérarchie

            if (suppButton != null) {
                suppButton.setOnAction(event -> {
                    ServiceUser serviceUser = new ServiceUser();

                    serviceUser.supprimerByEmail(users.get(finalI).getEmail());
                    Userlayout.getChildren().remove(hBox);
                    users.remove(finalI);
                });

            }


        }
        for (int i = 0; i < Userlayout.getChildren().size(); i++) {
            HBox hbox = (HBox) Userlayout.getChildren().get(i);
            //hbox.setOnMouseClicked(createClickHandler(hbox));
            selectedBox = hbox;
        }

    }

    public void onAllClick(ActionEvent actionEvent) {
        ServiceUser service = new ServiceUser();
        Userlayout.getChildren().clear();
        for (int i = 0; i < users.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/User/user_item.fxml"));
            HBox hBox;

            try {
                hBox = fxmlLoader.load();

            } catch (IOException e) {


                throw new RuntimeException(e);
            }


            if (users.get(i).getRole() != Role.responsable) {
                System.out.println(users.get(i).getFirstname());
                if (users.get(i).getIdMunicipalite() == service.chercherParIsconnected()) {
                    UseritemController useritem = fxmlLoader.getController();
                    useritem.setData(users.get(i));
                    HBox.setMargin(hBox, new Insets(70, 0, 0, 0));
                    Userlayout.getChildren().add(hBox);
                }
            }

            int finalI = i;
            final HBox finalHBox = hBox;
            hBox.setOnMouseClicked(event -> {
                clean.setVisible(true);
                clean.setDisable(false);
                btn_bloq.setVisible(true);
                btn_bloq.setDisable(false);
                btn_supp.setVisible(true);
                btn_supp.setDisable(false);
                btn_ajouter.setVisible(true);
                btn_ajouter.setDisable(true);
                User user = new User();
                ServiceUser serviceUser = new ServiceUser();
                display(serviceUser.findParEmail(users.get(finalI).getEmail()));
                //j=finalI;
                //  System.out.println(j);

                if (selectedBox != null) {
                    System.out.println("heloooo");
                    selectedBox.getStyleClass().remove("hboxselected");
                }
                hBox.getStyleClass().add("hboxselected");
                System.out.println("ddddddddddddd");
                selectedBox = hBox;

            });

            Button suppButton = (Button) hBox.lookup("#supp"); // La fonction lookup(String selector) est une méthode de la classe Node qui cherche par id dans l' hiérarchie

            if (suppButton != null) {
                suppButton.setOnAction(event -> {
                    ServiceUser serviceUser = new ServiceUser();
                    serviceUser.supprimerByEmail(users.get(finalI).getEmail());
                    Userlayout.getChildren().remove(hBox);
                    users.remove(finalI);
                });

            }


        }


    }



   public void supprimer(ActionEvent actionEvent) {

//        System.out.println(selectedUser);
//        users.remove(selectedUser);
//        ServiceUser serviceUser = new ServiceUser();
//        serviceUser.supprimerByEmail(email.getText());
//        clearVbox();
//        users.remove(selectedUser);
    }


    public void clearVbox()
    {
        //Image image=new Image()
        //photos.setImage();
       role.setText(null);
       photos.setImage(null);
       email.clear();
       lastname.clear();
       firstname.clear();
       age.clear();
       cin.clear();
       dob.setValue(null);
       date.setValue(null);
       adresse.clear();
       status.clear();
       phone.clear();
    }


           public void clean(ActionEvent actionEvent) {
               clearVbox();

           }
}
