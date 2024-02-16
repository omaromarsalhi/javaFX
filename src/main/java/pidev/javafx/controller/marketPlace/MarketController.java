package pidev.javafx.controller.marketPlace;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import pidev.javafx.crud.marketplace.CrudBien;
import pidev.javafx.tools.CustomMouseEvent;
import pidev.javafx.tools.EventBus;
import pidev.javafx.tools.MyListener;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Product;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @FXML
    private Button searchBtn;
    @FXML
    private HBox searchHbox;
    @FXML
    private TextField searchTextField;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu filter;

    private VBox vBox;
    private VBox chatBox;
    private Timer animTimer;
    private Image image;
    private MyListener myListener;
    private MyListener MainWindowListener;
    private Timeline fiveSecondsWonder;
    private String searchBarState;
    private int idProd4nextSelection;
    private boolean isChatActivated;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fiveSecondsWonder=new Timeline();
        isChatActivated=false;
        vBox=null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/chat/chat.fxml"));
        try {
            VBox chatBox = fxmlLoader.load();
            animateChanges(hepfullBar,chatBox);
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        try {
            hepfullBar = FXMLLoader.load(getClass().getResource( "/fxml/marketPlace/helpfullBar.fxml" ));
            chatBox = FXMLLoader.load(getClass().getResource( "/fxml/chat/chat.fxml" ));
        } catch (IOException e) {
            e.printStackTrace();
        }


        showGridPane(CrudBien.getInstance().selectItems() );
        setMenueBar();

        ImageAnchorPane.setVisible( false );
        exitImageBtn.setOnAction( event -> {
            ImageAnchorPane.setVisible( false );
            grid.setOpacity( 1 );
        } );

        searchBarState="closed";
        animTimer = new Timer();
        searchTextField.setVisible( false );
        searchBtn.setStyle( "-fx-border-radius: 20;" +
                "-fx-background-radius:20;" );

        searchBtn.setOnMouseClicked(event -> animateSearchBar());

        EventBus.getInstance().subscribe( "loadChat",this::loadChat);
        EventBus.getInstance().subscribe( "filterProducts",this::onFilterClicked);
    }





    public void setMenueBar(){
        var allProducts=new MenuItem("All Products",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/more.png"))));
        var todayProducts=new MenuItem("Today's Products",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        allProducts.setOnAction( event -> {
            showGridPane(CrudBien.getInstance().selectItems() );
        } );
        todayProducts.setOnAction( event -> {
            showGridPane(CrudBien.getInstance().filterItems( LocalDate.now().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) ),"",-1,-1,-1,"" ));
        } );

        menuBar.getMenus().get( 0 ).getItems().addAll(allProducts,todayProducts);


        var allServices=new MenuItem("All Services",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        var todayServices=new MenuItem("Today's Services",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        menuBar.getMenus().get( 1 ).getItems().addAll(allServices ,todayServices);


        var filterProd=new MenuItem("Product",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        var filterService=new MenuItem("Service",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        menuBar.getMenus().get( 2 ).getItems().addAll(filterProd ,filterService);

        filterProd.setOnAction( event -> {
            EventBus.getInstance().publish( "filter",event );
//            if(isChatActivated) {
//                animateChanges( chatBox, hepfullBar );
//                isChatActivated=false;
//            }
        } );


    }

    public void animateSearchBar(){
        if(searchBarState.equals( "closed" )){
            searchBarState="opened";
            animTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(searchTextField.getWidth()==16){
                        searchBtn.setStyle( "-fx-border-radius: 0 20 20 0;" +
                                "-fx-border-color: black  black black transparent ;");
                        searchTextField.setVisible( true );
                    }
                    if (searchTextField.getWidth()<(searchHbox.getWidth()-searchBtn.getWidth()-20)) {
                        searchTextField.setPrefWidth(searchTextField.getWidth()+10);
                    } else
                        this.cancel();
                }

            }, 0, 15);
        }
        else if(searchBarState.equals( "opened" )&&searchTextField.getText().isEmpty()){
            searchBarState="closed";
            animTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (searchTextField.getWidth() <=16) {
                        searchBtn.setStyle( "-fx-border-radius: 20;" +
                                "-fx-background-radius:20;");
                        searchTextField.setVisible( false );
                    }
                    if (searchTextField.getWidth() > 16) {
                        searchTextField.setPrefWidth( searchTextField.getWidth() - 10 );
                    } else
                        this.cancel();
                }
            }, 0, 15);
        }
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


    public void onFilterClicked(CustomMouseEvent<ObservableList<Bien>> customMouseEvent){
        showGridPane(customMouseEvent.getEventData());
    }


    public void showGridPane(ObservableList<Bien> biens){
        grid.getChildren().clear();
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

            }
            grid.setPadding( new Insets( 0,0,40,0 ));
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
        isChatActivated=true;
        animateChanges(vBox,chatBox);
    }



}
