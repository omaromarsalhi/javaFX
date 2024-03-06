//package pidev.javafx.controller.transport;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.chart.BarChart;
//import javafx.scene.chart.CategoryAxis;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart;
//import javafx.scene.control.*;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import org.json.JSONException;
//import org.json.JSONObject;
//import pidev.javafx.crud.transport.ServicesStation;
//import pidev.javafx.model.Transport.Station;
//import com.google.gson.Gson;
//
//import org.json.JSONObject;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.Statement;
//import java.util.*;
//
//   public class StationController implements Initializable {
//    @FXML
//   private TextArea Referance_text;
//    @FXML
//    private Button showBtn;
//    @FXML
//    private Button deleteBtn;
//    private Connection connect;
//    private Statement statement;
//    private PreparedStatement prepare;
//    @FXML
//    private ListView<Station> StationListView;
//    @FXML
//    private AnchorPane mainBorderPain;
//    private ServicesStation ss=new ServicesStation();
//    @FXML
//    protected void onTextChanged() {
//    String[] name = new String[10];
//    StationListView.setVisible(false);
//    name[1] = Referance_text.getText();
//    if (name[1].matches("[a-zA-Z]+"))
//        Referance_text.setStyle("-fx-control-inner-background: #25c12c;");
//    else
//        Referance_text.setStyle("-fx-control-inner-background: #bb2020;");}
////    @FXML
////    XYChart.Series<String, Number> series1 ;
//    @FXML
//    private BarChart<String, Number> series1;
//
//    public void stats() {
//        CategoryAxis xAxis = new CategoryAxis();
//        NumberAxis yAxis = new NumberAxis();
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//           if (series1.getData().isEmpty()||series1.getData().size()<4) {
//            // Add back the initial data when an item is selected
//            System.out.println(series1.getData().size());
//            series.getData().add(new XYChart.Data<>("Lundi", 20));
//            series.getData().add(new XYChart.Data<>("Mardi", 40));
//            series.getData().add(new XYChart.Data<>("Mercredi", 30));
//            series.getData().add(new XYChart.Data<>("Jeudi", 20));
//            series.getData().add(new XYChart.Data<>("Vendredi", 40));
//            series.getData().add(new XYChart.Data<>("Samedi", 30));
//            series.getData().add(new XYChart.Data<>("Dimanche", 20));
//            series1.getData().add(series);
//            addHoverEffect(series);
//            } else {
//            series1.getData().remove(0);
//
//        }
//    }
//
//    private void addHoverEffect(XYChart.Series<String, Number> series) {
//        for (XYChart.Data<String, Number> data : series.getData()) {
//            javafx.scene.Node bar = data.getNode();
//            if (bar != null) {
//                bar.setOnMouseEntered(event -> {
//                    bar.setScaleY(1.2);
//                    bar.setScaleX(1.2);
//                });
//                bar.setOnMouseExited(event -> {
//                    bar.setScaleY(1);
//                    bar.setScaleX(1);
//                });
//            }
//        }
//    }
//
//    private void stats_(){
//        StationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                // Check if the item is already selected
//                boolean isSelected = StationListView.getSelectionModel().isSelected(StationListView.getSelectionModel().getSelectedIndex());
//
//                if (isSelected) {
//                    // Item is selected, clear selection and update stats label
//                    StationListView.getSelectionModel().clearSelection();
//                    System.out.println("Stats will be displayed here.");
//                } else {
//                    // Item is not selected, update stats label with the selected column
//                    System.out.println("Stats for " + newValue);
//                }
//            }
//        });
//    }
//       Station selectedItem =new Station();
//       Station selectedItem_1 =new Station();
//    public void onListViewClicked(MouseEvent event) {
//        if (event.getClickCount() == 1) {
//            if (StationListView.getSelectionModel().getSelectedItem() != null ) {
//                selectedItem_1 = StationListView.getSelectionModel().getSelectedItem();
//             //   System.out.println(selectedItem.equals(StationListView.getSelectionModel().getSelectedItem()));
//                if(!selectedItem_1.equals(selectedItem)) {
//                    selectedItem=selectedItem_1;
//                    stats();
//                }
//            }
//        }
//    }
//
//
//    Map<String,Integer> stationMap = new HashMap<>();
//    public void afficher(){
//    Set<Station> dataList;
//    dataList=ss.getAll();
//
//    ObservableList<Station> data = FXCollections.observableArrayList(dataList);
//
//    StationListView.getItems().addAll(dataList);
//
//    StationListView.setCellFactory(param -> new ListCell<Station>() {
//        @Override
//        protected void updateItem(Station item, boolean empty) {
//            super.updateItem(item, empty);
//
//            if (empty || item == null) {
//                setText(null);
//            } else {
//                setText(item.getNomStation());
//                stationMap.put(item.getNomStation(),item.getIdStation());
//
//            }
//        }
//    });
//
//}
//
//    public void onInsertStationClicked(ActionEvent event)  throws IOException {
//        ScrollPane scrollPane = FXMLLoader.load(Objects.requireNonNull( getClass().getResource("/fxml/Transport/Gui_Station/AddStation.fxml")));
//        scrollPane.setPrefHeight(mainBorderPain.getPrefHeight()  );
//        scrollPane.setPrefWidth( mainBorderPain.getPrefWidth() );
//        mainBorderPain.getChildren().setAll(scrollPane);
//    };
//       private static final String API_URL = "https://api.open-meteo.com/v1/forecast?latitude=24.656426&longitude=45.739876&current=temperature_2m,wind_speed_10m,relative_humidity_2m";
//
//
//       private String fetchWeatherData(String apiUrl) throws IOException {
//           URL url = new URL(apiUrl);
//           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//           connection.setRequestMethod("GET");
//
//           int responseCode = connection.getResponseCode();
//           if (responseCode == HttpURLConnection.HTTP_OK) {
//               BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//               StringBuilder response = new StringBuilder();
//               String line;
//
//               while ((line = reader.readLine()) != null) {
//                   System.out.println(response.toString());
//                   response.append(line);
//               }
//
//               reader.close();
//               return response.toString();
//           } else {
//               throw new IOException("Failed to fetch data. Response code: " + responseCode);
//           }
//       }
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//        afficher();
//
//        Label temperatureLabel = new Label();
//
//
//        try {
//            String jsonResponse = fetchWeatherData(API_URL);
//            System.out.println(jsonResponse);
//
//
//            // Parse JSON string
//            JSONObject jsonObject = new JSONObject(jsonResponse);
//
//            // Retrieve required data
//            double temperature2m = jsonObject.getJSONObject("current").getDouble("temperature_2m");
//            double windSpeed10m = jsonObject.getJSONObject("current").getDouble("wind_speed_10m");
//            int relativeHumidity2m = jsonObject.getJSONObject("current").getInt("relative_humidity_2m");
//
//            // Print the retrieved data
//            System.out.println("Temperature at 2m: " + temperature2m + " °C");
//            System.out.println("Wind Speed at 10m: " + windSpeed10m + " km/h");
//            System.out.println("Relative Humidity at 2m: " + relativeHumidity2m + " %");
//
//            // Parse the JSON response
////            Gson gson = new Gson();
////            WeatherResponse weatherResponse = gson.fromJson(jsonResponse, WeatherResponse.class);
////
////            // Display current weather information
////            temperatureLabel.setText("Temperature: " + weatherResponse.getCurrent().getTemperature2m() + "°C");
////            System.out.println(jsonResponse);
//        } catch (IOException e) {
//            temperatureLabel.setText("Error fetching weather data.");
//            e.printStackTrace();
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//        //stats();
//    }
//
//
//}
//
//


