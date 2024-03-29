package pidev.javafx.controller.transport;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.crud.transport.ServicesTransport;
import pidev.javafx.model.Transport.Station;
import pidev.javafx.model.Transport.Transport;
import pidev.javafx.model.Transport.Type_Vehicule;

import javax.swing.*;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static javafx.stage.StageStyle.UNDECORATED;

public class TranportController implements Initializable {

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
    @FXML
    private TextField ReferenceText;
    @FXML
    private TextField PrixText;
    @FXML
    private ComboBox<Station> Depart;
    @FXML
    private ComboBox<Station> Arrive;
    @FXML
    private ComboBox BoxTypeVehicule;

    @FXML
    private Button Add_photoBtn;
    @FXML
    private Button AnnulerBtn;
    @FXML
    private Button AddBtn;
    @FXML
    private Button Ajouter_imageBtn;
    String imagePath;
    private Stage primaryStage;

    ActionEvent event;
    @FXML
    private Spinner<Integer> timeSpinner;
    @FXML
    private ImageView Image;
    ServicesTransport sp = new ServicesTransport();
    Dialog<String> dialog = new Dialog<>();
    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();


    Set<String> resultSetItems = new HashSet<>();
    private Transport data = new Transport();
    private URL url;
    private ResourceBundle resourceBundle;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Transport/Buttons/Delete.fxml"));
    Button btn = new Button();

    Transport t;
    int selectedRow = 0;
    ServicesTransport st = new ServicesTransport();

    @FXML
    private Pane insertPane;
    @FXML
    private Pane updatePane;
    final String destinationString = "src/main/resources/transportImg";


    @FXML
    private ComboBox<Station> Arrive1;

    @FXML
    private ComboBox BoxTypeVehicule1;

    @FXML
    private ComboBox<Station> Depart1;

    @FXML
    private ImageView Image1;


    @FXML
    private TextField PrixText1;

    @FXML
    private TextField ReferenceText1;

    @FXML
    private Spinner<Integer> timeSpinner1;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficher();
        intialiase_timer();


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
                        ImageView deleteIcon = new ImageView(new Image("file:src/main/resources/icons/userIcons/supprimer (1).png"));
                        ImageView updateIcon = new ImageView(new Image("file:src/main/resources/icons/userIcons/modifier.png"));

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
                            data = getTableView().getItems().get(getIndex());
                            selectedRow = data.getIdTransport();
                            load_update(data);

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

    public void updateTransport() {
        String Type = BoxTypeVehicule1.getValue().toString();
        Station DEPART = Depart1.getValue();
        Station ARRIVEE = Arrive1.getValue();
        Time Temp;
        int totalMinutes = timeSpinner1.getValue();
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        LocalTime localTime = LocalTime.of(hours, minutes);
        Time time = Time.valueOf(localTime);
        if (ReferenceText1.getText().matches("1") || PrixText1.getText() == null) {


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You should match the fields to insert");
            alert.setContentText("Vous voules vraiment effacer ce fichier ?");
            alert.showAndWait();

        } else {
            String Reference = ReferenceText1.getText();
            Float Prix = Float.parseFloat(PrixText1.getText());
            Transport T = new Transport(Type, DEPART, ARRIVEE, Reference, imagePath, Prix, time);
            System.out.println(data.getIdTransport());
            T.setIdTransport(data.getIdTransport());


            st.updateItem(T);
            afficher();
            unexpand();


        }
    }

    public void Supprimer(int id) {
        st.deleteItem(id);

    }

