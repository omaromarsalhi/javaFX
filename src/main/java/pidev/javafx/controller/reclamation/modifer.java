package pidev.javafx.controller.reclamation;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.reclamation.Reclamation;


import java.io.File;
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

    ServiceReclamation si = new ServiceReclamation();

//    public void initialize() {
//        try {
//            // Get the data from the database
//
//            VBox box = new VBox();
//
//            // Iterate through the result set and create labels
//            for (Reclamation reclamation : si.getAllPrivateKeys()) {
//                String data = reclamation.getPrivateKey()+" "+ reclamation.getSubject() + " " + reclamation.getTitre(); // replace with your method names
//                Label label = new Label(data);
//                box.getChildren().add(label);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
                setText(reclamation.getPrivateKey() + " | " + reclamation.getDate() +" | "+reclamation.getSubject() + " | " + reclamation.getTitre());
                System.out.println(reclamation.toString());


                ImageView imageView = new ImageView();

                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
              if (empty || reclamation.getImagePath()== null)
              {

              }
               else
               { Image image = new Image(reclamation.getImagePath());
                    imageView.setImage(image);
                setGraphic(imageView);}
                setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
            }
        }
    });
}
    @FXML
    void modifer_Reclamation()
    {
        Reclamation   rec = new Reclamation(privateKey.getText(), subject.getText(), title.getText(),description.getText());
        si.modifier(rec);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Reclmation has been modified !");

        // Show the alert
        alert.show();
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
    @FXML
    void supprimer_Reclamation() {
        // Assuming privateKey.getText() returns the id of the reclamation
        String idReclamation = privateKey.getText();
        si.supprimer(idReclamation);
        clearFields();

        displayDetailsInTextField();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Reclmation Delelte!");

        // Show the alert
        alert.showAndWait();
    }
    void clearFields() {
        privateKey.setText("");
        title.setText("");
        subject.setText("");
        description.setText("");
    }



}

