package pidev.javafx.controller.user;

import javafx.animation.FadeTransition;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import pidev.javafx.model.user.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;

import java.util.ResourceBundle;
import java.util.UUID;

import javafx.stage.Stage;
import pidev.javafx.crud.user.ServiceUser;
import pidev.javafx.tools.marketPlace.MyTools;
import pidev.javafx.tools.user.windowController;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AccountController implements Initializable {

    @FXML
    VBox Vbox1;
    @FXML
    Button btnsupp;
    @FXML
    Pane panee;

    @FXML
    Button dashboard;

    @FXML
    TextField firstname;

    @FXML
    TextField email;

    @FXML
    private DatePicker dob;

    @FXML
    private TextField status;

    @FXML
    private TextField lastname;

    @FXML
    private TextField cin;

    @FXML
    private TextField age;

    @FXML
    private TextField adresse;

    @FXML
    private TextField telephone;
    @FXML
    private Label labelname;

    @FXML
    private Label labelemail;
    @FXML
    private ImageView isconnected;
    @FXML
    private ImageView myImageView;
    @FXML
    private  Button reglage;
    private String imagePath;
    private Stage primaryStage;

    @FXML
    private AnchorPane secondPane;

    private FXMLLoader fxmlLoader;

     @FXML
    private  TextField gender;
     @FXML
     private Button alerteLastname;
    @FXML
    private Button alertegender;
    @FXML
    private Button alerteCin;
    @FXML
    private Button alerteTelephone;
    @FXML
    private Button alerteStatus;
    @FXML
    private Button alerteFirstname;
    @FXML
    private Button alerteAdresse;
    @FXML
    private Button alerteAge;
    @FXML
    private Button alerteEmail;
    @FXML
    private Button alerteDob;


    public AccountController() {
    }
    public void setFXMLLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }


    @FXML
    public void onb1bouttonclick(ActionEvent event) {
        Vbox1.setVisible(!Vbox1.isVisible());
        Vbox1.setPrefHeight(-100);
        Vbox1.setPrefWidth(10);
        panee.setPrefWidth(350);
        //panee.setPrefHeight();

    }
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//     //   originalScene = stage.getScene();
//        secondPane.setVisible(false);
//         alerteLastname.setVisible(false);
//         alerteAdresse.setVisible(false);
//         alerteCin.setVisible(false);
//         alertegender.setVisible(false);
//         alerteStatus.setVisible(false);
//         alerteTelephone.setVisible(false);
//         alerteAge.setVisible(false);
//         alerteFirstname.setVisible(false);
//        alerteEmail.setVisible(false);
//        firstname.setEditable(false);
//        email.setEditable(false);
//        adresse.setEditable(false);
//        alerteDob.setVisible(false);
//        reglage.setOnAction(event -> {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/User/popup.fxml"));
//
//        try {
//
//            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//            Stage popupStage = new Stage();
//
//            Scene scene = new Scene(fxmlLoader.load());
//
//            scene.setFill(Color.TRANSPARENT);
//
//            popupController pop =fxmlLoader.getController();
//
//            ServiceUser service=new ServiceUser();
//
//            String email= this.email.getText();
//
//            String password =service.RetriveHashedPassword(email);
//
//            pop.setData(password,email);
//
//            pop.setAccountcontroller((Stage) ((Node) event.getSource()).getScene().getWindow());
//
//            scene.getStylesheets().add(String.valueOf(getClass().getResource("/style/StylePopup.css")));
//
//            popupStage.setScene(scene);
//
//            windowController.addWindow(popupStage);
//
//            Bounds boundsInScreen = reglage.localToScreen(reglage.getBoundsInLocal());
//
//            popupStage.setX(boundsInScreen.getMinX());
//
//            popupStage.setY(620);
//
//            popupStage.initOwner(primaryStage);
//
//            popupStage.initStyle(StageStyle.UNDECORATED);
//
//            popupStage.show();
//
//            panee.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(javafx.scene.input.MouseEvent event) {
//
//                  windowController.deleteLastWindow();
//                }
//            });
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    });
//
//
//
//
//}


    public void display(User user)
    {
        this.firstname.setText(user.getFirstname());
        this.lastname.setText(user.getLastname());
        this.email.setText(user.getEmail());
        this.adresse.setText(user.getAdresse());
        this.cin.setText(user.getCin());
        this.status.setText(user.getStatus());
        this.age.setText(String.valueOf(user.getAge()));
        this.telephone.setText(String.valueOf(user.getNum()));
        this.gender.setText(user.getGender());
        //hethi bech nsel aaliha omar kn mchet
        // System.out.println(user.getPhotos());
        //Image image = new Image(user.getPhotos());
        //myImageView.setImage(image);
        //this.dob.setValue(LocalDate.parse(user.getDob()));
        this.labelemail.setText((user.getEmail()));
        this.labelname.setText(user.getFirstname()+" "+user.getLastname());
        this.isconnected.setVisible(true);
    }
    public void insert_Image(){
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Choose a File");
//        var selectedFile = fileChooser.showOpenDialog(primaryStage);
//        if (selectedFile != null) {
//            imagePath=selectedFile.getAbsolutePath() ;
//            System.out.println(selectedFile.getAbsolutePath());
//            //String imagePath = "image/" + imagePath;
//           // Image image = new Image(imagePath);
//            //myImageView.setImage(image);
//
//        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        var selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
//            String randomFileName = UUID.randomUUID().toString() + ".png";
//         //   System.out.println(randomFileName);
//
//            String destinationFolderPath = "file:/src/main/resources/image/";
//            String destinationPath = destinationFolderPath + randomFileName;
//            System.out.println(destinationPath);
//            File destinationFolder = new File(destinationFolderPath);
//            System.out.println(destinationFolder);
//            File destinationFile = new File(destinationPath);
//            System.out.println(destinationFile);
//            if (!destinationFolder.exists()) {
//                destinationFolder.mkdirs();
//
//
//            }
//
//
//            try {
//                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            imagePath = destinationPath;
       Image image = new Image("file:/src/main/resources"+ MyTools.getInstance().getPathAndSaveIMGUser( selectedFile.getAbsolutePath() ) );
       myImageView.setImage(image);
        }
    }

    public void btnModifier(ActionEvent actionEvent) {
        String agereel;
        String regexAge = "^(?:1[8-9]|[2-9][0-9])$";
        String regexLastName = "[a-zA-Z\\s]{3,}+";
        String regexTelephone = "^[0-9]{8,8}$";
        String regexCIN = "^[0-9]{8}$";
        String aage = "Valide les ages entre 18 et 99 et il faut l age correspond a la dob ";
        String tel = "Valide un numéro de téléphone composé exactement de 8 chiffres.";
        String chaine = "Accepte uniquement les chaines composés d'au moins 3 caractères alphabétiques.";
        String cinn = "Valide un numéro de carte d'identité nationale composé exactement de 8 chiffres.";
        if (!lastname.getText().isEmpty() &&
                !age.getText().isEmpty() && !cin.getText().isEmpty() && !telephone.getText().isEmpty() && dob.getValue() != null && !status.getText().isEmpty() && !gender.getText().isEmpty()) {
            Period period = Period.between(dob.getValue(), LocalDate.now());
            int agerel = period.getYears();
            agereel=String.valueOf(agerel);
            System.out.println(agereel);
            alerteDob.setVisible(false);
            if (cin.getText().matches(regexCIN) && lastname.getText().matches(regexLastName) && status.getText().matches(regexLastName) && gender.getText().matches(regexLastName) && telephone.getText().matches(regexTelephone) &&
                    age.getText().matches(regexAge) && age.getText().equals(agereel) && gender.getText().matches(regexLastName)) {
                alerteAge.setVisible(false);
                alerteCin.setVisible(false);
                alerteLastname.setVisible(false);
                alertegender.setVisible(false);
                alerteTelephone.setVisible(false);
                alerteDob.setVisible(false);

                User user = new User();
                user.setFirstname(firstname.getText());
                user.setEmail(email.getText());
                user.setLastname(lastname.getText());
                user.setAge(Integer.parseInt(age.getText()));
                user.setCin(cin.getText());
                user.setDob(String.valueOf(dob.getValue()));
                user.setNum(Integer.parseInt(telephone.getText()));
                user.setStatus(status.getText());
                user.setPhotos(imagePath);
                user.setGender(gender.getText());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("");
                alert.setContentText("This acount will be modifed are you surev?");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/style/styleSignup.css").toExternalForm());
                //applyBlurEffect((Stage) btnsupp.getScene().getWindow());

                alert.showAndWait().ifPresent(response -> {
//
//
                    if (response == ButtonType.OK) {
                        ServiceUser serviceUser = new ServiceUser();
                     serviceUser.modifier(user);

                        Task<Void> task = new Task() {
                            @Override
                            protected Object call() throws Exception {
                                Platform.runLater(() -> {
                                    panee.setOpacity(0.3);
                                    secondPane.setVisible(true);
                                });
                                Thread.sleep(3000);
                                return null;
                            }
                        };

                        task.setOnSucceeded(workerStateEvent -> {
                            Platform.runLater(() -> {
                                System.out.println(secondPane.isVisible());
                                panee.setOpacity(1);
                                secondPane.setVisible(false);
                            });
                        });

                        new Thread(task).start();
                    }
                });
            }

            else {

                if (lastname.getText().matches(regexLastName)) {
                    alerteLastname.setVisible(false);

                }



                if (!lastname.getText().matches(regexLastName)) {
                    alerteLastname.setVisible(true);
                    showAlerteButton(alerteLastname, "ddd", chaine);

                }


                if (cin.getText().matches(regexCIN)) {
                    System.out.println("cin");

                    alerteCin.setVisible(false);

                }

                if (!cin.getText().matches(regexCIN)) {
                    System.out.println("cin");

                    alerteCin.setVisible(true);
                    showAlerteButton(alerteCin, "ddddddd", cinn);
                }

                if (status.getText().matches(regexLastName)) {
                    System.out.println("status");

                    alerteStatus.setVisible(false);
                    showAlerteButton(alerteStatus, "ddddddd", chaine);
                }
                if (!status.getText().matches(regexLastName)) {
                    System.out.println("status");

                    alerteStatus.setVisible(true);
                    showAlerteButton(alerteStatus, "ddddddd", chaine);
                }

                if (telephone.getText().matches(regexTelephone)) {
                    alerteTelephone.setVisible(false);
                    showAlerteButton(alerteTelephone, "ddddddd", tel);
                }
                if (!telephone.getText().matches(regexTelephone)) {
                    alerteTelephone.setVisible(true);
                    showAlerteButton(alerteTelephone, "ddddddd", tel);
                }


                if (age.getText().matches(regexAge) && age.getText().equals(agereel)) {
                    alerteAge.setVisible(false);

                }

                if (!age.getText().equals(agereel)||!age.getText().matches(regexAge))
                {
                    System.out.println(agereel);

                    alerteAge.setVisible(true);
                    showAlerteButton(alerteAge, "ddddddd", aage);
                }
                if (gender.getText().matches(regexLastName)) {
                    System.out.println("gender");

                    alertegender.setVisible(false);
                    showAlerteButton(alertegender, "ddddddd", chaine);
                }
                if (!gender.getText().matches(regexLastName)) {
                    System.out.println("gender");

                    alertegender.setVisible(true);
                    showAlerteButton(alertegender, "ddddddd", chaine);
                }

            }
        }
         else {
            if (lastname.getText().isEmpty()) {

                alerteLastname.setVisible(true);
                showAlerteButton(alerteLastname, "ddddddd", chaine);

            }


            if (cin.getText().isEmpty()) {

                alerteCin.setVisible(true);
                showAlerteButton(alerteCin, "ddddddd", cinn);
            }

            if (status.getText().isEmpty() ) {
                alerteStatus.setVisible(true);
                showAlerteButton(alerteStatus, "ddddddd", chaine);
            }

            if (telephone.getText().isEmpty()) {

                alerteTelephone.setVisible(true);
                showAlerteButton(alerteTelephone, "ddddddd", tel);
            }


            if (age.getText().isEmpty()) {

                alerteAge.setVisible(true);
                showAlerteButton(alerteAge, "ddddddd", aage);
            }

            if (gender.getText().isEmpty()) {

                alertegender.setVisible(true);
                showAlerteButton(alertegender, "ddddddd", chaine);
            }
            if(dob.getValue()==null){
                alerteDob.setVisible(true);

            }

        }


        }




    public void onDeleteAccount(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("This account will be deleted are you sure");
        alert.setContentText("Are you sure you want to proceed?");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/style/styleSignup.css").toExternalForm());
        applyBlurEffect((Stage) btnsupp.getScene().getWindow());

        alert.showAndWait().ifPresent(response -> {


            if (response == ButtonType.OK) {
                ServiceUser serviceUser = new ServiceUser();
                serviceUser.supprimer(Integer.parseInt(cin.getText()));
                windowController.deleteLastWindow();
            } else {

            }
            removeBlurEffect((Stage) btnsupp.getScene().getWindow());
        });


    }


    public void uploadimag(ActionEvent actionEvent) {
        insert_Image();

    }





    public static Alert showAlert(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setPrefWidth(400);
        alert.getDialogPane().setPrefHeight(200);
//        alert.getDialogPane().getStylesheets().add( AccountController.class.getResource("/style/user/styleSignup.css"));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        // stage.getIcons().add(new Image("C:/Users/Latifa/Desktop/PI-DEV/src/main/resources/img/gmail.png")); // Remplacez le chemin par votre propre chemin

        return alert;
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
    private void applyBlurEffect(Stage stage) {

        FadeTransition ft = new FadeTransition(Duration.millis(900), stage.getScene().getRoot());
        ft.setFromValue(1.0);
        ft.setToValue(0.5);
        ft.play();
    }

    private void removeBlurEffect(Stage stage) {
        // Création d'une transition de déflou
        FadeTransition ft = new FadeTransition(Duration.millis(900), stage.getScene().getRoot());
        ft.setFromValue(0.5);
        ft.setToValue(1.0);
        ft.play();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
