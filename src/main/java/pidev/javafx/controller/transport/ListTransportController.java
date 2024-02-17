package pidev.javafx.controller.transport;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListTransportController implements Initializable {

    @FXML
    private Label Arrive_Status;

    @FXML
    private Label InfoLabel1;

    @FXML
    private Label InfoLabel11;

    @FXML
    private Label InfoLabel2;

    @FXML
    private Label InfoLabel21;

    @FXML
    private Label InfoLabel3;

    @FXML
    private Label InfoLabel31;

    @FXML
    private Label InfoLabel32;

    @FXML
    private Label InfoLabel321;

    @FXML
    private Label InfoLabel33;

    @FXML
    private Label InfoLabel331;

    @FXML
    private Label InfoLabel3311;

    @FXML
    private Label InfoLabel332;

    @FXML
    private Label InfoLabel34;

    @FXML
    private Pane detailsTransport;

    @FXML
    private ToggleButton dropToggle;

    @FXML
    private AnchorPane mainanchor;

    @FXML
    private VBox showItems;

    @FXML
    private ToggleButton toggleButton1;

    @FXML
    private ToggleButton toggleButton2;

    @FXML
    private ToggleButton toggleButton3;

    @FXML
    private ToggleButton toggleButton4;
    @FXML
    private Label InfoLabel;
    VBox vBox;

    String clickedOption;

    public void showArrive_status(){

        Arrive_Status.setText("arrive");
//        Timeline timeline = new Timeline(
//                new KeyFrame(Duration.seconds(2), event -> {
//                    // Update the label text
//                    label.setText(texts[currentIndex]);
//
//                    // Switch to the next text
//                    currentIndex = (currentIndex + 1) % texts.length;
//                })
//        );
//        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
//        timeline.play();
    }
    public void Station_Infos() {
        showArrive_status();

        if(toggleButton1.isSelected()){
            System.out.println(1);
            InfoLabel.setText("option 1");
        }
        else if(toggleButton2.isSelected()){
            System.out.println(2);
            InfoLabel.setText("option 2");
        }
        else if(toggleButton3.isSelected()){
            InfoLabel.setText("option 3");
            System.out.println(3);
        }
        else  if(toggleButton4.isSelected()){
            InfoLabel.setText("option 4");
            System.out.println(4);
        }

    }
    @FXML
    private void handleToggleButtonAction(ToggleButton clickedButton) {
        // Unselect other buttons

        if(clickedButton.isSelected()) {
            if(clickedButton.equals(dropToggle))
            {
                onDropdownClick();
            }
            else{
                toggleButton1.setSelected(false);
                toggleButton2.setSelected(false);
                toggleButton3.setSelected(false);
                toggleButton4.setSelected(false);
                clickedButton.setSelected(true);
                clickedOption = clickedButton.toString();
                Station_Infos();}

        }

        else {
            // If the clicked button was already selected, unselect it
            clickedButton.setSelected(false);
        }
    }


    @FXML
    void onDropdownClick(ActionEvent event) {

    }
    @FXML
    private void handleToggleButton1() {
        handleToggleButtonAction(toggleButton1);
    }

    @FXML
    private void handleToggleButton2() {
        handleToggleButtonAction(toggleButton2);
    }

    @FXML
    private void handleToggleButton3() {
        handleToggleButtonAction(toggleButton3);
    }

    @FXML
    private void handleToggleButton4() {
        handleToggleButtonAction(toggleButton4);
    }
    @FXML
    private void onDropdownClick() {
        if (dropToggle.isSelected()) {
            detailsTransport.setVisible(false);
        }
        else if (!dropToggle.isSelected()) {
            detailsTransport.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i=0;i<2;i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/Transport/transportDetails.fxml"));


            try {

                    vBox = fxmlLoader.load();
                    transportDetailsContoller transportItem = fxmlLoader.getController();
                Insets vboxInsets = new Insets(100, 20, 20, 40); // top, right, bottom, left
                VBox.setMargin(showItems, vboxInsets);
                    showItems.getChildren().add(vBox);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(vBox);

        }
    }
}
