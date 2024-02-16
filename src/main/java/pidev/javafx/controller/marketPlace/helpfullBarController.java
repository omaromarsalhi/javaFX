//package pidev.javafx.controller.marketPlace;
//
//
//import javafx.animation.*;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.StackedBarChart;
//import javafx.scene.chart.XYChart;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.VBox;
////import pidev.javafx.crud.marketplace.CrudBien;
//import pidev.javafx.model.MarketPlace.Bien;
//import pidev.javafx.model.MarketPlace.Categorie;
//import pidev.javafx.tools.CustomMouseEvent;
//import pidev.javafx.tools.EventBus;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class helpfullBarController implements Initializable {
//
//    @FXML
//    private AnchorPane animationAnchor;
//    @FXML
//    private ImageView animationImageView;
//    @FXML
//    private ChoiceBox<String> categoryChoice;
//    @FXML
//    private AnchorPane chart1;
//    @FXML
//    private AnchorPane chart2;
//    @FXML
//    private VBox filterAnchorPane;
//    @FXML
//    private DatePicker fromDate;
//    @FXML
//    private LineChart<?, ?> lineChart;
//    @FXML
//    private Label maxPriceL;
//    @FXML
//    private Label minPriceL;
//    @FXML
//    private Label quantityL;
//    @FXML
//    private StackedBarChart<?, ?> stachBarChart;
//    @FXML
//    private DatePicker toDate;
//    @FXML
//    private Slider quantitySlider;
//    @FXML
//    private Slider minPriceSlider;
//    @FXML
//    private Slider maxPriceSlider;
//
//
//    private Timeline fiveSecondsWonder;
//    private int imageIndex;
//
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        fiveSecondsWonder=new Timeline();
//        imageIndex=1;
//        getCharts();
////        animateImages();
//        filterAnchorPane.setVisible( false );
//        EventBus.getInstance().subscribe( "filter",this::showFilter);
//
//    }
//
//
//    public void showFilter(ActionEvent event){
//        filterAnchorPane.setVisible( true );
//        minPriceSlider.setMin( 0 );
//        minPriceSlider.setMax(2000 );
//        minPriceSlider.valueProperty().addListener( (observable, oldValue, newValue) -> minPriceL.setText(Integer.toString(observable.getValue().intValue() )+" Dt" ));
//
//        maxPriceSlider.setMin( 0 );
//        maxPriceSlider.setMax(2000 );
//        maxPriceSlider.valueProperty().addListener( (observable, oldValue, newValue) -> maxPriceL.setText(Integer.toString(observable.getValue().intValue() )+" Dt" ));
//
//
//        quantitySlider.setMin( 0 );
//        quantitySlider.setMax(20 );
//        quantitySlider.valueProperty().addListener( (observable, oldValue, newValue) -> quantityL.setText(Integer.toString(observable.getValue().intValue() )));
//
//        categoryChoice.getItems().add("ALL");
//        categoryChoice.setValue("ALL");
//        for(Categorie cat:Categorie.values())
//            categoryChoice.getItems().add(cat.toString());
//    }
//    @FXML
//    public void onFilterClicked(MouseEvent event){
//        String fromDateResult=(fromDate.getValue()==null)?"":fromDate.getValue().toString();
//        String toDateResult=(toDate.getValue()==null)?"":toDate.getValue().toString();
//        int minPriceSliderResult=(int)minPriceSlider.getValue();
//        int maxPriceSliderResult=(int)maxPriceSlider.getValue();
//        int quantitySliderResult=(int)quantitySlider.getValue();
//        String categoryChoiceResult=categoryChoice.getValue();
//
//        if(minPriceSliderResult==0)
//            minPriceSliderResult=-1;
//        if(maxPriceSliderResult==0)
//            maxPriceSliderResult=-1;
//        if(quantitySliderResult==0)
//            quantitySliderResult=-1;
//
//        var list= CrudBien.getInstance().filterItems(fromDateResult,toDateResult, minPriceSliderResult,maxPriceSliderResult,quantitySliderResult,categoryChoiceResult );
//        CustomMouseEvent<ObservableList<Bien>>  customMouseEvent=new CustomMouseEvent<>(list);
//        EventBus.getInstance().publish( "filterProducts",customMouseEvent);
//        System.out.println(list.size());
//    }
//
//
//    public void animateImages(){
//        animationImageView.setImage( new Image( "file:src/main/resources/newanim/"+imageIndex+".gif",animationImageView.getFitWidth(), animationImageView.getFitHeight(),true,true) );
//
////        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), animationImageView);
////        rotateTransition.setByAngle(360); // Rotate by 360 degrees
////        rotateTransition.setCycleCount( Animation.INDEFINITE); // Continuous rotation
////        rotateTransition.setInterpolator( Interpolator.LINEAR); // Linear interpolation
////        rotateTransition.play();
////        KeyFrame animateImagesKeyFrame = new KeyFrame( Duration.seconds( 5 ), event -> {
////            animationImageView.setImage( new Image( "file:src/main/resources/newanim/"+imageIndex+".gif",animationImageView.getFitWidth(), animationImageView.getFitHeight(),true,true) );
////            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), animationImageView);
////            rotateTransition.setByAngle(360); // Rotate by 360 degrees
////            rotateTransition.setCycleCount( Animation.INDEFINITE); // Continuous rotation
////            rotateTransition.setInterpolator( Interpolator.LINEAR); // Linear interpolation
////            rotateTransition.play();
////            //            imageIndex++;
//////            if(imageIndex>3)
//////                imageIndex=1;
////        } );
////
////        fiveSecondsWonder.getKeyFrames().add( animateImagesKeyFrame );
////        fiveSecondsWonder.setCycleCount( Timeline.INDEFINITE );
////        fiveSecondsWonder.play();
//    }
//
//
//
//
//
//    private void getCharts() {
//        lineChart.getYAxis().setLabel("Product Number");
//        var serie = new XYChart.Series();
//        serie.setName("This week");
//        serie.getData().add(new XYChart.Data("Mo", 100 ));
//        serie.getData().add(new XYChart.Data("Tu", 45 ));
//        serie.getData().add(new XYChart.Data("We", 20 ));
//        serie.getData().add(new XYChart.Data("Th", 110 ));
//        serie.getData().add(new XYChart.Data("Fr", 60 ));
//        serie.getData().add(new XYChart.Data("Sa", 66 ));
//        serie.getData().add(new XYChart.Data("Su", 13 ));
//
//        lineChart.getData().addAll(serie);
//        lineChart.getStylesheets().add( String.valueOf( getClass().getResource("/style/lineChartStyle.css") ) );
//
//
//        stachBarChart.getYAxis().setLabel("value in $");
//        var selled = new XYChart.Series();
//        var purshased = new XYChart.Series();
//        var traded = new XYChart.Series();
//
//        selled.setName("Selled");
//        selled.getData().add(new XYChart.Data("Mo", 100 ));
//        selled.getData().add(new XYChart.Data("Tu", 45 ));
//        selled.getData().add(new XYChart.Data("We", 20 ));
//        selled.getData().add(new XYChart.Data("Th", 110 ));
//        selled.getData().add(new XYChart.Data("Fr", 60 ));
//        selled.getData().add(new XYChart.Data("Sa", 66 ));
//        selled.getData().add(new XYChart.Data("Su", 13 ));
//
//        purshased.setName("Purshased");
//        purshased.getData().add(new XYChart.Data("Mo", 10 ));
//        purshased.getData().add(new XYChart.Data("Tu", 80 ));
//        purshased.getData().add(new XYChart.Data("We", 70 ));
//        purshased.getData().add(new XYChart.Data("Th", 31 ));
//        purshased.getData().add(new XYChart.Data("Fr", 16 ));
//        purshased.getData().add(new XYChart.Data("Sa", 120 ));
//        purshased.getData().add(new XYChart.Data("Su", 95 ));
//
//        traded.setName("Traded");
//        traded.getData().add(new XYChart.Data("Mo", 59 ));
//        traded.getData().add(new XYChart.Data("Tu", 45 ));
//        traded.getData().add(new XYChart.Data("We", 73 ));
//        traded.getData().add(new XYChart.Data("Th", 110 ));
//        traded.getData().add(new XYChart.Data("Fr", 120 ));
//        traded.getData().add(new XYChart.Data("Sa", 88 ));
//        traded.getData().add(new XYChart.Data("Su", 14 ));
//
//
//        stachBarChart.getData().addAll(selled,purshased,traded);
//        stachBarChart.getStylesheets().add( String.valueOf( getClass().getResource("/style/lineChartStyle.css") ) );
//
//    }
//
//
//}
