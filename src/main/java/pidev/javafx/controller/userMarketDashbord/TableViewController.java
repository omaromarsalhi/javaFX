package pidev.javafx.controller.userMarketDashbord;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Categorie;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML
    private TableColumn<Bien, String> descCol;
    @FXML
    private TableColumn<Bien, String> imgCol;
    @FXML
    private TableColumn<Bien, String> nameCol;
    @FXML
    private TableColumn<Bien, Float> priceCol;
    @FXML
    private TableColumn<Bien, Float> quantityCol;
    @FXML
    private TableColumn<Bien, Boolean> stateCol;
    @FXML
    private TableColumn<Bien, Timestamp> timestampCol;
    @FXML
    private TableColumn<Bien, Categorie> categoryCol;
    @FXML
    private TableView<Bien> ProductTable;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("descreption"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        timestampCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("categorie"));
    }

    public void setData(ObservableList<Bien> bienList){
        ProductTable.setItems( bienList );
    }

}
