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
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.marketPlace.MyListener;
import pidev.javafx.tools.marketPlace.MyTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
    private static String usageOfThisForm;
    private boolean isImageUpdated;
    private Product product;
    private String formLayoutBeforRegexCheck;
    private String formLayoutAfterRegexCheck;
    Popup popup4Regex = MyTools.getInstance().createPopUp();
    private boolean[] isAllInpulValid;
    private User user;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadinPage.setVisible(false);
        isImageUpdated=false;

        formLayoutBeforRegexCheck="-fx-border-color:black;"+"-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-color: white;" +
                "-fx-background-radius: 10";

        formLayoutAfterRegexCheck="-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-color: white;" +
                "-fx-background-radius: 10";

        isAllInpulValid=new boolean[]{false,false,false,false,false,false,false};
        adresse.setEditable(false);
        email.setEditable(false);

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
        Button dele = new Button();


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
                if(modifierData()){
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
                        MyTools.getInstance().getTextNotif().setText( "User Has Been Modified Successfully" );
                        MyTools.getInstance().showNotif();
                    });
                });
                new Thread(task).start();
                }
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
        itemInfo.setAlignment(Pos.CENTER_RIGHT);
        itemInfo.getStylesheets().add(String.valueOf(getClass().getResource("/style/marketPlace/Buttons.css")));
        itemInfo.setPadding(new Insets(0, 10, 0, 0));
    }


    public void setDataUser(User user) {
        name.setText(user.getFirstname());
        if(!user.getLastname().isEmpty())
        lastName.setText(user.getLastname());
        if(user.getAge()!=0)
        age.setText(String.valueOf(user.getAge()));
        if(!user.getCin().isEmpty())
        cin.setText(user.getCin());
        email.setText(user.getEmail());
        if(!user.getGender().isEmpty())
        gender.setText(user.getGender());
        System.out.println(user.getAdresse());
        adresse.setText(user.getAdresse());
        if(!user.getStatus().isEmpty())
        status.setText(user.getStatus());
        if(user.getNum()!=0)
        phone.setText(String.valueOf(user.getNum()));
        if(user.getDob()!=null)
            dob.setValue( LocalDate.parse( user.getDob().formatted( DateTimeFormatter.ofPattern( "yyy/MM/DD" )) ) );
        img.setImage(new Image( "file:src/main/resources"+user.getPhotos()));
        UserController.setUser(user);

    }

    public boolean modifierData(){
        testChampsBeforRegex();
//        System.out.println("lastname"+isAllInpulValid[1]);
//        System.out.println("name"+isAllInpulValid[0]);
//        System.out.println("age"+isAllInpulValid[2]);
//        System.out.println("cin"+isAllInpulValid[3]);
//        System.out.println("phone"+isAllInpulValid[4]);
//        System.out.println("status"+isAllInpulValid[5]);
//        System.out.println("gender"+isAllInpulValid[6]);

        if(isAllInpulValid[0] && isAllInpulValid[1] && isAllInpulValid[2] && isAllInpulValid[3] && isAllInpulValid[4] && isAllInpulValid[5] && isAllInpulValid[6]  ) {
            User user = new User();
            System.out.println( "wa" );
            System.out.println(user);
            user.setFirstname(name.getText());
            user.setEmail(email.getText());
            user.setLastname(lastName.getText());
            user.setAge(Integer.parseInt(age.getText()));
            user.setCin(cin.getText());
            if (dob != null)
                user.setDob(String.valueOf(dob.getValue()));
            user.setNum(Integer.parseInt(phone.getText()));
            user.setStatus(status.getText());
            user.setPhotos(UserController.getInstance().getCurrentUser().getPhotos());
            user.setGender(gender.getText());
            ServiceUser service = new ServiceUser();
            service.modifier(user);
            UserController.setUser(user);
            return true;
        }
          return false;
    }

    public void setRegEx() {
        String agereel;


        name.setOnKeyTyped(event -> {
            isAllInpulValid[0]=name.getText().matches(regexLastName);
            String color=(isAllInpulValid[0])?"green":"red";
            System.out.println(isAllInpulValid[0]);
            if(name.getText().isEmpty()){
                formBox.setStyle(formLayoutBeforRegexCheck);
                name.setStyle("");
            }

            else
            {
                name.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0" );

                    formBox.setStyle(formLayoutAfterRegexCheck);

            }


        });


        lastName.setOnKeyTyped(event -> {
            isAllInpulValid[1]=lastName.getText().matches(regexLastName);
            String color=(isAllInpulValid[1])?"green":"red";
            System.out.println(isAllInpulValid[1]);
            if(lastName.getText().isEmpty()){
                formBox.setStyle(formLayoutBeforRegexCheck);
                lastName.setStyle("");
            }

            else
            {
                lastName.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0" );

                formBox.setStyle(formLayoutAfterRegexCheck);

            }

        });

        age.setOnKeyTyped(event -> {
            if(age.getText().matches(regexAge))
            isAllInpulValid[2]=true;
            String color=(isAllInpulValid[2])?"green":"red";
            System.out.println(isAllInpulValid[2]);
            if(age.getText().isEmpty()){
                formBox.setStyle(formLayoutBeforRegexCheck);
                age.setStyle("");
            }
            else
            {
                age.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0" );
                formBox.setStyle(formLayoutAfterRegexCheck);
            }

        });

        cin.setOnKeyTyped(event -> {
            isAllInpulValid[3]=cin.getText().matches(regexCIN);
            String color=(isAllInpulValid[3])?"green":"red";
            System.out.println(isAllInpulValid[3]);
            if(cin.getText().isEmpty()){
                formBox.setStyle(formLayoutBeforRegexCheck);
                cin.setStyle("");
            }

            else
            {
                cin.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0" );

                formBox.setStyle(formLayoutAfterRegexCheck);

            }

        });
        phone.setOnKeyTyped(event -> {
            isAllInpulValid[4]=phone.getText().matches(regexTelephone);
            String color=(isAllInpulValid[4])?"green":"red";
            System.out.println(isAllInpulValid[4]);
            if(phone.getText().isEmpty()){
                formBox.setStyle(formLayoutBeforRegexCheck);
                phone.setStyle("");
            }

            else
            {
                phone.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0" );

                formBox.setStyle(formLayoutAfterRegexCheck);

            }

        });
        status.setOnKeyTyped(event -> {

            isAllInpulValid[5]=status.getText().matches(regexLastName);
            String color=(isAllInpulValid[5])?"green":"red";
            System.out.println(isAllInpulValid[5]);
            if(status.getText().isEmpty()){
                formBox.setStyle(formLayoutBeforRegexCheck);
                status.setStyle("");
            }

            else
            {
                status.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0" );

                formBox.setStyle(formLayoutAfterRegexCheck);

            }

        });
        gender.setOnKeyTyped(event -> {
            isAllInpulValid[6]=gender.getText().matches(regexLastName);
            String color=(isAllInpulValid[6])?"green":"red";
            System.out.println(isAllInpulValid[6]);
            if(gender.getText().isEmpty()){
                formBox.setStyle(formLayoutBeforRegexCheck);
                gender.setStyle("");
            }

            else
            {
                gender.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0" );

                formBox.setStyle(formLayoutAfterRegexCheck);

            }

        });
        dob.setOnKeyTyped(event -> {

            System.out.println("lplkojjjh");
            Period period = Period.between(dob.getValue(), LocalDate.now());
            int agerel = period.getYears();
            System.out.println(agerel);

        });



        name.setOnMouseEntered( event -> {
            if(name.getText()!=null&&!name.getText().isEmpty()&&!name.getText().matches(regexLastName)){
                ((Label)popup4Regex.getContent().get( 0 )).setText(chaine);
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }
        } );
        name.setOnMouseExited( event -> {
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
                ((Label)popup4Regex.getContent().get( 0 )).setText(chaine);
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
    void testChampsBeforRegex()
    {
        System.out.println("hiuhgytfrdesaw");
        System.out.println(dob.getValue());
        if(!name.getText().isEmpty() && name.getText().matches(regexLastName))
        {
            System.out.println(name.getText());
           isAllInpulValid[0]=true;
        }

        if(!lastName.getText().isEmpty() && lastName.getText().matches(regexLastName))
        {

            isAllInpulValid[1]=true;
        }

        if(!age.getText().isEmpty() && age.getText().matches(regexAge))
        {

            isAllInpulValid[2]=true;
        }
        if(!cin.getText().isEmpty() && cin.getText().matches(regexCIN))
        {
            isAllInpulValid[3]=true;
        }
        if(!phone.getText().isEmpty() && phone.getText().matches(regexTelephone))
        {
            isAllInpulValid[4]=true;
        }
        if(!status.getText().isEmpty() && status.getText().matches(regexLastName))
        {
            isAllInpulValid[5]=true;
        }
        if(!gender.getText().isEmpty() && gender.getText().matches(regexLastName))
        {
            isAllInpulValid[6]=true;
        }


    }





    @FXML
    public void imageDragDropped(DragEvent dragEvent) {
        for (File file : dragEvent.getDragboard().getFiles()){
            img.setImage( new Image( file.getAbsolutePath() ) );
            UserController.getInstance().getCurrentUser().setPhotos( MyTools.getInstance().getPathAndSaveIMGUser(file.getAbsolutePath() ) );
        }
    }

    @FXML
    public void imageDragOver(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasImage() ||dragEvent.getDragboard().hasFiles() )
            dragEvent.acceptTransferModes(TransferMode.COPY);
        dragEvent.consume();
    }
}




