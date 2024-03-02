package pidev.javafx.controller.reclamation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pidev.javafx.model.reclamation.Reclamation;

public class ReclamationBoxController {

    @FXML
    private Label date;
    @FXML
    private ImageView img;
    @FXML
    private Label subject;

    public void setData(Reclamation reclamation){
        img.setImage( new Image( "file:src/main/resources"+reclamation.getImagePath(),70,70,true,true) );
        subject.setText( reclamation.getSubject() );
        date.setText( reclamation.getDate() );
    }
}
