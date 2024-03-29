package pidev.javafx.controller.user;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.FileWriter;
import java.net.URLEncoder;

public class NominatimGeocoderController {
    public static void geocodeAddress(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            System.out.println(encodedAddress);
            String url = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json&dedupe=1&addressdetails=1";
            System.out.println(url);



            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);

            String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
            // Enregistrer la réponse JSON dans un fichier
            try (FileWriter fileWriter = new FileWriter("response.json")) {
                fileWriter.write(jsonResponse);
                System.out.println("Fichier JSON enregistré avec succès.");
            }
         catch (IOException e) {
            e.printStackTrace();
        }
            JSONArray jsonArray = new JSONArray(jsonResponse);
            System.out.println(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);

                // Afficher le nom de la municipalité à partir du display_name
                if (result.has("display_name")) {
                    String displayName = result.getString("display_name");
                    String[] parts = displayName.split(",");

                    if (parts.length >= 3) {
                        String municipality = parts[2].trim();
                        System.out.println("Municipalité : " + municipality);
                        break;
                    } else {
                        System.out.println("Municipalité non disponible.");
                    }
                } else {
                    System.out.println("Nom complet de l'adresse non disponible.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
