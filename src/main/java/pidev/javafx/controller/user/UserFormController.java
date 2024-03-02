package pidev.javafx.controller.user;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pidev.javafx.model.MarketPlace.Product;
import pidev.javafx.model.user.User;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.marketPlace.MyListener;

import java.io.File;
import java.net.URL;
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



    private File chosenFile;
    private List<File> chosenFiles;
    private MyListener listener;
    private static String usageOfThisForm;
    private HBox buttonsBox;
    private boolean isImageUpdated;
    private Product product;
    private boolean[] isAllInpulValid;
    String formLayoutBeforRegexCheck;
    String formLayoutAfterRegexCheck;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadinPage.setVisible( false );
//        itemInfo.getChildren().add( buttonsBox );
    }

    public void setUsageOfThisForm(String usage){
        usageOfThisForm=usage;
        createFormBtns();
        if(!usage.equals( "editDetails" )){
            name.setEditable( false );
            lastName.setEditable( false );
            age.setEditable( false );
            cin.setEditable( false );
            email.setEditable( false );
            gender.setEditable( false );
            adresse.setEditable( false );
            phone.setEditable( false );
        }
    }





    public void createFormBtns(){
        Button addProd= new Button();
        Button clearProd = new Button();

        Button cancel= new Button();

//        buttonsBox =new HBox();

        if(usageOfThisForm.equals( "editDetails" )) {
            addProd.setPrefWidth( 50 );
            clearProd.setPrefWidth( 50 );

            addProd.setPrefHeight( 32 );
            clearProd.setPrefHeight( 32 );

            Image img1= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/tab2.png" ) ));
            Image img2= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/broom.png" )));

            addProd.setGraphic( new ImageView( img1 ));
            clearProd.setGraphic( new ImageView( img2 ));

            clearProd.setOnMouseClicked( event -> {
                name.clear();
                lastName.clear();
                age.clear();
                cin.clear();
                email.clear();
                gender.clear();
                adresse.clear();
                phone.clear();
            } );
        }

        cancel.setPrefWidth( 50 );

        cancel.setPrefHeight( 32 );

        Image img3= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/paper.png" )));


        cancel.setGraphic( new ImageView( img3 ));

      //  addProd.setOnMouseClicked();
//        clearProd.setOnMouseClicked( event -> {
//
//        } );
        cancel.setOnMouseClicked( event -> EventBus.getInstance().publish( "exitFormUser",event ) );

        if(usageOfThisForm.equals( "editDetails" ))
            itemInfo.getChildren().addAll( addProd,clearProd,cancel );
        else
            itemInfo.getChildren().addAll( cancel );

        itemInfo.setSpacing( 30 );
        itemInfo.setAlignment( Pos.CENTER);
//        itemInfo.setId( "itemInfo" );
        itemInfo.getStylesheets().add( String.valueOf( getClass().getResource( "/style/marketPlace/Buttons.css" ) ) );
        itemInfo.setPadding( new Insets( 0,10,0,0 ) );
    }


   public void setDataUser(User user) {
        name.setText(user.getFirstname());
        lastName.setText(user.getLastname());
        age.setText( String.valueOf( user.getAge() ) );
        cin.setText(user.getCin());
        email.setText(user.getEmail());
        gender.setText(user.getGender());
        adresse.setText(user.getAdresse());
        phone.setText(user.getPhotos());
        img.setImage(new Image("file:src/main/resources/"+user.getImagePath()));

    }




}
