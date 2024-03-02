package pidev.javafx.controller.reclamation;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import pidev.javafx.crud.reclamation.DataSource;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.reclamation.Reclamation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;



public class AjouterReclamation {

    @FXML
    private Button importButton;
    @FXML
    private AnchorPane mainBorderPain;

    @FXML
    private TextField privateKey;
    @FXML
    private TextField title;
    @FXML
    private ImageView Image;
//    @FXML
//    private TextField subject;

    @FXML
    private TextArea description;
    @FXML
    public ComboBox ChoixMul;
    @FXML
    private Stage primaryStage;
    String getImagePath;
    @FXML
    ServiceReclamation si = new ServiceReclamation();
    //String imagePath;
    @FXML
    protected void onTextChanged() {
        String[] text = new String[10];

        text[1] = title.getText();
        text[2]= description.getText();

        if (text[1].matches("[a-zA-Z]*"))
            title.setStyle("-fx-text-fill: #25c12c;");
        else
            title.setStyle("-fx-text-fill: #bb2020;");

        if (text[2].matches("[a-zA-Z0-9 ]+"))
            description.setStyle("-fx-text-fill: #25c12c");
        else
            description.setStyle("-fx-text-fill: #bb2020 ");
    }
    private String imagePath;

    @FXML
    void ajouter_Reclamation() throws IOException {
        String selectedSubject = initialize();

        String generatedString = generateRandomString(20);
        privateKey.setText(generatedString);
        System.out.println(generatedString);
        if(imagePath==null)
        {
        }

        // Call the onTextChanged function here
        onTextChanged();

        // Check if the text fields are valid
        if (title.getStyle().equals("-fx-text-fill: #25c12c;") && description.getStyle().equals("-fx-text-fill: #25c12c")) {
            Reclamation rec = new Reclamation(privateKey.getText(), selectedSubject, title.getText(), description.getText(), imagePath);
            si.ajouter(rec);
            System.out.println(imagePath);
            // Generate a QR code for the reclamation
            String data = URLEncoder.encode(rec.toString(), StandardCharsets.UTF_8); // URL-encode the data
            String size = "200x200"; // Replace this with the desired size of the QR code
            String url = "https://api.qrserver.com/v1/create-qr-code/?data=" + data + "&size=" + size;

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            String qrCodeFilePath = "src/main/resources/reclamation/" + generatedString + ".png";
            try (OutputStream os = new FileOutputStream(qrCodeFilePath)) {
                response.getEntity().writeTo(os);
            }
            Stage popupStage = new Stage();
            ImageView qrCodeImageView = new ImageView(new Image("file:" + qrCodeFilePath));
            StackPane layout = new StackPane();
            layout.getChildren().add(qrCodeImageView);
            Scene scene = new Scene(layout);
            popupStage.setScene(scene);
            popupStage.showAndWait();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Reclamation has been added successfully!");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Verification failed. Please check your input.");
            // Show the alert
            alert.show();
        }
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb;
        String randomString;

        do {
            sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = rnd.nextInt(characters.length());
                sb.append(characters.charAt(index));
            }
            randomString = sb.toString();

            if (!doesExist(randomString)) {
                break;
            }
        } while (true);
        return randomString;
    }

    public boolean doesExist(String randomString) {
        Set<Reclamation> reclamations = getAll();

        for (Reclamation rec : reclamations) {
            if (rec.getPrivateKey().equals(randomString)) {
                return true;
            }
        }

        return false;
    }

    Connection cnx = DataSource.getInstance().getCnx();

    public Set<Reclamation> getAll() {
        Set<Reclamation> reclamations = new HashSet<>();
        String req = "SELECT * FROM `reclamation`";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reclamations.add(new Reclamation(rs.getString("privateKey"), rs.getString("subject"), rs.getString("titre"), rs.getDate("date"), rs.getString("description")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamations;
    }

    public String initialize() {

        ChoixMul.getItems().addAll("Problem technique", "application", "testt", "omar salhi", "khalil rmila ");
        return (String) ChoixMul.getSelectionModel().getSelectedItem();
    }
    public void clear(ActionEvent event) throws IOException {
        ScrollPane scrollPane = FXMLLoader.load(Objects.requireNonNull( getClass().getResource("/fxml/reclamation/testt.fxml")));
        scrollPane.setPrefHeight(mainBorderPain.getPrefHeight()  );
        scrollPane.setPrefWidth( mainBorderPain.getPrefWidth() );
        mainBorderPain.getChildren().setAll(scrollPane);
    }
    public void insert_Image(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        var selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            imagePath=selectedFile.getAbsolutePath() ;

            Image image = new Image(imagePath);
            Image.setFitHeight(114);
            Image.setFitWidth(114);
            Image.setImage(image);
           // analyzeImage(imagePath);
        }
    }
    private static final String API_KEY = "";

    public void analyzeImage(String imagePath) {
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            ByteString imgBytes = ByteString.readFrom(new FileInputStream(imagePath));
            com.google.cloud.vision.v1.Image img = com.google.cloud.vision.v1.Image.newBuilder().setContent(imgBytes).build();
            List<Feature> featureList = new ArrayList<>();
            featureList.add(Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build());
            featureList.add(Feature.newBuilder().setType(Feature.Type.FACE_DETECTION).build());
            featureList.add(Feature.newBuilder().setType(Feature.Type.WEB_DETECTION).build());
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .setImage(img)
                    .addAllFeatures(featureList)
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



}