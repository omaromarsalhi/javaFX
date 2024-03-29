package pidev.javafx.tools.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.auth.oauth2.AccessToken;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.auth.oauth2.ServiceAccountCredentials;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.concurrent.Worker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import netscape.javascript.JSObject;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.gson.GsonFactory;

import pidev.javafx.controller.login.LoginSignupController;
import pidev.javafx.controller.login.NewLogInController;

import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleApi {

    private static final String CLIENT_ID = "529672508780-tn5i6m5r03pkv4boi5c4qr1hp0cispnp.apps.googleusercontent.com"; // Replace with your actual client ID
    private static final String CLIENT_SECRET = "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"; // Replace with your actual client secret
    private static final String REDIRECT_URI = "http://localhost:8080"; // Replace with your redirect URI
    private static final String TOKEN_ENDPOINT = "https://www.googleapis.com/oauth2/v4/token";
    private static final String USER_INFO_ENDPOINT = "https://www.googleapis.com/oauth2/v1/userinfo";

    public WebView AccessTokenFetcher() {


        WebView webView = new WebView();
        webView.getEngine().load("https://accounts.google.com/o/oauth2/auth?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI + "&response_type=code&scope=email%20profile");

        webView.getEngine().getLoadWorker().stateProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        // Extract the code from the URL
                        JSObject window = (JSObject) webView.getEngine().executeScript("window");
                        webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                            @Override
                            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {

                                    JSObject buttons = (JSObject) webView.getEngine().executeScript("document.querySelectorAll('button')");
                                     for (int i = 0; i < Integer.parseInt(buttons.getMember("length").toString()); i++){
                                        JSObject button = (JSObject) buttons.getSlot(i);
                                        System.out.println("Button " + i + ": " + button.getMember("textContent") + " (" + button.getMember("id") + ")");
                                    }

                            }
                        });
                        String url = webView.getEngine().getLocation();

                        if (url.startsWith(REDIRECT_URI)) {
                            if (url.contains("code=")) {

                                String code = url.substring(url.indexOf("code=") + 5);

                                String idTokenString = exchangeCodeForAccessToken(code); // Replace with your actual access token

                                idTokenString = idTokenString.substring(idTokenString.indexOf("id_token\": ") + 12, idTokenString.indexOf("\"}"));

                                GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                                        new NetHttpTransport(),
                                        new GsonFactory()
                                ).setAudience(Collections.singletonList("529672508780-tn5i6m5r03pkv4boi5c4qr1hp0cispnp.apps.googleusercontent.com")).build();

                                GoogleIdToken idToken = null;
                                try {
                                    idToken = verifier.verify(idTokenString);
                                } catch (GeneralSecurityException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                if (idToken != null) {
                                    String userId = idToken.getPayload().getSubject();

                                    FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/fxml/user/LoginSignup.fxml"));
                                    Scene scene = null;
                                    try {
                                        scene = new Scene(fxmlLoader.load());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                   NewLogInController newLogInController = fxmlLoader.getController();
                                    newLogInController.display(idToken.getPayload().getEmail(),idToken.getPayload().get("given_name").toString(),idToken.getPayload().get("family_name").toString());

                                    scene.getStylesheets().add(String.valueOf(getClass().getResource("/style/user/newLogIn.css")));
                                    Stage stage;
                                    stage = (Stage) webView.getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.close();
                                    windowController.addWindow(stage);
                                    System.out.println(windowController.windows.size());


                                    stage.show();
                                    // Extract other user data as needed
                                } else {
                                    System.out.println("Invalid ID token");
                                }

                            }
                        }
                    }
                });

        return webView ;
    }


    public String exchangeCodeForAccessToken(String code) {
        try {
            URL url = new URL(TOKEN_ENDPOINT);
            System.out.println(url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            String postData = "client_id=" + CLIENT_ID +
                    "&client_secret=" + CLIENT_SECRET +
                    "&grant_type=authorization_code" +
                    "&code=" + code +
                    "&redirect_uri=" + REDIRECT_URI;
            connection.getOutputStream().write(postData.getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}


