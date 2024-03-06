package pidev.javafx.controller.transport;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pidev.javafx.model.Transport.Transport;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.EventBus;


import java.net.URL;
import java.util.ResourceBundle;

public class transportDetailsContoller implements Initializable {


    @FXML
    private ImageView imageLabel;

    @FXML
    private Label ArriveLabel;

    @FXML
    private Label HeureDepart;
    @FXML
    private Label DepartLabel;
    @FXML
    private Label ArriveLabel2;
    @FXML
    private Label referenceLabel;

    @FXML
    private Label stationLabel;
    @FXML
    private Label prixLabel;

    @FXML
    private Label typeLabel;



    @FXML
    private Pane detailsTransport;

    public ToggleButton getDropToggle() {
        return dropToggle;
    }

    public void setDropToggle(ToggleButton dropToggle) {
        this.dropToggle = dropToggle;
    }

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
        if(data!=null)
            fillDetails();
    }


    @FXML
    private void sendData(ActionEvent event)
    {
        EventBus.getInstance().publish("sendTransport", new CustomMouseEvent<Transport>(data));
    }

    @FXML
    void onDropdownClick(ActionEvent event)
    {
        if (dropToggle.isSelected())
          {
            detailsTransport.setMinSize(0, 0);
            detailsTransport.setMaxSize(0, 0);
            detailsTransport.setPrefSize(0,0);
            detailsTransport.setVisible(false);
          }
        else if (!dropToggle.isSelected())
          {
            detailsTransport.setVisible(true);
          }
    }

    public void fillDetails(){
        Image image = new Image("file:///"+data.getVehicule_Image());
        imageLabel.setImage(image);
        imageLabel.setFitWidth(94);
        imageLabel.setFitHeight(63);
        ArriveLabel.setText(data.getArivee());
        HeureDepart.setText(data.getHeure().toString().substring(0,5));
        DepartLabel.setText(data.getDepart());
        ArriveLabel2.setText(data.getArivee());
        prixLabel.setText(data.getPrix().toString());
        typeLabel.setText(data.getType_vehicule());
        referenceLabel.setText(data.getReference());
        stationLabel.setText("2AAAA");


    }

}
