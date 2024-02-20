package pidev.javafx.controller.reclamation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public class ReclamationItemController {
        private Reclamation reclamation; // The Reclamation object for this item
        private ServiceReclamation rs = new ServiceReclamation();




        // Method to set the Reclamation data for this item
        public void setReclamation(Reclamation reclamation) {
            this.reclamation = reclamation;

            // Set the labels to the reclamation's data
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

            // Add an action listener to the delete button

        }
    }


}
