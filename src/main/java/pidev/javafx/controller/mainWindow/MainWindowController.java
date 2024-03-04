package pidev.javafx.controller.mainWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import pidev.javafx.controller.contrat.CheckOutController;
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.marketPlace.ChatClient;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
//import pidev.javafx.tools.marketPlace.DetectProperties;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.model.MarketPlace.Bien;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private Button accountBtn;
    @FXML
    private Button agrendirBtn;
    @FXML
    private Button blogBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private Button dashbordBtn;
    @FXML
    private HBox header;
    @FXML
    private Button marketplacebtn;
    @FXML
    private Button newsBtn;
    @FXML
    private Button reduireBtn;
    @FXML
    private VBox sideBar;
    @FXML
    private Button stationBtn;
    @FXML
    private Button transportBtn;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private AnchorPane centerContainer;
    @FXML
    private ImageView accountImg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        EventBus.getInstance().subscribe( "laodCheckOut",this::laodCheckOut );
//        EventBus.getInstance().subscribe( "laodMarketPlace",this::onMarketPlaceBtnClicked );
//        mainBorderPane.getCenter())
        System.out.println(UserController.getInstance().getCurrentUser());
        accountImg.setImage(new Image( "file:src/main/resources"+UserController.getInstance().getCurrentUser().getPhotos(),25,25,true,true)  );
        accountBtn.setText( UserController.getInstance().getCurrentUser().getFirstname()+" "+UserController.getInstance().getCurrentUser().getLastname() );
//        ChatClient.getInstance().establishConnection();
    }



//    btns that changes the scenes
    @FXML
    public void onBlogBtnClicked(ActionEvent event) throws IOException {
        AnchorPane stations = null;
        try {
            stations = FXMLLoader.load( getClass().getResource( "/fxml/blog/blog.fxml" ) );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        mainBorderPane.setCenter(stations);
    }

    @FXML
    public void onTransportBtnClicked(ActionEvent event){
        AnchorPane stations = null;
        try {
            stations = FXMLLoader.load( getClass().getResource( "/fxml/Transport/Gui_Station/TransportClient.fxml" ) );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        mainBorderPane.setCenter(stations);
    }


    @FXML
    public void onStationsBtnClicked(ActionEvent event){
        AnchorPane stations = null;
        try {
            stations = FXMLLoader.load( getClass().getResource( "/fxml/Transport/Gui_Station/TransportClient.fxml" ) );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        mainBorderPane.setCenter(stations);
    }


    @FXML
    public void onNewsBtnClicked(ActionEvent event) throws IOException {
        VBox showEmpAnchorPane = FXMLLoader.load( Objects.requireNonNull( getClass().getResource( "/fxml/marketPlace/showItems.fxml" ) ) );
        mainBorderPane.setCenter(showEmpAnchorPane);
    }


    @FXML
    public void onMarketPlaceBtnClicked(ActionEvent event) {
        centerContainer.getChildren().clear();
        StackPane marketplace;
        try {
            marketplace = FXMLLoader.load(getClass().getResource( "/fxml/marketPlace/myMarket.fxml" ) );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        centerContainer.getChildren().add(marketplace);
    }


    @FXML
    public void onMarketplaceDashbordBtnClicked(ActionEvent event) {
        centerContainer.getChildren().clear();
        StackPane dashbord = null;
        try {
            dashbord = FXMLLoader.load(getClass().getResource( "/fxml/userMarketDashbord/userMainDashbord.fxml" ));
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        centerContainer.getChildren().add(dashbord);
    }

    @FXML
    public void onAccountBtnClicked(ActionEvent event) {
        centerContainer.getChildren().clear();
        StackPane account = null;
        try {
            account = FXMLLoader.load(getClass().getResource( "/fxml/user/newAccountOmar.fxml" ) );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        centerContainer.getChildren().add(account);
    }

//    @FXML
//    public void onMPDClicked(ActionEvent event) throws IOException {
//        StackPane stackPane = FXMLLoader.load(getClass().getResource( "/fxml/userMarketDashbord/userMainDashbord.fxml" ));
//        mainBorderPane.setCenter(stackPane);
//    }

//    @FXML
//    public void onMarketPlaceBtnClicked(ActionEvent event){
//        try {
//            mainhBox2 = FXMLLoader.load(getClass().getResource( "/fxml/marketPlace/myMarket.fxml" ));
//        } catch (IOException e) {
//            throw new RuntimeException( e );
//        }
////        mainhBox2.setMaxHeight(MainAnchorPane.getPrefHeight());
////        mainhBox2.setMaxWidth( MainAnchorPane.getPrefWidth());
//        mainBorderPane.setCenter(mainhBox2);
//    }

//    public void laodCheckOut(CustomMouseEvent<Bien> event) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource( "/fxml/Contract/checkOut.fxml" ));
//            mainhBox = fxmlLoader.load();
//            CheckOutController checkOutController = fxmlLoader.getController();
//            checkOutController.setData(event.getEventData());
//        } catch (IOException e) {
//            throw new RuntimeException( e );
//        }
////        mainhBox.setMaxHeight(MainAnchorPane.getPrefHeight()  );
////        mainhBox.setMaxWidth( MainAnchorPane.getPrefWidth());
//       mainBorderPane.setCenter(mainhBox);
//    }


    @FXML
    void onCloseBtnClicked() {
//        ChatClient.getInstance().closeConnection(UserController.getInstance().getCurrentUser().getId());
        System.exit(0);
    }

//    @FXML
//    void onReduireBtnClicked(ActionEvent event) {
//        Stage currentStage = (Stage) reduireBtn.getScene().getWindow();
//        currentStage.setIconified(true);
//    }

//    @FXML
//    void onAgrendirBtnClicked(ActionEvent event) {
//        Stage currentStage = (Stage) agrendirBtn.getScene().getWindow();
//        boolean etatFenetre = currentStage.isMaximized();
//        currentStage.setMaximized(!etatFenetre);
//    }

//    @FXML
//    void onBlogClicked(MouseEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/blog/blog.fxml"));
//        AnchorPane blogPane = loader.load();
//        mainBorderPane.setCenter(blogPane);
//    }

}
