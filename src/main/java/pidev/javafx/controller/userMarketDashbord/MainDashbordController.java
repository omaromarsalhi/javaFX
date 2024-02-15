package pidev.javafx.controller.userMarketDashbord;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import pidev.javafx.crud.CrudBien;
import pidev.javafx.crud.CrudLocalWrapper;
import pidev.javafx.controller.marketPlace.*;
import pidev.javafx.tools.CustomMouseEvent;
import pidev.javafx.tools.EventBus;
import pidev.javafx.tools.MyListener;
import pidev.javafx.tools.MyTools;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Product;
import pidev.javafx.model.Wrapper.LocalWrapper;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainDashbordController implements Initializable {


    @FXML
    private VBox informationBar;
    @FXML
    private AnchorPane accountInfo;
    @FXML
    private Button searchBtn;
    @FXML
    private HBox searchHbox;
    @FXML
    private TextField searchTextField;
    @FXML
    private MenuBar menuBar;
    @FXML
    private VBox helperBar;
    @FXML
    private ScrollPane scroll;
    @FXML
    private AnchorPane showAllProdsInfo;


    private VBox infoTemplate;
    private ItemInfoController infoTemplateController;
    private Timer animTimer;
    private EventHandler<MouseEvent> eventHandler;
    private EventHandler<MouseEvent> eventHandler4ScrollPane;
    private Product prod2Update;
    private TableView<Bien> tableViewProd;
    private TableViewController tableViewController;
    private Timeline fiveSecondsWonder;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fiveSecondsWonder=new Timeline();
        loadTableView();
        showAllProdsInfo.getChildren().add(tableViewProd);
        AnchorPane.setTopAnchor(tableViewProd,4d);
        AnchorPane.setLeftAnchor(tableViewProd,4d);
        AnchorPane.setBottomAnchor(tableViewProd,4d);
        AnchorPane.setRightAnchor(tableViewProd,10d);

        setMenueBar();

        PauseTransition pause = new PauseTransition( Duration.seconds(0.1));
        pause.setOnFinished(e -> {
            loadInfoTemplate();
            loadInfoOfSpecificItem(tableViewProd.getItems().get(0));
            informationBar.getChildren().addAll(infoTemplate);
        });
        pause.play();
        tableViewProd.setOnMouseClicked( event -> {
            loadInfoOfSpecificItem(tableViewProd.getSelectionModel().getSelectedItem());
        } );


        animTimer = new Timer();
        searchTextField.setVisible( false );
        searchBtn.setStyle( "-fx-border-radius: 20;" );

        eventHandler = event -> {
            animateSearchBar(String.valueOf( event.getEventType() ) );
        };
        eventHandler4ScrollPane = MouseEvent::consume;

        searchHbox.setOnMouseEntered(eventHandler);

        EventBus.getInstance().subscribe( "refreshTableOnDelete",this::refreshTableOnDelete );
        EventBus.getInstance().subscribe( "refreshTableOnAddOrUpdate",this::refreshTableOnAddOrUpdate );
        EventBus.getInstance().subscribe( "updateProd",this::doUpdate );
    }



    public void setMenueBar(){
        var addProduct=new MenuItem("Add Prod",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/more.png"))));
        var showForSaleProduct=new MenuItem("Show  My Prod",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        var showForPushasedProduct=new MenuItem("Show Purshased Prod",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        var showForSelledProduct=new MenuItem("Show Selled Prod",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        addProduct.setOnAction( event -> {
            tableViewProd.getSelectionModel().clearSelection();
            scroll.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler4ScrollPane);
            fiveSecondsWonder.stop();
            showAllProdsInfo.getChildren().clear();
            showAllProdsInfo.getChildren().add(tableViewProd);
            setFormForAddOrUpdate("add_prod");
        } );
        showForSaleProduct.setOnAction( event -> {
            scroll.removeEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler4ScrollPane);
            loadInfoOfSpecificItem(tableViewProd.getItems().get(0));
            informationBar.getChildren().clear();
            informationBar.getChildren().add(infoTemplate);
            showAllProdsInfo.getChildren().clear();
            showAllProdsInfo.getChildren().add(tableViewProd);
        } );
        showForPushasedProduct.setOnAction( event -> {
            loadSelledOrPurchsedProducts("PURCHSED");
        } );
        showForSelledProduct.setOnAction( event -> {
            loadSelledOrPurchsedProducts("SELLED");
        } );

        menuBar.getMenus().get( 0 ).getItems().addAll(addProduct ,showForSaleProduct,showForPushasedProduct,showForSelledProduct);

        var addService=new MenuItem("Add Service",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/more.png"))));
        var showService=new MenuItem("Show Service",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        menuBar.getMenus().get( 1 ).getItems().addAll(addService ,showService);
    }

    public void animateSearchBar(String eventType){

        if(eventType.equals("MOUSE_ENTERED")){
            animTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(searchTextField.getWidth()==16){
                        searchHbox.setOnMouseEntered(null);
                        searchHbox.setOnMouseExited(null);
                        searchBtn.setStyle( "-fx-border-radius: 0 20 20 0;" +
                                "-fx-border-color: black  black black transparent ;");
                        searchTextField.setVisible( true );
                    }
                    if (searchTextField.getWidth()<(searchHbox.getWidth()-searchBtn.getWidth()-20)) {
                        searchTextField.setPrefWidth(searchTextField.getWidth()+10);
                    } else {
                        searchHbox.setOnMouseExited(eventHandler);
                        this.cancel();
                    }
                }

            }, 500, 20);
        }
        else if(eventType.equals("MOUSE_EXITED")){
            animTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                        if (searchTextField.getWidth() <=16) {
                            searchHbox.setOnMouseEntered( null );
                            searchHbox.setOnMouseExited( null );
                            searchBtn.setStyle( "-fx-border-color: black;");
                            searchBtn.setStyle( "-fx-border-radius: 20;");
                            searchTextField.setVisible( false );
                        }
                        if (searchTextField.getWidth() > 16) {
                            searchTextField.setPrefWidth( searchTextField.getWidth() - 10 );
                        } else {
                            searchHbox.setOnMouseEntered( eventHandler );
                            this.cancel();
                        }
                }
            }, 1000, 15);
        }
    }

    public void doUpdate(CustomMouseEvent<Product> customMouseEvent){
        prod2Update=customMouseEvent.getEventData();
        setFormForAddOrUpdate("update_prod");
    }

    public void loadSelledOrPurchsedProducts(String trasactionType){

        showAllProdsInfo.getChildren().clear();
        ObservableList<LocalWrapper> localWrapperList= CrudLocalWrapper.getInstance().selectItemsByIdSeller(1,trasactionType);
        GridPane gridPane=new GridPane();
        gridPane.setPrefWidth(showAllProdsInfo.getPrefWidth()  );
        gridPane.setAlignment( Pos.CENTER );
        gridPane.setPadding( new Insets( 0,0,50,50 ) );

        int column = 0;
        int row = 1;
        for(int i=0;i< localWrapperList.size();i++){

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource( "/fxml/userMarketDashbord/tarnsactionDetails.fxml" ));
            HBox hboxOfStackPane = null;
            FXMLLoader fxmlLoader2 = new FXMLLoader();
            fxmlLoader2.setLocation(getClass().getResource("/fxml/marketPlace/item.fxml"));
            AnchorPane anchorPane = null;
            try {
                hboxOfStackPane = fxmlLoader.load();
                anchorPane = fxmlLoader2.load();
            } catch (IOException e) {
                throw new RuntimeException( e );
            }

            TransactionDetailsController transactionDetailsController = fxmlLoader.getController();
            ItemController itemController = fxmlLoader2.getController();
            transactionDetailsController.setData(localWrapperList.get( i ).getTransaction(),localWrapperList.get( i ).getContract());
            itemController.setData((Bien)localWrapperList.get( i ).getProduct());
            itemController.animateImages(fiveSecondsWonder, (Bien)localWrapperList.get( i ).getProduct() );

            AnchorPane finalAnchorPane = anchorPane;
            animateProdBox(1,transactionDetailsController.getDownloadBtn(),0.1f);
            animateProdBox(1,transactionDetailsController.getDeleteBtn(),0.1f);
            AtomicBoolean val= new AtomicBoolean( true );
            hboxOfStackPane.setOnMouseClicked( event -> {
                if(val.get()){
                    animateProdBox(1,finalAnchorPane,0.6f);
                    animateProdBox(0,transactionDetailsController.getDownloadBtn(),0.8f);
                    animateProdBox(0,transactionDetailsController.getDeleteBtn(),0.8f);
                    val.set( false );
                }
                else{
                    animateProdBox(0,finalAnchorPane,0.6f);
                    animateProdBox(1,transactionDetailsController.getDownloadBtn(),0.8f);
                    animateProdBox(1,transactionDetailsController.getDeleteBtn(),0.8f);
                    val.set( true );
                }
            } );

            int finalI = i;
            transactionDetailsController.getDownloadBtn().setOnMouseClicked( event -> MyTools.getInstance().generatePDF(localWrapperList.get( finalI ).getContract()));
            HBox finalHboxOfStackPane = hboxOfStackPane;
            transactionDetailsController.getDeleteBtn().setOnMouseClicked( event -> {
                CrudBien.getInstance().deleteItem( localWrapperList.get( finalI ).getProduct().getId() );
                gridPane.getChildren().remove( finalHboxOfStackPane );
            });

            ((StackPane)hboxOfStackPane.getChildren().get( 0 )).getChildren().add(anchorPane );

            if (column == 2) {
                column = 0;
                row++;
            }

            gridPane.setHgap( 40 );
            gridPane.setVgap( 30 );
            gridPane.add(hboxOfStackPane, column++, row);
        }
        showAllProdsInfo.getChildren().add(gridPane);
        scroll.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler4ScrollPane);
    }

    public void animateProdBox(int initialState,Node node,float duration){
        FadeTransition fade = new FadeTransition( Duration.seconds(duration),node  );
        fade.setFromValue(initialState);
        fade.setToValue(1-initialState);
        fade.play();
    }


    public void setFormForAddOrUpdate(String termOfUse){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/marketPlace/secondForm.fxml"));
        VBox form = null;
        try {
            form = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        FormController formController = fxmlLoader.getController();
        VBox finalForm = form;
        MyListener listeener=new MyListener() {
            @Override
            public void exit() {
                informationBar.getChildren().remove( finalForm );
                loadInfoTemplate();
                loadInfoOfSpecificItem(tableViewProd.getItems().get(0));
                informationBar.getChildren().add(infoTemplate);
            }
        };
        formController.setExitFunction(listeener);
        if(termOfUse.equals( "update_prod" ))
            formController.setInformaton( prod2Update );
        informationBar.getChildren().remove(infoTemplate);
        form.setPrefHeight(informationBar.getPrefHeight());
        form.setPrefWidth(informationBar.getPrefWidth());
        informationBar.getChildren().add(form);
    }

    public void loadInfoTemplate() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/marketPlace/itemInfo.fxml"));
        infoTemplate = null;
        try {
            infoTemplate = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        infoTemplateController = fxmlLoader.getController();
    }

    public void loadInfoOfSpecificItem(Product product) {
        infoTemplateController.setDataForLocalUser(product,(accountInfo.getWidth()/2));
        infoTemplate.setPrefHeight( informationBar.getPrefHeight()-40);

    }

    public void refreshTableOnDelete(CustomMouseEvent<Bien> event){
        tableViewProd.getItems().remove( event.getEventData() );
        tableViewProd.refresh();
        loadInfoOfSpecificItem(tableViewProd.getItems().get(0));
        tableViewProd.getSelectionModel().clearSelection();
    }

    public void refreshTableOnAddOrUpdate(MouseEvent event){
        tableViewProd.getItems().clear();
        tableViewController.setData(CrudBien.getInstance().selectItems());
        loadInfoOfSpecificItem(tableViewProd.getItems().get(0));
        tableViewProd.getSelectionModel().clearSelection();
    }


    public void loadTableView() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/userMarketDashbord/tableView.fxml"));
        tableViewProd = null;
        try {
            tableViewProd = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        tableViewController = fxmlLoader.getController();
        tableViewController.setData(CrudBien.getInstance().selectItems());

    }





}
