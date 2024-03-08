package pidev.javafx.controller.reclamation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pidev.javafx.model.reclamation.Reclamation;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.EventBus;

public class ReclamationBoxController {

    @FXML
    private Label date;
    @FXML
    private ImageView img;
    @FXML
    private Label subject;
    @FXML
    private Button Popup;


    @FXML
    private Button delete;


private Reclamation rec;
    public void setData(Reclamation reclamation){
        rec=reclamation;
        img.setImage( new Image( "file:src/main/resources"+reclamation.getImagePath(),70,70,true,true) );
        subject.setText( reclamation.getSubject() );
        date.setText( reclamation.getDate() );
    }

    public void showDetailsReclamation(MouseEvent event) {
        EventBus.getInstance().publish("showReclamation", event);
        System.out.println(rec);
        EventBus.getInstance().publish( "senddata", new CustomMouseEvent<Reclamation>(rec));
    }

    public Button getDelete() {
        return delete;
    }


    public Button getPopup() {
        return Popup;
    }
}
