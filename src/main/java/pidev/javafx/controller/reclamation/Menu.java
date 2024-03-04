package pidev.javafx.controller.reclamation;
//import com.google.cloud.vision.v1.*;
//import com.google.protobuf.ByteString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.aspose.imaging.internal.Exceptions.IO.IOException;
import com.aspose.imaging.memorymanagement.Configuration;
import com.itextpdf.text.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.reclamation.Reclamation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
//////////////////////////////////
import com.assemblyai.api.RealtimeTranscriber;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import static java.lang.Thread.interrupted;

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
    private AnchorPane mainBorderPain;

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
                document.add(new Paragraph("Subject: " + categorie.getText()));
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

    void showPopup(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reclamation/showallreclamation.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            modifer m = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Popup Window");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void showticket() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ticket/ticket.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Popup Window");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showReclmation() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reclamation/reponse.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Popup Window");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showReponse() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reclamation/reponse.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Popup Window");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void handleRecordButton(ActionEvent actionEvent) throws Exception {
        //streamingMicRecognize();
//        try {
////            SpeachToText speachToText = new SpeachToText();
////            speachToText.streamingMicRecognize();
////
////            String res= speachToText.getResult();
////
////            System.out.println("final string: " +res);
////            desciption.setText(res);
//            try {
//                // Replace this with the command to start your other Java application
////                String javaApp = "C:/Users/Public/Documents/PiDevPersonal/test/target/test-1.0-SNAPSHOT.jar";
////                ProcessBuilder processBuilder = new ProcessBuilder(javaApp.split(" "));
////                processBuilder.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
//// this for translate speach to text
//
//    private void transcribe() throws Exception {
//        Thread thread = new Thread(() -> {
//            try {
//                RealtimeTranscriber realtimeTranscriber = RealtimeTranscriber.builder()
//                        .apiKey("412397bf46b04102b43016cfece06506" )
//                        .sampleRate(16_000)
//                        .onSessionBegins(sessionBegins -> System.out.println(
//                                "Session opened with ID: " + sessionBegins.getSessionId()))
//                        .onPartialTranscript(transcript -> {
//                            if (!transcript.getText().isEmpty())
//                                System.out.println("Partial: " + transcript.getText());
//                        })
//                        .onFinalTranscript(transcript -> System.out.println("Final: " + transcript.getText()))
//                        .onError(err -> System.out.println("Error: " + err.getMessage()))
//                        .build();
//
//                System.out.println("Connecting to real-time transcript service");
//                realtimeTranscriber.connect();
//
//                System.out.println("Starting recording");
//                AudioFormat format = new AudioFormat(3200, 16, 2, true, true);
//                // `line` is your microphone
//
//                TargetDataLine line = AudioSystem.getTargetDataLine(format);
//                line.open(format);
//
//                byte[] data = new byte[line.getBufferSize()];
//                line.start();
//                System.out.println("Setup complete");
//
//                while (!interrupted()) {
//                    // Read the next chunk of data from the TargetDataLine.
//                    System.out.println("Reading lines: " +data.toString());
//                    line.read(data, 0, data.length);
//                    realtimeTranscriber.sendAudio(data);
//                }
//
//                System.out.println("Stopping recording");
//                line.close();
//
//                System.out.println("Closing real-time transcript connection");
//                realtimeTranscriber.close();
//            } catch (LineUnavailableException e) {
//                System.out.println("Error here happening");
//                throw new RuntimeException(e);
//            }
//        });
//        thread.start();
//
//        System.out.println("Press ENTER key to stop...");
//        System.in.read();
//        thread.interrupt();
//
//    }
//public static void streamingMicRecognize() throws Exception {
//
//    try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
//
//        // The path to the image file to annotate
//        String fileName = "C:/Users/khali/Downloads/chall.png";
//
//        // Reads the image file into memory
//        Path path = Paths.get(fileName);
//        byte[] data = Files.readAllBytes(path);
//        ByteString imgBytes = ByteString.copyFrom(data);
//
//        // Builds the image annotation request
//        List<AnnotateImageRequest> requests = new ArrayList<>();
//        com.google.cloud.vision.v1.Image img = com.google.cloud.vision.v1.Image.newBuilder().setContent(imgBytes).build();
//        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
//        AnnotateImageRequest request =
//                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
//        requests.add(request);
//
//        // Performs label detection on the image file
//        BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
//        List<AnnotateImageResponse> responses = response.getResponsesList();
//
//        for (AnnotateImageResponse res : responses) {
//            if (res.hasError()) {
//                System.out.format("Error: %s%n", res.getError().getMessage());
//                return;
//            }
//
//            for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
//                annotation
//                        .getAllFields()
//                        .forEach((k, v) -> System.out.format("%s : %s%n", k, v.toString()));
//            }
//        }
//    }
}
