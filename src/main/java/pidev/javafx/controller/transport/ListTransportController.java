package pidev.javafx.controller.transport;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import pidev.javafx.crud.transport.ServicesTransport;
import pidev.javafx.model.Transport.Transport;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    private Pane metePane;

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
    ServicesTransport st = new ServicesTransport();



    public void Station_Infos() throws IOException {

        if (toggleButton1.isSelected()) {
            System.out.println(metePane);
           // Pane meteo = FXMLLoader.load(getClass().getResource("/fxml/Transport/Weather/Tempreture1.fxml"));
            Pane tempreture1 = FXMLLoader.load(getClass().getResource("/fxml/Transport/Weather/Tempreture1.fxml"));
            metePane.getChildren().setAll(tempreture1);
        }
        else if (toggleButton2.isSelected()) {
            System.out.println(metePane);
            // Pane meteo = FXMLLoader.load(getClass().getResource("/fxml/Transport/Weather/Tempreture1.fxml"));
            Pane tempreture2 = FXMLLoader.load(getClass().getResource("/fxml/Transport/Weather/Tempreture2.fxml"));
            metePane.getChildren().setAll(tempreture2);
        }
        else if (toggleButton3.isSelected()) {
            System.out.println(metePane);
            // Pane meteo = FXMLLoader.load(getClass().getResource("/fxml/Transport/Weather/Tempreture1.fxml"));
            Pane tempreture3 = FXMLLoader.load(getClass().getResource("/fxml/Transport/Weather/Tempreture3.fxml"));
            metePane.getChildren().setAll(tempreture3);
        }


    }

    @FXML
    private void handleToggleButtonAction(ToggleButton clickedButton) throws IOException {

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
    private void handleToggleButton1() throws IOException {
        handleToggleButtonAction(toggleButton1);
    }

    @FXML
    private void handleToggleButton2() throws IOException {
        handleToggleButtonAction(toggleButton2);
    }

    @FXML
    private void handleToggleButton3() throws IOException {
        handleToggleButtonAction(toggleButton3);
    }

    @FXML
    private void handleToggleButton4() throws IOException {
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
        toggleButton1.setSelected(true);
        try {
            Station_Infos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    int selected_cell =0;
    private void handleCellClick(Pane label, int row, int col) {


        if(cellSelected==false){

            selected_cell=row;
            expand_column(row);
            cellSelected=true;
            System.out.println(row+" -> " +selected_cell);
        }
        else {
            if(selected_cell==row){
                unexpand_column(row);
                selected_cell=0;
                cellSelected = false;
                System.out.println(row+" -> " +selected_cell);

            }
            else {
                unexpand_column(selected_cell);
              //  expand_column(row);
                cellSelected=false;
                System.out.println(row+" -> " +selected_cell);

            }
        }
    }

    @FXML
    private Pane TransportPane;
@FXML
public void openDetails() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Transport/Gui_Station/StationInfos.fxml"));
    AnchorPane loadedPane = loader.load();
    TransportPane.getChildren().setAll(loadedPane.getChildren());
}

@FXML
   public void openArrive() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Transport/Gui_Station/ListTransports.fxml"));
    Pane loadedPane = loader.load();
    TransportPane.getChildren().setAll(loadedPane.getChildren());
}


@FXML
    public void loadList(int pos) {

Set<Transport> T= new HashSet<>();
T=st.getAll();
    List<Transport> dataList = new ArrayList<>(T);
    Iterator<Transport> iterator = T.iterator();

        showItems.setPrefHeight(showItems.getPrefHeight() * T.size());

       try {

    for (int i = 0; i < T.size(); i++) {
        Transport data = dataList.get(i);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        showItems.getRowConstraints().add(rowConstraints);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/Transport/Gui_Station/transportDetails.fxml"));

        vBox = fxmlLoader.load();
        transportDetailsContoller transportItem = fxmlLoader.getController();
        transportItem.setData(data);

        // Explicitly call the initialize method
        transportItem.initialize(null, null);
        transportItem.onDropdownClick(new ActionEvent()); // Replace with the actual function name
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