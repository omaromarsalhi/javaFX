package pidev.javafx.controller.chat;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.layout.HBox.setMargin;

public class ChatController implements Initializable {

    @FXML
    private Button addImageBtn;
    @FXML
    private AnchorPane ancherPaneOfgridPane;
    @FXML
    private AnchorPane ancherPaneOfgridPaneMain;
    @FXML
    private VBox chat;
    @FXML
    private VBox chatContainer;
    @FXML
    private Button clearMsgBtn;
    @FXML
    private Button emojie00;
    @FXML
    private Button emojie01;
    @FXML
    private Button emojie02;
    @FXML
    private Button emojie10;
    @FXML
    private Button emojie11;
    @FXML
    private Button emojie12;
    @FXML
    private Button emojie20;
    @FXML
    private Button emojie21;
    @FXML
    private Button emojie22;
    @FXML
    private VBox itemDeatails;
    @FXML
    private TextField messageTextField;
    @FXML
    private Button moreOptions;
    @FXML
    private Button sendMsgBtn;
    private boolean val;


    private List<File>  chosenFiles;



    @Override
    public void initialize(URL location, ResourceBundle resources){
        chosenFiles=null;
        ancherPaneOfgridPaneMain.setVisible( false );
        val=false;
    }




    public HBox createTextChatBox(String text,boolean changeOrder){
        HBox msgBox=new HBox();
        msgBox.setPrefWidth( chatContainer.getPrefWidth());

        msgBox.setPadding( new Insets( 0,0,0,10 ) );
        msgBox.setSpacing( 4 );


        Label msgLabel=new Label();
        msgLabel.setStyle( "-fx-background-color:  #D9D9D9;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;");
        msgLabel.setMinHeight( 40 );
        msgLabel.setText(text);
        msgLabel.setMinHeight( Region.USE_PREF_SIZE);
        msgLabel.setWrapText( true );
        msgLabel.setPadding( new Insets( 6 ) );

        Label timeLabel=new Label();
        timeLabel.setStyle( "-fx-font-size: 10;");
        timeLabel.setMinSize( 25,15 );

        timeLabel.setText( LocalTime.now().format( DateTimeFormatter.ofPattern("hh:mm")) );

        ImageView usernIcon=new ImageView( new Image("file:src/main/resources/img/me.png",16,16,false,false) );
        ImageView deleteIcon=new ImageView( new Image("file:src/main/resources/namedIcons/miniTrash.png",16,16,true,true) );
        setMargin(usernIcon,new Insets( 0,0,4,0 ));
        setMargin(deleteIcon,new Insets( 0,0,msgLabel.getPrefHeight()/2,0 ));
        System.out.println(msgLabel.getPrefHeight()/2-8);
        if(!changeOrder) {
            msgBox.setAlignment( Pos.BOTTOM_RIGHT );
            msgBox.getChildren().addAll(deleteIcon, msgLabel, timeLabel, usernIcon );
        }
        else {
            msgBox.setAlignment( Pos.BOTTOM_LEFT );
            msgBox.getChildren().addAll(deleteIcon, usernIcon, timeLabel, msgLabel );
        }
        return msgBox;
    }

    public HBox createImageChatBox(String path,boolean changeOrder){
        HBox msgBox=new HBox();
        msgBox.setPrefWidth( chatContainer.getPrefWidth());
//        msgBox.setStyle( "-fx-background-color: red" );
        msgBox.setAlignment( Pos.BOTTOM_RIGHT );
        msgBox.setPadding( new Insets( 0,0,0,10 ) );
        msgBox.setSpacing( 4 );


        ImageView image=new ImageView( new Image(path,80,100,true,true) );

//        msgLabel.setStyle( "-fx-background-color: blue" );
        image.setStyle( "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;");

        Label timeLabel=new Label();
        timeLabel.setStyle( "-fx-font-size: 10;");
        timeLabel.setMinSize( 25,15 );

        timeLabel.setText( LocalTime.now().format( DateTimeFormatter.ofPattern("hh:mm")) );

        ImageView usernIcon=new ImageView( new Image("file:src/main/resources/img/me.png",16,16,false,false) );
        ImageView deleteIcon=new ImageView( new Image("file:src/main/resources/img/me.png",16,16,true,true) );
        setMargin(usernIcon,new Insets( 0,0,4,10 ));
        setMargin(deleteIcon,new Insets( 0,0,4,10 ));

        
        if(!changeOrder) {
            msgBox.setAlignment( Pos.BOTTOM_RIGHT );
            msgBox.getChildren().addAll(deleteIcon, image, timeLabel, usernIcon );
        }
        else {
            msgBox.setAlignment( Pos.BOTTOM_LEFT );
            msgBox.getChildren().addAll(deleteIcon, usernIcon, timeLabel, image );
        }
        return msgBox;
    }

    @FXML
    public void onSendMsgBtnClicked(){

        if(chosenFiles==null)
            chatContainer.getChildren().add(createTextChatBox(messageTextField.getText(),val));
        else {
            for (int i = 0; i < chosenFiles.size(); i++)
                chatContainer.getChildren().add( createImageChatBox( chosenFiles.get( i ).getAbsolutePath(),val ) );
            chosenFiles=null;
        }
        val=(!val);
        messageTextField.clear();
    }

    @FXML
    public void chooseImage(){
            FileChooser fileChooser = new FileChooser();
            setExtFilters(fileChooser);
            fileChooser.setTitle("chose Image");
            chosenFiles  = fileChooser.showOpenMultipleDialog( Stage.getWindows().get(0) );
            String val="";
        for(int i=0;i<chosenFiles.size();i++)
            val+="Img "+(i+1)+" ";
        messageTextField.setText(val );

    }

    private void setExtFilters(FileChooser chooser){
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }


    @FXML
    public void onMoreOptionsClick(){
        FadeTransition fadeTransition=new FadeTransition( Duration.seconds( 0.4 ),ancherPaneOfgridPaneMain);
        System.out.println(ancherPaneOfgridPaneMain.isVisible());
        if(ancherPaneOfgridPaneMain.isVisible()){
            fadeTransition.setFromValue(1 );
            fadeTransition.setToValue(0 );
            emojie00.setOnMouseClicked(null );
            emojie01.setOnMouseClicked(null);
            emojie10.setOnMouseClicked( null );
            emojie02.setOnMouseClicked(null );
            emojie20.setOnMouseClicked(null);
            emojie21.setOnMouseClicked(null);
            emojie22.setOnMouseClicked(null);
            emojie12.setOnMouseClicked(null);
            emojie11.setOnMouseClicked( null);
            fadeTransition.setOnFinished( event -> ancherPaneOfgridPaneMain.setVisible(false));
            fadeTransition.play();
        }
        else if(!ancherPaneOfgridPaneMain.isVisible()) {
            ancherPaneOfgridPaneMain.setVisible( true );
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            emojie00.setOnMouseClicked( event -> chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/angry.png" ,val)));
            emojie01.setOnMouseClicked( event -> chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/in-love.png",val )) );
            emojie10.setOnMouseClicked( event -> chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/angry.png"  ,val)));
            emojie02.setOnMouseClicked( event -> chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/party.png"  ,val)));
            emojie20.setOnMouseClicked( event -> chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/smiling.png" ,val )));
            emojie21.setOnMouseClicked( event -> chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/nerd.png"  ,val)));
            emojie22.setOnMouseClicked( event -> chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/surprised.png" ,val )));
            emojie12.setOnMouseClicked( event -> chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/sad.png" ,val )));
            emojie11.setOnMouseClicked( event -> chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/laugh.png" ,val )));
            fadeTransition.play();
        }

    }

}
