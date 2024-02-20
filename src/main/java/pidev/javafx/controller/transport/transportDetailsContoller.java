package pidev.javafx.controller.transport;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pidev.javafx.model.Transport.Transport;

import java.net.URL;
import java.util.ResourceBundle;

public class transportDetailsContoller implements Initializable {

    @FXML
    private Label Arrive_Status;

    @FXML
    private Label InfoLabel2;

    @FXML
    private Label InfoLabel21;

    @FXML
    private Label InfoLabel3;

    @FXML
    private Label InfoLabel32;

    @FXML
    private Label InfoLabel321;

    @FXML
    private Label InfoLabel33;

    @FXML
    private Label ArriveLabel;

    @FXML
    private Label HeureDepart;
    @FXML
    private Label DepartLabel;
    @FXML
    private Label ArriveLabel2;

    @FXML
    private Label InfoLabel331;

    @FXML
    private Label InfoLabel3311;

    @FXML
    private Label InfoLabel332;

    @FXML
    private Label InfoLabel34;

    @FXML
    private VBox TransportBox;

    @FXML
    private Pane detailsTransport;

    @FXML
    private ToggleButton dropToggle;

    public Transport getData() {
        return data;
    }

    public void setData(Transport data) {
        this.data = data;
    }

    private Transport data;
    int id;
    Transport t;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailsTransport.setVisible(false);
        dropToggle.setSelected(true);
     //   fillDetails();
       // System.out.println(1);
       // System.out.println(data.toString());
        //System.out.println(1);
    }

    @FXML
    void onDropdownClick(ActionEvent event) {
        if (dropToggle.isSelected()) {
            detailsTransport.setMinSize(0, 0); // Set minimum size to zero
            detailsTransport.setMaxSize(0, 0);
            detailsTransport.setPrefSize(0,0);
            detailsTransport.setVisible(false);
        }
        else if (!dropToggle.isSelected()) {
            detailsTransport.setVisible(true);
        }
    }

    public void fillDetails(){
         ArriveLabel.setText(data.getArivee());
         HeureDepart.setText(data.getDepart());
         DepartLabel.setText(data.getHeure().toString());
        ArriveLabel2.setText(data.getArivee());
}

}
