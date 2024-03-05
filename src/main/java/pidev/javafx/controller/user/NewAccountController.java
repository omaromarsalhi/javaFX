package pidev.javafx.controller.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
//import pidev.javafx.controller.blog.BlogController;
import pidev.javafx.controller.reclamation.ReclamationBoxController;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.blog.Post;
import pidev.javafx.model.reclamation.Reclamation;
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.marketPlace.MyTools;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class NewAccountController implements Initializable {


    @FXML
    private AnchorPane accountSection;
    @FXML
    private VBox blogSection;
    @FXML
    private MenuBar menuBar;
    @FXML
    private VBox reclamsSection;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Button searchBtn;
    @FXML
    private HBox searchHbox;
    @FXML
    private ImageView searchInfoBtn;
    @FXML
    private TextField searchTextField;
    @FXML
    private HBox toolBar;
    @FXML
    private HBox ultraBigContainer;
    @FXML
    private VBox firstinterface;
    @FXML
    private VBox secondInterface;



    //BlogController blogController;
    //List<Post> posts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        secondInterface.setVisible( false );
        VBox blog = null;
//        AnchorPane account = null;
        HBox reclamations = null;
        try {
            blog = FXMLLoader.load( getClass().getResource( "/fxml/blog/resizedBlog.fxml" )  );
            scroll.removeEventFilter(MouseEvent.MOUSE_PRESSED, MouseEvent::consume);
            EventBus.getInstance().publish( "loadPosts",new CustomMouseEvent<>("/fxml/blog/resizedPost.fxml" ) );

        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        blogSection.getChildren().add(blog);
        for (Reclamation reclamationData: ServiceReclamation.getInstance().getAll()){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/reclamation/reclamation.fxml"));
            try {
                reclamations = fxmlLoader.load( );
                ReclamationBoxController reclamationBoxController=fxmlLoader.getController();
                reclamationBoxController.setData( reclamationData );
            } catch (IOException e) {
                throw new RuntimeException( e );
            }
            reclamsSection.getChildren().add(reclamations);
        }
        setMenueBar();


        EventBus.getInstance().subscribe( "exitFormUser",this::onExitFormBtnClicked );
    }


    public void setMenueBar(){
        var editDetails=new MenuItem("Edit My Details",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/more.png" ))));
        var showDetails=new MenuItem("Show My Details",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/more.png" ))));


        Menu advancedSettings = new Menu("Advanced Settings",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/more.png" ))));

        // Create submenu for "File" menu
        Menu updateCredentials= new Menu("Update Credentials");
        Menu account = new Menu("Account");

        MenuItem updatePassword = new MenuItem("Update Password");
        MenuItem updateEmail = new MenuItem("Update Email");
        updateCredentials.getItems().addAll(updatePassword, updateEmail);

        MenuItem disconnect = new MenuItem("Disconnect");
        MenuItem deleteAccount = new MenuItem("Delete Account");
        account.getItems().addAll(disconnect, deleteAccount);

        // Add submenus to "File" menu
        advancedSettings.getItems().addAll(updateCredentials,account);

        editDetails.setOnAction( event -> showFormUser("editDetails") );
        showDetails.setOnAction( event -> showFormUser("showDetails") );

        updatePassword.setOnAction( event -> showFormadvancedSettings("updatePassword") );
        updateEmail.setOnAction( event -> showFormadvancedSettings("updateEmail") );

        menuBar.getMenus().get( 3 ).getItems().addAll(editDetails,showDetails,advancedSettings);


        var addReclamation=new MenuItem("Add Reclamation",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/more.png" ))));

        addReclamation.setOnAction( event -> showFormReclamation() );

        menuBar.getMenus().get( 0 ).getItems().addAll(addReclamation);

//        editDetails.fire();
    }


    public void onExitFormBtnClicked(MouseEvent event){
        firstinterface.setOpacity( 1);
        secondInterface.setVisible( false );
        secondInterface.getChildren().clear();
    }

    public void showFormUser(String usage){
        StackPane form=null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/user/form.fxml" ));
        try {
            form = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        UserFormController userFormController=fxmlLoader.getController();
        userFormController.setUsageOfThisForm( usage );
        userFormController.setDataUser( UserController.getInstance().getCurrentUser() );
        firstinterface.setOpacity( 0.4 );
        secondInterface.setVisible( true );
        secondInterface.getChildren().add(form);
        MyTools.getInstance().showAnimation( form );
    }

    public void showFormReclamation(){
        StackPane form=null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/reclamation/reclamationForm.fxml" ));
        try {
            form = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }

        firstinterface.setOpacity( 0.4 );
        secondInterface.setVisible( true );
        secondInterface.getChildren().add(form);
        MyTools.getInstance().showAnimation( form );
    }


    public void showFormadvancedSettings(String usage)
    {
        StackPane form=null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/user/advancedSettings.fxml" ));
        try {
            form = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        AdvancedSettingsController advancedSettingsController=fxmlLoader.getController();
        advancedSettingsController.setUsageOfThisForm(usage);
        advancedSettingsController.setData( UserController.getInstance().getCurrentUser() );
        blogSection.getChildren().clear();
        blogSection.getChildren().add(form);
        MyTools.getInstance().showAnimation( form );
    }


}