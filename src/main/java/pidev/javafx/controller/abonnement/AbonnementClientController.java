package pidev.javafx.controller.abonnement;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import pidev.javafx.crud.transport.ServicesAbonnement;
import pidev.javafx.model.Transport.Abonnement;
import pidev.javafx.model.Transport.Transport;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.*;

import okhttp3.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AbonnementClientController implements Initializable {

    ObservableList<Transport> dataList = FXCollections.observableArrayList();
    List<Abonnement> abonnementList = new ArrayList<>();
    ServicesAbonnement sa =new ServicesAbonnement();


    @FXML
    private Label DebutLabel;
    @FXML
    private Label FinLabel;
    @FXML
    private Label IdLabel;
    @FXML
    private Label NomLabel;
    @FXML
    private TextField NomText;
    @FXML
    private Label PrenomLabel;
    @FXML
    private TextField PrenomText;
    @FXML
    private ComboBox<String> TypeAbonnementBox;
    @FXML
    private Pane form;
    @FXML
    private ImageView imageAbn;
    @FXML
    private ImageView imageAbonne;
    @FXML
    private VBox mainContainer;
    @FXML
    private Button nextBtn;
    @FXML
    private Pane paneToAnnimate;
    @FXML
    private Button previousBtn;
    @FXML
    private VBox statsPannel;


    Map<String, Double> tagMap = new HashMap<>();



    private static final String API_KEY = "acc_94dd4f1769c190a";
    private static final String API_SECRET = "5a56d117d922cf4da9488e1349dd7c09";
       int i;
       Set <Abonnement> abonnementSet;
       TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), paneToAnnimate);
       String imagePath;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TypeAbonnementBox.getItems().addAll("Annuel", "mensuel");
        afficher();
        if (abonnementList.size() > 0)
            remplir_abonnement();
        translateTransition.setNode(paneToAnnimate);

        VBox root = new VBox();

    }


    @FXML
    public void afficher() {

      abonnementSet=sa.getAll();
      abonnementList = List.copyOf(abonnementSet);
      System.out.println(abonnementList.toString());

    }
    public void insert_Image(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        var selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            Task<Map> task=new Task<Map>() {
                @Override
                protected Map call() throws Exception {
                    Platform.runLater(()->{
                        showCustomDialog();
                    });
                    return image_api(imagePath);
                }
            };

            task.setOnSucceeded(workerStateEvent -> {
                if(!task.getValue().isEmpty()&&task.getValue().containsKey("man")) {
                    Platform.runLater(()->{

                        close_dialog();
                        imageAbn.setImage(new Image(imagePath));
                    });
                }
                else System.out.println("image should be of a humain being and in portrait mode");
            });


            new Thread(task).start();



            imagePath = selectedFile.getAbsolutePath();



        }
    }
    public void add_load(){
        expand();
        UpdateBtn.setVisible(false);
    }

    public void ajouter(){



      Abonnement p=new Abonnement(NomText.getText(),PrenomText.getText(),TypeAbonnementBox.getValue().toString(),imagePath);

      sa.addItem(p);
        afficher();
        remplir_abonnement();
        unexpand();
    };

