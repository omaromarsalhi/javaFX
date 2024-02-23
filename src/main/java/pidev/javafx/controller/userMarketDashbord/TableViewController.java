package pidev.javafx.controller.userMarketDashbord;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import javafx.util.converter.*;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Categorie;
import pidev.javafx.tools.CustomHoverTableCell;
import pidev.javafx.tools.CustomTextFieldTableCell;


import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML
    private TableColumn<Bien, String> descCol;
    @FXML
    private TableColumn<Bien, Image> imgCol;
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


        var table=new CustomHoverTableCell<Bien,Image>();
        imgCol.setCellFactory(table.forTableColumn());
        nameCol.setCellFactory( CustomTextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        descCol.setCellFactory( CustomTextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        priceCol.setCellFactory( CustomTextFieldTableCell.forTableColumn(new FloatStringConverter()));
        quantityCol.setCellFactory( CustomTextFieldTableCell.forTableColumn(new FloatStringConverter()));
        stateCol.setCellFactory( CustomTextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        timestampCol.setCellFactory( CustomTextFieldTableCell.forTableColumn( new StringConverter<Timestamp>() {
            @Override
            public String toString(Timestamp object) {
                return object.toLocalDateTime().format( DateTimeFormatter.ofPattern( "yyy/MM:dd hh:mm" ) );
            }

            @Override
            public Timestamp fromString(String string) {
                return null;
            }
        } ));
        categoryCol.setCellFactory( CustomTextFieldTableCell.forTableColumn( new StringConverter<Categorie>() {
            @Override
            public String toString(Categorie object) {
                return object.toString();
            }

            @Override
            public Categorie fromString(String string) {
                return null;
            }
        } ));
    }

    public void setData(ObservableList<Bien> bienList){
        ProductTable.setItems( bienList );
    }

}
