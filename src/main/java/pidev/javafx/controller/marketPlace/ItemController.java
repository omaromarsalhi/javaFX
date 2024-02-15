package pidev.javafx.controller.marketPlace;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import pidev.javafx.tools.CustomMouseEvent;
import pidev.javafx.tools.EventBus;
import pidev.javafx.tools.MyListener;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label stateLabel;
    @FXML
    private Label categoryLable;
    @FXML
    private Label priceLable;
    @FXML
    private ImageView img;
    @FXML
    private HBox itemInfo;
    @FXML
    private AnchorPane anchorPaneItem;
    @FXML
    private VBox bascInfoItems;
    @FXML
    private VBox vboxItem;


    private Bien bien;
    private MyListener myListener;
    private HBox hbox;
    private int imageIndex;



//    private static Timeline fiveSecondsWonder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        fiveSecondsWonder=new Timeline();
    }

//    public static void stopTimeLine() {
//        fiveSecondsWonder.stop();
//    }
//
//    public static void startTimeLine() {
//        TranslateTransition translateTransition=new TranslateTransition( Duration.seconds( 0.6 ), img);
//
//        translateTransition.setByX( -200 );
//        animateImagesKeyFrame= new KeyFrame(Duration.seconds(5), event -> {
//            System.out.println("This is called every 5 seconds on the UI thread");
//            translateTransition.setByX( -200 );
//            translateTransition.play();
//            translateTransition.setOnFinished( event1 -> {
//                img.setImage(new Image("file:src/main/resources"+bien.getImageSourceByIndex(imageIndex++)));
//                translateTransition.setByX(200);
//                translateTransition.play();
//                translateTransition.setOnFinished( null );
//                if(bien.getAllImagesSources().size()==imageIndex)
//                    imageIndex=0;
//            } );
//        } );
//        fiveSecondsWonder.getKeyFrames().add(animateImagesKeyFrame);
//        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
//        fiveSecondsWonder.play();
//    }

    public void setData(Bien bien, MyListener myListener) {
        this.imageIndex=1;
        this.bien = bien;
        this.myListener = myListener;
        nameLabel.setText(bien.getName());
        priceLable.setText( "$"+bien.getPrice());
        stateLabel.setText((bien.getState())?"In Stock":"Out Of Stock");
        categoryLable.setText(bien.getCategorie().name());
        Image image = new Image("file:src/main/resources"+bien.getImgSource());
        img.setImage(image);
        hbox=createItemsBtns();
    }
    public void setData(Bien bien) {
        nameLabel.setText(bien.getName());
        priceLable.setText( "$"+bien.getPrice());
        stateLabel.setText((bien.getState())?"In Stock":"Out Of Stock");
        categoryLable.setText(bien.getCategorie().name());
        Image image = new Image("file:src/main/resources"+bien.getImgSource());
        img.setImage(image);
    }

    public void animateImages(Timeline fiveSecondsWonder,Bien bien) {
        if(bien.getAllImagesSources().size()>1) {
            TranslateTransition translateTransition = new TranslateTransition( Duration.seconds( 0.4 ), img );
            FadeTransition fadeTransition = new FadeTransition( Duration.seconds( 0.15 ), img );
            KeyFrame animateImagesKeyFrame = new KeyFrame( Duration.seconds( 5 ), event -> {
                fadeTransition.setDelay( Duration.seconds( 0 ) );
                translateTransition.setByX( -100 );
                fadeTransition.setFromValue( 1 );
                fadeTransition.setToValue( 0 );
                fadeTransition.play();
                translateTransition.play();

                translateTransition.setOnFinished( event1 -> {
                    if (bien.getAllImagesSources().size() == (++imageIndex))
                        imageIndex = 0;
                    img.setImage( new Image( "file:src/main/resources" + bien.getImageSourceByIndex( imageIndex ) ) );
                    translateTransition.setByX( 100 );
                    fadeTransition.setFromValue( 0 );
                    fadeTransition.setDelay( Duration.seconds( 0.2 ) );
                    fadeTransition.setToValue( 1 );
                    translateTransition.play();
                    fadeTransition.play();

                    translateTransition.setOnFinished( null );
                } );

            } );
            fiveSecondsWonder.getKeyFrames().add( animateImagesKeyFrame );
            fiveSecondsWonder.setCycleCount( Timeline.INDEFINITE );
            fiveSecondsWonder.play();
        }
    }


    public void showTransitionInfo(Boolean state){
        if(state){
            vboxItem.getChildren().remove( hbox );
            vboxItem.getChildren().add(hbox);
            vboxItem.setMargin(hbox,new Insets( 0,5,5,5 ) );
        }
        else
            vboxItem.getChildren().remove( hbox );
    }


    public HBox createItemsBtns(){
        Button add2Card= new Button();
        Button trade = new Button();
        Button info= new Button();

        HBox hbox=new HBox();

        trade.setPrefWidth( 50 );
        info.setPrefWidth( 50 );
        add2Card.setPrefWidth( 50 );

        trade.setPrefHeight( 32 );
        info.setPrefHeight( 32 );
        add2Card.setPrefHeight( 32 );

        Image img1= new Image(String.valueOf( getClass().getResource("/namedIcons/buy.png") ));
        Image img2= new Image(String.valueOf( getClass().getResource("/namedIcons/exchange.png")));
        Image img3= new Image(String.valueOf( getClass().getResource("/namedIcons/interface.png")));

        add2Card.setGraphic( new ImageView( img1 ));
        trade.setGraphic( new ImageView( img2 ));
        info.setGraphic( new ImageView( img3 ));


        add2Card.setOnMouseClicked( event -> {
            CustomMouseEvent<Product> customEvent = new CustomMouseEvent<>(bien);
            EventBus.getInstance().publish( "laodCheckOut",customEvent);
        });
        info.setOnMouseClicked( event -> myListener.onClickListener( bien ) );

        hbox.getChildren().addAll( add2Card,trade,info );
        hbox.setSpacing( 10 );
        hbox.setAlignment(Pos.CENTER);
        hbox.setId( "itemInfo" );
        hbox.getStylesheets().add( String.valueOf( getClass().getResource("/style/Buttons.css") ) );
        return hbox;
    }


}
