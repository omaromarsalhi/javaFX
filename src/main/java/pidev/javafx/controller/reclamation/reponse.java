package pidev.javafx.controller.reclamation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

    public List<Reclamation> getReclamations(){
        ServiceReclamation bs = new ServiceReclamation();
        return new ArrayList<>(bs.getAll());
    }


    private void loadReclamation(Reclamation reclamation) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/fxml/reclamation/window.fxml"));

        Pane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        window si = fxmlLoader.getController();
        si.setReclamation(reclamation);
        reclamationcontrainer.getChildren().add(pane);
    }
}
