package pidev.javafx.controller.reponse;

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
import pidev.javafx.controller.reclamation.Popupupdate;
import pidev.javafx.crud.reclamation.ServiceReponse;
import pidev.javafx.model.reclamation.Reclamation;
import pidev.javafx.model.reclamation.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class ReponseWindow {

    @FXML
    private Button Delete;

    @FXML
    private Label date;

    @FXML
    private Label etat;

    @FXML
    private ImageView imagePath;

    @FXML
    private Label privatekey;

    @FXML
    private Label reponse;

    @FXML
    private Label subject;

    @FXML
    private Button update;

    private Reclamation reclamation;

    private Response response;
    ServiceReponse siResponse = new ServiceReponse();




    public void setReponse( Response response) {
        this.response = response;
        privatekey.setText(reclamation.getPrivateKey());
        subject.setText(reclamation.getSubject());
        imagePath.setImage(new Image("file:"+reclamation.getImagePath(),50,50,true,true));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date.setText(reclamation.getDate() != null ? formatter.format(reclamation.getDate()) : "empty");
        reponse.setText(response.getDescription());
    }

    @FXML
    void showPopup(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reclamation/interfacemodifer.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Popupupdate controller = fxmlLoader.getController();
            controller.setData(reclamation);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Popup Window");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void supprimer_Reclamation(ActionEvent event) {
        String idReclamation = privatekey.getText();
        siResponse.supprimer(idReclamation);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Reclamation Deleted!");

        // Show the alert
        alert.showAndWait();
    }

}




