package pidev.javafx.tools.marketPlace;

import javafx.scene.layout.AnchorPane;
import pidev.javafx.controller.marketPlace.ItemController;

public class CustomReturnItem {

    private final AnchorPane first;
    private final ItemController second;

    public CustomReturnItem(AnchorPane first, ItemController second) {
        this.first = first;
        this.second = second;
    }


    public AnchorPane getFirst() {
        return first;
    }

    public ItemController getSecond() {
        return second;
    }
}
