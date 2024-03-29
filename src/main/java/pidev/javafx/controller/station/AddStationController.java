package pidev.javafx.controller.station;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONException;
import org.json.JSONObject;
import pidev.javafx.crud.transport.ServicesStation;
import pidev.javafx.model.Transport.Station;
import pidev.javafx.model.Transport.Type_Vehicule;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.io.InputStream;
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
    private ScrollPane mainBorderPain;
    ServicesStation ss=new ServicesStation();
    String  Address;
    private static final String OPENCAGE_API_KEY = "53ee85fa919942ebb5df4021833590b4";
    private Stage primaryStage;
    public void load_adress(){
   geocodeAddress(NomStationText.getText());
}


    @FXML
    private ImageView Image;

String imagePath;
    public void insert_Image(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        var selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            imagePath=selectedFile.getAbsolutePath() ;
            Image image = new Image(imagePath);
            Image.setFitHeight(114);
            Image.setFitWidth(114);
            Image.setImage(image);
        }
    }
    private  void geocodeAddress(String address) {
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
                    AdressText.setText(coordinates[0]+","+coordinates[1]);
                } else {
                    AdressText.setText(Arrays.toString(new String[]{"Invalid", "format"}));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            AdressText.setText(Arrays.toString(new String[]{"Error", "occurred"}));
        }
    }

    private static String[] extractCoordinates(String jsonResponse) throws JSONException {
        JSONObject json = new JSONObject(jsonResponse);
        JSONObject firstResult = json.getJSONArray("results").optJSONObject(0);

        String[] coordinates = new String[2];

        if (firstResult != null) {
            JSONObject geometry = firstResult.optJSONObject("geometry");
            if (geometry != null) {
                coordinates[0] = geometry.optString("lat", "N/A");
                coordinates[1] = geometry.optString("lng", "N/A");
            }
        }
        coordinates[0] = coordinates[0].replace("\u00b0", "");
        coordinates[1] = coordinates[1].replace("\u00b0", "");

        return coordinates;
    }


    @FXML
    protected void onTextChanged() {
        String[] text = new String[10];

        text[1] = NomStationText.getText();

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
        String address = AdressText.getText();

        Station st = new Station(nomStation,imagePath,address,  BoxTypeVehicule.getValue().toString());
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
