package pidev.javafx.controller.reclamation;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.crud.reclamation.ServiceReponse;
import pidev.javafx.model.reclamation.Reclamation;
import pidev.javafx.model.reclamation.Reponse;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.reclamation.ReclamationController;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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


    private HBox buttonsBox;
    private boolean isAllInpulValid;
    String formLayoutBeforRegexCheck;
    String formLayoutAfterRegexCheck;
    Reponse rec = new Reponse();
     Reclamation recR = new Reclamation();



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
    }

    public void createFormBtns(){
        Button addProd1= new Button();
        Button clearProd = new Button();
        Button cancel= new Button();

        buttonsBox =new HBox();

        addProd1.setPrefWidth( 50 );
        clearProd.setPrefWidth( 50 );
        cancel.setPrefWidth( 50 );

        addProd1.setPrefHeight( 32 );
        clearProd.setPrefHeight( 32 );
        cancel.setPrefHeight( 32 );

        Image  img1= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/tab2.png" ) ));
        Image img2= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/broom.png" )));
        Image img3= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/paper.png" )));

        addProd1.setGraphic( new ImageView( img1 ));
        clearProd.setGraphic( new ImageView( img2 ));
        cancel.setGraphic( new ImageView( img3 ));

        addProd1.setOnMouseClicked( this::onAddClicked);

        cancel.setOnMouseClicked( event -> EventBus.getInstance().publish( "exitFormUser",event ) );

        buttonsBox.getChildren().addAll( addProd1,clearProd,cancel );
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
