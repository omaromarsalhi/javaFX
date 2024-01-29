package pidev.javafx.Controller.MarketPlace;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import pidev.javafx.Model.MarketPlace.Bien;
import pidev.javafx.Model.MarketPlace.Categorie;
import pidev.javafx.Model.MarketPlace.Fruit;
import pidev.javafx.MyListener;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MarketController implements Initializable {
//    @FXML
//    private VBox chosenFruitCard;
//
//    @FXML
//    private Label fruitNameLable;
//
//    @FXML
//    private Label fruitPriceLabel;
//
//    @FXML
//    private ImageView fruitImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    private List<Bien> biens = new ArrayList<>();
    private Image image;
    private MyListener myListener;

//    private List<Fruit> getData() {
//        List<Fruit> fruits = new ArrayList<>();
//        Fruit fruit;
//
//        fruit = new Fruit();
//        fruit.setName("Kiwi");
//        fruit.setPrice(2.99);
//        fruit.setImgSrc("/img/kiwi.png");
//        fruit.setColor("6A7324");
//        fruits.add(fruit);
//
//        fruit = new Fruit();
//        fruit.setName("Coconut");
//        fruit.setPrice(3.99);
//        fruit.setImgSrc("/img/coconut.png");
//        fruit.setColor("A7745B");
//        fruits.add(fruit);
//
//        fruit = new Fruit();
//        fruit.setName("Peach");
//        fruit.setPrice(1.50);
//        fruit.setImgSrc("/img/peach.png");
//        fruit.setColor("F16C31");
//        fruits.add(fruit);
//
//        fruit = new Fruit();
//        fruit.setName("Grapes");
//        fruit.setPrice(0.99);
//        fruit.setImgSrc("/img/grapes.png");
//        fruit.setColor("291D36");
//        fruits.add(fruit);
//
//        fruit = new Fruit();
//        fruit.setName("Watermelon");
//        fruit.setPrice(4.99);
//        fruit.setImgSrc("/img/watermelon.png");
//        fruit.setColor("22371D");
//        fruits.add(fruit);
//
//        fruit = new Fruit();
//        fruit.setName("Orange");
//        fruit.setPrice(2.99);
//        fruit.setImgSrc("/img/orange.png");
//        fruit.setColor("FB5D03");
//        fruits.add(fruit);
//
//        fruit = new Fruit();
//        fruit.setName("StrawBerry");
//        fruit.setPrice(0.99);
//        fruit.setImgSrc("/img/strawberry.png");
//        fruit.setColor("80080C");
//        fruits.add(fruit);
//
//        fruit = new Fruit();
//        fruit.setName("Mango");
//        fruit.setPrice(0.99);
//        fruit.setImgSrc("/img/mango.png");
//        fruit.setColor("FFB605");
//        fruits.add(fruit);
//
//        fruit = new Fruit();
//        fruit.setName("Cherry");
//        fruit.setPrice(0.99);
//        fruit.setImgSrc("/img/cherry.png");
//        fruit.setColor("5F060E");
//        fruits.add(fruit);
//
//        fruit = new Fruit();
//        fruit.setName("Banana");
//        fruit.setPrice(1.99);
//        fruit.setImgSrc("/img/banana.png");
//        fruit.setColor("E7C00F");
//        fruits.add(fruit);
//
//        return fruits;
//    }

//    private void setChosenFruit(Fruit fruit) {
//        fruitNameLable.setText(fruit.getName());
//        fruitPriceLabel.setText("$" + fruit.getPrice());
//        image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
//        fruitImg.setImage(image);
//        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
//                "    -fx-background-radius: 30;");
//    }

    private List<Bien> getData() {
        List<Bien> biens = new ArrayList<>();
        Bien bien;

        for(int i=0;i<12;i++){
            bien=new Bien(i,1,"Product_"+i,"/icons/"+i+".png",i*25f,20f,false,new Timestamp(System.currentTimeMillis()), Categorie.ENTERTAINMENT );
            biens.add( bien );
        }


        return biens;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        biens.addAll(getData());
//        if (fruits.size() > 0) {
//            setChosenFruit(fruits.get(0));
//            myListener = new MyListener() {
//                @Override
//                public void onClickListener(Fruit fruit) {
//                    setChosenFruit(fruit);
//                }
//            };
//        }
        int column = 0;
        int row = 1;

//        ColumnConstraints clm1=new ColumnConstraints();
//        ColumnConstraints clm2=new ColumnConstraints();
//        ColumnConstraints clm3=new ColumnConstraints();
//
//        grid.getColumnConstraints().add( clm1 );
//        grid.getColumnConstraints().add( clm2 );
//        grid.getColumnConstraints().add( clm3 );
//
//        clm1.setPercentWidth( 33 );
//        clm2.setPercentWidth( 33 );
//        clm3.setPercentWidth( 34 );

        try {
            for (int i = 0; i < biens.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/marketPlace/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(biens.get(i),myListener);


                if (column == 3) {
                    column = 0;
                    row++;
                }

//                grid.setGridLinesVisible( true );
                grid.setHgap( 20 );
                grid.setVgap( 20 );

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
            grid.setPrefHeight(scroll.getPrefHeight());
            grid.setPrefWidth( scroll.getPrefWidth()-20);
            grid.setPadding( new Insets( 10,10,10,20 ) );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
