package pidev.javafx.Utils;

import org.json.JSONArray;
import org.json.JSONObject;
import pidev.javafx.Models.News;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewsDataApi {
    public List<News> getNews ()

    {
        List<News> newsList = new ArrayList<>();
        String apiKey = "pub_392294278cf250c575945041b139b558918a6"; // Remplacez par votre clé API
        String apiUrl = "https://newsdata.io/api/1/news?apikey=" + apiKey + "&country=tn&language=fr";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                // Convertir la réponse JSON en objet JSON
                String jsonResponse = response.toString();
                JSONObject jsonObject = new JSONObject(jsonResponse);

                // Extraire la liste des articles
                JSONArray articles = jsonObject.getJSONArray("results");

                // Parcourir chaque article et imprimer le titre
                for (int i = 0; i < articles.length(); i++) {
                    JSONObject article = articles.getJSONObject(i);
//String sourceIcon = null;
                    String date = article.getString("pubDate");
                    String title = article.getString("title");
                    String description = article.getString("description");
                    String imageUrl = article.getString("image_url");
                    String linkUrl = article.getString("link");
                    //if (!article.isNull("source_icon"))
                    //String sourceIcon = article.getString("source_icon");
                    newsList.add(new News (date, title, description, imageUrl, linkUrl));
                }

            } else {
                System.out.println("Erreur lors de la récupération des données. Code d'état : " + responseCode);
            }
            return newsList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
