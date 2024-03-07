package pidev.javafx.controller.reclamation;

import com.google.api.client.testing.util.MockSleeper;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import pidev.javafx.controller.contrat.CheckOutController;
import pidev.javafx.crud.marketplace.CrudBien;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Product;
import pidev.javafx.model.reclamation.Reclamation;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.CustomReturnItem;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.marketPlace.MyTools;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;

public class Menu implements Initializable {
        @FXML
        private GridPane grid;
        @FXML
        private ScrollPane scroll;
        @FXML
        private HBox mainHbox;
        @FXML
        private Button searchBtn;
        @FXML
        private HBox searchHbox;
        @FXML
        private TextField searchTextField;
        @FXML
        private MenuBar menuBar;
        @FXML
        private AnchorPane secondInterface;
        @FXML
        private HBox bigContainer;
        @FXML
        private StackPane marketStackPane;



        private VBox itemInfo;
        private VBox hepfullBar;
        private VBox chatBox;
        private Timer animTimer;
        private Image image;
        private String searchBarState;
        private int idProd4nextSelection;
        private int currentNbrColumns;





        @Override
        public void initialize(URL location, ResourceBundle resources) {


            setMenueBar();

            searchBarState="closed";
            animTimer = new Timer();
            searchTextField.setVisible( false );
            searchBtn.setStyle( "-fx-border-radius: 20;" +
                    "-fx-background-radius:20;" );

            searchBtn.setOnMouseClicked(event ->{
                if(searchBarState.equals("opened")&&!searchTextField.getText().isEmpty())
                    ;
                else
                    animateSearchBar();
            } );



            showGridPane( FXCollections.observableArrayList(ServiceReclamation.getInstance().getAll())  );
        }



    public void showGridPane(ObservableList<Reclamation> biens){
        grid.getChildren().clear();
        grid.setHgap( 20 );
        grid.setVgap( 20 );
        int column = 0;
        int row = 1;
        for (int i = 0; i < biens.size() ; i++) {
            if (column == 3) {
                column = 0;
                row++;
            }
            HBox reclamations = null;
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/reclamation/reclamation.fxml"));
            try {
                reclamations = fxmlLoader.load( );
                ReclamationBoxController reclamationBoxController=fxmlLoader.getController();
                reclamationBoxController.setData( biens.get( i ) );
            } catch (IOException e) {
                throw new RuntimeException( e );
            }
            grid.add( reclamations,column++,row);
        }
    }



        public void setMenueBar(){
            var allProducts=new MenuItem("All Products",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/more.png" ))));
            var todayProducts=new MenuItem("Today's Products",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/database.png" ))));




            menuBar.getMenus().get(0).getItems().addAll(allProducts,todayProducts);

            var filterProd=new MenuItem("Product",new ImageView(new Image(getClass().getResourceAsStream( "/icons/marketPlace/database.png" ))));
            menuBar.getMenus().get( 1 ).getItems().addAll(filterProd);



        }

        public void animateSearchBar(){
            if(searchBarState.equals( "closed" )){
                searchBarState="opened";
                animTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if(searchTextField.getWidth()==16){
                            searchBtn.setStyle( "-fx-border-radius: 0 20 20 0;" +
                                    "-fx-border-color: black  black black transparent ;");
                            searchTextField.setVisible( true );
                        }
                        if (searchTextField.getWidth()<(searchHbox.getWidth()-searchBtn.getWidth()-20)) {
                            searchTextField.setPrefWidth(searchTextField.getWidth()+10);
                        } else
                            this.cancel();
                    }

                }, 0, 15);
            }
            else if(searchBarState.equals( "opened" )&&searchTextField.getText().isEmpty()){
                searchBarState="closed";
                animTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (searchTextField.getWidth() <=16) {
                            searchBtn.setStyle( "-fx-border-radius: 20;" +
                                    "-fx-background-radius:20;");
                            searchTextField.setVisible( false );
                        }
                        if (searchTextField.getWidth() > 16) {
                            searchTextField.setPrefWidth( searchTextField.getWidth() - 10 );
                        } else
                            this.cancel();
                    }
                }, 0, 15);
            }
        }



















}
