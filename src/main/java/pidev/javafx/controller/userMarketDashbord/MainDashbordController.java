package pidev.javafx.controller.userMarketDashbord;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import pidev.javafx.controller.chat.ChatController;
import pidev.javafx.controller.user.UserController;
import pidev.javafx.crud.marketplace.CrudBien;
import pidev.javafx.crud.marketplace.CrudFavorite;
import pidev.javafx.crud.marketplace.CrudLocalWrapper;
import pidev.javafx.controller.marketPlace.*;
import pidev.javafx.model.MarketPlace.Favorite;
import pidev.javafx.model.User.Role;
import pidev.javafx.model.User.User;
import pidev.javafx.tools.ChatClient;
import pidev.javafx.tools.CustomMouseEvent;
import pidev.javafx.tools.EventBus;
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
    private AnchorPane accountInfo;
    @FXML
    private ScrollPane scroll;
    @FXML
    private AnchorPane showAllProdsInfo;
    @FXML
    private Button searchBtn;
    @FXML
    private HBox searchHbox;
    @FXML
    private TextField searchTextField;
    @FXML
    private MenuBar menuBar;
    @FXML
    private VBox bigContainer;
    @FXML
    private Label userEmail;
    @FXML
    private ImageView userImage;
    @FXML
    private Label userlasteName;
    @FXML
    private Label username;
    @FXML
    private AnchorPane secondInterface;
    @FXML
    private HBox secondIHbox;
    @FXML
    private HBox firstInterface;


    private VBox infoTemplate;
    private ItemInfoController infoTemplateController;
    private EventHandler<MouseEvent> eventHandler4ScrollPane;
    private Product prod2Update;
    private TableView<Bien> tableViewProd;
    private TableViewController tableViewController;
    private Timeline fiveSecondsWonder;
    private Timer animTimer;
    private String searchBarState;
    private GridPane gridPane4Favorite;
    private double xOffset = 0;
    private double yOffset = 0;






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        secondInterface.setVisible( false );


        userImage.setImage( new Image( "file:src/main/resources/"+ UserController.getInstance().getCurrentUser().getImagePath() ) );
        username.setText(  UserController.getInstance().getCurrentUser().getFirstname() );
        userlasteName.setText(UserController.getInstance().getCurrentUser().getLastname()  );
        userEmail.setText(UserController.getInstance().getCurrentUser().getEmail() );


        fiveSecondsWonder=new Timeline();
        loadTableView();
        showAllProdsInfo.getChildren().add(tableViewProd);
        AnchorPane.setTopAnchor(tableViewProd,4d);
        AnchorPane.setLeftAnchor(tableViewProd,4d);
        AnchorPane.setBottomAnchor(tableViewProd,4d);
        AnchorPane.setRightAnchor(tableViewProd,0d);

        setMenueBar();