public void DeleteAbonnement(){

        int id = abonnementList.get(i).getIdAboonnement();
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Vous voules vraiment effacer ce fichier ?");
        alert.showAndWait();
        sa.deleteItem(id);
        afficher();
        remplir_abonnement();
};

    @FXML
    protected void onTextChanged() {
        String[] text = new String[10];

        text[1] = NomText.getText();
        text[2]= PrenomText.getText();

        if (text[1].matches("[a-zA-Z ]*"))
            NomText.setStyle("-fx-text-fill: #25c12c;");
        else
            NomText.setStyle("-fx-text-fill: #bb2020;");

        if (text[2].matches("[a-zA-Z ]+"))
            PrenomText.setStyle("-fx-text-fill: #25c12c");
        else
            PrenomText.setStyle("-fx-text-fill: #bb2020 ");
    }

    public  void remplir_abonnement(){
        if (i==0)
            previousBtn.setVisible(false);
        else if (i==abonnementList.size()-1) {
            nextBtn.setVisible(false);
        }
String [] time=abonnementList.get(i).getDateDebut().toLocalDateTime().toString().split("T");
String id=Integer.toString(abonnementList.get(i).getIdAboonnement());
         DebutLabel.setText(time[0]);
        FinLabel.setText(abonnementList.get(i).getDateFin().toString());
        NomLabel.setText(abonnementList.get(i).getNom());
        PrenomLabel.setText(abonnementList.get(i).getPrenom());
        IdLabel.setText("000"+id);
        imagePath=abonnementList.get(i).getImage() ;
        System.out.println(imagePath);
        Image image = new Image(imagePath);
        imageAbonne.setImage(image);

    }
    @FXML
    public void nextAb() {

        if (i < abonnementList.size() - 1) {
            nextBtn.setVisible(true);
            previousBtn.setVisible(true);
            i = i + 1;
            System.out.println(i);
            remplir_abonnement();
        } else {
            nextBtn.setVisible(false);
            previousBtn.setVisible(true);
        }
    }


    @FXML
    public void previousAb() {

        if (i > 0) {
            previousBtn.setVisible(true);
            nextBtn.setVisible(true);
            i = i - 1;
            System.out.println(i);
            remplir_abonnement();
        } else if (i == 0) {
            previousBtn.setVisible(false);
            nextBtn.setVisible(true);
        }
    }
    public void LoadUpdate(){
        expand();
        UpdateBtn.setVisible(true);
    NomText.setText(abonnementList.get(i).getNom());
    PrenomText.setText(abonnementList.get(i).getPrenom());
    TypeAbonnementBox.setValue(abonnementList.get(i).getType());
    imagePath=abonnementList.get(i).getImage();

    }
    Stage dialogStage = new Stage();
@FXML
public void close_dialog(){

        dialogStage.close();
}
    @FXML
    private void showCustomDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Transport/exceptions/scan_photo.fxml"));
            AnchorPane dialogContent = loader.load();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.setTitle("Custom Dialog");

            Scene dialogScene = new Scene(dialogContent);
            dialogScene.setFill(Color.TRANSPARENT);
            dialogStage.setScene(dialogScene);
             dialogStage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    Button UpdateBtn;
    public void Update(){

        Abonnement A = new Abonnement(NomText.getText(),PrenomText.getText(),TypeAbonnementBox.getValue(),imagePath);
        System.out.println(NomText.getText());
          A.setIdAboonnement(abonnementList.get(i).getIdAboonnement());
        sa.updateItem(A);
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Vous voules vraiment effacer ce fichier ?");
        alert.showAndWait();
        UpdateBtn.setVisible(false);
        afficher();
        remplir_abonnement();
        unexpand();
    }
@FXML
    VBox statsPannel;
    @FXML
    Pane statsPane;
    @FXML
    Button expandBtn;
