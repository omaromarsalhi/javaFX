package pidev.javafx.controller.transport;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.util.Duration;
import pidev.javafx.crud.transport.ServicesTransport;
import pidev.javafx.model.Transport.Station;
import pidev.javafx.model.Transport.Transport;
import pidev.javafx.tools.CustomMouseEvent;
import pidev.javafx.tools.transport.EventBus;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.sql.Time;

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
    @FXML
    private ScrollPane scrollDetails;


    public void Station_Infos() throws IOException {

        if (toggleButton1.isSelected()) {
            Pane tempreture1 = FXMLLoader.load(getClass().getResource("/fxml/Transport/Weather/Tempreture1.fxml"));
            metePane.getChildren().setAll(tempreture1);
        } else if (toggleButton2.isSelected()) {
            Pane tempreture2 = FXMLLoader.load(getClass().getResource("/fxml/Transport/Weather/Tempreture2.fxml"));
            metePane.getChildren().setAll(tempreture2);
        } else if (toggleButton3.isSelected()) {
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

    int count = 0;
    @FXML
    private Label timeLabel;
    @FXML
    private Label stationName;
    Station receivedStation = new Station();
    LocalTime currentTime;
    Time timeVariable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentTime = LocalTime.now();
        timeVariable = Time.valueOf(currentTime);

        EventBus.getInstance().subscribe("StationEvent", this::first_function);
        create_timeline();
        toggleButton1.setSelected(true);
    }

    private void afterInitialization() {
        if (count < 1) {
            loadList();
            count++;
        }
    }

    public void first_function(CustomMouseEvent<Station> T) {
        receivedStation = T.getEventData();
    }

    @FXML
    private Pane mainPain;

    public void Close() {
        mainPain.setVisible(false);
    }

    public void expand_column(int i) {

        showItems.setPrefHeight(showItems.getPrefHeight() + 110);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPrefHeight(177);
        showItems.getRowConstraints().set(i, rowConstraints);
    }


    public void unexpand_column(int i) {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPrefHeight(70);
        showItems.getRowConstraints().set(i, rowConstraints);
        showItems.setPrefHeight(showItems.getPrefHeight() - 110);
    }

    boolean cellSelected = false;
    int selected_cell = 0;

    private void handleCellClick(Pane label, int row, int col) {


        if (cellSelected == false) {

            selected_cell = row;
            expand_column(row);
            cellSelected = true;
            System.out.println(row + " -> " + selected_cell);
        } else {
            if (selected_cell == row) {
                unexpand_column(row);
                selected_cell = 0;
                cellSelected = false;
                System.out.println(row + " -> " + selected_cell);

            } else {
                unexpand_column(selected_cell);
                //  expand_column(row);
                cellSelected = false;
                System.out.println(row + " -> " + selected_cell);

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

    public void create_timeline() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> fillPopup()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
    // Create a Timeline to update the time every second

    private void fillPopup() {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        timeLabel.setText(formattedTime + " CET (UTC +01:00) | 17 Feb 2024");
        stationName.setText(receivedStation.getNomStation());
        afterInitialization();
    }

    @FXML
    private Button openArrive;
    @FXML
    private Button openDepart;
    @FXML
    private Button openStop;
    private int clickedState;

    public void handleClickedOption() {
        openArrive.setOnAction(event -> {
            clickedState = 1;
            System.out.println(1);
            loadList();
        });

        openDepart.setOnAction(event -> {
            System.out.println(2);
            clickedState = 2;
            loadList();

        });
        openStop.setOnAction(event -> {
            System.out.println(3);
            clickedState = 3;
            loadList();

        });
    }

    @FXML
    public void loadList() {

        Set<Transport> T;
        showItems.getChildren().clear();
        showItems.setPrefHeight(0);
        if (receivedStation != null) {
            T = st.getByid(receivedStation.getIdStation());

            Iterator<Transport> iterator = T.iterator();

            if (clickedState == 1) {
//
                List<Transport> filteredList = T.stream()
                        .filter(Transport -> Transport.getDepart().equals(receivedStation.getNomStation()))
                        .collect(Collectors.toList());
//                showItems.setPrefHeight(70 * filteredList.size());
//                System.out.println(showItems.getPrefHeight());
//                try {
//
//                    for (int i = 0; i < filteredList.size(); i++) {
//                        Transport data = filteredList.get(i);
//                        RowConstraints rowConstraints = new RowConstraints();
//                        rowConstraints.setVgrow(Priority.ALWAYS);
//                        showItems.getRowConstraints().add(rowConstraints);
//                        FXMLLoader fxmlLoader = new FXMLLoader();
//                        fxmlLoader.setLocation(getClass().getResource("/fxml/Transport/Gui_Station/transportDetails.fxml"));
//                        vBox = fxmlLoader.load();
//                        transportDetailsContoller transportItem = fxmlLoader.getController();
//                        transportItem.setData(data);
//                        transportItem.initialize(null, null);
//                        transportItem.onDropdownClick(new ActionEvent());
//                        int finalI = i;
//                        ToggleButton yourButton = transportItem.getDropToggle();
//                        yourButton.setOnAction(event -> transportItem.onDropdownClick(event));
//                        yourButton.setOnMouseClicked(event -> handleCellClick(vBox, finalI, 0));
//                        showItems.add(vBox, 0, i);
//                    }
//                    System.out.println(showItems.getAlignment());
//                    System.out.println(showItems.getLayoutX()+showItems.getLayoutY());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }

                showItems.getRowConstraints().clear(); // Clear existing row constraints
                showItems.getChildren().clear(); // Clear existing children
                showItems.setPrefHeight(70 * filteredList.size());

                try {
                    for (int i = 0; i < filteredList.size(); i++) {
                        Transport data = filteredList.get(i);
                        RowConstraints rowConstraints = new RowConstraints();
                        rowConstraints.setVgrow(Priority.ALWAYS);
                        showItems.getRowConstraints().add(rowConstraints);

                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/fxml/Transport/Gui_Station/transportDetails.fxml"));
                        vBox = fxmlLoader.load();
                        transportDetailsContoller transportItem = fxmlLoader.getController();
                        transportItem.setData(data);
                        transportItem.initialize(null, null);
                        transportItem.onDropdownClick(new ActionEvent());
                        int finalI = i;
                        ToggleButton yourButton = transportItem.getDropToggle();
                        yourButton.setOnAction(event -> transportItem.onDropdownClick(event));
                        yourButton.setOnMouseClicked(event -> handleCellClick(vBox, finalI, 0));
                        showItems.add(vBox, 0, i);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (clickedState == 2) {

                List<Transport> filteredList = T.stream()
                        .filter(Transport -> Transport.getArivee().equals(receivedStation.getNomStation()))
                        .collect(Collectors.toList());
                showItems.getRowConstraints().clear(); // Clear existing row constraints
                showItems.getChildren().clear(); // Clear existing children
                showItems.setPrefHeight(70 * filteredList.size());

                try {
                    for (int i = 0; i < filteredList.size(); i++) {
                        Transport data = filteredList.get(i);
                        RowConstraints rowConstraints = new RowConstraints();
                        rowConstraints.setVgrow(Priority.ALWAYS);
                        showItems.getRowConstraints().add(rowConstraints);

                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/fxml/Transport/Gui_Station/transportDetails.fxml"));
                        vBox = fxmlLoader.load();
                        transportDetailsContoller transportItem = fxmlLoader.getController();
                        transportItem.setData(data);
                        transportItem.initialize(null, null);
                        transportItem.onDropdownClick(new ActionEvent());
                        int finalI = i;
                        ToggleButton yourButton = transportItem.getDropToggle();
                        yourButton.setOnAction(event -> transportItem.onDropdownClick(event));
                        yourButton.setOnMouseClicked(event -> handleCellClick(vBox, finalI, 0));
                        showItems.add(vBox, 0, i);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (clickedState == 3) {

                List<Transport> filteredList = T.stream()
                        .filter(Transport -> Transport.getArivee().equals(receivedStation.getNomStation()))
                        .filter(Transport -> Transport.getHeure().after(timeVariable))
                        .collect(Collectors.toList());
                System.out.println(70 * filteredList.size());
                showItems.setPrefHeight(70 * filteredList.size());
                System.out.println(showItems.getPrefHeight());

                try {

                    for (int i = 0; i < filteredList.size(); i++) {

//                             Transport data = dataList.get(i);
                        Transport data = filteredList.get(i);
                        RowConstraints rowConstraints = new RowConstraints();
                        rowConstraints.setVgrow(Priority.ALWAYS);
                        showItems.getRowConstraints().add(rowConstraints);
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/fxml/Transport/Gui_Station/transportDetails.fxml"));
                        vBox = fxmlLoader.load();
                        transportDetailsContoller transportItem = fxmlLoader.getController();
                        transportItem.setData(data);
                        transportItem.initialize(null, null);
                        transportItem.onDropdownClick(new ActionEvent());
                        int finalI = i;
                        ToggleButton yourButton = transportItem.getDropToggle();
                        yourButton.setOnAction(event -> transportItem.onDropdownClick(event));
                        yourButton.setOnMouseClicked(event -> handleCellClick(vBox, finalI, 0));
                        showItems.add(vBox, 0, i);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}