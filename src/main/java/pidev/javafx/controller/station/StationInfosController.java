package pidev.javafx.controller.station;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.json.JSONException;
import org.json.JSONObject;
import pidev.javafx.model.Transport.Station;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.transport.DataHolder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class StationInfosController implements Initializable {

    @FXML
    private Label temperatureLabel ;
    @FXML
    private Label windLabel;
    @FXML
    private Label humidityLabel;

    private  String API_URL;
     static String lon;
     static String lat;
    public  double[] extractLatLon(String coordinates) {
        String[] parts = coordinates.split(",");
        double[] result = new double[2];
        if (parts.length == 2) {
            try {
                result[0] = Double.parseDouble(parts[0].trim()); // Latitude
                result[1] = Double.parseDouble(parts[1].trim()); // Longitude
              lon=  Double.toString(result[0]);
               lat = Double.toString(result[1]);
                API_URL = "https://api.open-meteo.com/v1/forecast?latitude="+lon+"&longitude="+lat+"&current=temperature_2m,wind_speed_10m,relative_humidity_2m";
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Handle the case where parsing fails, e.g., invalid format
            }
        } else {
            // Handle the case where the input string is not in the expected format
            System.err.println("Invalid coordinates format");
        }

        return result;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        first_function();
        afficher_meteo();
    }
    double[] S ;

    public void first_function() {
        String address= DataHolder.getStation().getAddressStation();
        if(address!=null)
            S = extractLatLon(address);
    }


    private String fetchWeatherData(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(response.toString());
                response.append(line);
            }

            reader.close();
            return response.toString();
        } else {
            throw new IOException("Failed to fetch data. Response code: " + responseCode);
        }
    }




    public void afficher_meteo(){
        try {
            String jsonResponse = fetchWeatherData(API_URL);

            JSONObject jsonObject = new JSONObject(jsonResponse);

            double temperature2m = jsonObject.getJSONObject("current").getDouble("temperature_2m");

            double windSpeed10m = jsonObject.getJSONObject("current").getDouble("wind_speed_10m");

            int relativeHumidity2m = jsonObject.getJSONObject("current").getInt("relative_humidity_2m");

            if(temperatureLabel!=null)
                temperatureLabel.setText(String.valueOf(temperature2m)+"°C");
            if(windLabel!=null)
                windLabel.setText(String.valueOf(windSpeed10m)+"km/h");
            if(humidityLabel!=null)
                humidityLabel.setText(String.valueOf(relativeHumidity2m)+"%");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };
}