package pidev.javafx.controller.station;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pidev.javafx.crud.transport.ServicesStation;
import pidev.javafx.model.Transport.Station;
import pidev.javafx.model.Transport.Type_Vehicule;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

public class StationController implements Initializable {
    @FXML
    private TextArea Referance_text;

    @FXML
    private Button showBtn;
    @FXML
    private Button Updatebtn;

    @FXML
    private Button deleteBtn;

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private Stage primaryStage;

    @FXML
    private ListView<Station> StationListView;

    @FXML
    private AnchorPane mainBorderPain;

    private ServicesStation ss = new ServicesStation();
    @FXML
    private Pane UpdatePane;
    @FXML
    private TextField SearchText;



    @FXML
    protected void onTextChanged() {
        String[] name = new String[10];
        StationListView.setVisible(false);
        name[1] = NomStationText.getText();
        if (name[1].matches("[a-zA-Z]+"))
            NomStationText.setStyle("-fx-control-inner-background: #25c12c;");
        else
            NomStationText.setStyle("-fx-control-inner-background: #bb2020;");
    }
    @FXML
    private BarChart<String, Number> series1;
    public void stats() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        if (series1.getData().isEmpty() || series1.getData().size() < 4) {
            System.out.println(series1.getData().size());
            series.getData().add(new XYChart.Data<>("Lundi", 20));
            series.getData().add(new XYChart.Data<>("Mardi", 40));
            series.getData().add(new XYChart.Data<>("Mercredi", 30));
            series.getData().add(new XYChart.Data<>("Jeudi", 20));
            series.getData().add(new XYChart.Data<>("Vendredi", 40));
            series.getData().add(new XYChart.Data<>("Samedi", 30));
            series.getData().add(new XYChart.Data<>("Dimanche", 20));
            series1.getData().add(series);
            addHoverEffect(series);
        } else {
            series1.getData().remove(0);
        }
    }



    @FXML
    public void unexpand() {

        BoxBlur blur = new BoxBlur();
        blur.setWidth(10);
        blur.setHeight(10);
        blur.setIterations(3);
        displayTransport.toFront();
        displayTransport.setEffect(null);
        UpdatePane.toBack();
        displayTransport.setOpacity(1);

    }



    private void addHoverEffect(XYChart.Series<String, Number> series) {
        for (XYChart.Data<String, Number> data : series.getData()) {
            javafx.scene.Node bar = data.getNode();
            if (bar != null) {
                bar.setOnMouseEntered(event -> {
                    bar.setScaleY(1.2);
                    bar.setScaleX(1.2);
                });
                bar.setOnMouseExited(event -> {
                    bar.setScaleY(1);
                    bar.setScaleX(1);
                });
            }
        }
    }
    String[] name = new String[10];

    private void stats_() {
        StationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                boolean isSelected = StationListView.getSelectionModel().isSelected(StationListView.getSelectionModel().getSelectedIndex());

                if (isSelected) {
                    StationListView.getSelectionModel().clearSelection();
                    System.out.println("Stats will be displayed here.");
                } else {
                    System.out.println("Stats for " + newValue);
                }
            }
        });
    }


    public void filterStations(String searchText) {
        if (searchText.isEmpty()) {
            StationListView.setItems(data);
        } else {
            ObservableList<Station> filteredStations = FXCollections.observableArrayList();
            for (Station station : data) {
                if (station.getNomStation().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredStations.add(station);
                }
            }
            StationListView.setItems(filteredStations);
        }
    }
    public void delete_Btn(){
        ss.deleteItem(StationListView.getSelectionModel().getSelectedItem().getIdStation());
        afficher();
    }
