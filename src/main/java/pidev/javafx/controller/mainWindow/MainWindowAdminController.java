package pidev.javafx.controller.mainWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.marketPlace.MyTools;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowAdminController implements Initializable {

    @FXML
    private Button abonnementBtn;
    @FXML
    private Button accountBtn;
    @FXML
    private ImageView accountImg;
    @FXML
    private Button agrendirBtn;
    @FXML
    private Button blogBtn;
    @FXML
    private AnchorPane centerContainer;
    @FXML
    private Button closeBtn;
    @FXML
    private HBox header;
    @FXML
    private Label imageNotif;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button marketplacebtn;
    @FXML
    private HBox notifHbox;
    @FXML
    private Button propertyBtn;

    @FXML
    private Button reduireBtn;
    @FXML
    private VBox sideBar;
    @FXML
    private Button stationBtn;
    @FXML
    private Label textNotif;
    @FXML
    private Button transportBtn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountImg.setImage(new Image( "file:src/main/resources"+ UserController.getInstance().getCurrentUser().getPhotos(),25,25,true,true)  );
        accountBtn.setText( UserController.getInstance().getCurrentUser().getFirstname()+" "+UserController.getInstance().getCurrentUser().getLastname() );
//        ChatClient.getInstance().establishConnection();
        notifHbox.setVisible( false );
        MyTools.getInstance().setImageNotif(imageNotif);
        MyTools.getInstance().setNotifHbox(notifHbox);
        MyTools.getInstance().setTextNotif(textNotif);
        MyTools.getInstance().showAndHideAnimation( MyTools.getInstance().getNotifHbox(),0,0 );
        notifHbox.setVisible( true );


    }



    @FXML
    void onAccountBtnClicked(ActionEvent event) {
        centerContainer.getChildren().clear();
        AnchorPane dashbord = null;
        try {
            dashbord = FXMLLoader.load(getClass().getResource( "/fxml/user/ListeUser.fxml" ));
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        centerContainer.getChildren().add(dashbord);
    }



    @FXML
    void onAbonnementBtnClicked(ActionEvent event) {
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
    void onBlogBtnClicked(ActionEvent event) {
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
    void onMarketPlaceBtnClicked(ActionEvent event) {
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
    void onMarketplaceDashbordBtnClicked(ActionEvent event) {
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
    void onNewsBtnClicked(ActionEvent event) {
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


}
