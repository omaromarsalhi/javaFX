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
import pidev.javafx.model.reclamation.Reclamation;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.EventBus;

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
    private TextField privatekey1;

    private HBox buttonsBox;
    private boolean isAllInpulValid;
    String formLayoutBeforRegexCheck;
    String formLayoutAfterRegexCheck;
    Reclamation rec = new Reclamation();



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

        cancel.setOnMouseClicked( event -> EventBus.getInstance().publish( "exitFormUser",event ) );

        buttonsBox.getChildren().addAll( addProd,clearProd,cancel );
        buttonsBox.setSpacing( 20 );
        buttonsBox.setAlignment( Pos.CENTER);
        buttonsBox.setId( "itemInfo" );
        buttonsBox.getStylesheets().add( String.valueOf( getClass().getResource( "/style/marketPlace/Buttons.css" ) ) );
        buttonsBox.setPadding( new Insets( 0,0,10,0 ) );
    }



    public void onAddClicked(MouseEvent event) {
            ServiceReclamation.getInstance().supprimer(rec.getIdReclamation());
    }
    public void showFormReclamationReponse(MouseEvent event) {
        EventBus.getInstance().publish("showReponse", event);
        System.out.println(rec);
        EventBus.getInstance().publish( "senddata", new CustomMouseEvent<Reclamation>(rec));

    }



    void first_fonction( CustomMouseEvent<Reclamation> event ) {
        rec =event.getEventData();
        img.setImage( new Image( "file:src/main/resources"+rec.getImagePath(),70,70,true,true) );
        //privatekey1.setText(rec.getPrivateKey());
        date1.setText(rec.getDate());
        Pname1.setText(rec.getSubject());
        Pdescretion1.setText(rec.getDescription());
//        generatePdf(rec);
    }


    public static void generatePdf(Reclamation rec) {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        // Set extension filter for PDF files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        // Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            Document document = new Document();
            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
// Read HTML file
                String html = new String(Files.readAllBytes(Paths.get("src/main/java/pidev/javafx/controller/reclamation/new-email.html")));
// Replace placeholders in HTML
                html = html.replace("KEY_VALUE", rec.getPrivateKey());
                html = html.replace("DATE_VALUE", rec.getDate());
                html = html.replace("NAME_VALUE", rec.getDescription());
                html = html.replace("DESCRIPTION_VALUE", rec.getDescription());

// Convert HTML to PDF
                XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(html.getBytes()));

                document.close();
            } catch (DocumentException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