@FXML
Button openBtn;



    public void exportPaneToImage() {
        WritableImage writableImage = paneToAnnimate.snapshot(new SnapshotParameters(), null);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                BufferedImage bufferedImage = new BufferedImage(
                        (int) writableImage.getWidth(),
                        (int) writableImage.getHeight(),
                        BufferedImage.TYPE_INT_ARGB
                );

                PixelReader pixelReader = writableImage.getPixelReader();
                for (int x = 0; x < writableImage.getWidth(); x++) {
                    for (int y = 0; y < writableImage.getHeight(); y++) {
                        bufferedImage.setRGB(x, y, pixelReader.getArgb(x, y));
                    }
                }

                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

@FXML
public void unexpand() {
    Timeline timeline = new Timeline();
    timeline.setCycleCount(1);
    timeline.setAutoReverse(false);

    double panelWidth = statsPannel.getWidth();

    KeyValue initTranslateX = new KeyValue(statsPannel.translateXProperty(), 0);
    KeyValue finalTranslateX = new KeyValue(statsPannel.translateXProperty(), panelWidth-50);
    KeyValue initWidth = new KeyValue(statsPannel.prefWidthProperty(), 404);
    KeyValue finalWidth = new KeyValue(statsPannel.prefWidthProperty(), 50);

    KeyFrame initFrame = new KeyFrame(Duration.ZERO, initWidth, initTranslateX);
    KeyFrame finalFrame = new KeyFrame(Duration.seconds(0.3), finalWidth, finalTranslateX);

    timeline.getKeyFrames().addAll(finalFrame, initFrame);

    statsPane.setVisible(false);
    expandBtn.setVisible(true);

    timeline.play();




    BoxBlur blur = new BoxBlur();
    blur.setWidth(10);
    blur.setHeight(10);
    blur.setIterations(3);
    displayAbonnement.toFront();
    displayAbonnement.setEffect(null);
    form.toBack();
    displayAbonnement.setOpacity(1);



}



    @FXML
    public void expand() {
        Timeline timeline2 = new Timeline();
        timeline2.setCycleCount(1);
        timeline2.setAutoReverse(false);

        double panelWidth = statsPannel.getWidth();

        KeyValue initTranslateX2 = new KeyValue(statsPannel.translateXProperty(), panelWidth);
        KeyValue finalTranslateX2 = new KeyValue(statsPannel.translateXProperty(), 0);
        KeyValue initWidth2 = new KeyValue(statsPannel.prefWidthProperty(), 50);
        KeyValue finalWidth2 = new KeyValue(statsPannel.prefWidthProperty(), 404);
        KeyFrame initFrame2 = new KeyFrame(Duration.ZERO, initWidth2, initTranslateX2);
        KeyFrame finalFrame2 = new KeyFrame(Duration.seconds(0.3), finalWidth2, finalTranslateX2);
        timeline2.getKeyFrames().addAll(initFrame2, finalFrame2);

        timeline2.play();

        statsPane.setVisible(true);
        expandBtn.setVisible(true);
        displayAbonnement.toBack();
        BoxBlur blur = new BoxBlur();
        blur.setWidth(10);
        blur.setHeight(10);
        blur.setIterations(3);
        displayAbonnement.toBack();
        displayAbonnement.setEffect(blur);

        form.toFront();
        form.setOpacity(0.85);
        displayAbonnement.setOpacity(0.85);

    }

public Map<String, Double> image_api(String s){
     imagePath = s; // Replace with your local image path

    OkHttpClient client = new OkHttpClient();
    Request request = buildRequest(imagePath);

    try (Response response = client.newCall(request).execute()) {
        String responseBody = response.body().string();
        JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();


        // Extract tags and confidence from the JSON response
        JsonArray tagsArray = json.getAsJsonObject("result").getAsJsonArray("tags");

        // Create Map to store the first 5 tags and their confidence

        tagMap.clear();
        for (int i = 0; i < Math.min(tagsArray.size(), 5); i++) {
            JsonObject tagObject = tagsArray.get(i).getAsJsonObject();
            String tag = tagObject.getAsJsonObject("tag").get("en").getAsString();
            double confidence = tagObject.get("confidence").getAsDouble();
            tagMap.put(tag, confidence);
        }

        // Print the contents of the map
        tagMap.forEach((tag, confidence) -> System.out.println("Tag: " + tag + ", Confidence: " + confidence));
        System.out.println(tagMap.toString());
        return tagMap;

    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }

}

    private static Request buildRequest(String imagePath) {
        File imageFile = new File(imagePath);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.getName(),
                        RequestBody.create(MediaType.parse("image/*"), imageFile))
                .build();

        return new Request.Builder()
                .url("https://api.imagga.com/v2/tags")
                .header("Authorization", "Basic " + getBasicAuth())
                .post(requestBody)
                .build();
    }

    private static String getBasicAuth() {
        String credentials = API_KEY + ":" + API_SECRET;
        return java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
    }


}
