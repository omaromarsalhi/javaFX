package pidev.javafx.controller.marketPlace;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pidev.javafx.crud.CrudBien;
import pidev.javafx.tools.CustomMouseEvent;
import pidev.javafx.tools.EventBus;
import pidev.javafx.tools.MyListener;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Product;

import java.io.File;

public class ItemInfoController {

    @FXML
    private Label categoryLable;

    @FXML
    private Button exit;

    @FXML
    private VBox itemDeatails;

    @FXML
    private TextArea itemDesc;

    @FXML
    private ImageView itemImage;

    @FXML
    private Label priceLable;

    @FXML
    private Label quantityLable;

    @FXML
    private Label stateLabel;

    @FXML
    private ImageView userImage;
    @FXML
    private Label prodName;
    @FXML
    private Label userName;
    @FXML
    private HBox userInfo;
    @FXML
    private Button openChatBtn;



    private MyListener myListener;
    private Product product;
    private HBox infoTemplateBtn;

    public void setData(Product product,MyListener myListener) {
        this.product=product;
        this.myListener=myListener;
        userName.setText("Omar Salhi");
        prodName.setText( product.getName() );
        itemImage.setImage(new Image("file:src/main/resources"+product.getImgSource()));
        itemDesc.setText( "qsfdgoauiehrtgpbea ufhgae ouifehg dfvb ae rhtgqfvhbj aert qfvhuaerçtg" );
        priceLable.setText( Float.toString(product.getPrice()) );
        quantityLable.setText(Float.toString(product.getQuantity())   );
        stateLabel.setText((product.getState())?"In Stock":"Out Of Stock");
    }

    public void setDataForLocalUser(Product product, double width) {
        itemDeatails.getChildren().remove(exit);
        itemDeatails.getChildren().remove(userInfo);
        itemDeatails.setPrefHeight( itemDeatails.getPrefHeight()-100 );
        this.product=product;
        prodName.setStyle( "-fx-font-size: 20;" );
        prodName.setText( product.getName().toUpperCase() );

        itemImage.setFitWidth( width );
        if(!product.getImgSource().isEmpty())
            itemImage.setImage(new Image("file:src/main/resources"+product.getImgSource(),width,width-20,false,false));
        itemDesc.setText( product.getDescreption() );
        priceLable.setText( Float.toString(product.getPrice()) );
        quantityLable.setText(Float.toString(product.getQuantity())   );
        stateLabel.setText((product.getState())?"In Stock":"Out Of Stock");
        if(infoTemplateBtn==null) {
            createUpdateAndDeleteBtns();
            itemDeatails.getChildren().add( infoTemplateBtn );
        }
    }

    public void createUpdateAndDeleteBtns(){
        infoTemplateBtn=new HBox();

        Button update= new Button();
        Button delete = new Button();

        update.setPrefWidth( 50 );
        delete.setPrefWidth( 50 );

        update.setPrefHeight( 32 );
        delete.setPrefHeight( 32 );

        Image img1= new Image(String.valueOf( getClass().getResource("/namedIcons/refresh.png") ));
        Image img2= new Image(String.valueOf( getClass().getResource("/namedIcons/delete.png")));

        update.setGraphic( new ImageView( img1 ));
        delete.setGraphic( new ImageView( img2 ));

        update.setOnAction( event -> {
            CustomMouseEvent<Bien> customMouseEvent=new CustomMouseEvent<>((Bien) product);
            EventBus.getInstance().publish( "updateProd",customMouseEvent);
        } );
        delete.setOnAction( event -> {
            CustomMouseEvent<Bien> customMouseEvent=new CustomMouseEvent<>((Bien) product);
            CrudBien.getInstance().deleteItem( product.getId());
            for(String path :product.getAllImagesSources())
                new File("src/main/resources"+path).delete();
            EventBus.getInstance().publish( "refreshTableOnDelete",customMouseEvent);
        } );


        infoTemplateBtn.getChildren().addAll( update,delete );
        infoTemplateBtn.setSpacing( 20 );
        infoTemplateBtn.setAlignment( Pos.CENTER);
        infoTemplateBtn.setId( "itemInfo" );
        infoTemplateBtn.getStylesheets().add( String.valueOf( getClass().getResource("/style/Buttons.css") ) );
        infoTemplateBtn.setPadding( new Insets( 20,0,0,0 ) );
    }

    @FXML
    public void onExitBtnClicked(ActionEvent event){
       myListener.exit();
    }

    @FXML
    public void onOpenChatBtnClicked(MouseEvent event){
        EventBus.getInstance().publish( "loadChat",event);
    }
}
