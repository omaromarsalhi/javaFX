package pidev.javafx.Controller.MarketPlace;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pidev.javafx.Model.MarketPlace.Bien;
import pidev.javafx.Model.MarketPlace.Categorie;
import pidev.javafx.Model.MarketPlace.Fruit;
import pidev.javafx.MyListener;

public class ItemController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label categoryLable;
    @FXML
    private Label priceLable;
    @FXML
    private ImageView img;

//    @FXML
//    private void click(MouseEvent mouseEvent) {
//        myListener.onClickListener(fruit);
//    }

    private Bien bien;
//    private MyListener myListener;

    public void setData(Bien bien, MyListener myListener) {
        this.bien = bien;
//        this.myListener = myListener;
        nameLabel.setText(bien.getName());
        priceLable.setText( "$"+bien.getPrice());
        quantityLabel.setText(Float.toString(bien.getQuantity()));
        firstNameLabel.setText( "Omar" );
        lastNameLabel.setText("Salhi"  );
        categoryLable.setText(bien.getCategorie().name());
        Image image = new Image(getClass().getResourceAsStream(bien.getImgSource()));
        img.setImage(image);
    }

}
