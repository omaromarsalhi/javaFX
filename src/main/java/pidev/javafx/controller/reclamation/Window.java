package pidev.javafx.controller.reclamation;

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

import java.io.IOException;
import java.text.SimpleDateFormat;

public class Window {
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
    private Reponse reponseController;
    private  Reclamation reclamation;
    ServiceReclamation si = new ServiceReclamation();
    public void setReponseController(Reponse reponseController, Reclamation reclamation) {
    this.reclamation=reclamation;
        this.reponseController = reponseController;
        System.out.println("setReponseController called");

    }
    public void showPopup() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reclamation/interfacemodifer.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Popupupdate controller = fxmlLoader.getController();
            controller.setData(reclamation);
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
    public void setReclamation(Reclamation reclamation) {
        privatekey.setText(reclamation.getPrivateKey());
        subject.setText(reclamation.getSubject());
        titre.setText(reclamation.getTitre());
        descirption.setText(reclamation.getDescription());
        imagePath.setImage(new Image("file:"+reclamation.getImagePath(),50,50,false,true));
        System.out.println(reclamation.getImagePath());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date.setText(reclamation.getDate() != null ? formatter.format(reclamation.getDate()) : "empty");
    }

    public void displayDetails() {
        reponseController.displayReclamationDetails(reclamation);
    }

    @FXML
    void supprimer_Reclamation() {
        String idReclamation = privatekey.getText();
        si.supprimer(idReclamation);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Reclmation Delelte!");
        alert.showAndWait();
    }



}