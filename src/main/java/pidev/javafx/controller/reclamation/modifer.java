package pidev.javafx.controller.reclamation;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.reclamation.Reclamation;

import java.util.ArrayList;
import java.util.List;

public class modifer {

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
    private Tool tool1;

    ServiceReclamation si = new ServiceReclamation();



    public void initialize() {
        ServiceReclamation service = new ServiceReclamation();
        List<Reclamation> reclamations = new ArrayList<>(service.getAll());

        // Add the Reclamation objects to the ListView
        lista.getItems().addAll(reclamations);

        // Optionally, you can set a custom cell factory to control how each Reclamation is displayed
        lista.setCellFactory(reclamationListView -> new ListCell<Reclamation>() {
            @Override
            protected void updateItem(Reclamation reclamation, boolean empty) {
                super.updateItem(reclamation, empty);
                if (empty || reclamation == null) {
                    setText(null);
                } else {
                    setText("private Key : "+ reclamation.getPrivateKey() + " | date :  " + reclamation.getDate() +" |  subject : "+reclamation.getSubject() + " | titre :  " + reclamation.getTitre());
                //    System.out.println(reclamation.toString());

                    ImageView imageView = new ImageView();

                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);

                    setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
                }
            }
        });
    }


    public void onlic(Tool t)
    {
        this.tool1=t;
    }
    public void displayDetailsInTextField() {
        Reclamation selectedItem = (Reclamation) lista.getSelectionModel().getSelectedItem();

        lista.setOnMouseClicked(event -> {
            tool1.onclic(selectedItem);
        });
    }
}
