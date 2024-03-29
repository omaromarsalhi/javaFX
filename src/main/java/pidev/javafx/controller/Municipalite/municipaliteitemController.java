package pidev.javafx.controller.Municipalite;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pidev.javafx.model.user.Municipalite;
import pidev.javafx.model.user.User;

public class municipaliteitemController {
    @FXML
    public HBox hbox;

    @FXML
    private ImageView image;

    @FXML
    private VBox vbox;

    @FXML
    private Label label1;

    @FXML
    private Label label2;
    public void setPos(int pos) {
        this.pos = pos;
    }
    private int pos;
    public void setData(Municipalite municipalite){
        label1.setText(municipalite.getName());
        label2.setText(municipalite.getAdresse());
        System.out.println(label1.getText());

    }
}
