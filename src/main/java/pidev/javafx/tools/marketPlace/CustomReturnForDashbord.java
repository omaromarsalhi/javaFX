package pidev.javafx.tools.marketPlace;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import pidev.javafx.controller.marketPlace.ItemController;
import pidev.javafx.controller.userMarketDashbord.ProductsHboxController;

public class CustomReturnForDashbord {
    private final HBox first;
    private final ProductsHboxController second;


    public CustomReturnForDashbord(HBox first, ProductsHboxController second) {
        this.first = first;
        this.second = second;
    }


    public HBox getFirst() {
        return first;
    }

    public ProductsHboxController getSecond() {
        return second;
    }
}
