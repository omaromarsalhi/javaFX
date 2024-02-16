package pidev.javafx.controller.transport;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import pidev.javafx.crud.transport.ServicesAbonnement;
import pidev.javafx.model.Transport.Abonnement;
import pidev.javafx.model.Transport.Transport;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AbonnementController implements Initializable {

    private Connection connect;

    ObservableList<Transport> dataList = FXCollections.observableArrayList();
    List<Abonnement> abonnementList = new ArrayList<>();
    ServicesAbonnement sa =new ServicesAbonnement();
    @FXML
    Pane paneToAnnimate;

    private Stage primaryStage;

    @FXML
    private Label DebutLabel;

    @FXML
    private Label FinLabel;

    @FXML
    private Label IdLabel;

    @FXML
    private Label NomLabel;

    @FXML
    private Label PrenomLabel;
    @FXML
            private Button nextBtn;
    @FXML
            private Button previousBtn;
    @FXML
            private TextField NomText;
    @FXML
            private TextField PrenomText;
    @FXML
            private ComboBox <String> TypeAbonnementBox;

       int i;
       Set <Abonnement> abonnementSet;
    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), paneToAnnimate);
String imagePath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TypeAbonnementBox.getItems().addAll("Annuel","mensuel");
       // toolsBar.setVisible(false);
        afficher();
      if(abonnementList.size()>0)
        remplir_abonnement();

        translateTransition.setNode(paneToAnnimate);

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
            imagePath=selectedFile.getAbsolutePath() ;

        }
    }
    public void add_load(){
        UpdateBtn.setVisible(false);
    }
    public void ajouter(){



      Abonnement p=new Abonnement(NomText.getText(),PrenomText.getText(),TypeAbonnementBox.getValue().toString(),imagePath);

      sa.ajouter(p);
        afficher();
        remplir_abonnement();
    };

public void DeleteAbonnement(){

        int id = abonnementList.get(i).getIdAboonnement();
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Vous voules vraiment effacer ce fichier ?");
        alert.showAndWait();
        sa.supprimer(id);
        afficher();
        remplir_abonnement();
};

    @FXML
    protected void onTextChanged() {
        String[] text = new String[10];

        text[1] = NomText.getText();
        text[2]= PrenomText.getText();

        if (text[1].matches("[a-zA-Z]*"))
            NomText.setStyle("-fx-text-fill: #25c12c;");
        else
            NomText.setStyle("-fx-text-fill: #bb2020;");

        if (text[2].matches("[a-zA-Z]+"))
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

    }
    @FXML
    public void nextAb() {
     //   translateTransition.setToX(400);
       // translateTransition.play();
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
        UpdateBtn.setVisible(true);
    NomText.setText(abonnementList.get(i).getNom());
    PrenomText.setText(abonnementList.get(i).getPrenom());
    TypeAbonnementBox.setValue(abonnementList.get(i).getType());
    imagePath=abonnementList.get(i).getImage();
    }
    @FXML
    Button UpdateBtn;
    public void Update(){

        Abonnement A = new Abonnement(NomText.getText(),PrenomText.getText(),TypeAbonnementBox.getValue(),imagePath);
        System.out.println(NomText.getText());
          A.setIdAboonnement(abonnementList.get(i).getIdAboonnement());
        sa.modifier(A);
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Vous voules vraiment effacer ce fichier ?");
        alert.showAndWait();
        UpdateBtn.setVisible(false);
        afficher();
        remplir_abonnement();
    }
@FXML
    VBox statsPannel;

    @FXML
    Pane statsPane;
    @FXML
    Button expandBtn;
@FXML
Button openBtn;
//    @FXML
//    public void expand(){
//
//        Timeline timeline = new Timeline();
//        timeline.setCycleCount(1);
//        timeline.setAutoReverse(false);
//        KeyValue initWidth = new KeyValue(statsPannel.prefWidthProperty(), 380);
//        KeyValue initTranslateX = new KeyValue(statsPannel.translateXProperty(), 50);
//        KeyValue finalWidth = new KeyValue(statsPannel.prefWidthProperty(), 50);
//        KeyValue finalTranslateX = new KeyValue(statsPannel.translateXProperty(), 380);
//        KeyFrame initFrame = new KeyFrame(Duration.ZERO, initWidth, initTranslateX);
//        KeyFrame finalFrame = new KeyFrame(Duration.seconds(1), finalWidth, finalTranslateX);
//        timeline.getKeyFrames().addAll(initFrame, finalFrame);
//        openBtn.setVisible(true);
//        statsPane.setVisible(false);
//        expandBtn.setVisible(true);
//         timeline.play();
//
//
//
//
//    }

//
//    @FXML
//    public void unexpand(){
//
//        Timeline timeline2 = new Timeline();
//        timeline2.setCycleCount(1);
//        timeline2.setAutoReverse(false);
//        KeyValue initWidth2 = new KeyValue(statsPannel.prefWidthProperty(), 50);
//        KeyValue initTranslateX2 = new KeyValue(statsPannel.translateXProperty(), 404);
//        KeyValue finalWidth2 = new KeyValue(statsPannel.prefWidthProperty(), 404);
//        KeyValue finalTranslateX2 = new KeyValue(statsPannel.translateXProperty(), 50);
//        KeyFrame initFrame2 = new KeyFrame(Duration.ZERO, initWidth2, initTranslateX2);
//        KeyFrame finalFrame2 = new KeyFrame(Duration.seconds(1), finalWidth2, finalTranslateX2);
//        timeline2.getKeyFrames().addAll(initFrame2, finalFrame2);
//        statsPane.setVisible(true);
//        expandBtn.setVisible(true);
//        openBtn.setVisible(false);
//        timeline2.play();
//
//    }
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

    openBtn.setVisible(true);
    statsPane.setVisible(false);
    expandBtn.setVisible(true);

    timeline.play();
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
        openBtn.setVisible(false);
    }






}
