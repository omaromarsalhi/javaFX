package pidev.javafx.controller.reclamation;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import pidev.javafx.crud.reclamation.DataSource;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.reclamation.Reclamation;
import java.io.IOException;
import java.sql.Connection;

public class Popupupdate {
    @FXML
    private TextField privateKey;
    @FXML
    private TextField title;
    @FXML
    private ImageView Image;
    @FXML
    private TextArea description;
    @FXML
    private ChoiceBox<String> aaaa;
    @FXML
    private MenuButton menu;
    @FXML
    private TextField subject;
    @FXML
    ServiceReclamation si = new ServiceReclamation();
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
    private Reclamation reclamation;
    public void setData(Reclamation reclamation) {
        this.reclamation = reclamation;
        System.out.println("setData called with Reclamation: " + reclamation);
        privateKey.setText(reclamation.getPrivateKey());
        title.setText(reclamation.getTitre());
        subject.setText(reclamation.getSubject());
        description.setText(reclamation.getDescription());
    }

    @FXML
    void modifier_reclmation() throws IOException {

        onTextChanged();
        if (title.getStyle().equals("-fx-text-fill: #25c12c;") && description.getStyle().equals("-fx-text-fill: #25c12c")) {
            Reclamation   rec = new Reclamation(privateKey.getText(), subject.getText(), title.getText(),description.getText());
            si.modifier(rec);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Reclmation has been modified !");

            // Show the alert
            alert.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Verification failed. Please check your input.");
            alert.show();
        }
    }
    public void initialize(Reclamation reclamation) {
        System.out.println("initialize called with Reclamation: " + reclamation);
        privateKey.setText(reclamation.getPrivateKey());
        title.setText(reclamation.getTitre());
        subject.setText(reclamation.getSubject());
        description.setText(reclamation.getDescription());
    }








}