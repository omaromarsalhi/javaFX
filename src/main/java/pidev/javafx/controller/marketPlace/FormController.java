package pidev.javafx.controller.marketPlace;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pidev.javafx.crud.CrudBien;
import pidev.javafx.tools.EventBus;
import pidev.javafx.tools.MyListener;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Categorie;
import pidev.javafx.model.MarketPlace.Product;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;


public class FormController implements Initializable {

    @FXML
    private ChoiceBox<Categorie> Pcategory;
    @FXML
    private TextArea Pdescretion;
    @FXML
    private TextField Pname;
    @FXML
    private TextField Pprice;
    @FXML
    private TextField Pquantity;
    @FXML
    private VBox formBox;
    @FXML
    private Button imageBtn;




    private File chosenFile;
    private List<File>  chosenFiles;
    private MyListener listener;
    private static String usageOfThisForm="add_prod";
    private HBox buttonsBox;
    private boolean isImageUpdated;
    private Product product;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isImageUpdated=false;
        createFormBtns();
        formBox.getChildren().add( buttonsBox );
        Pcategory.getItems().addAll( Categorie.values() );
        imageBtn.setOnAction( event -> {
            FileChooser fileChooser = new FileChooser();
            setExtFilters(fileChooser);
            fileChooser.setTitle("Save Image");
//            chosenFile = fileChooser.showOpenDialog( Stage.getWindows().get(0) );
            chosenFiles  = fileChooser.showOpenMultipleDialog( Stage.getWindows().get(0) );
            if (usageOfThisForm.equals( "update_prod" ))
                isImageUpdated=true;
        } );

    }

    private void setExtFilters(FileChooser chooser){
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }



    public void onAddOrUpdateBienClicked(MouseEvent event) {
        if(isImageUpdated&&usageOfThisForm.equals( "update_prod" )){
            for(String path :product.getAllImagesSources()){
                File file=new File("src/main/resources"+path );
                file.delete();
            }
        }
        Bien bien =new Bien( (product==null)?0:product.getId(),
                1,
                Pname.getText(),
                Pdescretion.getText(),
                (chosenFiles==null)?"DO_NOT_UPDATE_OR_ADD_IMAGE":"",
                Float.parseFloat( Pprice.getText() ),
                Float.parseFloat( Pquantity.getText()),
                Boolean.TRUE,
                Timestamp.valueOf( LocalDateTime.now() ),
                Pcategory.getValue());

        List<String> imagesList=new ArrayList<>();
        for(File file :chosenFiles)
            imagesList.add(  file.getAbsolutePath());
        bien.setAllImagesSources(imagesList);

        if(usageOfThisForm.equals( "add_prod" )) {
            CrudBien.getInstance().addItem( bien );
        }
        else if(usageOfThisForm.equals( "update_prod" ))
            CrudBien.getInstance().updateItem(bien);
        EventBus.getInstance().publish( "refreshTableOnAddOrUpdate",event);

    }


    public void createFormBtns(){
        Button addProd= new Button();
        Button clearProd = new Button();
        Button cancel= new Button();

        buttonsBox =new HBox();

        addProd.setPrefWidth( 50 );
        clearProd.setPrefWidth( 50 );
        cancel.setPrefWidth( 50 );

        addProd.setPrefHeight( 32 );
        clearProd.setPrefHeight( 32 );
        cancel.setPrefHeight( 32 );

        Image  img1= new Image(String.valueOf( getClass().getResource("/namedIcons/tab2.png") ));
        Image img2= new Image(String.valueOf( getClass().getResource("/namedIcons/broom.png")));
        Image img3= new Image(String.valueOf( getClass().getResource("/namedIcons/paper.png")));

        addProd.setGraphic( new ImageView( img1 ));
        clearProd.setGraphic( new ImageView( img2 ));
        cancel.setGraphic( new ImageView( img3 ));

        addProd.setOnMouseClicked( this::onAddOrUpdateBienClicked);
        clearProd.setOnMouseClicked( event -> {
            Pdescretion.setText( "" );
            Pname.setText( "" );
            Pprice.setText( "" );
            Pquantity.setText( "" );
        } );
        cancel.setOnMouseClicked( event -> listener.exit() );

        buttonsBox.getChildren().addAll( addProd,clearProd,cancel );
        buttonsBox.setSpacing( 20 );
        buttonsBox.setAlignment( Pos.CENTER);
        buttonsBox.setId( "itemInfo" );
        buttonsBox.getStylesheets().add( String.valueOf( getClass().getResource("/style/Buttons.css") ) );

    }

    public void setExitFunction(MyListener listener) {
        this.listener=listener;
    }

    public void setInformaton(Product product) {
        if(product!=null) {
            this.product = product;
            Pname.setText( product.getName() );
            Pdescretion.setText( product.getDescreption() );
            Pprice.setText( Float.toString( product.getPrice() ) );
            Pquantity.setText( Float.toString( product.getQuantity() ) );
            usageOfThisForm="update_prod";
            Image img1= new Image(String.valueOf( getClass().getResource("/namedIcons/validation.png") ));
            ((Button)buttonsBox.getChildren().get( 0 )).setGraphic( new ImageView(img1) );
        }
    }

}






















//        int nameLenght=chosenFile.getName().length();
//        String fileName=Double.toString(chosenFile.getPath().length()* randomVal.nextInt(chosenFile.getPath().length())*nameLenght/2)+chosenFile.getName().substring(0,nameLenght-4);
//        String path ="usersImg/"+fileName+".png";
//
//
//        String sql = "INSERT INTO bien "
//                + "(idUser,name,imgSource,price,quantity,state,timestamp,category) "
//                + "VALUES(?,?,?,?,?,?,?,?)";
//
//        connect = ConnectionDB.connectDb();
//
//        try {
//            prepare = connect.prepareStatement(sql);
//            prepare.setString(1, "1");
//            prepare.setString(2, Pname.getText());
//            prepare.setString(3, path );
////                prepare.setString(4, String.valueOf(dob.getValue()));
//            prepare.setString(4, Pprice.getText());
//            prepare.setString(5, Pquantity.getText());
//            prepare.setString(6, "1");
//            prepare.setString(7, Timestamp.valueOf(LocalDateTime.now()).toString());
//            prepare.setString(8, Pcategory.getValue().toString());
//            prepare.executeUpdate();
//
//            try {
//                ImageIO.write(bi, "png", new File( "src/main/resources/"+path ));
//            } catch (IOException e) {
//                throw new RuntimeException( e );
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
