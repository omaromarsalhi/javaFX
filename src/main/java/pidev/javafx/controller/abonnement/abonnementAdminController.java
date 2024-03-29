package pidev.javafx.controller.abonnement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import pidev.javafx.crud.transport.ServicesAbonnement;
import pidev.javafx.model.Transport.Abonnement;
import pidev.javafx.model.Transport.Station;
import pidev.javafx.tools.transport.allStat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class abonnementAdminController implements Initializable {

    @FXML
    private ListView<Abonnement> abonnementListView;
    ServicesAbonnement sa=new ServicesAbonnement();
    @FXML
    private TextField NomText;

    @FXML
    private TextField PrenomText;


    @FXML
    private TextField SearchText;
    @FXML
    private ComboBox <String> TypeAbonnementBox;
    @FXML
    private ComboBox<Station> Depart;
    @FXML
    private ComboBox<Station> Arrive;



    @FXML
    private Button UpdateBtn;

    @FXML
    private Pane displayTransport;

    @FXML
    private Pane displayTransport1;

    @FXML
    private Button expandBtn;

    @FXML
    private Button insertStation;

    @FXML
    private Pane statsPane;

    @FXML
    private VBox statsPannel;
    List<Abonnement> abonnementList = new ArrayList<>();
    String imagePath;
    Abonnement selectedItem = new Abonnement();
    Abonnement selectedItem_1 = new Abonnement();
    final String destinationString = "src/main/resources/";
    allStat stat= new allStat();
@FXML
private Pane UpdatePane;
    public void LoadUpdate(){
        Abonnement abn=new Abonnement();
        System.out.println(abonnementListView.getSelectionModel().getSelectedItem().getIdAboonnement());
        abn=sa.findById(abonnementListView.getSelectionModel().getSelectedItem().getIdAboonnement());

        UpdateBtn.setVisible(true);
        NomText.setText(abn.getNom());
        PrenomText.setText( abn.getPrenom());
        TypeAbonnementBox.setValue(abn.getType());
        UpdatePane.setOpacity(0.85);
        UpdatePane.toFront();

    }
    public void onListViewClicked(MouseEvent event) {
       String i= abonnementListView.getSelectionModel().getSelectedItem().toString();
            if (abonnementListView.getSelectionModel().getSelectedItem() != null) {
                selectedItem_1 = abonnementListView.getSelectionModel().getSelectedItem();
                System.out.println(selectedItem_1.toString());
                if (!selectedItem_1.equals(selectedItem)) {
                    selectedItem = selectedItem_1;


                }
            }

    }


    ObservableList<Abonnement> data;
    public void afficher() {
        Set<Abonnement> dataList = sa.getAll();
          data = FXCollections.observableArrayList(dataList);
        abonnementListView.setItems(data);

        abonnementListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Abonnement> call(ListView<Abonnement> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Abonnement abs, boolean empty) {
                        super.updateItem(abs, empty);

                        if (empty || abs == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(abs.getNom() + " " + abs.getPrenom());
                            ImageView imageView = new ImageView("file:"+destinationString+abs.getImage());
                            imageView.setFitWidth(70);
                            imageView.setFitHeight(70);
                            setGraphic(imageView);
                        }
                    }
                };
            }
        });

        abonnementListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Abonnement item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getNom() + " " + item.getPrenom());
                    ImageView imageView = new ImageView("file:"+destinationString+item.getImage());
                    imageView.setFitWidth(70);
                    imageView.setFitHeight(70);
                    setGraphic(imageView);
                }
            }
        });
    }

    public void delete(){
sa.deleteItem(abonnementListView.getSelectionModel().getSelectedItem().getIdAboonnement());
afficher();

    }
    public void searchAbonnement(){
        if (SearchText.getText().isEmpty()) {
            abonnementListView.setItems(data);
        } else {
            ObservableList<Abonnement> filteredStations = FXCollections.observableArrayList();
            for (Abonnement abonnement : data) {
                if (abonnement.getPrenom().toLowerCase().contains(SearchText.getText().toLowerCase())) {
                    filteredStations.add(abonnement);
                }
            }
            abonnementListView.setItems(filteredStations);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TypeAbonnementBox.getItems().addAll("Annuel", "mensuel");
        afficher();

        int number[]=stat.statAbn();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Annual", number[0]));
        series.getData().add(new XYChart.Data<>("Mensuel", number[1]));
        series1.getData().add(series);
    }


    @FXML
    private BarChart<String, Number> series1;
}