//        PauseTransition pause = new PauseTransition( Duration.seconds(0.1));
//        pause.setOnFinished(e -> {
//            loadInfoTemplate();
//            loadInfoOfSpecificItem(tableViewProd.getItems().get(0));
//            informationBar.getChildren().addAll(infoTemplate);
//        });
//        pause.play();
        tableViewProd.setOnMouseClicked( event -> {
            loadInfoItem(tableViewProd.getSelectionModel().getSelectedItem());
        } );


        animTimer = new Timer();
        searchBarState="closed";
        searchTextField.setVisible( false );
        searchBtn.setStyle( "-fx-border-radius: 20;" +
                "-fx-background-radius:20;" );
        searchHbox.setOnMouseClicked(event -> animateSearchBar());

        eventHandler4ScrollPane = MouseEvent::consume;


        EventBus.getInstance().subscribe( "refreshTableOnDelete",this::refreshTableOnDelete );
        EventBus.getInstance().subscribe( "refreshTableOnAddOrUpdate",this::refreshTableOnAddOrUpdate );
        EventBus.getInstance().subscribe( "updateProd",this::doUpdate );
        EventBus.getInstance().subscribe( "onExitForm",this::onExitForm );
        EventBus.getInstance().subscribe( "add2Grid",this::add2Grid );


    }


    public void setMenueBar(){
        var addProduct=new MenuItem("Add Prod",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/more.png"))));
        var showForSaleProduct=new MenuItem("Show  My Prod",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        var showForPushasedProduct=new MenuItem("Show Purshased Prod",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        var showForSelledProduct=new MenuItem("Show Selled Prod",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        addProduct.setOnAction( event -> {
            secondInterface.setVisible( true );
            firstInterface.setOpacity( 0.4 );
            setFormForAddOrUpdate("add_prod");
        } );
        showForSaleProduct.setOnAction( event -> {
            deleteFavoriteLabel();
            scroll.removeEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler4ScrollPane);
//            loadInfoOfSpecificItem(tableViewProd.getItems().get(0));
//            informationBar.getChildren().clear();
//            informationBar.getChildren().add(infoTemplate);
            showAllProdsInfo.getChildren().clear();
            showAllProdsInfo.getChildren().add(tableViewProd);
        } );
        showForPushasedProduct.setOnAction( event -> loadSelledOrPurchsedProducts("PURCHSED"));
        showForSelledProduct.setOnAction( event -> loadSelledOrPurchsedProducts("SELLED") );

        menuBar.getMenus().get( 0 ).getItems().addAll(addProduct ,showForSaleProduct,showForPushasedProduct,showForSelledProduct);

        var addService=new MenuItem("Add Service",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/more.png"))));
        var showService=new MenuItem("Show Service",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        menuBar.getMenus().get( 1 ).getItems().addAll(addService ,showService);

        var add2Favorite=new MenuItem("Manage",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        menuBar.getMenus().get( 2 ).getItems().add(add2Favorite);

        add2Favorite.setOnAction( event -> {
            loadFavoriteView();
            loadFavorite();
            EventBus.getInstance().publish( "showFavorite",event);
        });

        var openChat=new MenuItem("open chat ",new ImageView(new Image(getClass().getResourceAsStream("/namedIcons/database.png"))));
        menuBar.getMenus().get( 3 ).getItems().add(openChat);
        openChat.setOnAction( event -> openChatWindow() );
    }


    private void openChatWindow() {
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource( "/fxml/chat/seperatedChat.fxml" ));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), Color.TRANSPARENT);
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        ChatController controller=fxmlLoader.getController();
        controller.initliazeData();
        controller.getExitBtn().setOnMouseClicked( event ->{
            ChatClient.getInstance().closeConnection();
            newStage.close();
        }  );
        controller.getContainer().setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        controller.getContainer().setOnMouseDragged(event -> {
            newStage.setX(event.getScreenX() - xOffset);
            newStage.setY(event.getScreenY() - yOffset);
        });
        controller.loadUsers( FXCollections.observableArrayList( new User( 1,
                "omar",
                "salhi",
                "salhiomar362@gmail.com",
                "12710434",
                22,
                29624921,
                "beb saadoun",
                Role.simpleutlisateur,
                "salhi",
                "img/me.png" ) ,
                new User( 2,
                "latifa",
                "benzaied",
                "latifa@gmail.com",
                "25251400",
                22,
                50421001,
                "menzah 1",
                Role.simpleutlisateur,
                "benzaied",
                "img/latifa.png") ) );
        newStage.setResizable( false );
        newStage.setOnCloseRequest(event -> System.exit(0));
        newStage.initStyle( StageStyle.TRANSPARENT);
        newStage.setScene(scene);
        newStage.show();
    }





    public void loadFavorite(){
        VBox favorite = null;
        try {
            favorite = FXMLLoader.load(getClass().getResource( "/fxml/marketPlace/helpfullBar.fxml" ));
        } catch (IOException e) {
            throw new RuntimeException( e );
        }

        Label label=new Label("Here you can select your Wishlist criteria." +
                "This criterion will allow you to get notified when a matching product gets added.");
        label.setWrapText( true );
        label.setFont( Font.font( "System", FontWeight.BOLD, FontPosture.ITALIC,16 ));
        label.setAlignment( Pos.CENTER );
        label.setPadding( new Insets( 0,0,0,15 ) );
        label.setMinHeight( 80 );
        bigContainer.getChildren().add(0, label );

        favorite.setPadding( new Insets( 0 ) );
        favorite.setSpacing( 0 );
//        informationBar.getChildren().clear();
//        informationBar.getChildren().add(favorite);
//        scroll.setPrefHeight(informationBar.getPrefHeight()-80 );
    }


    public void deleteFavoriteLabel(){
        if(bigContainer.getChildren().size()>1){
            bigContainer.getChildren().remove(bigContainer.getChildren().get( 0 ));
            scroll.setPrefHeight(scroll.getPrefHeight()+80 );
        }
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


    public void doUpdate(CustomMouseEvent<Product> customMouseEvent){
        prod2Update=customMouseEvent.getEventData();
        secondIHbox.getChildren().clear();
        secondInterface.setVisible( true );
        firstInterface.setOpacity( 0.4 );
        setFormForAddOrUpdate("update_prod");
    }


    public void loadInfoItem(Product prod){
        VBox info = null;
        try {
            info = FXMLLoader.load(getClass().getResource( "/fxml/marketPlace/itemInfo.fxml" ));
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        EventBus.getInstance().publish( "setItemInfoData4LocalUser",new CustomMouseEvent<>(prod));
        secondInterface.setVisible( true );
        firstInterface.setOpacity( 0.4 );
        secondIHbox.getChildren().clear();
        secondIHbox.getChildren().add(info);
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
            int finalRow = row;
            int finalColumn = column;
            transactionDetailsController.getDeleteBtn().setOnMouseClicked( event -> {
                CrudBien.getInstance().deleteItem( localWrapperList.get( finalI ).getProduct().getId());
                rfreshGridPane(gridPane,finalHboxOfStackPane, finalRow, finalColumn,2);
            });

            ((StackPane)hboxOfStackPane.getChildren().get( 0 )).getChildren().add(anchorPane );

            if (column == 3) {
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


    public void onExitForm(MouseEvent event){
        secondInterface.setVisible( false );
        firstInterface.setOpacity( 1 );
        secondIHbox.getChildren().clear();
    }


    public void setFormForAddOrUpdate(String termOfUse){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource( "/fxml/userMarketDashbord/secondForm.fxml" ));
        StackPane form = null;
        try {
            form = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        FormController formController = fxmlLoader.getController();
        if(termOfUse.equals( "update_prod" ))
            formController.setInformaton( prod2Update );
//        form.setPrefHeight(informationBar.getPrefHeight());
//        form.setPrefWidth(informationBar.getPrefWidth());
        secondIHbox.getChildren().add(form);
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



    public void refreshTableOnDelete(CustomMouseEvent<Bien> event){
        tableViewProd.getItems().remove( event.getEventData() );
        tableViewProd.refresh();
        tableViewProd.getSelectionModel().clearSelection();
    }


    public void refreshTableOnAddOrUpdate(MouseEvent event){
        tableViewProd.getItems().clear();
        tableViewController.setData(CrudBien.getInstance().selectItems());
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


    public void loadFavoriteView() {
        showAllProdsInfo.getChildren().clear();
        gridPane4Favorite=new GridPane();
        gridPane4Favorite.setPrefWidth(showAllProdsInfo.getPrefWidth());
        gridPane4Favorite.setAlignment( Pos.CENTER );
        gridPane4Favorite.setPadding( new Insets( 20,0,20,0 ) );
        gridPane4Favorite.setHgap( 40 );
        gridPane4Favorite.setVgap( 30 );
        add2Grid(new CustomMouseEvent<>(CrudFavorite.getInstance().selectItems()));
        showAllProdsInfo.getChildren().add(gridPane4Favorite);
        scroll.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler4ScrollPane);
    }


    public void add2Grid(CustomMouseEvent<ObservableList<Favorite>> customMouseEvent) {
        int row= (int) Math.ceil((float)gridPane4Favorite.getChildren().size()/3);
        int column=gridPane4Favorite.getChildren().size()%3;
        row-=(row==0||column==0)?0:1;

        for(int i=0;i< customMouseEvent.getEventData().size();i++){
            HBox favoriteAnchorPanes = null;
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/userMarketDashbord/favorite.fxml"));
            try {
                favoriteAnchorPanes = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException( e );
            }
            FavoriteController favoriteController=fxmlLoader.getController();
            favoriteController.setData(customMouseEvent.getEventData().get( i ) ,row*3+column+1);
            HBox finalFavoriteAnchorPanes = favoriteAnchorPanes;
            int finalRow = row;
            int finalColumn = column;
            int finalI = i;
            favoriteController.getDeleteBtn().setOnMouseClicked( event -> {
                rfreshGridPane(gridPane4Favorite,finalFavoriteAnchorPanes,finalRow,finalColumn,2);
                CrudFavorite.getInstance().deleteItem( customMouseEvent.getEventData().get( finalI ).getIdFavorite() );
            });
            if (column == 3) {
                column = 0;
                row++;
            }
            gridPane4Favorite.add(favoriteAnchorPanes, column++, row);
        }
    }


    public void rfreshGridPane(GridPane gridPane,Node node2Delete,int row,int column,int nbrColumns){
        gridPane.getChildren().remove( node2Delete );
        for (Node node : gridPane.getChildren()){
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);
            if (rowIndex != null && ((rowIndex==row && columnIndex>column)||rowIndex>row )){
                rowIndex+=(columnIndex==0&&rowIndex!=0)?-1:0;
                columnIndex+=(columnIndex==0)?nbrColumns:-1;
                GridPane.setColumnIndex( node,columnIndex );
                GridPane.setRowIndex(node, rowIndex );
            }
        }
        gridPane.layout();
    }


}
