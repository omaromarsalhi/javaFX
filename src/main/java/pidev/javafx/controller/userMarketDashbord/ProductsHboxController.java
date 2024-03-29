package pidev.javafx.controller.userMarketDashbord;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Product;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductsHboxController implements Initializable {
    @FXML
    private HBox actions;
    @FXML
    private Label category;
    @FXML
    private Label creationDate;
    @FXML
    private Label descreption;
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label quantity;
    @FXML
    private Label state;
    @FXML
    private Label id;
    @FXML
    private ImageView stateImage;

    private Bien bien;
    private  Popup popup = new Popup();
    private  Timeline fiveSecondsWonder = new Timeline();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    public void setData(Product product){
        if( product instanceof Bien bien) {
            this.bien=bien;
            id.setText( Integer.toString(bien.getId()) );
            id.setVisible( false );
            image.setImage( new Image( "file:src/main/resources/" + bien.getImageSourceByIndex( 0 ),40,40,false,false ) );
            name.setText( bien.getName() );
            descreption.setText( bien.getDescreption() );
            price.setText( bien.getPrice().toString() );
            quantity.setText( bien.getQuantity().toString() );
            state.setText( bien.getState().toString() );
            creationDate.setText( bien.getTimestamp().toString() );
            category.setText( bien.getCategorie().toString() );
            if(product.getState().equals( "verified" ))
                stateImage.setImage(new Image( "file:src/main/resources/icons/marketPlace/approve24C.png",24,24,true,true ) );
        }
    }



    @FXML
    private void onMouseEntered(MouseEvent event) {

        final VBox mainContainer=new VBox();
        mainContainer.setMinSize( 100,100 );
        mainContainer.setAlignment( Pos.CENTER );
        mainContainer.setStyle("-fx-background-color: #5f7470; -fx-border-color: #5f7470; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: white;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;");
        final ImageView imageView=new ImageView();
        imageView.setImage( new Image("file:src/main/resources/"+bien.getImageSourceByIndex( 0 ),90,90,true,true ) );
        mainContainer.getChildren().add( imageView );

        if(bien.getAllImagesSources().size()>1) {
            AtomicInteger indexOfImage= new AtomicInteger(1);
            fiveSecondsWonder.getKeyFrames().add( new KeyFrame( Duration.seconds( 0.8 ), event1 -> {
                imageView.setImage( new Image("file:src/main/resources/"+bien.getImageSourceByIndex( indexOfImage.get() ),90,90,true,true ) );
                indexOfImage.getAndIncrement();
                if(indexOfImage.get()>=bien.getAllImagesSources().size())
                    indexOfImage.set( 0 );
            } ) );
            fiveSecondsWonder.setCycleCount( Timeline.INDEFINITE );
            fiveSecondsWonder.play();
        }

        popup.getContent().clear();
        popup.getContent().add(mainContainer);
        popup.show( Stage.getWindows().get(0), event.getScreenX() + 20, event.getScreenY()-30);
    }


    @FXML
    private void onMouseExited(MouseEvent event) {
        fiveSecondsWonder.stop();
        popup.hide();
    }



    @FXML
    private void onStateMouseEntered(MouseEvent event) {
        Label mainContainer=new Label("WE ARE VERIFYING THIS PRODUCT SO THAT IF IT IS COMPATIBLE OTHERWISE IT WILL BE DELETED");
        mainContainer.setMaxWidth( 200);
        mainContainer.setWrapText( true );
        mainContainer.setAlignment( Pos.CENTER );
        mainContainer.setStyle("-fx-background-color: #5f7470; -fx-border-color: #5f7470; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: white;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;");

        popup.getContent().clear();
        popup.getContent().add(mainContainer);
        popup.show( Stage.getWindows().get(0), event.getScreenX() + 20, event.getScreenY()-30);
    }


    @FXML
    private void onStateMouseExited(MouseEvent event) {
        popup.hide();
    }


//    @FXML
//    public void onUpdateClicked(ActionEvent event){
//        CustomMouseEvent<Bien> customMouseEvent=new CustomMouseEvent<>(bien);
//        EventBus.getInstance().publish( "updateProd",customMouseEvent);
//    }



}
