package pidev.javafx.controller.reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.reclamation.Reclamation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class reponse implements Initializable {
    @FXML
    private TextArea description;

    @FXML
    private TextField privateKey;

    @FXML
    private VBox reclamationcontrainer;

    @FXML
    private TextField subject;

    @FXML
    private TextField title;
    private String reponse;

    ServiceReclamation si = new ServiceReclamation();

    @FXML
    void show_ajouter(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reclamation/testt.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Popup Window");
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void modifer_Reclamation(ActionEvent event) {

        Reclamation   rec = new Reclamation(privateKey.getText(), subject.getText(), title.getText(),description.getText());
        si.modifier(rec);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Reclmation has been modified !");

        // Show the alert
        alert.show();
        clearFields();
    }


    private void clearFields() {
        privateKey.setText("");
        title.setText("");
        subject.setText("");
        description.setText("");
    }

    private List<Reclamation> reclamations;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reclamations = new ArrayList<>(getReclamations());
        for (Reclamation reclamation : reclamations) {
            try {
                loadReclamation(reclamation);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void displayReclamationDetails(Reclamation reclamation) {
        System.out.println("reponseController: " + reponse);

        // Display the details in the text fields
        privateKey.setText(reclamation.getPrivateKey());
        subject.setText(reclamation.getSubject());
        title.setText(reclamation.getTitre());
        description.setText(reclamation.getDescription());
    }


    public List<Reclamation> getReclamations(){
        ServiceReclamation bs = new ServiceReclamation();
        return new ArrayList<>(bs.getAll());
    }


    private void loadReclamation(Reclamation reclamation) throws IOException {
        System.out.println("loadReclamation called");

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/fxml/reclamation/window.fxml"));

        HBox hBox = null;
        try {
            hBox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        window si = fxmlLoader.getController();
        si.setReponseController(this, reclamation);

        si.setReclamation(reclamation);
        reclamationcontrainer.getChildren().add(hBox);
    }
    @FXML
    void supprimer_Reclamation() {
        String idReclamation = privateKey.getText();
        si.supprimer(idReclamation);
    }

}
