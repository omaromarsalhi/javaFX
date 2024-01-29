package pidev.javafx.Controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private boolean isSideBarOpen;
    @FXML
    private Button sideBarBtn;
    @FXML
    private Button showEmp;
    @FXML
    private Button btn2;
    @FXML
    private Button marketPlaceBtn;
    @FXML
    private VBox sideBar;
    @FXML
    private VBox zipSideBar;
    @FXML
    private BorderPane mainBorderPain;
    @FXML
    private AnchorPane leftAnchorPane;
    @FXML
    private MenuButton menubottons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
//    btns that changes the scenes
    @FXML
    public void onShowEmpClicked(ActionEvent event) throws IOException {
        VBox showEmpAnchorPane = FXMLLoader.load( Objects.requireNonNull( getClass().getResource( "/fxml/marketPlace/showItems.fxml" ) ) );
        mainBorderPain.setCenter(showEmpAnchorPane);
    }

    @FXML
    public void onBtn2Clicked(ActionEvent event) throws IOException {
        AnchorPane showEmpAnchorPane = FXMLLoader.load(getClass().getResource( "/fxml/Employe/btn2.fxml" ));
        mainBorderPain.setCenter(showEmpAnchorPane);
    }

    @FXML
    public void onMarketPlaceBtnClicked(ActionEvent event) throws IOException {
        ScrollPane scrollPane = FXMLLoader.load(getClass().getResource( "/fxml/marketPlace/myMarket.fxml" ));
        scrollPane.setPrefHeight(mainBorderPain.getPrefHeight()  );
        scrollPane.setPrefWidth( mainBorderPain.getPrefWidth()-sideBar.getPrefWidth() );
        mainBorderPain.setCenter(scrollPane);
    }

}
