package pidev.javafx.controller.station;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.json.JSONException;
import org.json.JSONObject;
import pidev.javafx.controller.transport.transportDetailsContoller;
import pidev.javafx.crud.transport.ServicesTransport;
import pidev.javafx.model.Transport.Transport;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import pidev.javafx.tools.marketPlace.MyTools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MapStationController  {
    @FXML
    private AnchorPane ListeClient;
    @FXML
    private ToggleButton toggleButton1;
    @FXML
    private ToggleButton toggleButton2;
    @FXML
    private ToggleButton toggleButton3;
    @FXML
    private ToggleButton toggleButton4;
    @FXML
    private VBox TransportBox;
    @FXML
    private Label aaa;
    @FXML
    private Label label;
    @FXML
    private Label Arrive_Status;
    @FXML
    private Pane detailsTransport;
    @FXML
    private ToggleButton dropToggle;
    @FXML
    private VBox showItems;
    @FXML
    private VBox vBox;
    @FXML
    private  VBox mainVbox;
    @FXML
    private AnchorPane mapAnchorPane;



    ServicesTransport st=new ServicesTransport();
    List<Transport> Transports;
    private static final String GOOGLE_MAPS_URL = "https://www.google.com/maps";
    private static final String[] texts = {"Avaible ", "8:00PM"};
    private int currentIndex = 0;


    public void Onclick(){
        load("/fxml/Transport/Gui_Station/ListeTransport.fxml");
    }


    private void load(String fxmlPath) {
        try {
            VBox content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            ListeClient.getChildren().setAll(content);
            MyTools.getInstance().showAnimation( content );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




//    public void showDetailsPane(Set<Transport> transportSet) throws IOException {
//    Set<Transport> T = new HashSet<>();
//        T = st.getAll();
//
//        for (Transport transport : transportSet) {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Transport/Gui_Station/transportDetails.fxml"));
//                AnchorPane anchorPane = loader.load();
//
//                transportDetailsContoller itemController = loader.getController();
//                itemController.setData(transport);
//
//                mainVbox.getChildren().add(anchorPane);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Transport/Gui_Station/transportDetails.fxml"));
//            AnchorPane anchorPane = loader.load();
//            transportDetailsContoller itemController = fxmlLoader.getController();
//            itemController.setData(transport);
//            mainVbox.getChildren().add(anchorPane);
//        }
//    }

}





