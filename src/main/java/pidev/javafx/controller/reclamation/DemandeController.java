package pidev.javafx.controller.reclamation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.animation.Timeline;

import java.io.IOException;

public class DemandeController {

    public Pane box1;
    public Button showmore;
    public Button showmore1;
    public Button showmore2;
    public Pane box2;
    public Pane box3;
    @FXML
    private AnchorPane mainBorderPain;


    public void getBoton_on_click_add() throws IOException {
        box1.setOnMouseEntered(event -> showmore.setVisible(true));
        box1.setOnMouseExited(event -> showmore.setVisible(false));
        box1.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 10, 0, 0, 0));
        box2.setEffect(new BoxBlur(10, 10, 3));
        box3.setEffect(new BoxBlur(10, 10, 3));
        // timer1();
        // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Demande/intrface.fxml"));
        AnchorPane showEmpAnchorPane = FXMLLoader.load(getClass().getResource("/fxml/Demande/showuser_modife.fxml"));
        mainBorderPain.getChildren().setAll(showEmpAnchorPane);
    }

    public void  getBoton_on_click_add1() {
        box2.setOnMouseEntered(event -> showmore1.setVisible(true));
        box2.setOnMouseExited(event -> showmore1.setVisible(false));
        box2.setEffect(null);
        box2.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 10, 0, 0, 0));
        box1.setEffect(new BoxBlur(10, 10, 3));
        box3.setEffect(new BoxBlur(10, 10, 3));
        timer2();
        AnchorPane showEmpAnchorPane = null;
        try {
            showEmpAnchorPane = FXMLLoader.load(getClass().getResource("/fxml/Demande/intrface.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainBorderPain.getChildren().setAll(showEmpAnchorPane);

    }
    public void  getBoton_on_click_add2() {
        box3.setOnMouseEntered(event -> showmore2.setVisible(true));
        box3.setOnMouseExited(event -> showmore2.setVisible(false));
        box3.setEffect(null);
        box3.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 10, 0, 0, 0));
        box2.setEffect(new BoxBlur(10, 10, 3));
        box1.setEffect(new BoxBlur(10, 10, 3));
        timer3();
    }
    public void timer1()
    {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(3), new KeyValue(box2.effectProperty(), null)));
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(3), new KeyValue(box3.effectProperty(), null)));
        timeline.play();

    }
    public void timer2()
    {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(3), new KeyValue(box1.effectProperty(), null)));
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(3), new KeyValue(box3.effectProperty(), null)));
        timeline.play();

    }
    public void timer3()
    {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(3), new KeyValue(box2.effectProperty(), null)));
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(3), new KeyValue(box1.effectProperty(), null)));
        timeline.play();

    }

}


