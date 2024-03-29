package pidev.javafx.tools.user;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GeocodingAi {
    public   static  String getAddressInformation(String adresse) {
        String API_KEY = "AIzaSyC_T-LX7HSxtA_4NkvIw1dBmjA0Lf2KPrk";

        String url;
        try {
            url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(adresse, "UTF-8") + "&key=" + API_KEY;

            // Effectuer la requête HTTP GET
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            // Lire la réponse
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
             //   System.out.println(response);
            }
            reader.close();

            // Parsez la réponse JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray results = jsonResponse.getJSONArray("results");
            System.out.println(results);
            if (results.length() > 0) {
                JSONObject addressInfo = results.getJSONObject(0);
                JSONArray addressComponents = addressInfo.getJSONArray("address_components");


                String country = "";
                String city = "";
                String postalCode = "";
                String municipality="";

                for (int i = 0; i < addressComponents.length(); i++) {

                    JSONObject component = addressComponents.getJSONObject(i);
                    JSONArray types = component.getJSONArray("types");
                    String longName = component.getString("long_name");

                    // Vérifier les types de composants et extraire les informations pertinentes
                    for (int j = 0; j < types.length(); j++) {
                        String type = types.getString(j);
                        if (type.equals("country")) {
                            country = longName;

                        } else if (type.equals("locality")) {
                             municipality = component.getString("long_name");
                        } else if (type.equals("postal_code")) {

                            postalCode = longName;
                        }


                    }
                }
                return municipality;
//                System.out.println(country);
//                System.out.println(city);
//                System.out.println(postalCode);
//                System.out.println(municipality);
                // Afficher les informations dans l'interface utilisateur

            } else {
                System.out.println("No information found for the address.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred while fetching information.");

        }
        return "";
    }
}

