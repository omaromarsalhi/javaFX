package pidev.javafx.controller.user;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pidev.javafx.crud.user.ServiceUser;
import pidev.javafx.model.MarketPlace.Product;
import pidev.javafx.model.user.User;
import pidev.javafx.test.Main;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.marketPlace.MyListener;
import pidev.javafx.tools.marketPlace.MyTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {

    @FXML
    private VBox itemInfo;
    @FXML
    private TextField adresse;
    @FXML
    private TextField age;
    @FXML
    private TextField cin;
    @FXML
    private TextField dateOfBirth;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dob;

    @FXML
    private HBox formBox;
    @FXML
    private HBox formBox1;
    @FXML
    private HBox formBox11;
    @FXML
    private HBox formBox12;
    @FXML
    private HBox formBox2;
    @FXML
    private HBox formBox21;
    @FXML
    private HBox formBox3;
    @FXML
    private HBox formBox31;
    @FXML
    private HBox formBox311;
    @FXML
    private HBox formBox32;
    @FXML
    private TextField gender;
    @FXML
    private ImageView img;
    @FXML
    private TextField lastName;
    @FXML
    private AnchorPane loadinPage;
    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextField status;
    private boolean modifier = false;


    private File chosenFile;
    private List<File> chosenFiles;
    private MyListener listener;
    private static String usageOfThisForm;
    private HBox buttonsBox;
    private boolean isImageUpdated;
    private Product product;
    private String formLayoutBeforRegexCheck;
    private String formLayoutAfterRegexCheck;
    Popup popup4Regex = MyTools.getInstance().createPopUp();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadinPage.setVisible(false);

        formLayoutBeforRegexCheck="-fx-border-color:black;"+"-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-color: white;" +
                "-fx-background-radius: 10";

        formLayoutAfterRegexCheck="-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-color: white;" +
                "-fx-background-radius: 10";
    }

    public void setUsageOfThisForm(String usage) {
        usageOfThisForm = usage;
        createFormBtns();
        if (!usage.equals("editDetails")) {
            name.setEditable(false);
            lastName.setEditable(false);
            age.setEditable(false);
            cin.setEditable(false);
            email.setEditable(false);
            gender.setEditable(false);
            adresse.setEditable(false);
            phone.setEditable(false);
            status.setEditable(false);
        }
        else
            setRegEx();

    }


    public void createFormBtns() {
        Button updateUser = new Button();
        Button clearProd = new Button();
        Button cancel = new Button();


        if (usageOfThisForm.equals("editDetails")) {
            updateUser.setPrefWidth(50);
            clearProd.setPrefWidth(50);
            updateUser.setPrefHeight(32);
            clearProd.setPrefHeight(32);

            Image img1 = new Image(String.valueOf(getClass().getResource("/icons/marketPlace/tab2.png")));
            Image img2 = new Image(String.valueOf(getClass().getResource("/icons/marketPlace/broom.png")));

            updateUser.setGraphic(new ImageView(img1));
            clearProd.setGraphic(new ImageView(img2));

            clearProd.setOnMouseClicked(event -> {
                name.clear();
                lastName.clear();
                age.clear();
                cin.clear();
                email.clear();
                gender.clear();
                adresse.clear();
                phone.clear();
            });


            updateUser.setOnMouseClicked(event -> {
                modifierData();

                Task<Void> task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        Platform.runLater(() -> {
                            formBox.setVisible(false);
                            loadinPage.setOpacity(0.4);
                            loadinPage.setVisible(true);
                        });
                        Thread.sleep(3000);
                        return null;
                    }
                };

                task.setOnSucceeded(workerStateEvent -> {
                    Platform.runLater(() -> {
                        EventBus.getInstance().publish("exitFormUser", event);
                    });
                });
                new Thread(task).start();

            });

        }


        cancel.setPrefWidth(50);

        cancel.setPrefHeight(32);

        Image img3 = new Image(String.valueOf(getClass().getResource("/icons/marketPlace/paper.png")));

        cancel.setGraphic(new ImageView(img3));

        cancel.setOnMouseClicked(event -> EventBus.getInstance().publish("exitFormUser", event));

        if (usageOfThisForm.equals("editDetails"))
            itemInfo.getChildren().addAll(updateUser, clearProd, cancel);
        else
            itemInfo.getChildren().addAll(cancel);

        itemInfo.setSpacing(30);
        itemInfo.setAlignment(Pos.CENTER);
        itemInfo.getStylesheets().add(String.valueOf(getClass().getResource("/style/marketPlace/Buttons.css")));
        itemInfo.setPadding(new Insets(0, 10, 0, 0));
    }


    public void setDataUser(User user) {
        System.out.println(user);
        name.setText(user.getFirstname());
        lastName.setText(user.getLastname());
        age.setText(String.valueOf(user.getAge()));
        cin.setText(user.getCin());
        email.setText(user.getEmail());
        gender.setText(user.getGender());
        adresse.setText(user.getAdresse());
        status.setText(user.getStatus());
        phone.setText(String.valueOf(user.getNum()));
       // dob.setValue(LocalDate.parse(user.getDob()));
        img.setImage(new Image("file:src/main/resources/" + user.getPhotos()));

    }

    public void modifierData() {
        User user=new User();
        user.setPhotos(img.getImage().getUrl());
//        System.out.println(user.getPhotos());

        System.out.println(user.getPhotos());

            user.setFirstname(name.getText());
            user.setEmail(email.getText());
            user.setLastname(lastName.getText());
            user.setAge(Integer.parseInt(age.getText()));
            user.setCin(cin.getText());
            user.setDob(String.valueOf(dob.getValue()));
            user.setNum(Integer.parseInt(phone.getText()));
            user.setStatus(status.getText());
            user.setPhotos(MyTools.getInstance().getPathAndSaveIMGUser(img.getImage().getUrl()));
            user.setGender(gender.getText());
            ServiceUser service = new ServiceUser();
            service.modifier(user);

    }

    public void setRegEx() {
       // String agereel;
        String regexAge = "^(?:1[8-9]|[2-9][0-9])$";
        String regexLastName = "[a-zA-Z\\s]{3,}+";
        String regexTelephone = "^[0-9]{8,8}$";
        String regexCIN = "^[0-9]{8}$";
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        String aage = "Valide les ages entre 18 et 99 et il faut l age correspond a la dob ";
        String emaill="Doit être au format standard, par exemple \"utilisateur@example.com";
        String tel = "Valide un numéro de téléphone composé exactement de 8 chiffres.";
        String chaine = "Accepte uniquement les chaines composés d'au moins 3 caractères alphabétiques.";
        String cinn = "Valide un numéro de carte d'identité nationale composé exactement de 8 chiffres.";
        name.setOnKeyTyped(event -> {
            if(name.getText().isEmpty()){
                System.out.println(name.getText());
                formBox.setStyle(formLayoutBeforRegexCheck);
            }

                else
                {
                    if(name.getText().matches(regexLastName))
                    {

                        formBox.setStyle(formLayoutAfterRegexCheck);
                        name.setStyle( "-fx-border-color: transparent transparent  green transparent;"+
                                "-fx-border-width:0 0 2 0;" +
                                "-fx-border-radius: 0" );
                        modifier=false;


                    }
                    else
                    {

                        formBox.setStyle(formLayoutAfterRegexCheck);
                        name.setStyle( "-fx-border-color: transparent transparent  red transparent;"+
                                "-fx-border-width:0 0 2 0;" +
                                "-fx-border-radius: 0" );
                    }
                }


        });
        lastName.setOnKeyTyped(event -> {
            if(lastName.getText().isEmpty()){

                formBox.setStyle(formLayoutBeforRegexCheck);
            }
            else
            {
                if(lastName.getText().matches(regexLastName))
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    lastName.setStyle( "-fx-border-color: transparent transparent  green transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );

                }
                else
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    lastName.setStyle( "-fx-border-color: transparent transparent  red transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );
                    modifier=false;
                }
            }
        });
        email.setOnKeyTyped(event -> {
            if(email.getText().isEmpty()){

                formBox.setStyle(formLayoutBeforRegexCheck);
            }
            else
            {
                if(email.getText().matches(regexLastName))
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    email.setStyle( "-fx-border-color: transparent transparent  green transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );

                }
                else
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    email.setStyle( "-fx-border-color: transparent transparent  red transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );
                    modifier=false;
                }
            }
        });
        adresse.setOnKeyTyped(event -> {
            if(adresse.getText().isEmpty()){

                formBox.setStyle(formLayoutBeforRegexCheck);
            }
            else
            {
                if(adresse.getText().matches(regexLastName))
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    adresse.setStyle( "-fx-border-color: transparent transparent  green transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );

                }
                else
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    adresse.setStyle( "-fx-border-color: transparent transparent  red transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );
                    modifier=false;
                }
            }
        });
        age.setOnKeyTyped(event -> {
            if(age.getText().isEmpty()){

                formBox.setStyle(formLayoutBeforRegexCheck);
            }
            else
            {
                if(age.getText().matches(regexAge))
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    age.setStyle( "-fx-border-color: transparent transparent  green transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );

                }
                else
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    age.setStyle( "-fx-border-color: transparent transparent  red transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );
                    modifier=false;
                }
            }
        });
        cin.setOnKeyTyped(event -> {
            if(cin.getText().isEmpty()){

                formBox.setStyle(formLayoutBeforRegexCheck);
            }
            else
            {
                if(cin.getText().matches(regexCIN))
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    cin.setStyle( "-fx-border-color: transparent transparent  green transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );

                }
                else
                {
                    cin.setStyle(formLayoutAfterRegexCheck);
                    cin.setStyle( "-fx-border-color: transparent transparent  red transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );
                    modifier=false;
                }
            }
        });
        phone.setOnKeyTyped(event -> {
            if(phone.getText().isEmpty()){

                formBox.setStyle(formLayoutBeforRegexCheck);
            }
            else
            {
                if(phone.getText().matches(regexTelephone))
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    phone.setStyle( "-fx-border-color: transparent transparent  green transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );

                }
                else
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    phone.setStyle( "-fx-border-color: transparent transparent  red transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );
                    modifier=false;
                }
            }
        });
        status.setOnKeyTyped(event -> {
            if(status.getText().isEmpty()){

                formBox.setStyle(formLayoutBeforRegexCheck);
            }
            else
            {
                if(status.getText().matches(regexLastName))
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    status.setStyle( "-fx-border-color: transparent transparent  green transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );

                }
                else
                {
                    formBox.setStyle(formLayoutAfterRegexCheck);
                    status.setStyle( "-fx-border-color: transparent transparent  red transparent;"+
                            "-fx-border-width:0 0 2 0;" +
                            "-fx-border-radius: 0" );
                    modifier=false;
                }
            }
        });
        gender.setOnKeyTyped(event -> {

        });
        name.setOnMouseEntered( event -> {
            if(name.getText()!=null&&!name.getText().isEmpty()&&!name.getText().matches(regexLastName)){
                ((Label)popup4Regex.getContent().get( 0 )).setText(chaine);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }
        } );
        name.setOnMouseExited( event -> {
//            if(isAllInpulValid[3])
            popup4Regex.hide();
        } );
        lastName.setOnMouseEntered( event -> {
            if(lastName.getText()!=null&&!lastName.getText().isEmpty()&&!lastName.getText().matches(regexLastName)){
                ((Label)popup4Regex.getContent().get( 0 )).setText(chaine);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }

        } );
        lastName.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );
        age.setOnMouseEntered( event -> {
            if(phone.getText()!=null&&!age.getText().matches(regexAge)&&!age.getText().isEmpty()){
                ((Label)popup4Regex.getContent().get( 0 )).setText(aage);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }

        } );
        age.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );
        cin.setOnMouseEntered( event -> {
            if(cin.getText()!=null&&!cin.getText().isEmpty()&&!cin.getText().matches(regexCIN)){
                ((Label)popup4Regex.getContent().get( 0 )).setText(cinn);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }

        } );
        cin.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );
        status.setOnMouseEntered( event -> {
            if(status.getText()!=null&&!status.getText().isEmpty()&&!status.getText().matches(regexLastName)){
                ((Label)popup4Regex.getContent().get( 0 )).setText(chaine);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }

        } );
        status.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );
        phone.setOnMouseEntered( event -> {
            if(phone.getText()!=null&&!phone.getText().isEmpty()&&!phone.getText().matches(regexTelephone)){
                ((Label)popup4Regex.getContent().get( 0 )).setText(tel);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }

        } );
        phone.setOnMouseExited( event -> {

            popup4Regex.hide();
        } );
        gender.setOnMouseEntered( event -> {
            if(gender.getText()!=null&&!gender.getText().isEmpty()&&!gender.getText().matches(regexLastName)){
                ((Label)popup4Regex.getContent().get( 0 )).setText("ONLY CHARACTERS AND NUMBERS ARE ALLOWED");
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }

        } );
        gender.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );
        email.setOnMouseEntered( event -> {
            if(email.getText()!=null&&!email.getText().isEmpty()&&!email.getText().isEmpty()&&!email.getText().matches(emailRegex)){
                ((Label)popup4Regex.getContent().get( 0 )).setText(emaill);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }

        } );
        email.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );
        lastName.setOnMouseEntered( event -> {
            if(lastName.getText()!=null&&!lastName.getText().isEmpty()&&!lastName.getText().isEmpty()&&!lastName.getText().matches(regexLastName)){
                ((Label)popup4Regex.getContent().get( 0 )).setText(chaine);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }
        } );
        lastName.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );
        adresse.setOnMouseEntered( event -> {
            if(adresse.getText()!=null&&!adresse.getText().isEmpty()&&!adresse.getText().matches(regexLastName)){
                ((Label)popup4Regex.getContent().get( 0 )).setText(chaine);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }

        } );
        adresse.setOnMouseExited( event -> {
            popup4Regex.hide();
        } );

    }
    @FXML
    public void imageDragDropped(DragEvent dragEvent) {

//        Dragboard dragboard=dragEvent.getDragboard();
//        if(dragboard.hasImage()||dragboard.hasFiles()) {
//            try {
//                img.setImage(new Image(new FileInputStream(dragboard.getFiles().get(0))));
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
        for (File file : dragEvent.getDragboard().getFiles()){
            img.setImage( new Image( file.getAbsolutePath() ) );
            System.out.println(img.getImage().getUrl());

        }
    }

@FXML
    public void imageDragOver(DragEvent dragEvent) {
        System.out.println("heloo");
        Dragboard dragboard=dragEvent.getDragboard();


        if(dragboard.hasImage() ||dragboard.hasFiles() ){
            dragEvent.acceptTransferModes(TransferMode.COPY);
        }
        dragEvent.consume();

    }
}





