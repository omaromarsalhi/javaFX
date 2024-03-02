package pidev.javafx.controller.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pidev.javafx.controller.blog.BlogController;
import pidev.javafx.model.blog.Post;
import pidev.javafx.tools.UserController;
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
    private AnchorPane blogSection;
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
    private VBox formContainer;
    @FXML
    private VBox firstinterface;
    @FXML
    private AnchorPane secondInterface;



    BlogController blogController;
    List<Post> posts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        secondInterface.setVisible( false );
        AnchorPane blog = null;
//        AnchorPane account = null;
        HBox reclamations = null;
        try {
            blog = FXMLLoader.load( getClass().getResource( "/fxml/blog/blog.fxml" )  );
//            account = FXMLLoader.load( getClass().getResource( "/fxml/blog/blog.fxml" )  );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        blogSection.getChildren().add(blog);
        for (int i=0;i<10;i++){
            try {
                reclamations = FXMLLoader.load( getClass().getResource( "/fxml/reclamation/reclamation.fxml" )  );
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


        editDetails.setOnAction( event -> showFormUser("editDetails") );
        showDetails.setOnAction( event -> showFormUser("showDetails") );

        menuBar.getMenus().get( 3 ).getItems().addAll(editDetails,showDetails);
    }


    public void onExitFormBtnClicked(MouseEvent event){
        firstinterface.setOpacity( 1);
        secondInterface.setVisible( false );
        formContainer.getChildren().clear();
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
            formContainer.getChildren().add(form);
            MyTools.getInstance().showAnimation( form );
    }


}
