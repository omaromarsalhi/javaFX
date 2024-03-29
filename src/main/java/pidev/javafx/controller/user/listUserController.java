package pidev.javafx.controller.user;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;

import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import pidev.javafx.tools.marketPlace.MyTools;
import pidev.javafx.tools.user.PasswordHasher;
import pidev.javafx.controller.Municipalite.municipaliteitemController;
import pidev.javafx.controller.user.DetailsController;
import pidev.javafx.crud.user.ServiceMunicipalite;
import pidev.javafx.model.user.Municipalite;

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
    private ImageView boutonleft;
    @FXML
    private Button searchBtn;
    @FXML
    private HBox searchHbox;
    @FXML
    private TextField searchTextField;
    @FXML
    private ImageView boutonRight;
    @FXML
    private Pane pane;
    private HBox selectedBox;
    private int selectedUser;
    private int pos;

    @FXML
    private ScrollPane scrolPane;
    @FXML
    private HBox municipCrds;

    @FXML
    private ImageView ajouterMunicipalite;

    @FXML
    private ImageView fermer;
    @FXML
    private TextField name;
    @FXML
    private TextField adressee;

    private List<Municipalite> muni;
    @FXML
    private AnchorPane layoutAdd;
    @FXML
    private AnchorPane firstLayout;

    @FXML
    private Button addMunicipalite;
    @FXML
    private Button imageBtn;
    public String imgToSave;
    public FileChooser chooser;
    private File chosenFiles;


    List<User> users;
    List<User> test;


    @FXML
    private HBox munilayout;
    int j = 1;
    private Timer animTimer;
    private String searchBarState;
    int index = 0;

    private void setExtFilters(FileChooser chooser) {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    public void loadMuni(Municipalite municipalite) {
        municipCrds.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/User/municiplaite_item.fxml"));
        try {
            VBox cardhBox = fxmlLoader.load();
            municipaliteitemController muniitem = fxmlLoader.getController();
            muniitem.setData(municipalite);
            VBox.setMargin(cardhBox, new Insets(10, 10, 0, 0));
            municipCrds.getChildren().add(cardhBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        layoutAdd.setVisible(false);
        fermer.setOnMouseClicked(event -> {
            layoutAdd.setVisible(false);
            firstLayout.setOpacity(1);
        });
        ajouterMunicipalite.setOnMouseClicked(event -> {
            Municipalite municipalite = new Municipalite();
            municipalite.setName(name.getText());
            municipalite.setAdresse(adressee.getText());

          //  municipalite.setPhoto(imgToSave);
            ServiceMunicipalite service = new ServiceMunicipalite();
            service.ajouter(municipalite);
            loadMuni(municipalite);

        });
        imageBtn.setOnMouseClicked(event1 -> {
            FileChooser fileChooser = new FileChooser();
            setExtFilters(fileChooser);
            fileChooser.setTitle("Save Image");
            chosenFiles = (File) fileChooser.showOpenMultipleDialog(Stage.getWindows().get(0));
        });
        boutonleft.setOnMouseClicked(event -> {
            if (index < 0)
                index = muni.size() - 1;
            loadMuni(muni.get(index--));
            System.out.println(index);
            showUserMuni(index +1);
        });
        addMunicipalite.setOnMouseClicked(event -> {
            firstLayout.setOpacity(0.5);
            layoutAdd.setVisible(true);
            MyTools.getInstance().showAnimation(layoutAdd);

        });
        boutonRight.setOnMouseClicked(event -> {
            if (index >= muni.size() - 1)
                index = 0;
            loadMuni(muni.get(index++));
            System.out.println(index-1);
            showUserMuni(index);
        });
        boutonRight.setOnMouseClicked(event -> {
            if (index >= muni.size() - 1)
                index = 0;
            loadMuni(muni.get(index++));
            System.out.println(index);
            showUserMuni(index);
        });
        ServiceUser service = new ServiceUser();
        users = new ArrayList<>(service.getAll());
        //zedet houni
        ServiceMunicipalite servicee = new ServiceMunicipalite();
        muni = new ArrayList<>(servicee.getAll());
        loadMuni(muni.get(index++));
        for (int i = 0; i < users.size(); i++) {//hne bech ncouf ichkoun il responsable il connectee
                if (users.get(i).getRole() != Role.responsable) {
                    if (users.get(i).getIdMunicipalite() == muni.get(index).getId()) {
//                        if (users.get(i).getState() == 1)
//                            imgBloq.setImage(new Image("file:src/main/resources/namedIcons/green.png", 34, 34, true, true));

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
                                System.out.println(finalI1);

//                                btn_bloq.setVisible(true);
//                                btn_bloq.setDisable(false);
//                                btn_supp.setVisible(true);
//                                btn_supp.setDisable(false);
//                                btn_ajouter.setVisible(true);
//                                btn_ajouter.setDisable(true);
//                                User user = new User();
                                ServiceUser serviceUser = new ServiceUser();
                                display(serviceUser.findParEmail(users.get(finalI).getEmail()));
                              //  this.j = test.indexOf(users.get(finalI));
                                if (selectedBox != null) {
                                    selectedBox.getStyleClass().remove("hboxselected");
                                }
                                selectedBox.getStyleClass().add("-fx-background-color: #D7D7D7");
                                selectedUser = finalI1;
                                selectedBox = hBox;
                                System.out.println(finalI1);


                            });

//                            btn_supp.setOnMouseClicked(event -> {
//                                System.out.println(selectedBox);
//                                System.out.println(selectedUser);
//                                //  users.remove(selectedUser);
//                                ServiceUser serviceUser = new ServiceUser();
//                                serviceUser.supprimerByEmail(email.getText());
//                                Userlayout.getChildren().remove(selectedBox);
//                                users.remove(selectedUser);
//                                System.out.println(hBox);
//                            });

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
        searchBarState = "closed";
        animTimer = new Timer();
        searchTextField.setVisible(false);
        searchBtn.setStyle("-fx-border-radius: 20;" +
                "-fx-background-radius:20;");

        searchBtn.setOnMouseClicked(event -> {
            if (searchBarState.equals("opened") && !searchTextField.getText().isEmpty())
                System.out.println(searchTextField.getText());
            else
                animateSearchBar();
        });

    }

    public void animateSearchBar() {
        if (searchBarState.equals("closed")) {
            searchBarState = "opened";
            animTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (searchTextField.getWidth() == 16) {
                        searchBtn.setStyle("-fx-border-radius: 0 20 20 0;" +
                                "-fx-border-color: black  black black transparent ;");
                        searchTextField.setVisible(true);
                    }
                    if (searchTextField.getWidth() < (searchHbox.getWidth() - searchBtn.getWidth() - 20)) {
                        searchTextField.setPrefWidth(searchTextField.getWidth() + 10);
                    } else
                        this.cancel();
                }

            }, 0, 15);
        } else if (searchBarState.equals("opened") && searchTextField.getText().isEmpty()) {
            searchBarState = "closed";
            animTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (searchTextField.getWidth() <= 16) {
                        searchBtn.setStyle("-fx-border-radius: 20;" +
                                "-fx-background-radius:20;");
                        searchTextField.setVisible(false);
                    }
                    if (searchTextField.getWidth() > 16) {
                        searchTextField.setPrefWidth(searchTextField.getWidth() - 10);
                    } else
                        this.cancel();
                }
            }, 0, 15);
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


    public void supprimer(ActionEvent actionEvent) {

//        System.out.println(selectedUser);
//        users.remove(selectedUser);
//        ServiceUser serviceUser = new ServiceUser();
//        serviceUser.supprimerByEmail(email.getText());
//        clearVbox();
//        users.remove(selectedUser);
    }

    public void showUserMuni(int pos) {

        ServiceUser service = new ServiceUser();
        users = new ArrayList<>(service.getAll());
        Userlayout.getChildren().clear();
        System.out.println("aaaaa "+users);
        for (int i = 0; i < users.size(); i++) {
            System.out.println("oooooo "+i);
            if (users.get(i).getIdMunicipalite() == muni.get(pos).getId()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/User/user_item.fxml"));
                HBox hBox;
                try {
                    hBox = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (users.get(i).getRole() != Role.responsable) {
                    if (users.get(i).getIdMunicipalite() == service.chercherParIsconnected()) {
                        UseritemController useritem = fxmlLoader.getController();
                        useritem.setData(users.get(i));
                        HBox.setMargin(hBox, new Insets(70, 0, 0, 0));
                        Userlayout.getChildren().add(hBox);
                    }
                }

                int finalI = i;
                final HBox finalHBox = hBox;


                Button suppButton = (Button) hBox.lookup("#supp"); // La fonction lookup(String selector) est une méthode de la classe Node qui cherche par id dans l' hiérarchie

                if (suppButton != null) {
                    suppButton.setOnAction(event -> {
                        ServiceUser serviceUser = new ServiceUser();
                        serviceUser.supprimerByEmail(users.get(finalI).getEmail());
                        Userlayout.getChildren().remove(hBox);
                        users.remove(finalI);
                    });
                }

                Button bloqButton = (Button) hBox.lookup("#bloqUser"); // La fonction lookup(String selector) est une méthode de la classe Node qui cherche par id dans l' hiérarchie

                if (bloqButton != null) {
                    bloqButton.setOnAction(event -> {
                        ServiceUser serviceUser = new ServiceUser();
                        users.get(finalI).setState(1);//bloquer
                        serviceUser.supprimerByEmail(users.get(finalI).getEmail());
                        Userlayout.getChildren().remove(hBox);
                        users.remove(finalI);
                    });

                }


            }
        }


    }

    public void rechercheUser(KeyEvent keyEvent) {

        ServiceUser serviceUser = new ServiceUser();
        List<User> list = new ArrayList<>(serviceUser.rechercherUser(searchTextField.getText()));
        Userlayout.getChildren().clear();
        for (int i = 0; i < list.size(); i++) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/User/user_item.fxml"));
            HBox hBox;
            try {
                hBox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            UseritemController useritem = fxmlLoader.getController();
            useritem.setData(list.get(i));
            HBox.setMargin(hBox, new Insets(70, 0, 0, 0));
            Userlayout.getChildren().add(hBox);

        }
    }

    public void rechercheMunicipalite(KeyEvent keyEvent) {
        ServiceMunicipalite serviceMunicipalite = new ServiceMunicipalite();
        List<Municipalite> list = new ArrayList<>(serviceMunicipalite.rechercherMunicipalite(searchTextField.getText()));
        Userlayout.getChildren().clear();
        for (int i = 0; i < list.size(); i++) {

            loadMuni(list.get(i));
            showUserMuni(i);
        }
    }
}
