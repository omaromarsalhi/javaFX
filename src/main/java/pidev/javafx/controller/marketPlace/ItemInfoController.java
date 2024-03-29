package pidev.javafx.controller.marketPlace;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pidev.javafx.crud.marketplace.CrudBien;
import pidev.javafx.crud.user.ServiceUser;
import pidev.javafx.model.user.User;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Product;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class ItemInfoController implements Initializable {

    @FXML
    private Label categoryLable;
    @FXML
    private Button exit;
    @FXML
    private VBox itemDeatails;
    @FXML
    private VBox btnBox;
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
    private Button openChatBtn;
    @FXML
    private Button leftArrow;
    @FXML
    private Button rightArrow;
    @FXML
    private Button exitImageBtn;


    private Product product;
    private HBox infoTemplateBtn;
    private String whereAmI;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.getInstance().subscribe( "setItemInfoData",this::setData );
        EventBus.getInstance().subscribe( "setItemInfoData4LocalUser",this::setDataForLocalUser );

    }


    public void setData(CustomMouseEvent<Product> customMouseEvent) {
        whereAmI="show4AllUsers";
        this.product=customMouseEvent.getEventData();
        var service=new ServiceUser();
        User user=service.getUserById( customMouseEvent.getEventData().getIdUser() );
        userName.setText(user.getFirstname()+" "+user.getLastname());
        prodName.setText( product.getName().toUpperCase() );
        itemImage.setImage(new Image("file:src/main/resources"+product.getImgSource()));
        itemDesc.setText( product.getDescreption() );
        priceLable.setText( Float.toString(product.getPrice()) );
        quantityLable.setText(Float.toString(product.getQuantity())   );
        userImage.setImage(new Image("file:src/main/resources"+user.getPhotos()));
//        stateLabel.setText((product.getState())?"In Stock":"Out Of Stock");
        scroollImages();
    }


    public void scroollImages(){
        AtomicInteger imageIndex= new AtomicInteger(0);
        itemImage.setImage(new Image("file:src/main/resources"+ product.getImageSourceByIndex( imageIndex.get() ) ) );
        rightArrow.setOnAction( event -> {
            imageIndex.getAndIncrement();
            if(imageIndex.get() >=product.getAllImagesSources().size())
                imageIndex.set( 0 );
            itemImage.setImage( new Image( "file:src/main/resources"+product.getImageSourceByIndex( imageIndex.get() ) ) );
        } );
        leftArrow.setOnAction( event -> {
            imageIndex.getAndDecrement();
            if(imageIndex.get() <=0)
                imageIndex.set( product.getAllImagesSources().size() - 1 );
            itemImage.setImage( new Image("file:src/main/resources"+ product.getImageSourceByIndex( imageIndex.get() ) ) );
        } );

        exitImageBtn.setOnMouseClicked( event -> EventBus.getInstance().publish( "exitItemInfo",event));
    }


    public void setDataForLocalUser(CustomMouseEvent<Product> customMouseEvent) {
        whereAmI="show4TheOwner";
        this.product=customMouseEvent.getEventData();
        userName.setText("Omar Salhi");
        prodName.setText( product.getName().toUpperCase() );
        itemImage.setImage(new Image("file:src/main/resources"+product.getImgSource()));
        itemDesc.setText( product.getDescreption() );
        priceLable.setText( Float.toString(product.getPrice()) );
        quantityLable.setText(Float.toString(product.getQuantity())   );
//        stateLabel.setText((product.getState())?"In Stock":"Out Of Stock");
        scroollImages();

        createUpdateAndDeleteBtns();
        exitImageBtn.setOnMouseClicked( event ->  {
            EventBus.getInstance().publish( "onExitForm",event );
        } );
    }

    public void createUpdateAndDeleteBtns(){

        Button update= new Button();
        Button delete = new Button();

        update.setPrefWidth( 50 );
        delete.setPrefWidth( 50 );

        update.setPrefHeight( 32 );
        delete.setPrefHeight( 32 );

        Image img1= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/sync24.png" ) ),24,24,true,true);
        Image img2= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/delete24c.png" )),24,24,true,true);

        update.setGraphic( new ImageView( img1 ));
        delete.setGraphic( new ImageView( img2 ));

        update.setOnMouseClicked( event -> {
            CustomMouseEvent<Bien> customMouseEvent=new CustomMouseEvent<>((Bien) product);
            EventBus.getInstance().publish( "updateProd",customMouseEvent);
        } );

        delete.setOnMouseClicked( event -> {
            CustomMouseEvent<Bien> customMouseEvent=new CustomMouseEvent<>((Bien) product);
            CrudBien.getInstance().deleteItem( product.getId());
            for(String path :product.getAllImagesSources())
                new File("src/main/resources"+path).delete();

            EventBus.getInstance().publish( "refreshTableOnDelete",customMouseEvent);
//            onExitBtnClicked(event);
        } );

        btnBox.getChildren().add(3, update );
        btnBox.getChildren().add(4, delete );
    }

    public void onExitBtnClicked(MouseEvent event){
//        if(whereAmI.equals("show4TheOwner"))
            EventBus.getInstance().publish( "onExitForm",event );
//        else
//            EventBus.getInstance().publish( "showHelfullBar",event );
    }

    @FXML
    public void onOpenChatBtnClicked(ActionEvent event){
        var service=new ServiceUser();
        EventBus.getInstance().publish( "showChat",new CustomMouseEvent<User>( service.getUserById( product.getIdUser() )));
    }


}
