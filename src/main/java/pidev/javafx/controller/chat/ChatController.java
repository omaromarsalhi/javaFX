package pidev.javafx.controller.chat;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import pidev.javafx.tools.UserController;
import pidev.javafx.crud.marketplace.CrudChat;
import pidev.javafx.model.chat.Chat;
import pidev.javafx.model.user.User;
import pidev.javafx.tools.marketPlace.ChatClient;
import pidev.javafx.tools.marketPlace.MyTools;
import pidev.javafx.tools.marketPlace.ResultHolder;

import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
    @FXML
    private Button searchBtn;
    @FXML
    private HBox searchHbox;
    @FXML
    private TextField searchTextField;
    @FXML
    private VBox usersBox;
    @FXML
    private ScrollPane scroll;
    @FXML
    private ImageView exitBtn;
    @FXML
    private ImageView userImage;
    @FXML
    private Label userName;
    @FXML
    private ImageView connState;
    @FXML
    private HBox container;



    private  boolean amIAReciver;
    private List<File>  chosenFiles;
    private Timer animTimer;
    private User reciver;
    private ResultHolder resultHolder=new ResultHolder();
    private boolean isConnected;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        chosenFiles=null;
        ancherPaneOfgridPaneMain.setVisible( false );
        amIAReciver=false;
        resultHolder.setResult( "false" );

        searchBtn.setStyle( "-fx-border-radius: 0 10 10 0;" +
                "-fx-border-color: transparent ;");

        searchTextField.setStyle( "-fx-border-radius: 10 0 0 10;" +
                "-fx-border-color: transparent ;");



        chatContainer.heightProperty().addListener( new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scroll.setVvalue( (Double)newValue );
            }
        } );
    }

    public void initliazeData(){
        ChatClient.getInstance().establishConnection();
        animTimer = new Timer();
        ChatClient.getInstance().reciveMessagesFromOtherUser(chatContainer,resultHolder);
    }


    @FXML
    public void onSendMsgBtnClicked(){
        ChatClient.getInstance().isUserConnected(reciver.getId());
        try {
            isConnected=Boolean.parseBoolean(resultHolder.getResult());
        } catch (InterruptedException e) {
            throw new RuntimeException( e );
        }

        if(chosenFiles==null) {
            if(isConnected)
                ChatClient.getInstance().sendMessages( "@" + reciver.getId() + "_" + messageTextField.getText() );
            chatContainer.getChildren().add( createTextChatBox( messageTextField.getText(), false,"" ) );
            CrudChat.getInstance().addItem( new Chat( 0, UserController.getInstance().getCurrentUser(), reciver, messageTextField.getText(),isConnected,null) );
            resultHolder.setResult( null );
        }
        else {
            for (int i = 0; i < chosenFiles.size(); i++) {
                String imgPath= MyTools.getInstance().getPathAndSaveIMG(chosenFiles.get( i ).getAbsolutePath());
                if(isConnected)
                    ChatClient.getInstance().sendMessages( "@" + reciver.getId() + "_" + imgPath );
                chatContainer.getChildren().add( createImageChatBox(imgPath , false, "") );
                CrudChat.getInstance().addItem( new Chat( 0, UserController.getInstance().getCurrentUser(), reciver, imgPath,isConnected,null) );
            }
            chosenFiles=null;
        }
        scroll.setVvalue( 1 );
        messageTextField.clear();
    }


    public HBox createUserForAdd(User user){
        HBox hBox=new HBox();
        hBox.setMinHeight( 40 );

        Label notif=new Label("+12");
        notif.setFont(Font.font( "System", FontWeight.BOLD, FontPosture.ITALIC,14 ));
        notif.setStyle( "-fx-font-size: 16;");
        notif.setAlignment( Pos.CENTER );
        notif.setMinSize(28,24);
        notif.setStyle( "-fx-text-fill: red;" +
                "-fx-border-radius: 50;" +
                "-fx-background-radius: 50");

        ImageView userImage=new ImageView(new Image( "file:src/main/resources/"+user.getPhotos() ,32,32,true,true));
        Label userName=new Label(user.getFirstname()+" "+user.getLastname());
        userName.setFont(Font.font( "System", FontWeight.LIGHT, FontPosture.ITALIC,14 ));
        userName.setMinWidth( 80 );

        Button deleteBtn= new Button();
        deleteBtn.setGraphic(new ImageView(new Image( "file:src/main/resources/namedIcons/delete2.png",16,16,true,true ))  );         ;
        deleteBtn.setStyle("-fx-background-color: tarnsparent");
        deleteBtn.getStylesheets().add( String.valueOf( getClass().getResource( "/style/marketPlace/Buttons.css" ) ) );
        hBox.setStyle( "-fx-background-color:  #ced4da;"+
                "-fx-background-radius : 10;"  );
        hBox.setAlignment( Pos.CENTER_LEFT );
        hBox.setSpacing(6);
        hBox.setPadding( new Insets( 0,0,0,10 ) );
        hBox.getChildren().addAll(notif,userImage,userName,deleteBtn );

        hBox.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue)
                hBox.setStyle( "-fx-background-color: #fdc847;"+
                        "-fx-background-radius : 10;"  );
            else
                hBox.setStyle( "-fx-background-color: #ced4da;" +
                        "-fx-background-radius : 10;");
        });

        hBox.setOnMouseClicked( event -> setSelectedUserData(user));
        return hBox;
    }


    public void loadUsers(ObservableList<User> users){
        for(int i=0;i<users.size();i++)
            if(users.get( i ).getId()!=UserController.getInstance().getCurrentUser().getId())
                usersBox.getChildren().add(createUserForAdd(users.get( i )));
        setSelectedUserData(users.get( 0 ));
    }

    public void setUserToChatWith(User user){
        if(user.getId()!=UserController.getInstance().getCurrentUser().getId())
            setSelectedUserData(user);
    }


    public void setSelectedUserData(User user){
        this.reciver=user;
        userImage.setImage(new Image( "file:src/main/resources/"+user.getPhotos() ,46,46,true,true)) ;
        userName.setText( user.getFirstname().toUpperCase()+" "+user.getLastname().toUpperCase() );
        userName.setMinHeight( Region.USE_PREF_SIZE);
        connState.setImage(new Image( "file:src/main/resources/namedIcons/button.png" ,12,12,true,true));
        rettriveConversation();
    }


    public void rettriveConversation(){
        LocalDate date=LocalDate.of(0, 1, 1);
        for(Chat chat:CrudChat.getInstance().selectItems(reciver.getId())){
            if(chat.getMessage().startsWith( "/usersImg/" ))
                chatContainer.getChildren().add( createImageChatBox(chat.getMessage(),UserController.getInstance().getCurrentUser().getId()!=chat.getUserSender().getId(),chat.getTimestamp().toLocalDateTime().format( DateTimeFormatter.ofPattern("hh:mm"))) );
            else
                chatContainer.getChildren().add( createTextChatBox(chat.getMessage(),UserController.getInstance().getCurrentUser().getId()!=chat.getUserSender().getId(),chat.getTimestamp().toLocalDateTime().format( DateTimeFormatter.ofPattern("hh:mm"))) );
            LocalDate firstDate = LocalDate.parse( chat.getTimestamp().toLocalDateTime().format( DateTimeFormatter.ofPattern("yyyy-MM-dd")) ); // Your first timestamp
            if(firstDate.isBefore( LocalDate.now() )&&firstDate.isAfter( date )) {
                Label label=new Label(firstDate.toString());
                label.setMinHeight(20);
                chatContainer.getChildren().add(label);
                date=firstDate;
            }
        }
    }



    public static HBox createTextChatBox(String text,boolean changeOrder,String time){
        HBox msgBox=new HBox();

        msgBox.setSpacing( 6 );


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

        timeLabel.setText( (time.isEmpty())?LocalTime.now().format( DateTimeFormatter.ofPattern("hh:mm")):time );

        ImageView usernIcon=new ImageView( new Image("file:src/main/resources/img/me.png",16,16,false,false) );
//        ImageView deleteIcon=new ImageView( new Image("file:src/main/resources/namedIcons/miniTrash.png",16,16,true,true) );
        setMargin(usernIcon,new Insets( 0,0,2,0 ));
//        setMargin(deleteIcon,new Insets( 0,0,msgLabel.getPrefHeight()/2,0 ));
        if(!changeOrder) {
            msgBox.setAlignment( Pos.BOTTOM_RIGHT );
            msgBox.getChildren().addAll( msgLabel, timeLabel, usernIcon );
        }
        else {
            msgBox.setAlignment( Pos.BOTTOM_LEFT );
            msgBox.getChildren().addAll( usernIcon, timeLabel, msgLabel );
        }
        return msgBox;
    }


    public static HBox createImageChatBox(String path,boolean changeOrder,String time){
        HBox msgBox=new HBox();
        msgBox.setMaxHeight( 100 );
//        msgBox.setPrefWidth( chatContainer.getPrefWidth());
//        msgBox.setStyle( "-fx-background-color: red" );
        msgBox.setAlignment( Pos.BOTTOM_RIGHT );
//        msgBox.setPadding( new Insets( 0,0,0,10 ) );
        msgBox.setSpacing( 4 );

        ImageView image=new ImageView( new Image("file:src/main/resources"+path,80,100,true,true) );
//        msgLabel.setStyle( "-fx-background-color: blue" );
        image.setStyle( "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;");

        Label timeLabel=new Label();
        timeLabel.setStyle( "-fx-font-size: 10;");
        timeLabel.setMinSize( 25,15 );

        timeLabel.setText( (time.isEmpty())?LocalTime.now().format( DateTimeFormatter.ofPattern("hh:mm")):time );

        ImageView usernIcon=new ImageView( new Image("file:src/main/resources/img/me.png",16,16,false,false) );
        ImageView deleteIcon=new ImageView( new Image("file:src/main/resources/img/me.png",16,16,true,true) );
        setMargin(usernIcon,new Insets( 0,0,4,2 ));
//        setMargin(deleteIcon,new Insets( 0,0,4,10 ));


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
    public void chooseImage(){
            FileChooser fileChooser = new FileChooser();
            setExtFilters(fileChooser);
            fileChooser.setTitle("chose Image");
            chosenFiles  = fileChooser.showOpenMultipleDialog( Stage.getWindows().get(0) );
            String val="";
            if(chosenFiles!=null) {
                for (int i = 0; i < chosenFiles.size(); i++)
                    val += "Img " + (i + 1) + " ";
                messageTextField.setText( val );
            }
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
//            emojie00.setOnMouseClicked( event -> {
//                chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/angry.png" ,amIAReciver));
//                scroll.setVvalue( 1 );
//            });
//            emojie01.setOnMouseClicked( event -> {
//                chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/in-love.png",amIAReciver ));
//                scroll.setVvalue( 1 );
//            } );
//            emojie10.setOnMouseClicked( event -> {
//                chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/angry.png"  ,amIAReciver));
//                scroll.setVvalue( 1 );
//            });
//            emojie02.setOnMouseClicked( event -> {
//                chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/party.png"  ,amIAReciver));
//                scroll.setVvalue( 1 );
//            });
//            emojie20.setOnMouseClicked( event -> {
//                chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/smiling.png" ,amIAReciver ));
//                scroll.setVvalue( 1 );
//            });
//            emojie21.setOnMouseClicked( event -> {
//                chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/nerd.png"  ,amIAReciver));
//                scroll.setVvalue( 1 );
//            });
//            emojie22.setOnMouseClicked( event -> {
//                chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/surprised.png" ,amIAReciver ));
//                scroll.setVvalue( 1 );
//            });
//            emojie12.setOnMouseClicked( event -> {
//                chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/sad.png" ,amIAReciver ));
//                scroll.setVvalue( 1 );
//            });
//            emojie11.setOnMouseClicked( event -> {
//                chatContainer.getChildren().add(createImageChatBox( "file:src/main/resources/emojies/laugh.png" ,amIAReciver ));
//                scroll.setVvalue( 1 );
//            });
            fadeTransition.play();
        }

    }


    public ImageView getExitBtn() {
        return exitBtn;
    }


    public HBox getContainer() {
        return container;
    }



}
