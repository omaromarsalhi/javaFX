package pidev.javafx.controller.transport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import pidev.javafx.crud.transport.ServicesTransport;
import pidev.javafx.model.Transport.Transport;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

public class TranportController implements Initializable  {

@FXML
private ListView Transport_list;

@FXML
private TableView Transport_table;


@FXML
private ScrollPane mainBorderPain;
@FXML
private Pane displayTransport;

private Connection connect;

private PreparedStatement prepare;


    @FXML
    private TableColumn<Transport, Date> ArriveCol;

    @FXML
    private TableColumn<Transport, Date> DepartCol;

    @FXML
    private TableColumn<Transport, String> TypeCol;

    @FXML
    private TableColumn<Transport, Date> HeureCol;

    @FXML
    private TableColumn<Transport, Float> PrixCol;

    @FXML
    private TableColumn<Transport, String> ReferenceCol;
    @FXML
    private TableColumn<Transport, String> ImageCol;
     TableColumn<Transport, Void> functionCol = new TableColumn<>("");

@FXML
    TextField SearchText;



     Set<String> resultSetItems = new HashSet<>();
    private Transport data  =new Transport();
    private URL url;
    private ResourceBundle resourceBundle;
     FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Transport/Buttons/Delete.fxml"));
     Button btn = new Button();

Transport t;
     int selectedRow=0;
     ServicesTransport st=new ServicesTransport();



     @FXML
     public void initialize(URL url, ResourceBundle resourceBundle) {
         afficher();

//         OneSignal.startInit(this)
//                 .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                 .unsubscribeWhenNotificationsAreDisabled(true)
//                 .init();
         btn.setStyle("-fx-background-color: #bb2020; " +
                 "-fx-text-fill: white; -fx-font-size: 14px;" + "-fx-border-radius: 15px;" + "-fx-background-color: transparent");

         functionCol.setCellFactory(new Callback<TableColumn<Transport, Void>, TableCell<Transport, Void>>() {
             @Override
             public TableCell<Transport, Void> call(TableColumn<Transport, Void> param) {
                 return new TableCell<Transport, Void>() {
                     HBox hbox = new HBox();

                     {
                         Button Deletebtn = new Button();
                         Button Updatebtn = new Button();
                         ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/Delete-button.svg.png")));
                         ImageView updateIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/EditIcon.png")));

                         deleteIcon.setFitWidth(20); // Set your desired width
                         deleteIcon.setFitHeight(20);
                         updateIcon.setFitWidth(20); // Set your desired width
                         updateIcon.setFitHeight(20);
                         Deletebtn.setGraphic(deleteIcon);
                       Deletebtn.setStyle("-fx-background-color: transparent");
                       Updatebtn.setGraphic(updateIcon);
                       Updatebtn.setStyle("-fx-background-color: transparent");


                         Deletebtn.setOnAction((ActionEvent event) -> {
                             Transport data = getTableView().getItems().get(getIndex());
                             selectedRow = data.getIdTransport();

                             Supprimer(selectedRow);
                             afficher();
                         });

                         Updatebtn.setOnAction((ActionEvent event) -> {
                             Transport data = getTableView().getItems().get(getIndex());
                             selectedRow = data.getIdTransport();
                             t=data;


                             try{
                                 onUpdateButtonClicked(t);

                            }

                          catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                         });

                         hbox.getChildren().addAll(Deletebtn, Updatebtn);
                     }

                     @Override
                     protected void updateItem(Void item, boolean empty) {
                         super.updateItem(item, empty);
                         if (empty) {
                             setGraphic(null);
                         } else {
                             setGraphic(hbox);
                         }
                     }
                 };
             }
         });

         Transport_table.getColumns().add(functionCol);

     }




     @FXML
     public void afficher() {


        // dataList=st.getAll();




Set<Transport> dataList=new HashSet<>();

             ReferenceCol.setCellValueFactory(new PropertyValueFactory<>("Reference"));
             HeureCol.setCellValueFactory(new PropertyValueFactory<>("Heure"));
             DepartCol.setCellValueFactory(new PropertyValueFactory<>("depart"));
             ArriveCol.setCellValueFactory(new PropertyValueFactory<>("arivee"));
             TypeCol.setCellValueFactory(new PropertyValueFactory<>("type_vehicule"));
             PrixCol.setCellValueFactory(new PropertyValueFactory<>("Prix"));
             ImageCol.setCellValueFactory(new PropertyValueFactory<>("vehicule_Image"));


             ImageCol.setCellFactory(param -> new TableCell<>() {
                 private final ImageView imageView = new ImageView();

                 {
                     imageView.setFitWidth(50);
                     imageView.setFitHeight(50);
                 }

                 @Override
                 protected void updateItem(String imagePath, boolean empty) {
                     super.updateItem(imagePath, empty);

                     if (empty || imagePath == null) {
                         setGraphic(null);
                     } else {
                         // Set the image for the ImageView
                         Image image = new Image(imagePath);
                         imageView.setImage(image);
                         imageView.setStyle(    "-fx-background-radius: 50%;  "  );
                         imageView.getStyleClass().add("rounded-image");
                         setGraphic(imageView);

                     }
                 }
             });
             dataList=st.getAll();
         ObservableList<Transport> observableList = FXCollections.observableArrayList(dataList);

             Transport_table.setItems(observableList);



      }

        @FXML
        public Boolean Supprimer(int id) {       // Delete from the database
        st.deleteItem(id);
        return true;
        };


        @FXML
        public void onInsertClicked(ActionEvent event) throws IOException {

//            System.out.println(1);
//            ScrollPane scrollPane = FXMLLoader.load(  getClass().getResource("/fxml/Transport/AddTransport.fxml"));
//            scrollPane.setPrefHeight(mainBorderPain.getPrefHeight()  );
//            scrollPane.setPrefWidth( mainBorderPain.getPrefWidth() );
//            System.out.println(mainBorderPain.getChildren().size());
//            mainBorderPain.getChildren().remove(mainBorderPain.getChildren().get(0));
//            mainBorderPain.getChildren().add(scrollPane);

            ScrollPane scrollPane = FXMLLoader.load(Objects.requireNonNull( getClass().getResource("/fxml/Transport/Gui_Transport/AddTransport.fxml")));
           // ScrollPane scrollPane = FXMLLoader.load(Objects.requireNonNull( getClass().getResource("/fxml/Transport/AddTransport.fxml")));
            scrollPane.setPrefHeight(mainBorderPain.getPrefHeight()  );
            scrollPane.setPrefWidth( mainBorderPain.getPrefWidth() );
              mainBorderPain.setContent(scrollPane);
        }

     public void onUpdateButtonClicked(Transport t)  throws IOException {
         UpdateTransport.setData(t);
         ScrollPane scrollPane = FXMLLoader.load(Objects.requireNonNull( getClass().getResource("/fxml/Transport/Gui_Transport/UpdateTransport.fxml")));
          scrollPane.setPrefHeight(mainBorderPain.getPrefHeight()  );
         scrollPane.setPrefWidth( mainBorderPain.getPrefWidth() );
         mainBorderPain.setContent(scrollPane);
     };




}






