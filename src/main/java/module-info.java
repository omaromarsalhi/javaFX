module pidev.javafx{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens pidev.javafx to javafx.fxml;
    exports pidev.javafx;
    exports pidev.javafx.Controller;
    opens pidev.javafx.Controller to javafx.fxml;
    opens pidev.javafx.Controller.MarketPlace to javafx.fxml;
    exports pidev.javafx.Model.MarketPlace;
    opens pidev.javafx.Model.MarketPlace to javafx.fxml;
}

