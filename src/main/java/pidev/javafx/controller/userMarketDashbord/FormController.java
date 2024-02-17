package pidev.javafx.controller.userMarketDashbord;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import pidev.javafx.crud.marketplace.CrudBien;
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

import static javafx.scene.layout.HBox.setMargin;


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
    @FXML
    private HBox formBox1;
    @FXML
    private HBox formBox2;
    @FXML
    private HBox formBox3;





    private File chosenFile;
    private List<File>  chosenFiles;
    private MyListener listener;
    private static String usageOfThisForm="add_prod";
    private HBox buttonsBox;
    private boolean isImageUpdated;
    private Product product;
    private boolean[] isAllInpulValid;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isImageUpdated=false;
        isAllInpulValid=new boolean[]{true,true,true};

        createFormBtns();
        formBox.getChildren().add( buttonsBox );
        Pcategory.getItems().addAll( Categorie.values() );
        imageBtn.setOnAction( event -> {
            FileChooser fileChooser = new FileChooser();
            setExtFilters(fileChooser);
            fileChooser.setTitle("Save Image");
            chosenFiles  = fileChooser.showOpenMultipleDialog( Stage.getWindows().get(0) );
            if (usageOfThisForm.equals( "update_prod" ))
                isImageUpdated=true;
        } );
        setrRegEx();
    }

    private void setExtFilters(FileChooser chooser){
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    public ImageView createRegexImage(boolean isvalidated){
        ImageView imageView=new ImageView();
        if(isvalidated)
            imageView.setImage( new Image( "file:src/main/resources/namedIcons/green.png",32,32,true,true ) );
        else
            imageView.setImage( new Image( "file:src/main/resources/namedIcons/red.png",32,32,true,true ) );
        return imageView;
    }

    private void setrRegEx(){
        var regexValidatedIcon1=createRegexImage(true);
        var regexNotValidatedIcon1=createRegexImage(false);

        var regexValidatedIcon2=createRegexImage(true);
        var regexNotValidatedIcon2=createRegexImage(false);

        var regexValidatedIcon3=createRegexImage(true);
        var regexNotValidatedIcon3=createRegexImage(false);

        Pname.setOnKeyTyped( event -> {
            isAllInpulValid[0]=Pname.getText().matches("[a-zA-Z0-9]{0,10}");
            Node node=(isAllInpulValid[0])?regexValidatedIcon1:regexNotValidatedIcon1;
            String color=(isAllInpulValid[0])?"green":"red";

            if(Pname.getText().isEmpty()){
                formBox1.getChildren().removeAll( regexValidatedIcon1,regexNotValidatedIcon1 );
                Pname.setStyle( "");
                formBox.setStyle( "");
                isAllInpulValid[0]=true;
            }
            else {
                if (formBox1.getChildren().size() <= 1) {
                    formBox1.getChildren().add( node );
                } else if (formBox1.getChildren().get( 1 ) != node) {
                    formBox1.getChildren().remove( formBox1.getChildren().get( 1 ) );
                    formBox1.getChildren().add( node );
                }
                Pname.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0" );
                formBox.setStyle( "-fx-border-color:" + color + ";" +
                        "-fx-border-radius: 10;");
            }
            if(!isAllInpulValid[0]||!isAllInpulValid[1]||!isAllInpulValid[2])
                formBox.setStyle( "-fx-border-color:red;"+
                        "-fx-border-radius: 10;" );
        });
        Pprice.setOnKeyTyped( event -> {
            isAllInpulValid[1]=Pprice.getText().matches("([1-9]\\d{0,10}(,\\d{3})*)(\\.\\d{1,2})?");
            Node node=(isAllInpulValid[1])?regexValidatedIcon2:regexNotValidatedIcon2;
            String color=(isAllInpulValid[1])?"green":"red";

            if(Pprice.getText().isEmpty()){
                formBox2.getChildren().removeAll( regexValidatedIcon2,regexNotValidatedIcon2 );
                Pprice.setStyle( "");
                formBox.setStyle( "");
                isAllInpulValid[1]=true;
            }
            else {
                if (formBox2.getChildren().size() <= 1) {
                    formBox2.getChildren().add( node );
                } else if (formBox2.getChildren().get( 1 ) != node) {
                    formBox2.getChildren().remove( formBox2.getChildren().get( 1 ) );
                    formBox2.getChildren().add( node );
                }
                Pprice.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width: 0 0 2 0;" +
                        "-fx-border-radius: 0" );
                formBox.setStyle( "-fx-border-color:" + color + ";" +
                        "-fx-border-radius: 10;");
            }
            if(!isAllInpulValid[0]||!isAllInpulValid[1]||!isAllInpulValid[2])
                formBox.setStyle( "-fx-border-color:red;"+
                        "-fx-border-radius: 10;" );
        });
        Pquantity.setOnKeyTyped( event -> {
            isAllInpulValid[2]=Pquantity.getText().matches("[0-9]{1,4}");
            Node node=(isAllInpulValid[2])?regexValidatedIcon3:regexNotValidatedIcon3;
            String color=(isAllInpulValid[2])?"green":"red";

            if(Pquantity.getText().isEmpty()){
                formBox3.getChildren().removeAll( regexValidatedIcon3,regexNotValidatedIcon3 );
                Pquantity.setStyle( "");
                formBox.setStyle( "");
                isAllInpulValid[2]=true;
            }
            else {
                if (formBox3.getChildren().size() <= 1) {
                    formBox3.getChildren().add( node );
                } else if (formBox3.getChildren().get( 1 ) != node) {
                    formBox3.getChildren().remove( formBox3.getChildren().get( 1 ) );
                    formBox3.getChildren().add( node );
                }
                Pquantity.setStyle( "-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width: 0 0 2 0;" +
                        "-fx-border-radius: 0" );
                formBox.setStyle( "-fx-border-color:" + color + ";" +
                        "-fx-border-radius: 10;");
            }
            if(!isAllInpulValid[0]||!isAllInpulValid[1]||!isAllInpulValid[2])
                formBox.setStyle( "-fx-border-color:red;"+
                        "-fx-border-radius: 10;" );
        });
    }


    public void onAddOrUpdateBienClicked(MouseEvent event) {
        if(isAllInpulValid[0]&&isAllInpulValid[1]&&isAllInpulValid[2]) {
            if (isImageUpdated && usageOfThisForm.equals( "update_prod" )) {
                for (String path : product.getAllImagesSources()) {
                    File file = new File( "src/main/resources" + path );
                    file.delete();
                }
            }
            Bien bien = new Bien( (product == null) ? 0 : product.getId(),
                    1,
                    Pname.getText(),
                    Pdescretion.getText(),
                    (chosenFiles == null) ? "DO_NOT_UPDATE_OR_ADD_IMAGE" : "",
                    Float.parseFloat( Pprice.getText() ),
                    Float.parseFloat( Pquantity.getText() ),
                    Boolean.TRUE,
                    Timestamp.valueOf( LocalDateTime.now() ),
                    Pcategory.getValue() );

            List<String> imagesList = new ArrayList<>();
            for (File file : chosenFiles)
                imagesList.add( file.getAbsolutePath() );
            bien.setAllImagesSources( imagesList );

            if (usageOfThisForm.equals( "add_prod" )) {
                CrudBien.getInstance().addItem( bien );
            } else if (usageOfThisForm.equals( "update_prod" ))
                CrudBien.getInstance().updateItem( bien );
            EventBus.getInstance().publish( "refreshTableOnAddOrUpdate", event );
            EventBus.getInstance().publish( "onExitForm",event );
        }
        else{
            System.out.println("bayiiiiiiiiiiiii");
        }

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
        cancel.setOnMouseClicked( event -> EventBus.getInstance().publish( "onExitForm",event ) );

        buttonsBox.getChildren().addAll( addProd,clearProd,cancel );
        buttonsBox.setSpacing( 20 );
        buttonsBox.setAlignment( Pos.CENTER);
        buttonsBox.setId( "itemInfo" );
        buttonsBox.getStylesheets().add( String.valueOf( getClass().getResource("/style/Buttons.css") ) );
        buttonsBox.setPadding( new Insets( 10,0,10,0 ) );
    }

    public void setExitFunction(MyListener listener) {
        this.listener=listener;
    }

    public void setInformaton(Product product) {
        if(product!=null) {
            product=(Bien)product;
            this.product = product;
            Pname.setText( product.getName() );
            Pdescretion.setText( product.getDescreption() );
            Pprice.setText( Float.toString( product.getPrice() ) );
            Pquantity.setText( Float.toString( product.getQuantity() ) );
            usageOfThisForm="update_prod";
            Image img1= new Image(String.valueOf( getClass().getResource("/namedIcons/validation.png") ));
            ((Button)buttonsBox.getChildren().get( 0 )).setGraphic( new ImageView(img1) );
            Pcategory.setValue(  ((Bien) product).getCategorie());
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
