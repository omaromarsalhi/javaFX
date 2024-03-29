package pidev.javafx.tools.marketPlace;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ChatGPTAPIDescriber {
    public static String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";
//        String model = "gpt-3.5-turbo-instruct-0914";

        System.out.println(prompt);
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // Create the request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \""+prompt+" \"}]}";
            connection.setDoOutput(true);


            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Read the response
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // Extract the message from the JSON response
            return extractMessageFromJSONResponse(response.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getJSONArray( "choices" ).getJSONObject( 0 ).getJSONObject( "message" ).getString( "content" );
    }

//    public static void main(String[] args) {
////        String prompt = "Hello, how can I assist you?";
//        String prompt = "describe mercides benz s class car for sale specified its benefits";
//        System.out.println(chatGPT(prompt));
//    }
}

