package pidev.javafx.controller.reclamation;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class video {
    private static final String API_KEY = "";

        public static void analyzeImage(String imagePath) {
            try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
                ByteString imgBytes = ByteString.readFrom(new FileInputStream(imagePath));
                Image img = Image.newBuilder().setContent(imgBytes).build();

                List<Feature> featureList = new ArrayList<>();
                featureList.add(Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build());
                featureList.add(Feature.newBuilder().setType(Feature.Type.FACE_DETECTION).build());

                ImageContext imageContext = ImageContext.newBuilder().addWebDetectionParams(API_KEY).build();

                AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                        .setImage(img)
                        .addAllFeatures(featureList)
                        .setImageContext(imageContext)
                        .build();

                List<AnnotateImageRequest> requestList = new ArrayList<>();
                requestList.add(request);

                BatchAnnotateImagesRequest batchRequest = BatchAnnotateImagesRequest.newBuilder()
                        .addAllRequests(requestList)
                        .build();

                BatchAnnotateImagesResponse batchResponse = vision.batchAnnotateImages(batchRequest);

                for (AnnotateImageResponse response : batchResponse.getResponsesList()) {
                    for (EntityAnnotation textAnnotation : response.getTextAnnotationsList()) {
                        System.out.println("Texte détecté : " + textAnnotation.getDescription());
                    }

                    for (FaceAnnotation locationInfo : response.getFaceAnnotationsList()) {
                        System.out.println("Visage détecté à la position : " + locationInfo.getBoundingPoly());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Exemple d'utilisation
        public static void main(String[] args) {
            String imagePath = "chemin/vers/votre/image.jpg";
            analyzeImage(imagePath);
        }

}
