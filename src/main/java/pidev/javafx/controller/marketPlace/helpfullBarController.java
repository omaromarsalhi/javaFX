package pidev.javafx.controller.marketPlace;


import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pidev.javafx.crud.marketplace.CrudBien;
import pidev.javafx.crud.marketplace.CrudFavorite;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Categorie;
import pidev.javafx.model.MarketPlace.Favorite;
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.EventBus;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class helpfullBarController implements Initializable {

    @FXML
    private AnchorPane designAnchor;
    @FXML
    private ImageView animationImageView;
    @FXML
    private ChoiceBox<String> categoryChoice;
    @FXML
    private AnchorPane chart1;
    @FXML
    private AnchorPane chart2;
    @FXML
    private VBox filterAnchorPane;
    @FXML
    private DatePicker fromDate;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private Label maxPriceL;
    @FXML
    private Label minPriceL;
    @FXML
    private Label quantityL;
    @FXML
    private StackedBarChart<?, ?> stachBarChart;
    @FXML
    private DatePicker toDate;
    @FXML
    private Slider quantitySlider;
    @FXML
    private Slider minPriceSlider;
    @FXML
    private Slider maxPriceSlider;
    @FXML
    private Button filterOrAddBtn;
    @FXML
    private Label filterTitle;
    @FXML
    private VBox helpfullBarContainer;


    private Timeline fiveSecondsWonder;
    private int imageIndex;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fiveSecondsWonder=new Timeline();
        imageIndex=1;
        getCharts();
//        animateImages();
        filterAnchorPane.setVisible( false );
        EventBus.getInstance().subscribe( "filter",this::showFilter);
        EventBus.getInstance().subscribe( "showFavorite",this::showFavoriteFilter);
    }


    public void showFavoriteFilter(ActionEvent event) {
        helpfullBarContainer.getChildren().remove(designAnchor );
        filterOrAddBtn.setText("Add");
        filterTitle.setText( "Product Criteria" );
        showFilter(event);
    }

    public void showFilter(ActionEvent event){
        String result=CrudBien.getInstance().selectMaxValues4Filter();
        System.out.println(result);
        String[] parts=result.split( "_" );
        filterAnchorPane.setVisible( true );
        minPriceSlider.setMin( 0 );
        minPriceSlider.setMax( Float.parseFloat( parts[0]  ) );
        minPriceSlider.valueProperty().addListener( (observable, oldValue, newValue) -> minPriceL.setText(Float.toString(observable.getValue().intValue() )+" Dt" ));

        maxPriceSlider.setMin( 0 );
        maxPriceSlider.setMax(Float.parseFloat( parts[0]  ) );
        maxPriceSlider.valueProperty().addListener( (observable, oldValue, newValue) -> maxPriceL.setText(Float.toString(observable.getValue().intValue() )+" Dt" ));


        quantitySlider.setMin( 0 );
        quantitySlider.setMax(Float.parseFloat( parts[1]  ) );
        quantitySlider.valueProperty().addListener( (observable, oldValue, newValue) -> quantityL.setText(Integer.toString(observable.getValue().intValue() )));

        categoryChoice.getItems().add("ALL");
        categoryChoice.setValue("ALL");
        for(Categorie cat:Categorie.values())
            categoryChoice.getItems().add(cat.toString());
    }


    @FXML
    public void onFilterClicked(MouseEvent event){
        String fromDateResult = (fromDate.getValue() == null) ? "" : fromDate.getValue().toString();
        String toDateResult = (toDate.getValue() == null) ? "" : toDate.getValue().toString();
        int minPriceSliderResult = (int) minPriceSlider.getValue();
        int maxPriceSliderResult = (int) maxPriceSlider.getValue();
        int quantitySliderResult = (int) quantitySlider.getValue();
        String categoryChoiceResult = categoryChoice.getValue();

        if (minPriceSliderResult == 0)
            minPriceSliderResult = -1;
        if (maxPriceSliderResult == 0)
            maxPriceSliderResult = -1;
        if (quantitySliderResult == 0)
            quantitySliderResult = -1;

        if(filterOrAddBtn.getText().equals("Add" )){
            String specifications="";
            specifications+=(fromDateResult.isEmpty())?LocalDate.now():fromDateResult;
            specifications+="__";
            specifications+=toDateResult;
            specifications+="__";
            specifications+=minPriceSliderResult;
            specifications+="__";
            specifications+=maxPriceSliderResult;
            specifications+="__";
            specifications+=quantitySliderResult;
            specifications+="__";
            specifications+=categoryChoiceResult;

            Favorite favorite=new Favorite(0, UserController.getInstance().getCurrentUser().getId(),specifications);
            CrudFavorite.getInstance().addItem(favorite);
            favorite.setIdFavorite( CrudFavorite.getInstance().selectIdLastItem());
            EventBus.getInstance().publish( "add2Grid",new CustomMouseEvent<>( FXCollections.observableArrayList(favorite)));
        }
        else {
            var list = CrudBien.getInstance().filterItems( fromDateResult, toDateResult, minPriceSliderResult, maxPriceSliderResult, quantitySliderResult, categoryChoiceResult );
            CustomMouseEvent<ObservableList<Bien>> customMouseEvent = new CustomMouseEvent<>( list );
            EventBus.getInstance().publish( "filterProducts", customMouseEvent );
        }
    }


    @FXML
    public void onCancelFilterClicked(MouseEvent event){
        if(filterOrAddBtn.getText().equals("Add" )){
            fromDate.setValue( null );
            toDate.setValue( null );
            minPriceSlider.setValue( 0 );
            maxPriceSlider.setValue( 0 );
            quantitySlider.setValue( 0 );
            categoryChoice.setValue("ALL");
        }
        else
            EventBus.getInstance().publish( "exitFilter",event );
    }


    public void animateImages(){
        animationImageView.setImage( new Image( "file:src/main/resources/newanim/"+imageIndex+".gif",animationImageView.getFitWidth(), animationImageView.getFitHeight(),true,true) );
//        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), animationImageView);
//        rotateTransition.setByAngle(360); // Rotate by 360 degrees
//        rotateTransition.setCycleCount( Animation.INDEFINITE); // Continuous rotation
//        rotateTransition.setInterpolator( Interpolator.LINEAR); // Linear interpolation
//        rotateTransition.play();
//        KeyFrame animateImagesKeyFrame = new KeyFrame( Duration.seconds( 5 ), event -> {
//            animationImageView.setImage( new Image( "file:src/main/resources/newanim/"+imageIndex+".gif",animationImageView.getFitWidth(), animationImageView.getFitHeight(),true,true) );
//            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), animationImageView);
//            rotateTransition.setByAngle(360); // Rotate by 360 degrees
//            rotateTransition.setCycleCount( Animation.INDEFINITE); // Continuous rotation
//            rotateTransition.setInterpolator( Interpolator.LINEAR); // Linear interpolation
//            rotateTransition.play();
//            //            imageIndex++;
////            if(imageIndex>3)
////                imageIndex=1;
//        } );
//
//        fiveSecondsWonder.getKeyFrames().add( animateImagesKeyFrame );
//        fiveSecondsWonder.setCycleCount( Timeline.INDEFINITE );
//        fiveSecondsWonder.play();
    }





    private void getCharts() {
        lineChart.getYAxis().setLabel("Product Number");
        var serie = new XYChart.Series();
        serie.setName("This week");
        serie.getData().add(new XYChart.Data("Mo", 100 ));
        serie.getData().add(new XYChart.Data("Tu", 45 ));
        serie.getData().add(new XYChart.Data("We", 20 ));
        serie.getData().add(new XYChart.Data("Th", 110 ));
        serie.getData().add(new XYChart.Data("Fr", 60 ));
        serie.getData().add(new XYChart.Data("Sa", 66 ));
        serie.getData().add(new XYChart.Data("Su", 13 ));

        lineChart.getData().addAll(serie);
        lineChart.getStylesheets().add( String.valueOf( getClass().getResource( "/style/marketPlace/lineChartStyle.css" ) ) );


        stachBarChart.getYAxis().setLabel("value in $");
        var selled = new XYChart.Series();
        var purshased = new XYChart.Series();
        var traded = new XYChart.Series();

        selled.setName("Selled");
        selled.getData().add(new XYChart.Data("Mo", 100 ));
        selled.getData().add(new XYChart.Data("Tu", 45 ));
        selled.getData().add(new XYChart.Data("We", 20 ));
        selled.getData().add(new XYChart.Data("Th", 110 ));
        selled.getData().add(new XYChart.Data("Fr", 60 ));
        selled.getData().add(new XYChart.Data("Sa", 66 ));
        selled.getData().add(new XYChart.Data("Su", 13 ));

        purshased.setName("Purshased");
        purshased.getData().add(new XYChart.Data("Mo", 10 ));
        purshased.getData().add(new XYChart.Data("Tu", 80 ));
        purshased.getData().add(new XYChart.Data("We", 70 ));
        purshased.getData().add(new XYChart.Data("Th", 31 ));
        purshased.getData().add(new XYChart.Data("Fr", 16 ));
        purshased.getData().add(new XYChart.Data("Sa", 120 ));
        purshased.getData().add(new XYChart.Data("Su", 95 ));

        traded.setName("Traded");
        traded.getData().add(new XYChart.Data("Mo", 59 ));
        traded.getData().add(new XYChart.Data("Tu", 45 ));
        traded.getData().add(new XYChart.Data("We", 73 ));
        traded.getData().add(new XYChart.Data("Th", 110 ));
        traded.getData().add(new XYChart.Data("Fr", 120 ));
        traded.getData().add(new XYChart.Data("Sa", 88 ));
        traded.getData().add(new XYChart.Data("Su", 14 ));


        stachBarChart.getData().addAll(selled,purshased,traded);
        stachBarChart.getStylesheets().add( String.valueOf( getClass().getResource( "/style/marketPlace/lineChartStyle.css" ) ) );

    }


}
