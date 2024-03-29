package pidev.javafx.controller.userMarketDashbord;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import pidev.javafx.crud.marketplace.CrudBien;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Categorie;
import pidev.javafx.model.MarketPlace.Product;
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.marketPlace.*;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


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
    private Button deleteBtn;
    @FXML
    private Button exitImageBtn;
    @FXML
    private HBox formBox;
    @FXML
    private HBox formBox1;
    @FXML
    private HBox formBox2;
    @FXML
    private HBox formBox3;
    @FXML
    private Button imageBtn;
    @FXML
    private Button leftArrow;
    @FXML
    private ImageView relativeImageVieur;
    @FXML
    private Button rightArrow;
    @FXML
    private Button chatBtn;
    @FXML
    private VBox seconfInterface;
    @FXML
    private VBox Box1;
    @FXML
    private AnchorPane loadinPage;




    private File chosenFile;
    private List<File>  chosenFiles;
    private MyListener listener;
    private static String usageOfThisForm;
    private HBox buttonsBox;
    private boolean isImageUpdated;
    private Bien product;
    private boolean[] isAllInpulValid;
    String formLayoutBeforRegexCheck;
    String formLayoutAfterRegexCheck;
    private String  checkIfImageCompatableWithName;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usageOfThisForm="add_prod";
        loadinPage.setVisible( false );
        chatBtn.setStyle( "-fx-border-color: transparent;" );
        chatBtn.hoverProperty().addListener( (observable, oldValue, newValue) -> {
            if(newValue)
                chatBtn.setStyle("-fx-border-color: #fdc847;" +
                        "-fx-border-radius: 15");
            else
                chatBtn.setStyle("-fx-border-color: transparent;");

        } );


        seconfInterface.setVisible( false );
        exitImageBtn.setOnMouseClicked( event -> seconfInterface.setVisible( false ) );

        formLayoutBeforRegexCheck="-fx-border-color:black;"+"-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-color: white;" +
                "-fx-background-radius: 10";

        formLayoutAfterRegexCheck="-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-color: white;" +
                "-fx-background-radius: 10";

        isImageUpdated=false;
        isAllInpulValid=new boolean[]{false,false,false};

        createFormBtns();
        Box1.getChildren().add( buttonsBox );
        Pcategory.getItems().addAll( Categorie.values() );
        imageBtn.setOnAction( event -> {
            if (usageOfThisForm.equals( "update_prod" )) {
                isImageUpdated = true;
                seconfInterface.setVisible( true );
                seconfInterface.setMinHeight(formBox.getHeight());
                FadeTransition fade1 = new FadeTransition( Duration.seconds( 0.8 ), seconfInterface);
                fade1.setFromValue( 0 );
                fade1.setToValue( 1 );
                fade1.play();
                dealWithImages4Update();
            }
            else {
                FileChooser fileChooser = new FileChooser();
                setExtFilters( fileChooser );
                fileChooser.setTitle( "Save Image" );
                chosenFiles = fileChooser.showOpenMultipleDialog( Stage.getWindows().get( 0 ) );
            }
        } );

        setRegEx();
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
            imageView.setImage( new Image( "file:src/main/resources/icons/marketPlace/green.png",34,34,true,true ) );
        else
            imageView.setImage( new Image( "file:src/main/resources/icons/marketPlace/red.png",34,34,true,true ) );
        return imageView;
    }

    public void dealWithImages4Update(){
        AtomicInteger curentIndex= new AtomicInteger( 0 );
        relativeImageVieur.setImage( new Image( "file:src/main/resources/"+product.getImageSourceByIndex(curentIndex.get())));
        rightArrow.setOnAction( event -> {
            curentIndex.getAndIncrement();
            if(curentIndex.get()>=product.getAllImagesSources().size()) {
                curentIndex.set( 0 );
            }
            relativeImageVieur.setImage( new Image( "file:src/main/resources/"+product.getImageSourceByIndex( curentIndex.get() ) ) );
        });
        leftArrow.setOnAction( event -> {
            relativeImageVieur.setImage( new Image( "file:src/main/resources/"+product.getImageSourceByIndex( curentIndex.get() ) ) );curentIndex.getAndDecrement();
            if(curentIndex.get()<0)
                curentIndex.set( product.getAllImagesSources().size()-1 );
        } );
        deleteBtn.setOnAction( event -> {
            if(product.getAllImagesSources().size()>1) {
                new File( "src/main/resources" + product.getImageSourceByIndex( curentIndex.get() ) ).delete();
                product.deleteFromImagesSources( curentIndex.get() );
                rightArrow.fire();
            }
        });
    }


    @FXML
    public void handelDragOver(DragEvent dragEvent){
        if(dragEvent.getDragboard().hasFiles())
            dragEvent.acceptTransferModes( TransferMode.ANY );
    }

    @FXML
    public void handelDrag(DragEvent dragEvent){
        for (File file : dragEvent.getDragboard().getFiles()){
            relativeImageVieur.setImage( new Image( file.getAbsolutePath() ) );
            product.addFromImagesSources( MyTools.getInstance().getPathAndSaveIMG(file.getAbsolutePath() ) );
        }
    }

    @FXML
    public void generateDescription(ActionEvent event){
        if(!Pname.getText().isEmpty()&&isAllInpulValid[0]){

            Thread thread =loadingPageThread();
            thread.start();
            loadinPage.setVisible( true );
        }
    }

    private Thread loadingPageThread() {

        Task<String> myTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                return ChatGPTAPIDescriber.chatGPT( "describe "+Pname.getText()+" for sale specifying its benefits");
            }
        };

        myTask.setOnSucceeded(e -> {
            Platform.runLater( () -> Pdescretion.setText( myTask.getValue() ) );
            loadinPage.setVisible( false );
        });

        return new Thread(myTask);
    }

    private void setRegEx(){
        var regexValidatedIcon1=createRegexImage(true);
        var regexNotValidatedIcon1=createRegexImage(false);

        var regexValidatedIcon2=createRegexImage(true);
        var regexNotValidatedIcon2=createRegexImage(false);

        var regexValidatedIcon3=createRegexImage(true);
        var regexNotValidatedIcon3=createRegexImage(false);

        Popup popup4Regex =MyTools.getInstance().createPopUp();

        Pname.setOnKeyTyped( event -> {
            isAllInpulValid[0]=Pname.getText().matches("^[A-Za-z0-9 ]*$");
            Node node=(isAllInpulValid[0])?regexValidatedIcon1:regexNotValidatedIcon1;
            String color=(isAllInpulValid[0])?"green":"red";
            if(Pname.getText().isEmpty()){
                formBox1.getChildren().removeAll(regexValidatedIcon1,regexNotValidatedIcon1 );
                Pname.setStyle( "");
                formBox.setStyle(formLayoutBeforRegexCheck);
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
                        formLayoutAfterRegexCheck);
            }
            if((!isAllInpulValid[0]&&!Pname.getText().isEmpty())||(!isAllInpulValid[1]&&!Pprice.getText().isEmpty())||(!isAllInpulValid[2]&&!Pquantity.getText().isEmpty()))
                formBox.setStyle( "-fx-border-color:red;"+
                        formLayoutAfterRegexCheck );
        });
        Pprice.setOnKeyTyped( event -> {
            isAllInpulValid[1]=Pprice.getText().matches("([1-9]\\d{0,10}(,\\d{3})*)(\\.\\d{1,2})?");
            Node node=(isAllInpulValid[1])?regexValidatedIcon2:regexNotValidatedIcon2;
            String color=(isAllInpulValid[1])?"green":"red";

            if(Pprice.getText().isEmpty()){
                formBox2.getChildren().removeAll( regexValidatedIcon2,regexNotValidatedIcon2 );
                Pprice.setStyle( "");
                formBox.setStyle(formLayoutBeforRegexCheck);
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
                        formLayoutAfterRegexCheck);
            }
            if((!isAllInpulValid[0]&&!Pname.getText().isEmpty())||(!isAllInpulValid[1]&&!Pprice.getText().isEmpty())||(!isAllInpulValid[2]&&!Pquantity.getText().isEmpty()))
                formBox.setStyle( "-fx-border-color:red;"+
                        formLayoutAfterRegexCheck );
        });
        Pquantity.setOnKeyTyped( event -> {
            isAllInpulValid[2]=Pquantity.getText().matches("[0-9]{1,4}");
            Node node=(isAllInpulValid[2])?regexValidatedIcon3:regexNotValidatedIcon3;
            String color=(isAllInpulValid[2])?"green":"red";

            if(Pquantity.getText().isEmpty()){
                formBox3.getChildren().removeAll( regexValidatedIcon3,regexNotValidatedIcon3 );
                Pquantity.setStyle( "");
                formBox.setStyle(formLayoutBeforRegexCheck);
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
                        formLayoutAfterRegexCheck);
            }
            if((!isAllInpulValid[0]&&!Pname.getText().isEmpty())||(!isAllInpulValid[1]&&!Pprice.getText().isEmpty())||(!isAllInpulValid[2]&&!Pquantity.getText().isEmpty()))
                formBox.setStyle( "-fx-border-color:red;"+
                        formLayoutAfterRegexCheck );
        });

        Pname.setOnMouseEntered( event -> {
            if(!isAllInpulValid[0]&&!Pname.getText().isEmpty()){
                ((Label)popup4Regex.getContent().get( 0 )).setText("ONLY CHARACTERS AND NUMBERS ARE ALLOWED");
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }
            else{
                ((Label)popup4Regex.getContent().get( 0 )).setText("Please Give a descriptive Name To help The description Generator");
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #1ec92c;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }
        } );
        Pname.setOnMouseExited( event -> {
                popup4Regex.hide();
        } );
        Pprice.setOnMouseEntered( event -> {
            if(!isAllInpulValid[1]&&!Pprice.getText().isEmpty()){
                ((Label)popup4Regex.getContent().get( 0 )).setText("ONLY NUMBERS ARE ALLOWED");
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-40);
            }
        } );
        Pprice.setOnMouseExited( event -> {
            if(!Pprice.getText().isEmpty())
                popup4Regex.hide();
        } );
        Pquantity.setOnMouseEntered( event -> {
            if(!isAllInpulValid[2]&&!Pquantity.getText().isEmpty()){
                ((Label)popup4Regex.getContent().get( 0 )).setText("ONLY NUMBERS ARE ALLOWED");
                popup4Regex.getContent().get( 0 ).setStyle(popup4Regex.getContent().get( 0 ).getStyle()+"-fx-background-color: #ed1c27;"  );
                popup4Regex.show(Stage.getWindows().get(0),event.getScreenX()+40,event.getScreenY()-20);
            }
        } );
        Pquantity.setOnMouseExited( event -> {
            if(!Pquantity.getText().isEmpty())
                popup4Regex.hide();
        } );
    }


    public void onAddOrUpdateBienClicked(MouseEvent event) {
//        System.out.println(UserController.getInstance().getCurrentUser());
        if(isAllInpulValid[0]&&isAllInpulValid[1]&&isAllInpulValid[2]) {
            Bien bien = new Bien( (product == null) ? 0 : product.getId(),
                    UserController.getInstance().getCurrentUser().getId(),
                    Pname.getText(),
                    Pdescretion.getText(),
                    (isImageUpdated) ?"":"DO_NOT_UPDATE_OR_ADD_IMAGE",
                    Float.parseFloat( Pprice.getText() ),
                    Float.parseFloat( Pquantity.getText() ),
                    "unverified",
                    Timestamp.valueOf( LocalDateTime.now() ),
                    Pcategory.getValue() );
//            if(isImageUpdated)
//                bien.setAllImagesSources( product.getAllImagesSources() );
            if(chosenFiles!=null) {
                List<String> imagesList = new ArrayList<>();
                for (File file : chosenFiles)
                    imagesList.add(MyTools.getInstance().getPathAndSaveIMG(file.getAbsolutePath()) );
                bien.setAllImagesSources( imagesList );
            }
            else {
                bien.setAllImagesSources( product.getAllImagesSources() );
            }
            if (usageOfThisForm.equals( "add_prod" )) {
                CrudBien.getInstance().addItem( bien );
                bien=CrudBien.getInstance().selectLastItem();
                MyTools.getInstance().notifyUser4NewAddedProduct( bien );
            } else if (usageOfThisForm.equals( "update_prod" )) {
                bien.setState( product.getState() );
                CrudBien.getInstance().updateItem( bien );
                MyTools.getInstance().notifyUser4NewAddedProduct( bien );
            }
            product=bien;
            aiVerifyThread().start();
            Thread thread = sleepThread(event);
            loadinPage.setVisible( true );
            thread.start();
        }
        else{
            Alert confirmationAlert = new Alert( Alert.AlertType.ERROR );
            confirmationAlert.setTitle("Error");
            confirmationAlert.setHeaderText( null );
            confirmationAlert.setGraphic( null );
            confirmationAlert.getDialogPane().getStylesheets().add("file:src/main/resources/style/alertStyle.css");
            confirmationAlert.setContentText("There is some wrong Data please fix it !!!");
            confirmationAlert.show();
        }

    }


    private Thread sleepThread(MouseEvent event) {
        Task<Void> myTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(1000);
                return null;
            }
        };

        myTask.setOnSucceeded(e -> {
            if (usageOfThisForm.equals( "add_prod" ))
                EventBus.getInstance().publish( "updateTabProds", new CustomMouseEvent<>( product ) );
            else
                EventBus.getInstance().publish( "refreshProdContainer", event );
            EventBus.getInstance().publish( "onExitForm", event );
            loadinPage.setVisible( false );
            MyTools.getInstance().getTextNotif().setText( "Prod Has Been Added And Under Verification" );
            MyTools.getInstance().showNotif();
        });
        return new Thread(myTask);
    }

    private Thread aiVerifyThread() {
        Task<String> myTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                String descreption=CallPythonFromJava.run( "src/main/resources"+product.getImageSourceByIndex(0));
                return "does this paragraph "+descreption.substring( 0,descreption.indexOf( "." ) )+" speaks about "+product.getName() +" and please answer me with yes or no?";
            }
        };

        myTask.setOnSucceeded( event -> {
            String respose=ChatGPTAPIDescriber.chatGPT(myTask.getValue());
            if(respose.toLowerCase().contains( "yes" )){
                product.setState( "verified" );
                CrudBien.getInstance().updateItem( product );
                MyTools.getInstance().getTextNotif().setText( "Prod Has Been Verified" );
                MyTools.getInstance().showNotif();
            }
            else {
                CrudBien.getInstance().deleteItem( product.getId() );
                MyTools.getInstance().getTextNotif().setText( "Prod Has Been Disqualified" );
                MyTools.getInstance().showNotif();
            }
            EventBus.getInstance().publish( "doUpdateTabprodAfterAIverif",new CustomMouseEvent<>(product) );
            System.out.println("done");
        } );

        return new Thread(myTask);
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

        Image  img1= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/tab2.png" ) ));
        Image img2= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/broom.png" )));
        Image img3= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/paper.png" )));

        addProd.setGraphic( new ImageView( img1 ));
        clearProd.setGraphic( new ImageView( img2 ));
        cancel.setGraphic( new ImageView( img3 ));

        addProd.setOnMouseClicked( this::onAddOrUpdateBienClicked);
        clearProd.setOnMouseClicked( event -> {
            if(formBox1.getChildren().size()>1)
                formBox1.getChildren().removeAll( formBox1.getChildren().get( 1 ) );
            Pname.setStyle( "");
            if(formBox2.getChildren().size()>1)
                formBox2.getChildren().remove(formBox2.getChildren().get( 1 ) );
            Pprice.setStyle( "");
            if(formBox3.getChildren().size()>1)
                formBox3.getChildren().removeAll( formBox3.getChildren().get( 1 ) );
            Pquantity.setStyle( "");
            formBox.setStyle(formLayoutBeforRegexCheck);
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
        buttonsBox.getStylesheets().add( String.valueOf( getClass().getResource( "/style/marketPlace/Buttons.css" ) ) );
        buttonsBox.setPadding( new Insets( 0,0,10,0 ) );
    }

//    public void setExitFunction(MyListener listener) {
//        this.listener=listener;
//    }

    public void setInformaton(Product product) {
        if(product!=null) {
            product=(Bien)product;
            this.product =(Bien) product;
            Pname.setText( product.getName() );
            Pdescretion.setText( product.getDescreption() );
            Pprice.setText( Float.toString( product.getPrice() ) );
            Pquantity.setText( Float.toString( product.getQuantity() ) );
            usageOfThisForm="update_prod";
            Image img1= new Image(String.valueOf( getClass().getResource( "/icons/marketPlace/validation.png" ) ));
            ((Button)buttonsBox.getChildren().get( 0 )).setGraphic( new ImageView(img1) );
            Pcategory.setValue(  ((Bien) product).getCategorie());
            isAllInpulValid[0]=true;
            isAllInpulValid[1]=true;
            isAllInpulValid[2]=true;
        }
    }

}



