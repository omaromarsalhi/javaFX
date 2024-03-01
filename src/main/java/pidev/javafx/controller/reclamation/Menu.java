package pidev.javafx.controller.reclamation;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.reclamation.Reclamation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Menu {
    @FXML
    private ImageView analyticsIcon;

    @FXML
    private ImageView assetsIcon;

    @FXML
    private TextField categorie;

    @FXML
    private TextField date;

    @FXML
    private TextArea desciption;

    @FXML
    private ImageView imageView;

    @FXML
    private TableView lista;

    @FXML
    private ImageView marketingIcon;

    @FXML
    private TextField privateKey;

    @FXML
    private TextField searchBar;

    @FXML
    private TextField titre;

    @FXML
    private TableColumn<Reclamation, String> image;
    @FXML
    private TableColumn<Reclamation, String> titrec;
    @FXML
    private TableColumn<Reclamation, String> categoriec;
    ServiceReclamation si = new ServiceReclamation();
    public void initialize() {
        // Initialize columns
        categoriec.setCellValueFactory(new PropertyValueFactory<>("subject"));
        titrec.setCellValueFactory(new PropertyValueFactory<>("titre"));
        image.setCellValueFactory(new PropertyValueFactory<>("imagePath"));

        image.setCellFactory(param -> new TableCell<Reclamation, String>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitHeight(50); // set height
                imageView.setFitWidth(50); // set width
            }

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    try {
                        imageView.setImage(new Image(imagePath));
                    } catch (IllegalArgumentException e) {
                        // Image path not found or invalid, show default image
                        setGraphic(null);
                    }
                    setGraphic(imageView);
                }
            }
        });


        // Add data
        List<Reclamation> reclamations = new ArrayList<>(si.getAll());

        // Sort the list by date
        reclamations.sort(Comparator.comparing(Reclamation::getDate));

        lista.getItems().addAll(reclamations);
    }

    public void displayDetailsInTableView() {
        lista.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!lista.getSelectionModel().isEmpty())) {
                // Get the selected item
                Reclamation selectedItem = (Reclamation) lista.getSelectionModel().getSelectedItem();

                privateKey.setText(selectedItem.getPrivateKey());
                categorie.setText(selectedItem.getSubject());
                titre.setText(selectedItem.getTitre());
                date.setText(selectedItem.getDate().toString());
                desciption.setText(selectedItem.getDescription());
                try {
                    Image image = new Image(selectedItem.getImagePath());
                    imageView.setImage(image);
                } catch (IllegalArgumentException e) {
                    // Image path not found or invalid, show default image or clear the ImageView
                    imageView.setImage(null);
                }
            }
        });
    }





}
