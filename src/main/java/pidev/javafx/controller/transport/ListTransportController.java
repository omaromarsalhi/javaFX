package pidev.javafx.controller.transport;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import pidev.javafx.controller.userMarketDashbord.TransactionDetailsController;

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
    private GridPane showItems;

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
    private Pane vBox;

    String clickedOption;



    public void Station_Infos() {

        if (toggleButton1.isSelected()) {
            System.out.println(1);
            InfoLabel.setText("option 1");
        } else if (toggleButton2.isSelected()) {
            System.out.println(2);
            InfoLabel.setText("option 2");
        } else if (toggleButton3.isSelected()) {
            InfoLabel.setText("option 3");
            System.out.println(3);
        } else if (toggleButton4.isSelected()) {
            InfoLabel.setText("option 4");
            System.out.println(4);
        }

    }

    @FXML
    private void handleToggleButtonAction(ToggleButton clickedButton) {

        if (clickedButton.isSelected()) {
            if (clickedButton.equals(dropToggle)) {
                onDropdownClick();
            } else {
                toggleButton1.setSelected(false);
                toggleButton2.setSelected(false);
                toggleButton3.setSelected(false);
                toggleButton4.setSelected(false);
                clickedButton.setSelected(true);
                clickedOption = clickedButton.toString();
                Station_Infos();
            }

        } else {
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
        } else if (!dropToggle.isSelected()) {
            detailsTransport.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

loadList(0);
    }
    @FXML
    private Pane mainPain;
    public void Close(){
        mainPain.setVisible(false);
    }

    public void expand_column(int i){
     //   showItems.setPrefHeight(showItems.getPrefHeight()*5);
        //showItems.setPrefHeight(showItems.getPrefHeight()+113);
        showItems.setPrefHeight(showItems.getPrefHeight()+113);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(177);
        showItems.getRowConstraints().set(i, rowConstraints);

      //  showItems.getRowConstraints().add(rowConstraints);
    }
    public void unexpand_column(int i){
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(64);
        showItems.getRowConstraints().set(i, rowConstraints);
        showItems.setPrefHeight(showItems.getPrefHeight()-113);
    }
//@FXML
//    private void handleCellClick(MouseEvent event) {
//        Label clickedLabel = (Label) event.getSource();
//        int rowIndex = showItems.getRowIndex(clickedLabel);
//        int colIndex = showItems.getColumnIndex(clickedLabel);
//
//        System.out.println("Clicked on cell at row " + rowIndex + ", column " + colIndex);
//    }
boolean cellSelected =false;
    private void handleCellClick(Pane label, int row, int col) {

       // unexpand_column();
        System.out.println("Clicked on cell at row " + row + ", column " + col + ": " );
      //  showItems.setPrefHeight(showItems.getPrefHeight()+113);
        if(cellSelected==false){
        expand_column(row);
        cellSelected=true;
        }
        else
        {  unexpand_column(row);
        cellSelected=false;}

    }

    @FXML
    private Pane TransportPane;
@FXML
public void openDetails() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Transport/StationInfos.fxml"));
    AnchorPane loadedPane = loader.load();
    TransportPane.getChildren().setAll(loadedPane.getChildren());
}

@FXML
   public void openArrive() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Transport/ListTransports.fxml"));
    Pane loadedPane = loader.load();
    TransportPane.getChildren().setAll(loadedPane.getChildren());

}

//private ScrollPane scroll;
//    public void loadList(int pos) {
//        // Set Vgrow constraints outside the loop
//        showItems.setPrefHeight(showItems.getPrefHeight()*5);
//
//        try {
//        for (int i = 0; i <5; i++) {
//            RowConstraints rowConstraints = new RowConstraints();
//            rowConstraints.setVgrow(javafx.scene.layout.Priority.ALWAYS);
//            showItems.getRowConstraints().add(rowConstraints);
//
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("/fxml/Transport/transportDetails.fxml"));
//
//                vBox = fxmlLoader.load();
//            transportDetailsContoller Dc= new transportDetailsContoller();
//          //  Dc.onDropdownClick(new ActionEvent());
//                vBox.setMinHeight(60);
//                transportDetailsContoller transportItem = fxmlLoader.getController();
//            int finalI = i;
//            vBox.setOnMouseClicked(event->handleCellClick(vBox, finalI,0));
//
//                showItems.add( vBox ,0,i);
//
//
//
//
//        }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
@FXML
    public void loadList(int pos) {
        // Set Vgrow constraints outside the loop
        showItems.setPrefHeight(showItems.getPrefHeight() * 5);

        try {
            for (int i = 0; i < 5; i++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setVgrow(javafx.scene.layout.Priority.ALWAYS);
                showItems.getRowConstraints().add(rowConstraints);

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/Transport/transportDetails.fxml"));

                vBox = fxmlLoader.load();
                transportDetailsContoller transportItem = fxmlLoader.getController();

                // Call the desired function in the controller
//                transportItem.onDropdownClick(new ActionEvent()); // Replace with the actual function name
                int finalI = i;
                // Set up a button click event
                ToggleButton yourButton = transportItem.getDropToggle(); // Replace with the actual method to get your button
                yourButton.setOnAction(event -> transportItem.onDropdownClick(event));
                yourButton.setOnMouseClicked(event -> handleCellClick(vBox, finalI, 0)); // Replace with the actual button function

                vBox.setMinHeight(60);


                showItems.add(vBox, 0, i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}