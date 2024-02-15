package pidev.javafx.controller.marketPlace;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import pidev.javafx.crud.CrudBien;
import pidev.javafx.tools.EventBus;
import pidev.javafx.tools.MyListener;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Product;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MarketController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    @FXML
    private HBox mainHbox;
    @FXML
    private VBox hepfullBar;
    @FXML
    private Button addBien;
    @FXML
    private Button exitBtn;
    @FXML
    private ImageView relativeImageVieur;
    @FXML
    private AnchorPane ImageAnchorPane;
    @FXML
    private Button leftArrow;
    @FXML
    private Button rightArrow;
    @FXML
    private Button exitImageBtn;

    private VBox vBox;
    private VBox chatBox;



    private List<Bien> biens = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    private MyListener MainWindowListener;
    private Timeline fiveSecondsWonder;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fiveSecondsWonder=new Timeline();
        vBox=null;
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(getClass().getResource("/fxml/chat/chat.fxml"));
//        try {
//            VBox chatBox = fxmlLoader.load();
//            animateChanges(hepfullBar,chatBox);
//        } catch (IOException e) {
//            throw new RuntimeException( e );
//        }
        try {
            hepfullBar = FXMLLoader.load(getClass().getResource( "/fxml/marketPlace/helpfullBar.fxml" ));
            chatBox = FXMLLoader.load(getClass().getResource( "/fxml/chat/chat.fxml" ));
        } catch (IOException e) {
            e.printStackTrace();
        }


        showGridPane();


        ImageAnchorPane.setVisible( false );
        exitImageBtn.setOnAction( event -> {
            ImageAnchorPane.setVisible( false );
            grid.setOpacity( 1 );
        } );

        EventBus.getInstance().subscribe( "loadChat",this::loadChat);
    }



    public void getProduct(AnchorPane item,ItemController itemController){
        item.hoverProperty().addListener( (observable, oldValue, show)->{
            itemController.showTransitionInfo( show );
        } );
    }



    public void animateChanges(Node node1, Node node2){
        FadeTransition fade1 = new FadeTransition( Duration.seconds( 0.4 ), node1);
        fade1.setFromValue( 1 );
        fade1.setToValue( 0 );
        FadeTransition fade2 = new FadeTransition( Duration.seconds( 0.4), node2 );
        fade2.setFromValue( 0 );
        fade2.setToValue( 0.99);

        fade1.play();
        fade1.setOnFinished(event ->{
            mainHbox.getChildren().remove( node1 );
            mainHbox.getChildren().add( node2 );
            fade2.play();
        });
    }



    public void showGridPane(){
        biens= CrudBien.getInstance().selectItems();
        if (biens.size() > 0) {
            try {
                hepfullBar = FXMLLoader.load(getClass().getResource( "/fxml/marketPlace/helpfullBar.fxml" ));
                mainHbox.getChildren().add(hepfullBar);
            } catch (IOException e) {
                throw new RuntimeException( e );
            }
            myListener = new MyListener<Product>() {
                @Override
                public void onClickListener(Product arg){
                    mainHbox.getChildren().remove(vBox);
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation( getClass().getResource( "/fxml/marketPlace/itemInfo.fxml" ) );
                        vBox = fxmlLoader.load();
                        ItemInfoController itemInfoController = fxmlLoader.getController();
                        myListener = new MyListener<Product>() {
                            @Override
                            public void exit() {
                                animateChanges(vBox,hepfullBar);
                            }
                        };
                        itemInfoController.setData( arg, myListener );
                        animateChanges(hepfullBar,vBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < biens.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/marketPlace/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(biens.get(i),myListener);
                itemController.setData(biens.get( i ));
                itemController.animateImages(fiveSecondsWonder,biens.get( i ));
                int finalI = i;
                anchorPane.setOnMouseClicked( event -> showRelativeImages(biens.get( finalI )) );
                getProduct(anchorPane,itemController);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.setHgap( 25 );
                grid.setVgap( 25 );

                grid.add(anchorPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
            }
            grid.setPrefHeight(670);
            grid.setPrefWidth(800);
            grid.setPadding( new Insets( -10,0,40,20 ));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRelativeImages(Bien bien){
        AtomicInteger imageIndex= new AtomicInteger(0);
        ImageAnchorPane.setVisible( true );
        grid.setOpacity( 0.2 );
        relativeImageVieur.setImage(new Image("file:src/main/resources"+ bien.getImageSourceByIndex( imageIndex.get() ) ) );
        rightArrow.setOnAction( event -> {
            imageIndex.getAndIncrement();
            if(imageIndex.get() >=bien.getAllImagesSources().size())
                imageIndex.set( 0 );
            relativeImageVieur.setImage( new Image( "file:src/main/resources"+bien.getImageSourceByIndex( imageIndex.get() ) ) );

        } );
        leftArrow.setOnAction( event -> {
            imageIndex.getAndDecrement();
            if(imageIndex.get() <=0)
                imageIndex.set( bien.getAllImagesSources().size() - 1 );
            relativeImageVieur.setImage( new Image("file:src/main/resources"+ bien.getImageSourceByIndex( imageIndex.get() ) ) );
        } );
    }


    public void setMainWindowListener(MyListener listener){
        MainWindowListener=listener;
    }

    public void loadChat(MouseEvent event){
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(getClass().getResource("/fxml/chat/chat.fxml"));
//        try {
//            VBox chatBox = fxmlLoader.load();
//            animateChanges(hepfullBar,chatBox);
//        } catch (IOException e) {
//            throw new RuntimeException( e );
//        }
//        ChatController chatController = fxmlLoader.getController();
        animateChanges(vBox,chatBox);
    }



}
