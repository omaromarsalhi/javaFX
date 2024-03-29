package pidev.javafx.tools.blog;

import org.json.JSONArray;
import org.json.JSONObject;
import pidev.javafx.model.blog.News;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewsDataApi {
    public static List<News> getNews () {
        List<News> newsList = new ArrayList<>();
        String apiKey = "pub_392294278cf250c575945041b139b558918a6";
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

                String jsonResponse = response.toString();
                JSONObject jsonObject = new JSONObject(jsonResponse);

                JSONArray articles = jsonObject.getJSONArray("results");

                for (int i = 0; i < articles.length(); i++) {
                    JSONObject article = articles.getJSONObject(i);
                    String sourceId = article.getString("source_id");
                    String date = article.getString("pubDate");
                    String title = article.getString("title");
                    String description = article.getString("description");
                    String imageUrl = article.optString("image_url", null);
                    String linkUrl = article.getString("link");
                    String sourceIcon = article.optString("source_icon", null);

                    newsList.add(new News (sourceId ,date, title, description, imageUrl, linkUrl, sourceIcon));
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
