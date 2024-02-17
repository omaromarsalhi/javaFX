package pidev.javafx.controller.userMarketDashbord;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pidev.javafx.model.MarketPlace.Favorite;

import java.net.URL;
import java.util.ResourceBundle;

public class FavoriteController implements Initializable {

    @FXML
    private AnchorPane anchorPaneItem;
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
    private VBox vboxItem;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        String string = "004-034556";
//        String[] parts = string.split("-");
//        String part1 = parts[0]; // 004
//        String part2 = parts[1]; // 034556
    }

    public void setData(Favorite favorite,int i){
        nameLabel.setText( "FP "+i);
        String[] parts = favorite.getSpecifications().split("_");
        period.setText( parts[0]+"/"+parts[1] );
        price.setText( parts[2]+"/"+parts[3]+" Dt" );
        quantity.setText( parts[4] );
        category.setText( parts[5] );
    }
}
