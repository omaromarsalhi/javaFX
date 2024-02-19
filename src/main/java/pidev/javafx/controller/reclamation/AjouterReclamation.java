package pidev.javafx.controller.reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import pidev.javafx.crud.reclamation.DataSource;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.reclamation.Reclamation;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class AjouterReclamation {

    @FXML
    private Button importButton;

    @FXML
    private TextField privateKey;
    @FXML
    private TextField title;
//    @FXML
//    private TextField subject;

    @FXML
    private TextArea description;
    @FXML
    public ChoiceBox ChoixMul;
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

        if (text[2].matches("[a-zA-Z0-9]+"))
            description.setStyle("-fx-text-fill: #25c12c");
        else
            description.setStyle("-fx-text-fill: #bb2020 ");
    }
    private String imagePath; // Class variable to store the image path

    public void importFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath(); // Store the file path
            System.out.println("File imported: " + imagePath);
        } else {
            System.out.println("File selection cancelled.");// Default image path
        }
    }


    @FXML
    void ajouter_Reclamation() throws IOException {
        String selectedSubject = initialize();

        String generatedString = generateRandomString(20);
        privateKey.setText(generatedString);
        System.out.println(generatedString);
        if(imagePath==null)
        {
            imagePath = "src/main/java/pidev/javafx/controller/reclamation/6666.png";
        }

        // Call the onTextChanged function here
        onTextChanged();

        // Check if the text fields are valid
        if (title.getStyle().equals("-fx-text-fill: #25c12c;") && description.getStyle().equals("-fx-text-fill: #25c12c")) {
            Reclamation rec = new Reclamation(privateKey.getText(), selectedSubject, title.getText(), description.getText(), imagePath);
            si.ajouter(rec);

            // Generate a QR code for the reclamation
            String data = URLEncoder.encode(rec.toString(), StandardCharsets.UTF_8); // URL-encode the data
            String size = "200x200"; // Replace this with the desired size of the QR code
            String url = "https://api.qrserver.com/v1/create-qr-code/?data=" + data + "&size=" + size;

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);

// Save the response to a file
            String qrCodeFilePath = "src/main/resources/reclamation/" + generatedString + ".png"; // Each QR code will have a unique name
            try (OutputStream os = new FileOutputStream(qrCodeFilePath)) {
                response.getEntity().writeTo(os);
            }

            // Create a new JavaFX stage for the pop-up window
            Stage popupStage = new Stage();

            // Create an ImageView to display the QR code
            ImageView qrCodeImageView = new ImageView(new Image("file:" + qrCodeFilePath));

            // Create a layout for the pop-up window
            StackPane layout = new StackPane();
            layout.getChildren().add(qrCodeImageView);

            // Create a scene for the pop-up window
            Scene scene = new Scene(layout);

            // Set the scene for the pop-up window
            popupStage.setScene(scene);

            // Show the pop-up window
            popupStage.showAndWait();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Reclamation has been added successfully!");
            // Show the alert
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

            // Check if the generated string exists in the database
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


}