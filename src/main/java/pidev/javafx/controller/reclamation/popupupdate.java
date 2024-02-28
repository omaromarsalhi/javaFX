package pidev.javafx.controller.reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import pidev.javafx.crud.reclamation.DataSource;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.reclamation.Reclamation;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class popupupdate {
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
    Connection cnx = DataSource.getInstance().getCnx();

    private String imagePath;
    private Reclamation reclamation;
    public void setData(Reclamation reclamation) {
        this.reclamation = reclamation;
        System.out.println("setData called with Reclamation: " + reclamation);
    }

    @FXML
    void modifier_reclmation() throws IOException {
         //initialize(reclamation);

        onTextChanged();
        if (title.getStyle().equals("-fx-text-fill: #25c12c;") && description.getStyle().equals("-fx-text-fill: #25c12c")) {
            Reclamation rec = new Reclamation(privateKey.getText(), title.getText(), description.getText(), imagePath);
            si.modifier(rec);
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
        description.setText(reclamation.getDescription());
        aaaa.getItems().addAll("Problem technique", "application", "testt", "omar salhi", "khalil rmila ");
    }
}