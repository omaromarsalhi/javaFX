package pidev.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pidev.javafx.controller.contrat.CheckOutController;
import pidev.javafx.tools.CustomMouseEvent;
import pidev.javafx.tools.EventBus;
import pidev.javafx.model.MarketPlace.Bien;

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
    private Button MPD;
    @FXML
    private BorderPane mainBorderPain;
    @FXML
    private AnchorPane MainAnchorPane;
    @FXML
    private MenuButton menubottons;


    private HBox mainhBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventBus.getInstance().subscribe( "laodCheckOut",this::laodCheckOut );
        EventBus.getInstance().subscribe( "laodMarketPlace",this::onMarketPlaceBtnClicked );
    }
//    btns that changes the scenes
    @FXML
    public void onShowEmpClicked(ActionEvent event) throws IOException {
        VBox showEmpAnchorPane = FXMLLoader.load( Objects.requireNonNull( getClass().getResource( "/fxml/marketPlace/showItems.fxml" ) ) );
        mainBorderPain.setCenter(showEmpAnchorPane);
    }

    @FXML
    public void onMPDClicked(ActionEvent event) throws IOException {
        HBox hBox = FXMLLoader.load(getClass().getResource( "/fxml/userMarketDashbord/userMainDashbord.fxml" ));
        mainBorderPain.setCenter(hBox);
    }

    @FXML
    public void onMarketPlaceBtnClicked(ActionEvent event){
        mainBorderPain.getChildren().remove(mainhBox);
        try {
            mainhBox = FXMLLoader.load(getClass().getResource( "/fxml/marketPlace/myMarket.fxml" ));
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        mainhBox.setMaxHeight(MainAnchorPane.getPrefHeight()  );
        mainhBox.setMaxWidth( MainAnchorPane.getPrefWidth());
        mainBorderPain.setCenter(mainhBox);
    }

    public void laodCheckOut(CustomMouseEvent<Bien> event) {
        mainBorderPain.getChildren().remove(mainhBox);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/Contrat/checkOut.fxml"));
            mainhBox = fxmlLoader.load();
            CheckOutController checkOutController = fxmlLoader.getController();
            checkOutController.setData(event.getEventData());
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        mainhBox.setMaxHeight(MainAnchorPane.getPrefHeight()  );
        mainhBox.setMaxWidth( MainAnchorPane.getPrefWidth());
        mainBorderPain.setCenter(mainhBox);
    }

}
