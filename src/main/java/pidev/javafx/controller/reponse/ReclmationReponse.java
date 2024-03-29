package pidev.javafx.controller.reponse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pidev.javafx.crud.reclamation.ServiceReponse;
import pidev.javafx.model.reclamation.Response;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReclmationReponse implements Initializable {
    @FXML
    private Button ajouter;

    @FXML
    private VBox reclamationcontrainer;


    private List<Response> responses;

    public void initialize(URL url, ResourceBundle rb) {

        responses = new ArrayList<>(getResponse());
        for (Response response :responses){
                try {
                loadResponse(response);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

    }

    public List<Response> getResponse(){
        ServiceReponse bs = new ServiceReponse();
        return new ArrayList<>(bs.getAll());
    }
    public void show_ajouter(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reclamation/testt.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Popup Window");
        stage.setScene(new Scene(root));
        stage.show();
    }
    private void loadResponse( Response rep) throws IOException {
        System.out.println("loadReclamation called");

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/fxml/reponse/reclmationReponse.fxml"));
        HBox hBox = null;
        try {
            hBox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ReponseWindow si = fxmlLoader.getController();
        si.setReponse(rep);

        reclamationcontrainer.getChildren().add(hBox);
    }
}