    @FXML
    public void afficher() {


        Set<Transport> dataList = new HashSet<>();

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
                    Image image = new Image("file:src/main/resources" + imagePath);
                    imageView.setImage(image);
                    imageView.setStyle("-fx-background-radius: 50%;  ");
                    imageView.getStyleClass().add("rounded-image");
                    setGraphic(imageView);

                }
            }
        });


        dataList = st.getAll();
        ObservableList<Transport> observableList = FXCollections.observableArrayList(dataList);
        Transport_table.setItems(observableList);
        Transport_table.refresh();


    }


    @FXML
    public void recherche() {
        String test = SearchText.getText();
        String[] parts = test.split("_");
        Set<Transport> filteredSet=new HashSet<>();

        Set<Transport> dataList = new HashSet<>();
        dataList = st.getAll();
        if (parts[0].equals("Reference")) {
             filteredSet = dataList.stream()
                    .filter(transport -> transport.getReference().toLowerCase().contains(parts[1].toLowerCase()))
                    .collect(Collectors.toSet());
        } else if (parts[0].equals("Depart")) {
          filteredSet = dataList.stream()
                    .filter(transport -> transport.getDepart().toLowerCase().contains(parts[1].toLowerCase()))
                    .collect(Collectors.toSet());
        }


        else if (parts[0].equals("Arrive")) {
             filteredSet = dataList.stream()
                    .filter(transport -> transport.getArivee().toLowerCase().contains(parts[1].toLowerCase()))
                    .collect(Collectors.toSet());

        }
        else if (parts[0].equals("Type")) {
            filteredSet = dataList.stream()
                    .filter(transport -> transport.getType_vehicule().toLowerCase().contains(parts[1].toLowerCase()))
                    .collect(Collectors.toSet());
        }
        else if (parts[0].equals("Prix")) {
            filteredSet = dataList.stream()
                    .filter(transport -> transport.getPrix().toString().toLowerCase().contains(parts[1].toLowerCase()))
                    .collect(Collectors.toSet());
        }
        else if (parts[0].equals("Heure")) {
            filteredSet = dataList.stream()
                    .filter(transport -> transport.getHeure().toString().toLowerCase().contains(parts[1].toLowerCase()))
                    .collect(Collectors.toSet());
        }
        else {
            filteredSet=dataList;

        }



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
                    Image image = new Image("file:src/main/resources" + imagePath);
                    imageView.setImage(image);
                    imageView.setStyle("-fx-background-radius: 50%;  ");
                    imageView.getStyleClass().add("rounded-image");
                    setGraphic(imageView);

                }
            }
        });


        ObservableList<Transport> observableList = FXCollections.observableArrayList(filteredSet);
        Transport_table.setItems(observableList);
        Transport_table.refresh();


    }


    public void intialiase_timer() {
        SpinnerValueFactory<Integer> timeFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(7 * 60, 19 * 60, 0, 30);
        timeSpinner.setValueFactory(timeFactory);
        timeSpinner1.setValueFactory(timeFactory);

        timeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            int hours = newValue / 60;
            int minutes = newValue % 60;
            timeSpinner.getEditor().setText(String.format("%02d:%02d", hours, minutes));
        });
        timeSpinner1.valueProperty().addListener((obs, oldValue, newValue) -> {
            int hours = newValue / 60;
            int minutes = newValue % 60;
            timeSpinner1.getEditor().setText(String.format("%02d:%02d", hours, minutes));
        });

        timeSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
        timeSpinner1.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);

    }

    Connection cnx = ConnectionDB.getInstance().getCnx();


    public Set<Station> Load_Locations() {

        String sql = "SELECT * FROM station";

        Set<Station> s = new HashSet<>();

        try (
                PreparedStatement preparedStatement = cnx.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {

                String nomStation = resultSet.getString("NomStation");
                int id = resultSet.getInt("idStation");
                Station sn = new Station();
                sn.setIdStation(id);
                sn.setNomStation(nomStation);
                s.add(sn);


            }
            return s;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


    }

    @FXML
    protected void Load_Depart() {
        if (Depart.getValue() == null)
            Depart.getItems().addAll(Load_Locations());

        Depart.setCellFactory(param -> new ListCell<Station>() {
            @Override
            protected void updateItem(Station item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNomStation());
                }
            }
        });
    }

    @FXML
    protected void Load_Arrivee() {
        if (Arrive.getValue() == null)
            Arrive.getItems().addAll(Load_Locations());

        Arrive.setCellFactory(param -> new ListCell<Station>() {
            @Override
            protected void updateItem(Station item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNomStation());
                }
            }
        });
    }

    @FXML
    protected void Load_types() {
        if (BoxTypeVehicule.getValue() == null)
            BoxTypeVehicule.getItems().addAll(Type_Vehicule.values());
    }


    @FXML
    protected void Load_Depart_1() {
        if (Depart1.getValue() == null)
            Depart1.getItems().addAll(Load_Locations());

        Depart1.setCellFactory(param -> new ListCell<Station>() {
            @Override
            protected void updateItem(Station item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNomStation());
                }
            }
        });
    }

    @FXML
    protected void Load_Arrivee_1() {
        if (Arrive1.getValue() == null)
            Arrive1.getItems().addAll(Load_Locations());

        Arrive1.setCellFactory(param -> new ListCell<Station>() {
            @Override
            protected void updateItem(Station item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNomStation());
                }
            }
        });
    }

    @FXML
    protected void Load_types_1() {
        if (BoxTypeVehicule1.getValue() == null)
            BoxTypeVehicule1.getItems().addAll(Type_Vehicule.values());
    }


    @FXML
    protected void onTextChanged() {
        String[] text = new String[10];

        text[1] = ReferenceText.getText();
        text[2] = PrixText.getText();

        if (text[1].matches("[a-zA-Z0-9]*"))
            ReferenceText.setStyle("-fx-text-fill: #25c12c;");
        else
            ReferenceText.setStyle("-fx-text-fill: #bb2020;");

        if (text[2].matches("^\\d+(\\.\\d*)?$")) {
            PrixText.setStyle("-fx-text-fill: #25c12c");
        } else
            PrixText.setStyle("-fx-text-fill: #bb2020 ");
    }


    public void insert_Image() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png")
        );
        var selectedFile = fileChooser.showOpenDialog(primaryStage);

        imagePath = selectedFile.getAbsolutePath();
    }

    public void load_insert() {

        BoxBlur blur = new BoxBlur();
        blur.setWidth(10);
        blur.setHeight(10);
        blur.setIterations(3);
        displayTransport.setEffect(blur);
        insertPane.setOpacity(0.85);
        displayTransport.setOpacity(0.85);
        insertPane.toFront();
        updatePane.setVisible(false);
        displayTransport.toBack();
        insertPane.toFront();
        insertPane.setVisible(true);
        insertPane.setOpacity(0.85);
        Load_Arrivee();
        Load_Depart();
        Load_types();

    }

    public void load_update(Transport loadedTransport) {
        BoxBlur blur = new BoxBlur();
        blur.setWidth(10);
        blur.setHeight(10);
        blur.setIterations(3);
        displayTransport.setEffect(blur);
        updatePane.setOpacity(0.85);
        displayTransport.setOpacity(0.85);
        updatePane.toFront();
        updatePane.setVisible(true);
        insertPane.setVisible(false);
        Load_Arrivee_1();
        Load_Depart_1();
        Load_types_1();


        PrixText1.setText(loadedTransport.getPrix().toString());
        ReferenceText1.setText(loadedTransport.getReference());
        imagePath = loadedTransport.getVehicule_Image();
        BoxTypeVehicule1.setValue(loadedTransport.getType_vehicule());
        javafx.scene.image.Image image = new Image("file:src/main/resources" + loadedTransport.getVehicule_Image());
        Image1.setFitHeight(114);
        Image1.setFitWidth(114);
        Image1.setImage(image);
        displayTransport.toBack();
        updatePane.toFront();
        updatePane.setOpacity(0.7);

    }

    public void unexpand() {


        displayTransport.setEffect(null);
        updatePane.toBack();
        displayTransport.toFront();
        insertPane.toBack();
        insertPane.setVisible(false);
        updatePane.setVisible(false);

    }

    @FXML
    protected boolean insertTransport() throws IOException {
        String randomFileName = null;
        Path sourcePath = Paths.get(imagePath);
        if (imagePath.endsWith(".png")) {
            randomFileName = UUID.randomUUID().toString() + ".png";
        } else {
            randomFileName = UUID.randomUUID().toString() + ".jpg";
        }
        Path destinationPath = Paths.get(destinationString, randomFileName);
        System.out.println(sourcePath);
        System.out.println(destinationPath);
        try {
            Files.copy(sourcePath, destinationPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imagePath = "/transportImg" + "/" + randomFileName;

        if (ReferenceText.getText().matches("1") || PrixText.getText() == null || imagePath == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Transport/exceptions/Dialog.fxml"));
            Parent dialogContent = fxmlLoader.load();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Condition not met.\"");
            alert.getDialogPane().setContent(dialogContent);

            alert.showAndWait();
            return false;
        } else {
            String Type = BoxTypeVehicule.getValue().toString();
            Station DEPART = Depart.getValue();
            Station ARRIVEE = Arrive.getValue();
            Time Temp;
            int totalMinutes = timeSpinner.getValue();
            int hours = totalMinutes / 60;
            int minutes = totalMinutes % 60;
            LocalTime localTime = LocalTime.of(hours, minutes);

            Time time = Time.valueOf(localTime);

            String Reference = ReferenceText.getText();
            Float Prix = Float.parseFloat(PrixText.getText());
            Transport T = new Transport(Type, DEPART, ARRIVEE, Reference, imagePath, Prix, time);

            if (true == sp.addItem(T)) {

                unexpand();
                afficher();
            }

            return true;
        }
    }


}






