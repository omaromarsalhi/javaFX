package pidev.javafx.controller.station;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.json.JSONException;
import org.json.JSONObject;


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
    private static final String API_URL = "https://api.open-meteo.com/v1/forecast?latitude=24.656426&longitude=45.739876&current=temperature_2m,wind_speed_10m,relative_humidity_2m";
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficher_meteo();
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
