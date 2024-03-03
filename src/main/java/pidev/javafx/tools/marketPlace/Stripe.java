//package pidev.javafx.tools.marketPlace;
//
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.JsonNodeFactory;
//import com.fasterxml.jackson.databind.JsonNode;
//import javafx.fxml.FXMLLoader;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public class Stripe extends Application {
//    private static final String STRIPE_API_KEY = "sk_test_51OqAGtHloj9mQYRQwtiargJpc0KfBZFlT6yi2brz8KRzRBvOfCdCGQPk9NMYllC3RqLMKFxCy7tfh9HZ9VDs9eje00F9vd1QZg";
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // Your JavaFX UI code goes here
//        primaryStage.setTitle("Stripe JavaFX Example");
////        Scene scene = new Scene(new FXMLLoader(getClass().getResource("/your_fxml_file.fxml")).load());
////        primaryStage.setScene(scene);
//        primaryStage.show();
//
//        // Call Stripe API using OkHttp
//        callStripeAPI();
//    }
//
//    private void callStripeAPI() {
//        OkHttpClient client = new OkHttpClient();
//        String endpoint = "https://api.stripe.com/v1/charges"; // Replace this with the appropriate Stripe API endpoint
//
//        double amount = Double.parseDouble("1501");
//
//        Request request = new Request.Builder()
//                .url(endpoint)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", String.format("Bearer %s", STRIPE_API_KEY))
//                .post( RequestBody.create("{\"amount\":%f,\"currency\":\"usd\",\"description\":\"My First Test Charge\"}",))
//                .build();
//
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            if (response.isSuccessful()) {
////                JsonNode node = JsonNodeFactory.mapper().readTree(response.body().string());
//                // Process Stripe API response here
//                System.out.println("Response: " + response.body().string());
//            } else {
//                System.err.println("Failed to call the Stripe API. Response code was " + response.code());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (response != null) response.close();
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
