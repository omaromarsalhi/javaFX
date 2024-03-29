package pidev.javafx.controller.mainWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pidev.javafx.controller.contrat.CheckOutController;
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.marketPlace.ChatClient;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
//import pidev.javafx.tools.marketPlace.DetectProperties;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.tools.marketPlace.MyTools;


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
    @FXML
    private HBox notifHbox;
    @FXML
    private Label imageNotif;
    @FXML
    private Label textNotif;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        EventBus.getInstance().subscribe( "laodCheckOut",this::laodCheckOut );
//        EventBus.getInstance().subscribe( "laodMarketPlace",this::onMarketPlaceBtnClicked );
//        mainBorderPane.getCenter())
        accountImg.setImage(new Image( "file:src/main/resources"+UserController.getInstance().getCurrentUser().getPhotos(),25,25,true,true)  );
        accountBtn.setText( UserController.getInstance().getCurrentUser().getFirstname()+" "+UserController.getInstance().getCurrentUser().getLastname() );
//        ChatClient.getInstance().establishConnection();
        notifHbox.setVisible( false );
        MyTools.getInstance().setImageNotif(imageNotif);
        MyTools.getInstance().setNotifHbox(notifHbox);
        MyTools.getInstance().setTextNotif(textNotif);
        MyTools.getInstance().showAndHideAnimation( MyTools.getInstance().getNotifHbox(),0,0 );
        notifHbox.setVisible( true );



        StackPane dashbord = null;
        try {
            dashbord = FXMLLoader.load(getClass().getResource( "/fxml/user/newAccountOmar.fxml" ));
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        centerContainer.getChildren().add(dashbord);
    }



//    btns that changes the scenes
    @FXML
<<<<<<< HEAD
    public void onShowEmpClicked(ActionEvent event) throws IOException {
        VBox showEmpAnchorPane = FXMLLoader.load( Objects.requireNonNull( getClass().getResource( "/fxml/reclmation/interfacemodifer.fxml" ) ) );
        mainBorderPain.setCenter(showEmpAnchorPane);
    }

    @FXML
    public void onMPDClicked(ActionEvent event) throws IOException {
        AnchorPane showEmpAnchorPane;
        showEmpAnchorPane = FXMLLoader.load(getClass().getResource( "/fxml/reclamation/menuDemande.fxml" ));
        mainBorderPain.setCenter(showEmpAnchorPane);
    }

    @FXML
    public void onMarketPlaceBtnClicked(ActionEvent event){
        mainBorderPain.getChildren().remove(mainhBox);
        try {
            mainhBox = FXMLLoader.load(getClass().getResource( "/fxml/reclamation/testt.fxml" ));
=======
    public void onBlogBtnClicked(ActionEvent event) throws IOException {
        centerContainer.getChildren().clear();
        AnchorPane blog = null;
        try {
            blog = FXMLLoader.load( getClass().getResource( "/fxml/blog/blog.fxml" ) );
            EventBus.getInstance().publish( "loadPosts",new CustomMouseEvent<>("/fxml/blog/post.fxml" ) );
>>>>>>> main
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        centerContainer.getChildren().add(blog);
    }

    @FXML
    public void onAbonnementBtnClicked(ActionEvent event){
        centerContainer.getChildren().clear();
        StackPane stations = null;
        try {
            stations = FXMLLoader.load( getClass().getResource( "/fxml/Transport/Gui_Abonnement/AbonnementClient.fxml" ) );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        centerContainer.getChildren().add(stations);
    }


    @FXML
    public void onStationsBtnClicked(ActionEvent event){
        centerContainer.getChildren().clear();
        AnchorPane stations = null;
        try {
            stations = FXMLLoader.load( getClass().getResource( "/fxml/Transport/Gui_Station/TransportClient.fxml" ) );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        centerContainer.getChildren().add(stations);
    }


    @FXML
    public void onNewsBtnClicked(ActionEvent event) throws IOException {
        centerContainer.getChildren().clear();
        StackPane stations = null;
        try {
            stations = FXMLLoader.load( getClass().getResource( "/fxml/blog/newsPage.fxml" ) );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        centerContainer.getChildren().add(stations);
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



    @FXML
    void onCloseBtnClicked() {
//        ChatClient.getInstance().closeConnection(UserController.getInstance().getCurrentUser().getId());
        System.exit(0);
    }

    @FXML
    void onReduireBtnClicked(ActionEvent event) {
        Stage currentStage = (Stage) reduireBtn.getScene().getWindow();
        currentStage.setIconified(true);
    }

    @FXML
    void onAgrendirBtnClicked(ActionEvent event) {
        Stage currentStage = (Stage) agrendirBtn.getScene().getWindow();
        boolean etatFenetre = currentStage.isMaximized();
        currentStage.setMaximized(!etatFenetre);
    }

//    @FXML
//    void onBlogClicked(MouseEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/blog/blog.fxml"));
//        AnchorPane blogPane = loader.load();
//        mainBorderPane.setCenter(blogPane);
//    }

}
