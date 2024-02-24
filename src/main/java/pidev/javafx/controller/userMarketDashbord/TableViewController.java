package pidev.javafx.controller.userMarketDashbord;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import javafx.util.converter.*;
import pidev.javafx.crud.marketplace.CrudBien;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Categorie;
import pidev.javafx.model.MarketPlace.Product;
import pidev.javafx.tools.CustomHoverTableCell;
import pidev.javafx.tools.CustomMouseEvent;
import pidev.javafx.tools.CustomTextFieldTableCell;
import pidev.javafx.tools.EventBus;


import java.io.File;
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
    private TableColumn<Bien, String> actionCol;
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
        priceCol.setCellFactory( CustomTextFieldTableCell.forTableColumn(new FloatStringConverter()));
        quantityCol.setCellFactory( CustomTextFieldTableCell.forTableColumn(new FloatStringConverter()));
        stateCol.setCellFactory( CustomTextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        timestampCol.setCellFactory( CustomTextFieldTableCell.forTableColumn( new StringConverter<Timestamp>() {
            @Override
            public String toString(Timestamp object) {
                return object.toLocalDateTime().format( DateTimeFormatter.ofPattern( "yyy/MM/dd hh:mm" ) );
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

        TableColumn<Bien, String> actionCol = new TableColumn<>("Actions");

        actionCol.setCellFactory(param -> new TableCell<>() {
            private final HBox btnBox = new HBox();
            private final Button editButton = new Button();
            private final Button deleteButton = new Button();
            {
                btnBox.setMaxWidth( 40 );
                editButton.setGraphic(new ImageView(new Image( "file:src/main/resources/namedIcons/changes.png" ,16,16,true,true)) );
                deleteButton.setGraphic(new ImageView(new Image( "file:src/main/resources/namedIcons/delete16.png",16,16,true,true )) );


                editButton.setOnAction(event -> {
                    Bien item = getTableView().getItems().get(getIndex());
                    CustomMouseEvent<Bien> customMouseEvent=new CustomMouseEvent<>(item);
                    EventBus.getInstance().publish( "updateProd",customMouseEvent);
                });


                deleteButton.setOnAction(event -> {
                    Bien item = getTableView().getItems().get(getIndex());
                    CustomMouseEvent<Bien> customMouseEvent=new CustomMouseEvent<>(item);
                    CrudBien.getInstance().deleteItem( item.getId());
                    for(String path :item.getAllImagesSources())
                        new File("src/main/resources"+path).delete();
                    EventBus.getInstance().publish( "refreshTableOnDelete",customMouseEvent);
                });

                btnBox.setAlignment( Pos.CENTER );
                btnBox.getChildren().addAll(editButton,deleteButton);
                btnBox.getStylesheets().add("file:src/main/resources/style/Buttons.css");
                btnBox.setSpacing( 3 );
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnBox);
                }
            }
        });
        ProductTable.getColumns().add(actionCol);
        ProductTable.setFixedCellSize( 50d ); // Set the desired row height (in pixels)

    }

    public void setData(ObservableList<Bien> bienList){
        ProductTable.setItems( bienList );
    }

}
