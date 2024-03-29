package pidev.javafx.controller.station;


import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import pidev.javafx.controller.marketPlace.ItemInfoController;
import pidev.javafx.model.MarketPlace.Product;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.crud.transport.ServicesStation;
import pidev.javafx.crud.transport.ServicesTransport;
import pidev.javafx.model.Transport.Station;
import pidev.javafx.model.Transport.Transport;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.transport.DataHolder;

import java.io.IOException;
import java.net.URL;
import java.util.*;


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
    @FXML
    private Button searchBtn;
    @FXML
    private HBox searchHbox;
    @FXML
    private TextField searchTextField;

    @FXML
    private WebView webView;
    @FXML
    private AnchorPane mainanchor;
    @FXML
    private Label stationName;
    @FXML
    private Label stationName1;

    @FXML
    private AnchorPane container1;

    @FXML
    private AnchorPane container2;


    private static final String[] texts = {"Avaible ", "8:00PM"};
    ServicesTransport st = new ServicesTransport();
    private VBox infoTemplate;
    private Timer animTimer;
    private String searchBarState;





    ServicesStation ss = new ServicesStation();

    private static final String API_KEY = "AIzaSyC_T-LX7HSxtA_4NkvIw1dBmjA0Lf2KPrk";

    ObservableList<Station> data;
    @FXML
    private Station receivedStation;
    @FXML
    private Station receivedStation1;
    private WebEngine webEngine;
    @FXML
    private AnchorPane mainanchor1;

    public void openTitle(CustomMouseEvent event){

        container2.toBack();
        container1.toFront();
        mainanchor.setVisible(true);
//        listAnchor.toFront();
//        listAnchor.setVisible(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventBus.getInstance().subscribe("close_List", this::openTitle);
        data = FXCollections.observableArrayList(ss.getAll());
        EventBus.getInstance().subscribe("sendTransport", this::handleCustomEvent);
        showAddresses();
        searchBarState="closed";
        animTimer = new Timer();
        searchTextField.setVisible( false );
        searchBtn.setStyle( "-fx-border-radius: 20;" +
                "-fx-background-radius:20;" );

        searchBtn.setOnMouseClicked(event ->{
            if(searchBarState.equals("opened")&&!searchTextField.getText().isEmpty())
                handleSendStationButtonClick();
            else
                animateSearchBar();
        } );
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



    private void handleCustomEvent(CustomMouseEvent event) {
        Transport eventData = (Transport) event.getEventData();
        System.out.println(eventData.getStation_arrive());
        showPathbetweenStations();

//        return data.filtered(element -> element.getNomStation().equals(((Transport) event.getEventData()).getDepart())).stream().findFirst().orElse(null);


    }


    public void showPathbetweenStations(){

        WebEngine webEngine = webView.getEngine();

        // HTML content with embedded JavaScript
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Google Maps Directions</title>\n" +
                "    <script src=\"https://maps.googleapis.com/maps/api/js?key=" + API_KEY + "&callback=initMap\" async defer></script>\n" +
                "    <style>\n" +
                "      #map {\n" +
                "        height: 660px;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id=\"map\"></div>\n" +
                "    <script>\n" +
                "      function initMap() {\n" +
                "        var map = new google.maps.Map(document.getElementById('map'), {\n" +
                "          center: { lat: "+    36+", lng: "+10+" },\n" +
                "          zoom: 15\n" +
                "        });\n" +
                "\n" +
                "        var directionsService = new google.maps.DirectionsService();\n" +
                "        var directionsDisplay = new google.maps.DirectionsRenderer();\n" +
                "        directionsDisplay.setMap(map);\n" +
                "\n" +
                "        var start = new google.maps.LatLng("+36+","+10+" );  // Replace with your start location\n" +
                "        var end = new google.maps.LatLng("+37+","+10+" );    // Replace with your end location\n" +
                "\n" +
                "        var request = {\n" +
                "          origin: start,\n" +
                "          destination: end,\n" +
                "          travelMode: 'DRIVING'\n" +
                "        };\n" +
                "\n" +
                "        directionsService.route(request, function(result, status) {\n" +
                "          if (status == 'OK') {\n" +
                "            directionsDisplay.setDirections(result);\n" +
                "          }\n" +
                "        });\n" +
                "      }\n" +
                "    </script>\n" +
                "  </body>\n" +
                "</html>";

        webEngine.loadContent(htmlContent);

    }

    private static double[][] extractAndAddToStations(List<Station> stationList) {
        List<double[]> updatedStationsList = new ArrayList<>();

        for (Station station : stationList) {
            String[] latLong = station.getAddressStation().split(",");
            if (latLong.length == 2) {
                try {
                    double latitude = Double.parseDouble(latLong[0]);
                    double longitude = Double.parseDouble(latLong[1]);
                    updatedStationsList.add(new double[]{latitude, longitude});
                } catch (NumberFormatException e) {
                    // Handle parsing errors if necessary
                    e.printStackTrace();
                }
            }
        }
        return updatedStationsList.toArray(new double[0][0]);
    }


    public void showAddresses() {
        double[][] updatedStations = extractAndAddToStations(data);

        WebEngine webEngine = webView.getEngine();

        // Load a simple HTML file that includes Google Maps with markers
        StringBuilder htmlContent = new StringBuilder("<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Map with Markers</title>\n" +
                "    <script src=\"https://maps.googleapis.com/maps/api/js?key=" + API_KEY + "&callback=initMap\" async defer></script>\n" +
                "    <script>\n" +
                "      function initMap() {\n" +
                "        var map = new google.maps.Map(document.getElementById('map'), {\n" +
                "          center: { lat: " + updatedStations[0][0] + ", lng: " + updatedStations[0][1] + " },\n" +
                "          zoom: 10\n" +
                "        });\n");

        // Add markers for each station
        for (int i = 0; i < updatedStations.length; i++) {
            htmlContent.append("        var marker").append(i).append(" = new google.maps.Marker({\n")
                    .append("          position: { lat: ").append(updatedStations[i][0]).append(", lng: ").append(updatedStations[i][1]).append(" },\n")
                    .append("          map: map,\n")
                    .append("          title: 'Station ").append(i + 1).append("'\n")
                    .append("        });\n");
        }

        // Close the script block and body
        htmlContent.append("      }\n" +
                "    </script>\n" +
                "  </head>\n" +
                "  <body onload=\"initMap()\">\n" +
                "    <div id=\"map\" style=\"height: 660px;\"></div>\n" +
                "  </body>\n" +
                "</html>");

        webEngine.loadContent(htmlContent.toString());
    }





    private void handleSendStationButtonClick() {
        if (!searchTextField.getText().isEmpty()) {

            mainanchor.setVisible(true);
            mainanchor.toFront();
            ObservableList<Station> filteredStations = FXCollections.observableArrayList();
            for (Station station : data) {
                if (station.getNomStation().toLowerCase().contains(searchTextField.getText().toLowerCase())) {
                    filteredStations.add(station);
                    String[] splitStrings = filteredStations.get(0).getAddressStation().split(",");
                    String string1 = splitStrings[0];
                    String string2 = splitStrings[1];
                    stationName.setText(filteredStations.get(0).getNomStation());
                    receivedStation = filteredStations.get(0);
                    double lat = Double.parseDouble(string1);
                    double lon = Double.parseDouble(string2);
                    showStation(lat,lon);

                    DataHolder.setStation(receivedStation);

                }
            }

        }
    }


    public void showStation(Double a, Double b) {
        webEngine = webView.getEngine();
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Map with Markers</title>\n" +
                "    <script src=\"https://maps.googleapis.com/maps/api/js?key=" + API_KEY + "&callback=initMap\" async defer></script>\n" +
                "    <script>\n" +
                "      function initMap() {\n" +
                "        var map = new google.maps.Map(document.getElementById('map'), {\n" +
                "          center: { lat: " + a + ", lng: " + b + " },\n" +
                "          zoom: 15\n" +
                "        });\n" +
                "        var marker = new google.maps.Marker({\n" +
                "          position: { lat: " + a + ", lng: " + b + " },\n" +
                "          map: map,\n" +
                "          title: 'Marker 1'\n" +
                "        });\n" +
                "      }\n" +
                "    </script>\n" +
                "  </head>\n" +
                "  <body onload=\"initMap()\">\n" +
                "    <div id=\"map\" style=\"height: 670px;\"></div>\n" +
                "  </body>\n" +
                "</html>";

        webEngine.loadContent(htmlContent);
    }

    private AnchorPane save_pane;
    public void Onclick() {

        mainanchor.setVisible(false);
        container1.toBack();
        load("/fxml/Transport/Gui_Station/ListeTransport.fxml");
//        EventBus.getInstance().publish("StationEvent", new CustomMouseEvent<Station>(receivedStation));
    }

    @FXML
    private AnchorPane listAnchor;

    private void load(String fxmlPath) {
        try {
            Node content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            listAnchor.setVisible(true);
            listAnchor.getChildren().setAll(content);
            container2.toFront();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}






