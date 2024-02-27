package pidev.javafx.controller.station;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.json.JSONException;
import org.json.JSONObject;
import pidev.javafx.controller.transport.transportDetailsContoller;
import pidev.javafx.crud.transport.ServicesTransport;
import pidev.javafx.model.Transport.Transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


public class MapStationController implements Initializable {
    @FXML
    private AnchorPane ListeClient;
    @FXML
    private ToggleButton toggleButton1;
    @FXML
    private ToggleButton toggleButton2;
    @FXML
    private ToggleButton toggleButton3;
    @FXML
    private ToggleButton toggleButton4;
    @FXML
    private VBox TransportBox;
    @FXML
    private Label aaa;
    @FXML
    private Label label;
    @FXML
    private Label Arrive_Status;
    @FXML
    private Pane detailsTransport;
    @FXML
    private ToggleButton dropToggle;
    ServicesTransport st=new ServicesTransport();
    @FXML
    private VBox showItems;
    VBox vBox;
    @FXML
    private  VBox mainVbox;
    List<Transport> Transports;


@FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




}



    public void Onclick(){
        load("/fxml/Transport/Gui_Station/ListeTransport.fxml");

    }
    private void load(String fxmlPath) {
        try {
            // Load the content  the specified FXML file
            Node content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            ListeClient.getChildren().setAll(content);

            // Set the content of the ScrollPane to the loaded FXML content

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String[] texts = {"Avaible ", "8:00PM"};
    private int currentIndex = 0;





//  private  void retrieve(){
//        Set <Transport> T=new HashSet<>();
//        T=st.getAll();
//      for (Transport paneModel : T) {
//
//      }
//  }
    public void showDetailsPane(Set<Transport> transportSet) throws IOException {
 Set<Transport> T = new HashSet<>();
        T = st.getAll();

        for (Transport transport : transportSet) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Transport/Gui_Station/transportDetails.fxml"));
                AnchorPane anchorPane = loader.load();

                transportDetailsContoller itemController = loader.getController();
                itemController.setData(transport);

                mainVbox.getChildren().add(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Transport/Gui_Station/transportDetails.fxml"));
            AnchorPane anchorPane = loader.load();
            transportDetailsContoller itemController = fxmlLoader.getController();
            itemController.setData(transport);
            mainVbox.getChildren().add(anchorPane);
        }
    }

}





