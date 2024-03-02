package pidev.javafx.controller.reclamation;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import pidev.javafx.crud.marketplace.CrudBien;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Categorie;
import pidev.javafx.model.MarketPlace.Product;
import pidev.javafx.model.reclamation.Reclamation;
import pidev.javafx.tools.marketPlace.ChatGPTAPIDescriber;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.marketPlace.MyListener;
import pidev.javafx.tools.marketPlace.MyTools;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class reclamationFormController implements Initializable {

    @FXML
    private VBox Box1;
    @FXML
    private TextArea Pdescretion;
    @FXML
    private TextField Pname;
    @FXML
    private HBox formBox;
    @FXML
    private Button imageBtn;

    @FXML    private AnchorPane loadingPage;




    private File chosenFile;
    private MyListener listener;
    private static String usageOfThisForm;
    private HBox buttonsBox;
    private boolean isImageUpdated;
    private Product product;
    private boolean isAllInpulValid;
    String formLayoutBeforRegexCheck;
    String formLayoutAfterRegexCheck;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadingPage.setVisible( false );



        formLayoutBeforRegexCheck="-fx-border-color:black;"+"-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-color: white;" +
                "-fx-background-radius: 10";

        formLayoutAfterRegexCheck="-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-color: white;" +
                "-fx-background-radius: 10";

        isAllInpulValid=false;

        createFormBtns();
        Box1.getChildren().add( buttonsBox );


        imageBtn.setOnAction( event -> {
            FileChooser fileChooser = new FileChooser();
            setExtFilters( fileChooser );
            fileChooser.setTitle( "Save Image" );
            chosenFile = fileChooser.showOpenDialog( Stage.getWindows().get( 0 ) );
        } );

        setRegEx();
    }


    private void setExtFilters(FileChooser chooser){
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }









    private void setRegEx(){
        Popup popup4Regex =MyTools.getInstance().createPopUp();

        Pname.setOnKeyTyped( event -> {
            isAllInpulValid=Pname.getText().matches("^[A-Za-z0-9 ]*$");
            String color=(isAllInpulValid)?"green":"red";
            if(Pname.getText().isEmpty()){
                Pname.setStyle( "");
                formBox.setStyle(formLayoutBeforRegexCheck);
                isAllInpulValid=true;
            }
            else {
                Pname.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0" );
                formBox.setStyle( "-fx-border-color:" + color + ";" +
                        formLayoutAfterRegexCheck);
            }
            if((!isAllInpulValid&&!Pname.getText().isEmpty()))
                formBox.setStyle( "-fx-border-color:red;"+
                        formLayoutAfterRegexCheck );
        });

        Pname.setOnMouseEntered( event -> {
            if(!isAllInpulValid&&!Pname.getText().isEmpty()){
                ((Label)popup4Regex.getContent().get( 0 )).setText("ONLY CHARACTERS AND NUMBERS ARE ALLOWED");
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }
        } );

        Pname.setOnMouseExited( event -> {
//            if(isAllInpulValid[3])
            popup4Regex.hide();
        } );
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = rnd.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    public void onAddClicked(MouseEvent event) {
        if(isAllInpulValid&&!Pdescretion.getText().isEmpty()) {
            Reclamation reclamation=new Reclamation(
                    0,
                    generateRandomString(20),
                    Pname.getText(),
                    "",
                    Pdescretion.getText(),
                    ""
            );

            if(chosenFile!=null)
                reclamation.setImagePath(MyTools.getInstance().getPathAndSaveIMG(chosenFile.getAbsolutePath()) );
            System.out.println(reclamation);
            ServiceReclamation.getInstance().ajouter(reclamation);

            Thread thread = sleepThread(event);
            loadingPage.setVisible( true );
            thread.start();

        }
        else{
            Alert confirmationAlert = new Alert( Alert.AlertType.ERROR );
            confirmationAlert.setTitle("Error");
            confirmationAlert.setHeaderText( null );
            confirmationAlert.setGraphic( null );
            confirmationAlert.getDialogPane().getStylesheets().add("file:src/main/resources/style/alertStyle.css");
            confirmationAlert.setContentText("There is some wrong Data please fix it !!!");
            confirmationAlert.show();
        }
    }


    private Thread sleepThread(MouseEvent event) {
        Task<Void> myTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                return null;
            }
        };

        myTask.setOnSucceeded(e -> {
            EventBus.getInstance().publish( "exitFormUser", event );
            loadingPage.setVisible( false );
        });
        return new Thread(myTask);
    }


    public void createFormBtns(){
        Button addProd= new Button();
        Button clearProd = new Button();
        Button cancel= new Button();

        buttonsBox =new HBox();

        addProd.setPrefWidth( 50 );
        clearProd.setPrefWidth( 50 );
        cancel.setPrefWidth( 50 );

        addProd.setPrefHeight( 32 );
        clearProd.setPrefHeight( 32 );
        cancel.setPrefHeight( 32 );

        Image  img1= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/tab2.png" ) ));
        Image img2= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/broom.png" )));
        Image img3= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/paper.png" )));

        addProd.setGraphic( new ImageView( img1 ));
        clearProd.setGraphic( new ImageView( img2 ));
        cancel.setGraphic( new ImageView( img3 ));

        addProd.setOnMouseClicked( this::onAddClicked);

        clearProd.setOnMouseClicked( event -> {
            Pdescretion.setText( "" );
            Pname.setText( "" );
        } );
        cancel.setOnMouseClicked( event -> EventBus.getInstance().publish( "exitFormUser",event ) );

        buttonsBox.getChildren().addAll( addProd,clearProd,cancel );
        buttonsBox.setSpacing( 20 );
        buttonsBox.setAlignment( Pos.CENTER);
        buttonsBox.setId( "itemInfo" );
        buttonsBox.getStylesheets().add( String.valueOf( getClass().getResource( "/style/marketPlace/Buttons.css" ) ) );
        buttonsBox.setPadding( new Insets( 0,0,10,0 ) );
    }


}
