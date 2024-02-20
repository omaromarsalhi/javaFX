package pidev.javafx.controller.reclamation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.reclamation.Reclamation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class reponse implements Initializable {
    @FXML
    private VBox reclamationcontrainer;

    ArrayList<Reclamation> reclamations = new ArrayList<>(getReclamations());

for (Reclamation reclamation : reclamations) {
        try {
            loadReclamation(reclamation);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(getClass().getResource("/fxml/reclamation/window.fxml"));
//        Pane pane = null;
//        try {
//            pane = fxmlLoader.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        window si = fxmlLoader.getController();
//        reclamationcontrainer.getChildren().add(pane);
//    }
}
