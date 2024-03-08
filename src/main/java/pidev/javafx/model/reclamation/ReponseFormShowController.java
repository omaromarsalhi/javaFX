package pidev.javafx.model.reclamation;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import pidev.javafx.controller.reclamation.CallPythonFromJava;
import pidev.javafx.crud.reclamation.ServiceReponse;
import pidev.javafx.model.reclamation.Reclamation;
import pidev.javafx.model.reclamation.Reponse;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.marketPlace.MyTools;

import java.net.URL;
import java.util.ResourceBundle;

public class ReponseFormShowController implements Initializable {


    @FXML
    private VBox Box1;

    @FXML
    private TextArea Pdescretion1;

    @FXML
    private TextField Pname1;

    @FXML
    private TextField date1;

    @FXML
    private ImageView img;

    @FXML
    private TextArea reponsetext;

    @FXML
    private TextField privatekey1;
    @FXML
    private VBox Box12;


    private HBox buttonsBox;
    private boolean isAllInpulValid;
    String formLayoutBeforRegexCheck;
    String formLayoutAfterRegexCheck;
    Reponse rec = new Reponse();
    Reclamation recR = new Reclamation();


    //private Predictor<String, Classifications> predictor;


    //    public void analyzeText() {
//        String text = Pdescretion1.getText();
//        Classifications classifications;
//        try {
//            classifications = predictor.predict(text);
//            System.out.println("The sentiment of the text is: " + classifications);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        EventBus.getInstance().subscribe("senddata",this::first_fonction);
        setRegEx();
    }
    private void setRegEx() {
        Popup popup4Regex = MyTools.getInstance().createPopUp();

        reponsetext.setOnKeyTyped(event -> {
            isAllInpulValid = reponsetext.getText().matches("^[A-Za-z0-9 ]*$");
            String color = (isAllInpulValid) ? "green" : "red";
            if (reponsetext.getText().isEmpty()) {
                reponsetext.setStyle("");
                Box12.setStyle(formLayoutBeforRegexCheck);
                isAllInpulValid = true;
            } else {
                reponsetext.setStyle("-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0");
                Box12.setStyle("-fx-border-color:" + color + ";" +
                        formLayoutAfterRegexCheck);
            }
            if ((!isAllInpulValid && !reponsetext.getText().isEmpty()))
                Box12.setStyle("-fx-border-color:red;" +
                        formLayoutAfterRegexCheck);
        });

        reponsetext.setOnMouseEntered(event -> {
            if (!isAllInpulValid && !reponsetext.getText().isEmpty()) {
                ((Label) popup4Regex.getContent().get(0)).setText("ONLY CHARACTERS AND NUMBERS ARE ALLOWED");
                popup4Regex.getContent().get(0).setStyle(popup4Regex.getContent().get(0).getStyle() + "-fx-background-color: #ed1c27;");
                popup4Regex.show(Stage.getWindows().get(0), event.getScreenX() + 40, event.getScreenY() - 40);
            }
        });

        reponsetext.setOnMouseExited(event -> {
//            if(isAllInpulValid[3])
            popup4Regex.hide();
        });
    }
    public void createFormBtns(){
        Button addProd1= new Button();
        Button clearProd = new Button();
        Button cancel= new Button();
        Button voice= new Button();

        buttonsBox =new HBox();

        addProd1.setPrefWidth( 50 );
        clearProd.setPrefWidth( 50 );
        cancel.setPrefWidth( 50 );
        voice.setPrefWidth( 50 );

        addProd1.setPrefHeight( 32 );
        clearProd.setPrefHeight( 32 );
        cancel.setPrefHeight( 32 );
        voice.setPrefHeight( 32 );

        Image  img1= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/tab2.png" ) ));
        Image img2= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/broom.png" )));
        Image img3= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/paper.png" )));
        Image img4= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/update16C.png" )));

        addProd1.setGraphic( new ImageView( img1 ));
        clearProd.setGraphic( new ImageView( img2 ));
        cancel.setGraphic( new ImageView( img3 ));
        voice.setGraphic( new ImageView( img4 ));

        addProd1.setOnMouseClicked( this::onAddClicked);
        voice.setOnMouseClicked(this::voice);

        cancel.setOnMouseClicked( event -> EventBus.getInstance().publish( "exitFormUser",event ) );

        buttonsBox.getChildren().addAll( addProd1,clearProd,cancel,voice );
        buttonsBox.setSpacing( 20 );
        buttonsBox.setAlignment( Pos.CENTER);
        buttonsBox.setId( "itemInfo" );
        buttonsBox.getStylesheets().add( String.valueOf( getClass().getResource( "/style/marketPlace/Buttons.css" ) ) );
        buttonsBox.setPadding( new Insets( 0,0,10,0 ) );
    }

    public void onAddClicked(MouseEvent event) {
        Reponse reponse =new Reponse(
                0,
                recR.getIdReclamation(),
                reponsetext.getText()
        );
        ServiceReponse.getInstance().ajouter(reponse);
    }
    public void voice(MouseEvent event) {
        String pythonOutput = CallPythonFromJava.run();
        System.out.println(pythonOutput);
        if (!"Sorry, I did not get that".equals(pythonOutput)) {
            reponsetext.setText(pythonOutput);
        }
    }

    void first_fonction( CustomMouseEvent<Reclamation> event ) {
        recR =event.getEventData();
        System.out.println(recR.getIdReclamation());
        img.setImage( new Image( "file:src/main/resources"+recR.getImagePath(),70,70,true,true) );
        privatekey1.setText(recR.getPrivateKey());
        date1.setText(recR.getDate());
        Pname1.setText(recR.getSubject());
        Pdescretion1.setText(recR.getDescription());

    }

}
