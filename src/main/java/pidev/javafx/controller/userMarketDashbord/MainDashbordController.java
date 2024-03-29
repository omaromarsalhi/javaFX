package pidev.javafx.controller.userMarketDashbord;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import pidev.javafx.controller.chat.ChatController;
import pidev.javafx.crud.user.ServiceUser;
import pidev.javafx.tools.UserController;
import pidev.javafx.crud.marketplace.CrudBien;
import pidev.javafx.crud.marketplace.CrudFavorite;
import pidev.javafx.crud.marketplace.CrudLocalWrapper;
import pidev.javafx.controller.marketPlace.*;
import pidev.javafx.model.MarketPlace.Favorite;
import pidev.javafx.model.user.User;
import pidev.javafx.model.user.Role;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Product;
import pidev.javafx.model.Wrapper.LocalWrapper;
import pidev.javafx.tools.marketPlace.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainDashbordController implements Initializable {



    @FXML
    private AnchorPane accountInfo;
    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox showAllProdsInfo;
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
    private HBox ultraBigContainer;
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
    @FXML
    private ImageView searchInfoBtn;
    @FXML
    private HBox tableHead;
    @FXML
    private HBox transactionsHead;


    private VBox infoTemplate;
    private ItemInfoController infoTemplateController;
    private EventHandler<MouseEvent> eventHandler4ScrollPane;
    private Product prod2Update;
    private Timeline fiveSecondsWonder;
    private Timer animTimer;
    private String searchBarState;
    private GridPane gridPane4Favorite;
    private double xOffset = 0;
    private double yOffset = 0;
    private EventHandler<MouseEvent> universalEventHandler;






    @Override
    public void initialize(URL location, ResourceBundle resources){

        bigContainer.widthProperty().addListener( (observable, oldValue, newValue) -> {
            scroll.setMaxWidth((Double)newValue-20);
        } );

        scroll.setFitToWidth(true);
        secondInterface.setVisible( false );
        userImage.setImage( new Image( "file:src/main/resources/"+ UserController.getInstance().getCurrentUser().getPhotos() ) );
        username.setText(  UserController.getInstance().getCurrentUser().getFirstname().toUpperCase() );
        userEmail.setText(UserController.getInstance().getCurrentUser().getEmail() );

        fiveSecondsWonder=new Timeline();

        setMenueBar();

        searchBarState="closed";
        animTimer = new Timer();
        searchTextField.setVisible( false );
        searchBtn.setStyle( "-fx-border-radius: 20;" +
                "-fx-background-radius:20;" );

        searchBtn.setOnMouseClicked(event ->{
            if(searchBarState.equals("opened")&&!searchTextField.getText().isEmpty())
                parseData(searchTextField.getText());
            else
                animateSearchBar();
        } );

        Popup popup = MyTools.getInstance().createPopUp();
        ((Label)popup.getContent().get( 0 )).setText("we have provided you with an advanced search so in it you use COMMANDS as the follow columnName_value ");
        popup.getContent().get( 0 ).setStyle(popup.getContent().get( 0 ).getStyle()+"-fx-background-color: #64b5f6;");
        ((Label)popup.getContent().get( 0 )).setMaxWidth( 250 );
        ((Label)popup.getContent().get( 0 )).setFont( Font.font( "System", FontWeight.MEDIUM, FontPosture.REGULAR,12 ) );

        searchInfoBtn.setOnMouseEntered( event ->  popup.show(Stage.getWindows().get(0),event.getScreenX()-300,event.getScreenY()-40));
        searchInfoBtn.setOnMouseExited( event ->  popup.hide());

        eventHandler4ScrollPane = MouseEvent::consume;


        EventBus.getInstance().subscribe( "onExitForm",this::onExitForm );
        EventBus.getInstance().subscribe( "add2Grid",this::add2Grid );
        EventBus.getInstance().subscribe( "updateTabProds",this::updateTabProds );
        EventBus.getInstance().subscribe( "doUpdateTabprodAfterAIverif",this::doUpdateTabprodAfterAIverif);
        EventBus.getInstance().subscribe( "showChat",this::openChatWindow);

        loadingAllProductsThread(CrudBien.getInstance().selectItemsById()).start();

    }

    public void updateTabProds(CustomMouseEvent<Bien> customMouseEvent){
        loadingAllProductsThread(FXCollections.observableArrayList(customMouseEvent.getEventData())).start();
    }


    public Thread loadingAllProductsThread(ObservableList<Bien> prods){
        showOrHideTabHead(true,"");
        Task<Void> myTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                loadListOfProducts(prods);
                return null;
            }
        };
        return new Thread(myTask);
    }

    public void parseData(String data){
        List<String> columns = new ArrayList<>(Arrays.asList("name", "description", "price", "quantity", "state", "timestamp", "category"));
        if(!data.isEmpty()&&data.contains( "_" )) {
            String[] parts = data.split( "_" );
            if (parts.length > 2 || parts[0].matches( "[a-zA-Z]" ) || !columns.contains( parts[0] ))
                return;
            else {
                scroll.removeEventFilter( MouseEvent.MOUSE_PRESSED, eventHandler4ScrollPane );
                showAllProdsInfo.getChildren().clear();
                loadingAllProductsThread( CrudBien.getInstance().searchItems( parts[0], parts[1] ) ).start();
            }
        }
        else if(!data.isEmpty()){
            System.out.println(data);
            scroll.removeEventFilter( MouseEvent.MOUSE_PRESSED, eventHandler4ScrollPane );
            showAllProdsInfo.getChildren().clear();
            loadingAllProductsThread( CrudBien.getInstance().searchItems("name", data ) ).start();
        }
    }


    public void setMenueBar(){
        var addProduct=new MenuItem("Add Prod",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/more.png" ))));
        var showAllProduct=new MenuItem("Show  My Prod",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/database.png" ))));
        var showForPushasedProduct=new MenuItem("Show Purshased Prod",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/database.png" ))));
        var showForSelledProduct=new MenuItem("Show Selled Prod",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/database.png" ))));
        addProduct.setOnAction( event -> {
            secondInterface.setVisible( true );
            firstInterface.setOpacity( 0.4 );
            setFormForAddOrUpdate("add_prod");
        } );
        showAllProduct.setOnAction( event -> {
            deleteFavorite();
            scroll.removeEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler4ScrollPane);
            loadingAllProductsThread(CrudBien.getInstance().selectItemsById()).start();
        } );
        showForPushasedProduct.setOnAction( event -> loadSelledOrPurchsedProducts("PURCHSED"));
        showForSelledProduct.setOnAction( event -> loadSelledOrPurchsedProducts("SELLED") );

        menuBar.getMenus().get( 0 ).getItems().addAll(addProduct ,showAllProduct,showForPushasedProduct,showForSelledProduct);

        var add2Favorite=new MenuItem("Manage",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/database.png" ))));
        menuBar.getMenus().get( 1 ).getItems().add(add2Favorite);

        add2Favorite.setOnAction( event -> {
            loadFavoriteForm();
            loadFavoriteView();
            EventBus.getInstance().publish( "showFavorite",event);
        });

        var openChat=new MenuItem("open chat ",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/database.png" ))));
        menuBar.getMenus().get( 2 ).getItems().add(openChat);
        openChat.setOnAction( event -> openChatWindow(new CustomMouseEvent<>( UserController.getInstance().getCurrentUser() )) );
    }


    private void openChatWindow(CustomMouseEvent<User> customMouseEvent) {
        Stage newStage = new Stage();
        HBox vBox=null;
        FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource( "/fxml/chat/seperatedChat.fxml" ));
        Scene scene = null;
        try {
            vBox=fxmlLoader.load();
            scene = new Scene(vBox, Color.TRANSPARENT);
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        ChatController controller=fxmlLoader.getController();
        controller.initliazeData();
        controller.getExitBtn().setOnMouseClicked( event ->{
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
        var userService= new ServiceUser();
        controller.loadUsers(FXCollections.observableArrayList(userService.getAll()));
        controller.setUserToChatWith(customMouseEvent.getEventData() );
        newStage.setResizable( false );
        newStage.setOnCloseRequest(event -> System.exit(0));
        newStage.initStyle( StageStyle.TRANSPARENT);
        newStage.setScene(scene);
        MyTools.getInstance().showAnimation( vBox );
        newStage.show();
    }


    public void loadFavoriteForm(){
        VBox favorite = null;
        try {
            favorite = FXMLLoader.load(getClass().getResource( "/fxml/marketPlace/helpfullBar.fxml" ));
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        showOrHideTabHead(false,"Here you can select your Wishlist criteria." +
                "This criterion will allow you to get notified when a matching product gets added.");

        favorite.setPadding( new Insets( 10,14,2,10 ) );
        favorite.setSpacing( 0 );

        ultraBigContainer.getChildren().add(0,favorite);
    }


    public void deleteFavorite(){
        if(ultraBigContainer.getChildren().size()>1){
            MyTools.getInstance().deleteAnimation( ultraBigContainer.getChildren().get( 0 ),ultraBigContainer );
            showAllProdsInfo.getChildren().clear();
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
                    if (searchTextField.getWidth()<(searchHbox.getWidth()-searchBtn.getWidth()-50)) {
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
                    }
                    else
                        this.cancel();
                }
            }, 0, 15);
        }
    }


    public void doUpdate(Product product){
        prod2Update=product;
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
        MyTools.getInstance().showAnimation(info);
    }

    public void loadSelledOrPurchsedProducts(String trasactionType){
        deleteFavorite();
        showOrHideTabHead(false,trasactionType+" Products And Contracts Details");
        showAllProdsInfo.getChildren().clear();
        ObservableList<LocalWrapper> localWrapperList= CrudLocalWrapper.getInstance().selectItemsByIdSeller(UserController.getInstance().getCurrentUser().getId(),trasactionType);
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
                refreshGridPane(gridPane,finalHboxOfStackPane, finalRow, finalColumn,2);
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
        secondIHbox.getChildren().add(form);
        MyTools.getInstance().showAnimation(form);
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





    public void loadFavoriteView() {
        showAllProdsInfo.getChildren().clear();
        gridPane4Favorite=new GridPane();
        scroll.widthProperty().addListener( (observable, oldValue, newValue) -> {
            gridPane4Favorite.setMinWidth((Double)newValue);
        } );
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
                CrudFavorite.getInstance().deleteItem( customMouseEvent.getEventData().get( finalI ).getIdFavorite() );
                refreshGridPane(gridPane4Favorite,finalFavoriteAnchorPanes,finalRow,finalColumn,2);
            });
            if (column == 3) {
                column = 0;
                row++;
            }
            gridPane4Favorite.add(favoriteAnchorPanes, column++, row);
        }
    }


    public void refreshGridPane(GridPane gridPane, Node node2Delete, int row, int column, int nbrColumns){
        for (Node node : gridPane.getChildren()){
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);
            if (rowIndex != null && ((rowIndex ==row && columnIndex>column)||rowIndex>row) ){
                rowIndex+=(columnIndex==0&&rowIndex!=0)?-1:0;
                columnIndex+=(columnIndex==0)?nbrColumns:-1;
                GridPane.setColumnIndex( node,columnIndex);
                GridPane.setRowIndex(node, rowIndex );
            }
        }
        gridPane.layout();
        gridPane.getChildren().remove( node2Delete );
    }


    public void loadListOfProducts(ObservableList<Bien> biens){
        var executer= Executors.newFixedThreadPool(6);
        for (int i = 0; i < biens.size() ; i++)
            executer.submit(loadingItemsThread(biens.get( i )));
        executer.shutdown();
    }


    private Task<CustomReturnForDashbord> loadingItemsThread(Product prod) {
        Task<CustomReturnForDashbord> myTask = new Task<>() {
            @Override
            protected CustomReturnForDashbord call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/userMarketDashbord/productHbox.fxml"));
                HBox hBox = null;
                try {
                    hBox = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException( e );
                }
                ProductsHboxController productsHboxController = fxmlLoader.getController();
                return new CustomReturnForDashbord(hBox,productsHboxController);
            }
        };

        myTask.setOnSucceeded(e -> {
            Platform.runLater( () -> {
                myTask.getValue().getSecond().setData( (Bien) prod );
                myTask.getValue().getFirst().lookup( "#deleteBtn" ).setOnMouseClicked( event -> {
                    MyTools.getInstance().deleteAnimation( myTask.getValue().getFirst(), showAllProdsInfo );
                    CrudBien.getInstance().deleteItem( prod.getId() );
                    MyTools.getInstance().getTextNotif().setText( "Prod Has Been Deleted Successfully" );
                    MyTools.getInstance().showNotif();
                } );

                myTask.getValue().getFirst().lookup( "#updateBtn" ).setOnMouseClicked( event -> {
                    doUpdate( prod );
                    EventBus.getInstance().subscribe( "refreshProdContainer", event1 -> {
                                myTask.getValue().getSecond().setData( CrudBien.getInstance().selectItemById( prod.getId() ) );
                                MyTools.getInstance().getTextNotif().setText( "Table Has Been Updated Successfully" );
                                MyTools.getInstance().showNotif();
                            }
                    );
                } );
                myTask.getValue().getFirst().setOnMouseClicked( event -> loadInfoItem( prod ) );
                showAllProdsInfo.getChildren().add(myTask.getValue().getFirst() );
            } );
        });
        return myTask;
    }

    public void doUpdateTabprodAfterAIverif(CustomMouseEvent<Bien> bienCustomMouseEvent){
        for(Node node:showAllProdsInfo.getChildren()){
            if(Integer.parseInt(((Label)((HBox)node).getChildren().get( 0 )).getText())==bienCustomMouseEvent.getEventData().getId()){
                if(bienCustomMouseEvent.getEventData().getState().equals( "verified" )){
                    ((ImageView)((HBox)node).getChildren().get( 6 ).lookup( "#stateImage" )).setImage(new Image( "file:src/main/resources/icons/marketPlace/approve24C.png",24,24,true,true ) );
                    ((Label)((HBox)node).getChildren().get( 6 )).setText("verified");
                }
                else {
                    MyTools.getInstance().deleteAnimation(node, showAllProdsInfo );
                    CrudBien.getInstance().deleteItem( bienCustomMouseEvent.getEventData().getId() );
                }
            }
        }
    }

    public void showOrHideTabHead(boolean state,String text){
        tableHead.setVisible( state );
        transactionsHead.setVisible( !state );
        if(transactionsHead.isVisible())
            ((Label)transactionsHead.getChildren().get( 0 )).setText(text );
    }


}
