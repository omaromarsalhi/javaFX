package pidev.javafx.tools.marketPlace;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class VisionApiExample {
//    public static void main(String[] args) throws IOException {
//        // Load your service account credentials (JSON key file)
//        String credentialsPath = "src/main/resources/apiKey/citizenhub-416011-3e7a17f282e6.json";
//        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
//                .setCredentialsProvider(() -> ServiceAccountCredentials.fromStream(Files.newInputStream(Paths.get(credentialsPath))))
//                .build();
//
//        // Initialize the client
//        try (ImageAnnotatorClient visionClient = ImageAnnotatorClient.create(settings)) {
//            // Read an image file
//            Path imagePath = Paths.get("src/main/resources/img/marketPlace/banana.png");
//            ByteString imageBytes = ByteString.copyFrom(Files.readAllBytes(imagePath));
//
//            // Create an image request
//            Image image = Image.newBuilder().setContent(imageBytes).build();
//            Feature feature = Feature.newBuilder().setType(Feature.Type.IMAGE_PROPERTIES).build();
//            AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image).build();
//
//            // Perform the analysis
//            BatchAnnotateImagesResponse response = visionClient.batchAnnotateImages( Collections.singletonList( request ) );
//            ImageProperties properties = response.getResponses(0).getImagePropertiesAnnotation();
//
//            // Extract image properties (e.g., dominant colors)
//            for (ColorInfo color : properties.getDominantColors().getColorsList()) {
//                System.out.println("Color: " + color.getColor().getRed() + ", " + color.getColor().getGreen() + ", " + color.getColor().getBlue());
//            }
//        }
//    }
}