@FXML
    private Pane displayTransport;

    @FXML
    private TextField AdressText;
    @FXML
    private  TextField NomStationText;
    @FXML
    private ComboBox BoxTypeVehicule;
    @FXML
    private ImageView Image;
    String image_path;
    public void load_update(){

        BoxBlur blur = new BoxBlur();
        blur.setWidth(10);
        blur.setHeight(10);
        blur.setIterations(3);
        displayTransport.setEffect(blur);
        UpdatePane.setVisible(true);
        UpdatePane.toFront();
        UpdatePane.setOpacity(0.85);
        NomStationText.setText(selectedItem.getNomStation());
        AdressText.setText(selectedItem.getAddressStation());
        BoxTypeVehicule.setValue(selectedItem.getType_Vehicule());
        image_path=selectedItem.getImage_station();
      if(image_path!=null){
        Image.setImage(new Image(image_path));
    }
    }
    public void insert_Image(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        var selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            image_path=selectedFile.getAbsolutePath() ;

            Image image = new Image(image_path);
            Image.setFitHeight(114);
            Image.setFitWidth(114);
            Image.setImage(image);
        }
    }
    @FXML
    protected  void Load_types() {

        String a;
        if(BoxTypeVehicule.getValue()==null)
            BoxTypeVehicule.getItems().addAll(Type_Vehicule.values());
    }
    public void update_action(){
        Station updated_s=new Station(NomStationText.getText(),image_path,AdressText.getText(),BoxTypeVehicule.getValue().toString(),selectedItem.getIdStation());
        System.out.println(updated_s);
        ss.updateItem(updated_s);
        afficher();
        unexpand();
    }
    Station selectedItem = new Station();
    Station selectedItem_1 = new Station();

    public void onListViewClicked(MouseEvent event) {
        if (event.getClickCount() == 1) {
            if (StationListView.getSelectionModel().getSelectedItem() != null) {
                selectedItem_1 = StationListView.getSelectionModel().getSelectedItem();
                if (!selectedItem_1.equals(selectedItem)) {
                    selectedItem = selectedItem_1;
                    System.out.println(selectedItem);
                    stats();
                }
            }
        }
    }
    public void searchStation(){
        if (SearchText.getText().isEmpty()) {
            StationListView.setItems(data);
        } else {
            ObservableList<Station> filteredStations = FXCollections.observableArrayList();
            for (Station station : data) {
                if (station.getNomStation().toLowerCase().contains(SearchText.getText().toLowerCase())) {
                    filteredStations.add(station);
                }
            }
            StationListView.setItems(filteredStations);
        }
    }


    ObservableList<Station> data ;

    Map<String, Integer> stationMap = new HashMap<>();
    public void afficher() {
        Set<Station> dataList;
        dataList = ss.getAll();
        dataList.clear();
        dataList = ss.getAll();
          data = FXCollections.observableArrayList(dataList);
        StationListView.setItems(data);
        StationListView.setCellFactory(param -> new ListCell<Station>() {
            @Override
            protected void updateItem(Station item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNomStation());
                    stationMap.put(item.getNomStation(), item.getIdStation());
                }
            }
        });
    }

    public void onInsertStationClicked(ActionEvent event) throws IOException {
        ScrollPane scrollPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Transport/Gui_Station/AddStation.fxml")));
        scrollPane.setPrefHeight(mainBorderPain.getPrefHeight());
        scrollPane.setPrefWidth(mainBorderPain.getPrefWidth());
        mainBorderPain.getChildren().add(scrollPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficher();
        Load_types();
    }
}


