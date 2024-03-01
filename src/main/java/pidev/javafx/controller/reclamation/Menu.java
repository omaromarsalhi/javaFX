package pidev.javafx.controller.reclamation;

import com.aspose.imaging.internal.Exceptions.IO.IOException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.reclamation.Reclamation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
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

    public void exportToPDF() {
        Reclamation selectedItem = (Reclamation) lista.getSelectionModel().getSelectedItem();

        // Let the user choose the location and filename for the exported PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file));

                document.open();

                // Add logo
                String logoPath = "path_to_logo.png";
                if (new File(logoPath).exists()) {
                    com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(logoPath);
                    logo.scaleToFit(100, 50);
                    document.add(logo);
                }

                // Add title
                Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
                Chunk title = new Chunk("Reclamation Details", font);
                document.add(new Paragraph(title));

                // Add details
                document.add(new Paragraph("Private Key: " + privateKey.getText()));
                document.add(new Paragraph("Subject: " +  categorie.getText()));
                document.add(new Paragraph("Title: " + titre.getText()));
                document.add(new Paragraph("Date: " + date.getText()));
                document.add(new Paragraph("Description: " + desciption.getText()));

                // Let the user choose an image
                FileChooser imageChooser = new FileChooser();
                imageChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                File imageFile = imageChooser.showOpenDialog(null);
                if (imageFile != null) {
                    com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(imageFile.getAbsolutePath());
                    image.scaleToFit(500, 500);
                    document.add(image);
                }

                // Add signature
//                com.itextpdf.text.Image signature = com.itextpdf.text.Image.getInstance("path_to_signature.png");
//                signature.scaleToFit(200, 50);
//                document.add(signature);

                document.close();
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
        }
    }





}
