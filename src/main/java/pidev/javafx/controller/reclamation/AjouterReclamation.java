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
import java.sql.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


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
    void ajouter_Reclamation() {
        String selectedSubject = initialize();

        String generatedString = generateRandomString(20);
        privateKey.setText(generatedString);
        System.out.println(generatedString);
        if(imagePath==null)
        {
            imagePath = "6666.png";
        }
        Reclamation rec = new Reclamation(privateKey.getText(), selectedSubject, title.getText(), description.getText(), imagePath);
        si.ajouter(rec);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Reclmation has been added successfully!");
        // Show the alert
        alert.show();
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