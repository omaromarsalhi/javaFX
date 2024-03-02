package pidev.javafx.controller.marketPlace;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Product;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.CustomReturnItem;
import pidev.javafx.tools.marketPlace.EventBus;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;

public class MarketController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    @FXML
    private HBox mainHbox;
    @FXML
    private Button searchBtn;
    @FXML
    private HBox searchHbox;
    @FXML
    private TextField searchTextField;
    @FXML
    private MenuBar menuBar;
    @FXML
    private AnchorPane secondInterface;

    private VBox itemInfo;
    private VBox hepfullBar;
    private VBox chatBox;
    private Timer animTimer;
    private Image image;
    private String searchBarState;
    private int idProd4nextSelection;
    private String whoIsActiveNow;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        secondInterface.setVisible( false );

        itemInfo=null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/chat/chat.fxml"));
        try {
            chatBox = fxmlLoader.load();
            itemInfo = FXMLLoader.load(getClass().getResource( "/fxml/marketPlace/itemInfo.fxml" ));

        } catch (IOException e) {
            throw new RuntimeException( e );
        }

        whoIsActiveNow="hepfullBar";

        setMenueBar();

        searchBarState="closed";
        animTimer = new Timer();
        searchTextField.setVisible( false );
        searchBtn.setStyle( "-fx-border-radius: 20;" +
                "-fx-background-radius:20;" );

        searchBtn.setOnMouseClicked(event -> animateSearchBar());

        EventBus.getInstance().subscribe( "loadChat",this::loadChat);
        EventBus.getInstance().subscribe( "filterProducts",this::onFilterClicked);
        EventBus.getInstance().subscribe( "showAndSetItemInfo",this::loadAndSetItemInfo);
        EventBus.getInstance().subscribe( "exitItemInfo",this::exitItemInfo);

        loadingAllProductsThread(CrudBien.getInstance().selectItems()).start();
    }



    public Thread loadingAllProductsThread(ObservableList<Bien> prods){
        Task<Void> myTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                showGridPane(prods);
                return null;
            }
        };
        return new Thread(myTask);
    }

    public void setMenueBar(){
        var allProducts=new MenuItem("All Products",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/more.png" ))));
        var todayProducts=new MenuItem("Today's Products",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/database.png" ))));
        allProducts.setOnAction( event -> {
            loadingAllProductsThread(CrudBien.getInstance().selectItems()).start();
        } );
        todayProducts.setOnAction( event -> {
            loadingAllProductsThread(CrudBien.getInstance().filterItems( LocalDate.now().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) ),"",-1,-1,-1,"" )).start();
        } );
        menuBar.getMenus().get( 0 ).getItems().addAll(allProducts,todayProducts);




        var filterProd=new MenuItem("Product",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/database.png" ))));
        var filterService=new MenuItem("Service",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/database.png" ))));
        menuBar.getMenus().get( 1 ).getItems().addAll(filterProd ,filterService);


        filterProd.setOnAction( event -> {

            mainHbox.getChildren().add(hepfullBar);
            animateChanges( itemInfo, hepfullBar );

            EventBus.getInstance().publish( "filter",event );
            whoIsActiveNow="hepfullBar";
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
        loadingAllProductsThread(customMouseEvent.getEventData()).start();
    }


    public void loadAndSetItemInfo(CustomMouseEvent<Product> customMouseEvent){
        EventBus.getInstance().publish( "setItemInfoData", customMouseEvent);
        mainHbox.setOpacity( 0.4 );
        secondInterface.setVisible( true );
        ((HBox)secondInterface.getChildren().get( 0 )).getChildren().add(itemInfo );
//        MyTools.getInstance().showAnimation( itemInfo );
    }


    public void exitItemInfo(MouseEvent event){
        ((HBox)secondInterface.getChildren().get( 0 )).getChildren().clear();
        mainHbox.setOpacity( 1 );
        secondInterface.setVisible( false );
    }




//    public void showGridPane(ObservableList<Bien> biens){
//        grid.getChildren().clear();
//        int column = 0;
//        int row = 1;
//        for (int i = 0; i < biens.size() ; i++) {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("/fxml/marketPlace/item.fxml"));
//            AnchorPane anchorPane = null;
//            try {
//                anchorPane = fxmlLoader.load();
//            } catch (IOException e) {
//                throw new RuntimeException( e );
//            }
//
//            ItemController itemController = fxmlLoader.getController();
//            itemController.setData(biens.get( i ));
//            itemController.animateImages(fiveSecondsWonder,biens.get(i));
//            getProduct(anchorPane,itemController);
//
//            if (column == 3) {
//                column = 0;
//                row++;
//            }
//            grid.add(anchorPane, column++, row);
//        }
//        grid.setHgap( 25 );
//        grid.setVgap( 25 );
//        grid.setPadding( new Insets( 0,0,40,0 ));
//    }

    public void showGridPane(ObservableList<Bien> biens){
        Platform.runLater( () -> {
            grid.getChildren().clear();
            grid.setHgap( 25 );
            grid.setVgap( 25 );
            grid.setPadding( new Insets( 0,0,40,0 ));
        } );

        int column = 0;
        int row = 1;
        var executer= Executors.newFixedThreadPool(6);
        for (int i = 0; i < biens.size() ; i++) {
            if (column == 4) {
                column = 0;
                row++;
            }
            executer.submit(loadingItemsThread(biens.get( i ),column++,row));
        }
        executer.shutdown();
    }


    private Task<CustomReturnItem>  loadingItemsThread(Product prod, int column, int row) {
        Task<CustomReturnItem> myTask = new Task<>() {
            @Override
            protected CustomReturnItem call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/marketPlace/item.fxml"));
                AnchorPane anchorPane = null;
                try {
                    anchorPane = fxmlLoader.load();
                    anchorPane.setPrefWidth( anchorPane.getPrefWidth()+30 );
                } catch (IOException e) {
                    throw new RuntimeException( e );
                }
                ItemController itemController = fxmlLoader.getController();
                return new CustomReturnItem(anchorPane,itemController);
            }
        };

        myTask.setOnSucceeded(e ->
            Platform.runLater( () -> {
                Timeline fiveSecondsWonder=new Timeline();
                myTask.getValue().getSecond().setData((Bien) prod);
                myTask.getValue().getSecond().animateImages(fiveSecondsWonder,(Bien) prod);
                getProduct(myTask.getValue().getFirst(),myTask.getValue().getSecond());
                grid.add(myTask.getValue().getFirst(), column, row);
            } )
        );
        return myTask;
    }


    public void loadChat(MouseEvent event){
//        if(whoIsActiveNow.equals( "hepfullBar" ))
//            animateChanges(hepfullBar, chatBox );
//        else if (whoIsActiveNow.equals( "itemInfo" ))
//            animateChanges( itemInfo, chatBox );
        whoIsActiveNow="chatBox";
    }

}
