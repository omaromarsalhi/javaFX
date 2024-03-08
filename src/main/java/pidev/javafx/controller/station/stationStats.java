package pidev.javafx.controller.station;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pidev.javafx.model.Transport.Station;
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.transport.DataHolder;
import pidev.javafx.tools.transport.rateStation;

import java.net.URL;
import java.util.ResourceBundle;

public class stationStats implements Initializable {

    private static final String EMPTY_STAR_PATH = "file:src/main/resources/img_transport/star1.png"; // Replace with your empty star image
    private static final String FILLED_STAR_PATH = "file:src/main/resources/img_transport/star2.png"; // Replace with your filled star image
    private int rating;
    private Stage primaryStage;
    rateStation r = new rateStation();
    @FXML
    private Label rateLabel;



    public stationStats() {
        rating = r.getRating( UserController.getInstance().getCurrentUser().getId());
    }

    private ImageView createStar(int value) {
        ImageView star = new ImageView(new Image(EMPTY_STAR_PATH));
        star.setFitWidth(30);
        star.setFitHeight(30);

        star.setOnMouseClicked(event -> {
            rating = value;
            updateStars(true);
        });

        star.setOnMouseEntered(event -> highlightStars(value));

        star.setOnMouseExited(event -> updateStars(false));

        return star;
    }

    private void highlightStars(int value) {
        for (int i = 1; i <= value; i++) {
            ImageView star = (ImageView) root.getChildren().get(i - 1);
            star.setImage(new Image(FILLED_STAR_PATH));
        }
    }

    private void updateStars(boolean a) {
        for (int i = 1; i <= 5; i++) {
            ImageView star = (ImageView) root.getChildren().get(i - 1);
            if (i <= rating) {
                star.setImage(new Image(FILLED_STAR_PATH));
                System.out.println(rating);
            } else {
                star.setImage(new Image(EMPTY_STAR_PATH));
            }
        }
        if (a) {

            r.addItem(rating, UserController.getInstance().getCurrentUser().getId(), DataHolder.getStation().getIdStation());
            rateLabel.setText(Float.toString(r.calculateAverageRatingForStation(DataHolder.getStation().getIdStation())));
            raters.setText(Integer.toString(r.calculateRaters(DataHolder.getStation().getIdStation())));
            busLabel.setText(Integer.toString(r.countDepartureAndArrivalStations(DataHolder.getStation().getIdStation())[0]));
            metroLabel.setText(Integer.toString(r.countDepartureAndArrivalStations(DataHolder.getStation().getIdStation())[1]));

        }

      //  EventBus.getInstance().subscribe("Station", this::first_function);

    }

    @FXML
    private Text raters;
    @FXML
    private HBox root = new HBox(5);
    @FXML
    private Label busLabel;

    @FXML
    private Label metroLabel;
    @FXML
    private Label arrivalsLabel;
    @FXML
    private Label departureLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println(DataHolder.getStation());
        first_function();

        root.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 5; i++) {
            ImageView star = createStar(i);
            root.getChildren().add(star);
        }
        updateStars(false);

    }


    public void first_function( ) {
        DataHolder.getStation() ;
        rateLabel.setText(Float.toString(r.calculateAverageRatingForStation(DataHolder.getStation().getIdStation())));
        raters.setText(Integer.toString(r.calculateRaters(DataHolder.getStation().getIdStation())));
        busLabel.setText(Integer.toString(r.countDepartureAndArrivalStations(DataHolder.getStation().getIdStation())[0]));
        metroLabel.setText(Integer.toString(r.countDepartureAndArrivalStations(DataHolder.getStation().getIdStation())[1]));
        arrivalsLabel.setText(Integer.toString(r.countBusAndMetroStations(DataHolder.getStation().getIdStation())[0]));
        departureLabel.setText(Integer.toString(r.countBusAndMetroStations(DataHolder.getStation().getIdStation())[1]));

    }

}
