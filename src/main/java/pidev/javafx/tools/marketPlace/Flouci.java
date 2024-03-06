package pidev.javafx.tools.marketPlace;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Flouci{

    public static String  PayFlouci(double amount) {

        JSONObject jsonBody = new JSONObject();
        jsonBody.put( "app_token", "48b8c8a8-e891-417a-8f3c-1bdc2748b519" );
        jsonBody.put( "app_secret", "f7783305-c1f9-43d1-abaa-171f79757cfc" );
        jsonBody.put( "accept_card", true );
        jsonBody.put( "amount",amount);
        jsonBody.put( "success_link", "https://ruperhat.com/wp-content/uploads/2020/06/Paymentsuccessful21.png" );
        jsonBody.put( "fail_link", "https://hypixel.net/attachments/1690923493412-png.3230490/" );
        jsonBody.put( "session_timeout_secs", 1200 );
        jsonBody.put( "developer_tracking_id", "df9dd458-65ed-4d8b-b354-302077358ef2" );


        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse( "application/json" );
        RequestBody body = RequestBody.create( mediaType, jsonBody.toString() );
        Request request = new Request.Builder()
                .url( "https://developers.flouci.com/api/generate_payment" )
                .method( "POST", body )
                .addHeader( "Content-Type", "application/json" )
                .build();
        try {
            Response response = client.newCall( request ).execute();
            if (response.isSuccessful()) {
                System.out.println( "Payment generated successfully" );
                String responseBody = response.body().string();
                return responseBody;
            } else {
                System.out.println( "Error generating payment: " + response.code() );
                System.out.println( response.body().string() );
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException( e );
        }
        return null;
    }

}
