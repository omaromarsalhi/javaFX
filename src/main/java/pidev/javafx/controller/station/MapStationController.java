package pidev.javafx.controller.station;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;
import pidev.javafx.controller.transport.transportDetailsContoller;
import pidev.javafx.crud.transport.ServicesStation;
import pidev.javafx.crud.transport.ServicesTransport;
import pidev.javafx.model.Transport.Station;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MapStationController implements Initializable {
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
    ServicesTransport st=new ServicesTransport();
    @FXML
    private VBox showItems;
    VBox vBox;
    @FXML
    private  VBox mainVbox;
    List<Transport> Transports;
    @FXML
    private AnchorPane mapAnchorPane;
    private static final String GOOGLE_MAPS_URL = "https://www.google.com/maps";
    ServicesStation ss=new ServicesStation();


    ObservableList<Station> data ;



    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // load_map();


        data = FXCollections.observableArrayList(ss.getAll());
}
@FXML
private TextArea SearchText;
    @FXML
    private AnchorPane mainanchor;
    @FXML
    private Label stationName;
    public void searchStation(){
    if (SearchText.getText().isEmpty()) {
    } else {
        ObservableList<Station> filteredStations = FXCollections.observableArrayList();
        for (Station station : data) {
            if (station.getNomStation().toLowerCase().contains(SearchText.getText().toLowerCase())) {
                mainanchor.setVisible(true);
                filteredStations.add(station);
                String[] splitStrings = filteredStations.get(0).getAddressStation().split(",");
                String string1 = splitStrings[0];
                String string2 = splitStrings[1];
                System.out.println(string1);
                System.out.println(string2);
                stationName.setText(filteredStations.get(0).getNomStation());

            }
        }
    }
    }
    public void updateMap(String lon,String lat){

    }

    public void allStationsMap(){


    }

        public void Onclick(){
        load("/fxml/Transport/Gui_Station/ListeTransport.fxml");

    }
    private void load(String fxmlPath) {
        try {
            Node content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            ListeClient.getChildren().setAll(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String[] texts = {"Avaible ", "8:00PM"};
    private int currentIndex = 0;




}





