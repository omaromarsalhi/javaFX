package pidev.javafx.controller.transport;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.util.Duration;
import pidev.javafx.crud.transport.ServicesTransport;
import pidev.javafx.model.Transport.Transport;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private VBox mainanchor;
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
    private int numBtnSlected=1;





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

    @FXML
    private  Label timeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_timeline();
        loadList(0);
        toggleButton1.setSelected(true);
        loadMeteo().start();
    }

    public Thread loadMeteo(){
        Task<Pane> task=new Task<Pane>() {
            @Override
            protected Pane call() throws Exception {
                return Station_Infos();
            }
        };
        task.setOnSucceeded( event -> {
            Platform.runLater( ()->{
                metePane.getChildren().setAll(task.getValue());
            });
        } );
        return new Thread(task);
    }


    public Pane Station_Infos(){
        if (toggleButton1.isSelected())
            numBtnSlected=1;
        else if (toggleButton2.isSelected())
            numBtnSlected=2;
        else if (toggleButton3.isSelected())
            numBtnSlected=3;

        try {
            return FXMLLoader.load(getClass().getResource("/fxml/Transport/Weather/Tempreture"+numBtnSlected+".fxml"));
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
    }

    @FXML
    private Pane mainPain;
    public void Close(){
        mainPain.setVisible(false);
    }

    public void expand_column(int i){
        showItems.setPrefHeight(showItems.getPrefHeight()+113);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(177);
        showItems.getRowConstraints().set(i, rowConstraints);
    }

    public void unexpand_column(int i){
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(64);
        showItems.getRowConstraints().set(i, rowConstraints);
        showItems.setPrefHeight(showItems.getPrefHeight()-113);
    }

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

    public void create_timeline(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateCurrentTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }
    // Create a Timeline to update the time every second

    private void updateCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        timeLabel.setText(formattedTime+" CET (UTC +01:00) | ");
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

                transportItem.initialize(null, null);
                transportItem.onDropdownClick(new ActionEvent());
                int finalI = i;
                ToggleButton yourButton = transportItem.getDropToggle();
                yourButton.setOnAction(event -> transportItem.onDropdownClick(event));
                yourButton.setOnMouseClicked(event -> handleCellClick(vBox, finalI, 0));

                vBox.setMinHeight(60);


                showItems.add(vBox, 0, i);
    }

} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}