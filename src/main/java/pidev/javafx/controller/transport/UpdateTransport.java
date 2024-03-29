package pidev.javafx.controller.transport;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pidev.javafx.crud.ConnectionDB;

import pidev.javafx.crud.transport.ServicesTransport;
import pidev.javafx.model.Transport.Station;
import pidev.javafx.model.Transport.Transport;
import pidev.javafx.model.Transport.Type_Vehicule;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

public class UpdateTransport implements Initializable {
    static  private   Transport loadedTransport;



    @FXML
    private ComboBox Arrive;

    @FXML
    private ComboBox BoxTypeVehicule;

    @FXML
    private ComboBox Depart;

    @FXML
    private ImageView Image;
    private Stage primaryStage;



    @FXML
    private TextField PrixText;

    @FXML
    private TextField ReferenceText;

    @FXML
    private Spinner<Integer> timeSpinner;
    private Connection connect;
    private Set<String> resultSetItems;
    @FXML
    private ScrollPane mainBorderPain;
    String imagePath=loadedTransport.getVehicule_Image();
    private PreparedStatement prepare;
    ServicesTransport st=new ServicesTransport();
    Connection cnx = ConnectionDB.getInstance().getCnx();

    public static void setData(Transport transportData) {
        loadedTransport = transportData;
    }
    Pane Pane;

    {
        try {
            Pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Transport/added_succesfully.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Load_Arrivee();
        Load_Depart();
        Load_types();
        Pane.setPrefHeight(mainBorderPain.getPrefHeight());
        Pane.setPrefWidth(mainBorderPain.getPrefWidth());

        PrixText.setText(loadedTransport.getPrix().toString());
        ReferenceText.setText(loadedTransport.getReference());
        Depart.setValue(loadedTransport.getDepart());
        Arrive.setValue(loadedTransport.getArivee());
        BoxTypeVehicule.setValue(loadedTransport.getType_vehicule());
        javafx.scene.image.Image image = new Image(loadedTransport.getVehicule_Image());
        Image.setFitHeight(114);
        Image.setFitWidth(114);
        Image.setImage(image);
        intialiase_timer();

    }


    public  void intialiase_timer(){
        SpinnerValueFactory<Integer> timeFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(7 * 60 , 19 * 60 , 0,30);
        timeSpinner.setValueFactory(timeFactory);

        timeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            int hours = newValue / 60;
            int minutes = newValue % 60;
            timeSpinner.getEditor().setText(String.format("%02d:%02d", hours, minutes));
        });

        timeSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
    }

    @FXML
    protected void onTextChanged() {
        String[] text = new String[10];

        text[1] = ReferenceText.getText();
        text[2]= PrixText.getText();

        if (text[1].matches("[a-zA-Z0-9]*"))
            ReferenceText.setStyle("-fx-text-fill: #25c12c;");
        else
            ReferenceText.setStyle("-fx-text-fill: #bb2020;");

        if (text[2].length() > 2 && text[2].matches("^\\d+(\\.\\d+)?$"))
            PrixText.setStyle("-fx-text-fill: #25c12c");
        else
            PrixText.setStyle("-fx-text-fill: #bb2020 ");
    }


    public Set<Station> Load_Locations(){

        String sql = "SELECT * FROM station";

        Set<Station> s=new HashSet<>();

        try (
                PreparedStatement preparedStatement = cnx.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {

                String nomStation=resultSet.getString("NomStation");
                int id=resultSet.getInt("idStation");
                Station sn=new Station();
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
    protected  void     Load_Depart(){
        if(Depart.getValue()!=null)
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
    protected  void Load_Arrivee(){
        if(Arrive.getValue()!=null)
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
    protected  void Load_types() {
        if(BoxTypeVehicule.getValue()==null)
            BoxTypeVehicule.getItems().addAll(Type_Vehicule.values());
    }




    public void insert_Image(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        var selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            imagePath=selectedFile.getAbsolutePath() ;
            Image image = new Image(imagePath);
            Image.setFitHeight(114);
            Image.setFitWidth(114);
            Image.setImage(image);

        }

    }


    public void Return(ActionEvent event) throws IOException {
        ScrollPane scrollPane = FXMLLoader.load(Objects.requireNonNull( getClass().getResource("/fxml/Transport/Gui_Transport/Display_Transport.fxml")));
        scrollPane.setPrefHeight(mainBorderPain.getPrefHeight()  );
        scrollPane.setPrefWidth( mainBorderPain.getPrefWidth() );
        mainBorderPain.setContent(scrollPane);
    }

    public boolean update() throws IOException{
        String Type = BoxTypeVehicule.getValue().toString();
        String DEPART = Depart.getValue().toString();
        String ARRIVEE = Arrive.getValue().toString();
        Time Temp;
        int totalMinutes = timeSpinner.getValue();
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        LocalTime localTime = LocalTime.of(hours, minutes);
        Time time = Time.valueOf(localTime);
        if (ReferenceText.getText().matches("1") ||PrixText.getText() ==null  ) {


            System.out.println("Condition not met.");
            return false;
        } else {
            String Reference = ReferenceText.getText();
            Float Prix = Float.parseFloat(PrixText.getText());
            Transport T = new Transport(    Type,  DEPART, ARRIVEE, Reference, imagePath, Prix,time);
            T.setIdTransport(loadedTransport.getIdTransport());


               st.updateItem(T);

                Long StartTime =  System.currentTimeMillis();
                annimation();
                 while(StartTime-12000>System.currentTimeMillis()){
                     System.out.println("i");

                 }

                    ScrollPane scrollPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Transport/Gui_Transport/Display_Transport.fxml")));

                    scrollPane.setPrefHeight(mainBorderPain.getPrefHeight());
                    scrollPane.setPrefWidth(mainBorderPain.getPrefWidth());
                    mainBorderPain.setContent(scrollPane);


            return true;
        }

    }
    void annimation() throws IOException {

        mainBorderPain.setContent(Pane);
    };
}
