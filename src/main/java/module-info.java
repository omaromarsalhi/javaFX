module pidev.javafx{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive java.desktop;
    requires itextpdf;


    exports pidev.javafx.controller;
    opens pidev.javafx.controller to javafx.fxml;
    opens pidev.javafx.controller.marketPlace to javafx.fxml;
    exports pidev.javafx.model.MarketPlace;
    opens pidev.javafx.model.MarketPlace to javafx.fxml;
    exports pidev.javafx.controller.marketPlace;
    opens pidev.javafx.controller.userMarketDashbord to javafx.fxml;
    exports pidev.javafx.crud;
    opens pidev.javafx.crud to javafx.fxml;
    exports pidev.javafx.tools;
    opens pidev.javafx.tools to javafx.fxml;
    exports pidev.javafx.controller.contrat;
    opens pidev.javafx.controller.contrat to javafx.fxml;
    exports pidev.javafx.controller.userMarketDashbord;
    opens pidev.javafx.controller.chat to javafx.fxml;
    exports pidev.javafx.controller.chat;
    exports pidev.javafx.test;
    opens pidev.javafx.test to javafx.fxml;
}

