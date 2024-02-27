package pidev.javafx.controller.reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.reclamation.Reclamation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class window {
    @FXML
    private Label date;
    @FXML
    private Label descirption;
    @FXML
    private Label privatekey;
    @FXML
    private Label subject;
    @FXML
    private Label titre;
    @FXML
    private ImageView imagePath;
    @FXML
    private Button update;
    @FXML
    private Button Delete;
    private reponse reponseController;
    private  Reclamation reclamation;

    ServiceReclamation si = new ServiceReclamation();


    public void setReponseController(reponse reponseController, Reclamation reclamation) {
    this.reclamation=reclamation;
        this.reponseController = reponseController;
        System.out.println("setReponseController called");

    }
    public void showPopup() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reclamation/intrface.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Popup Window");
            stage.setScene(new Scene(root));

            // Show the popup stage.
            stage.show();
        } catch (com.aspose.imaging.internal.Exceptions.IO.IOException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void initialize() {
        update.setOnAction((ActionEvent event) -> {
            displayDetails();
        });
    }

    public void setReclamation(Reclamation reclamation) {
        privatekey.setText(reclamation.getPrivateKey());
        subject.setText(reclamation.getSubject());
        titre.setText(reclamation.getTitre());
        descirption.setText(reclamation.getDescription());
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        if ( reclamation.getImagePath() != null) {
            try (InputStream is = new FileInputStream(reclamation.getImagePath())) {
                Image image = new Image(is);
                imageView.setImage(image);
                imagePath.setImage(imageView.getImage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date.setText(reclamation.getDate() != null ? formatter.format(reclamation.getDate()) : "empty");
    }

    public void displayDetails() {
        reponseController.displayReclamationDetails(reclamation);
    }

    @FXML
    void supprimer_Reclamation() {
        // Assuming privateKey.getText() returns the id of the reclamation
        String idReclamation = privatekey.getText();
        si.supprimer(idReclamation);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Reclmation Delelte!");

        // Show the alert
        alert.showAndWait();
    }



}
