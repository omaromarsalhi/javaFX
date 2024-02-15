package pidev.javafx.controller.marketPlace;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class helpfullBarController implements Initializable {
    @FXML
    private LineChart<?,?> lineChart;
    @FXML
    private StackedBarChart<?, ?> stachBarChart;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getCharts();
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
        lineChart.getStylesheets().add( String.valueOf( getClass().getResource("/style/lineChartStyle.css") ) );


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
        stachBarChart.getStylesheets().add( String.valueOf( getClass().getResource("/style/lineChartStyle.css") ) );

    }


}
