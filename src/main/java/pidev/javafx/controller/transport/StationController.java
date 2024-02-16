package pidev.javafx.controller.transport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import pidev.javafx.crud.transport.ServicesStation;
import pidev.javafx.model.Transport.Station;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

public class StationController implements Initializable {
    @FXML
   private TextArea Referance_text;
    @FXML
    private Button showBtn;
    @FXML
    private Button deleteBtn;
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    @FXML
    private ListView<Station> StationListView;

    @FXML
    private ScrollPane mainBorderPain;
    private ServicesStation ss=new ServicesStation();

@FXML
    protected void onTextChanged() {
    String[] name = new String[10];

    name[1] = Referance_text.getText();

    if (name[1].matches("[a-zA-Z]+"))
        Referance_text.setStyle("-fx-control-inner-background: #25c12c;");
    else
        Referance_text.setStyle("-fx-control-inner-background: #bb2020;");}




public void afficher (){
    Set<Station> dataList;
    dataList=ss.getAll();
    ObservableList<Station> data = FXCollections.observableArrayList(dataList);
    StationListView.setItems(data);
}

    public void onInsertStationClicked(ActionEvent event)  throws IOException {
        ScrollPane scrollPane = FXMLLoader.load(Objects.requireNonNull( getClass().getResource("/fxml/Transport/AddStation.fxml")));
        scrollPane.setPrefHeight(mainBorderPain.getPrefHeight()  );
        scrollPane.setPrefWidth( mainBorderPain.getPrefWidth() );
        mainBorderPain.setContent(scrollPane);
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficher();
    }

}


