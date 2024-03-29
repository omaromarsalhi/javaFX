package pidev.javafx.controller.userMarketDashbord;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pidev.javafx.crud.marketplace.CrudBien;
import pidev.javafx.crud.marketplace.CrudFavorite;
import pidev.javafx.model.MarketPlace.Favorite;

import java.net.URL;
import java.util.ResourceBundle;

public class FavoriteController implements Initializable {

    @FXML
    private VBox bascInfoItems;
    @FXML
    private Label category;
    @FXML
    private Label nameLabel;
    @FXML
    private Label period;
    @FXML
    private Label price;
    @FXML
    private Label quantity;
    @FXML
    private ImageView deleteBtn;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setData(Favorite favorite,int i){
        nameLabel.setText( "FP "+i);
        String[] parts = favorite.getSpecifications().split("__");
        period.setText( parts[0]+"/"+parts[1] );
        price.setText( parts[2]+"/"+parts[3]+" Dt" );
        quantity.setText( parts[4] );
        category.setText( parts[5] );
    }

    public ImageView getDeleteBtn() {
        return deleteBtn;
    }
}
