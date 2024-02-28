package pidev.javafx.controller.station;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pidev.javafx.crud.transport.ServicesStation;
import pidev.javafx.model.Transport.Station;
import pidev.javafx.model.Transport.Type_Vehicule;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.ResourceBundle;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class AddStationController implements Initializable {
    @FXML
    private ComboBox BoxTypeVehicule;

    @FXML
    private TextField NomStationText;

    @FXML
    private TextField AdressText;

    @FXML
    private Pane Pane;

    @FXML
    private ScrollPane mainBorderPain;
    private Connection connect;

    private PreparedStatement prepare;


    ServicesStation ss=new ServicesStation();
    String  Address;

    private static final String OPENCAGE_API_KEY = "53ee85fa919942ebb5df4021833590b4";

public void load_adress(){

   geocodeAddress(NomStationText.getText());

}

    private void geocodeAddress(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String apiUrl = "https://api.opencagedata.com/geocode/v1/json?q=" + encodedAddress + "&key=" + OPENCAGE_API_KEY;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            try (InputStream responseBody = response.body();
                 Scanner scanner = new Scanner(responseBody)) {

                StringBuilder result = new StringBuilder();
                while (scanner.hasNextLine()) {
                    result.append(scanner.nextLine());
                }

                // Parse the JSON response and extract latitude and longitude
                String[] coordinates = extractCoordinates(result.toString());
                if (coordinates.length == 2) {
                    updateResultLabel(coordinates);
                } else {
                    updateResultLabel(new String[]{"Invalid", "format"});
                }
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            updateResultLabel(new String[]{"Error", "occurred"});
        }
    }

    private String[] extractCoordinates(String jsonResponse) {
        // Parse the JSON response and extract latitude and longitude
        // This is a simple example and might not cover all cases; consider using a JSON library for robust parsing
        String[] coordinates = new String[2];

        int indexLat = jsonResponse.indexOf("\"lat\":");
        int indexLng = jsonResponse.indexOf("\"lng\":");

        if (indexLat != -1 && indexLng != -1) {
            coordinates[0] = jsonResponse.substring(indexLat + 6, jsonResponse.indexOf(",", indexLat));
            coordinates[1] = jsonResponse.substring(indexLng + 6, jsonResponse.indexOf("}", indexLng));
        }

        // Remove \u00b0 (degree symbol)
        coordinates[0] = coordinates[0].replace("\u00b0", "");
        coordinates[1] = coordinates[1].replace("\u00b0", "");
        System.out.println( coordinates[0].replace("\u00b0", "°")+coordinates[1].replace("\u00b0", "°"));

        return coordinates;
    }

    private void updateResultLabel(String[] coordinates) {
   AdressText.setText(coordinates[0]+","+coordinates[1]);
    }
    @FXML
    protected void onTextChanged() {
        String[] text = new String[10];

        text[1] = NomStationText.getText();
       // text[2]= PrixText.getText();

        if (text[1].matches("[a-zA-Z ]*"))
            NomStationText.setStyle("-fx-text-fill: #25c12c;");
        else
            NomStationText.setStyle("-fx-text-fill: #bb2020;");


    }

    @FXML
    protected  void Load_types() {

        String a;
        if(BoxTypeVehicule.getValue()==null)
            BoxTypeVehicule.getItems().addAll( Type_Vehicule.values());
    }




    @FXML
    protected void insertStation() throws IOException {
        String nomStation = NomStationText.getText();
        String address = AdressText.getText();  // Replace with the actual address or get it from a control

        Station st = new Station(nomStation, address, BoxTypeVehicule.getValue().toString());
        ss.addItem(st);

        Pane successPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Transport/added_succesfully.fxml")));

        successPane.setPrefHeight(mainBorderPain.getPrefHeight());
        successPane.setPrefWidth(mainBorderPain.getPrefWidth());
        mainBorderPain.setContent(successPane);

        PauseTransition pause = new PauseTransition(Duration.seconds(2.33));
        pause.setOnFinished(event -> {
            try {
                AnchorPane anotherPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Transport/Gui_Station/Station.fxml")));
                anotherPane.setPrefHeight(mainBorderPain.getPrefHeight());
                anotherPane.setPrefWidth(mainBorderPain.getPrefWidth());
                mainBorderPain.setContent(anotherPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        pause.play();
    }


    void mapView(){


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Load_types();
    }
}
