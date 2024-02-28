package pidev.javafx.controller.reponse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.crud.reclamation.ServiceReponse;
import pidev.javafx.model.reclamation.Reclamation;
import pidev.javafx.model.reclamation.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class test {
    @FXML
    public ListView lista;
    private Button importButton;

    @FXML
    private TextField subject;
    @FXML
    private TextField privateKey;
    @FXML
    private TextField title;

    @FXML
    private ImageView imageView;

    @FXML
    private TextArea description ;
    ServiceReclamation si = new ServiceReclamation();
    ServiceReponse sirep = new ServiceReponse();



    public void initialize() {
        ServiceReponse service = new ServiceReponse();
        List<Response> reclamationsrp = new ArrayList<>(service.getAll());

        // Add the Reclamation objects to the ListView
        lista.getItems().addAll(reclamationsrp);

        // Optionally, you can set a custom cell factory to control how each Reclamation is displayed
        lista.setCellFactory(reclamationListView -> new ListCell<Response>() {
            @Override
            protected void updateItem(Response reclamation, boolean empty) {
                super.updateItem(reclamation, empty);
                if (empty || reclamation == null) {
                    setText(null);
                } else {
                    setText(reclamation.getReclamation().getPrivateKey() + " | " + reclamation.getReclamation().getDate() +" | "+reclamation.getReclamation().getSubject() + " | " + reclamation.getReclamation().getTitre() +" | "+reclamation.getDescription() );
                    //    System.out.println(reclamation.toString());

                    ImageView imageView = new ImageView();

                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    if (!empty && reclamation.getReclamation().getImagePath() != null) {
                        try (InputStream is = new FileInputStream(reclamation.getReclamation().getImagePath())) {
                            Image image = new Image(is);
                            imageView.setImage(image);
                            setGraphic(imageView);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
                }
            }
        });
    }
    public void displayDetailsInTextField() {
        lista.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!lista.getSelectionModel().isEmpty())) {
                // Get the selected item
                Reclamation selectedItem = (Reclamation) lista.getSelectionModel().getSelectedItem();
                // Display the details in the text fields
                privateKey.setText(selectedItem.getPrivateKey());
                subject.setText(selectedItem.getSubject());
                title.setText(selectedItem.getTitre());
                description.setText(selectedItem.getDescription());
            }
        });
    }

    public void modifer_Reclamation(ActionEvent actionEvent) {
    }

    public void supprimer_Reclamation(ActionEvent actionEvent) {
    }
}
