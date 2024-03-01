module pidev.javafx{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive java.desktop;
    requires itextpdf;
    //change 1
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires aspose.imaging;
    requires javafx.swing;
    requires org.apache.pdfbox;
    opens pidev.javafx.model.reclamation to javafx.base;

    opens pidev.javafx.controller.ticket ;
    exports pidev.javafx.controller.ticket to javafx.fxml;

    opens pidev.javafx.controller.reclamation;
    exports pidev.javafx.controller.reclamation to javafx.fxml;

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
    exports pidev.javafx.controller.mainWindow;
    opens pidev.javafx.controller.mainWindow to javafx.fxml;
    exports pidev.javafx.crud.marketplace;
    opens pidev.javafx.crud.marketplace to javafx.fxml;
    exports pidev.javafx.controller.reponse to javafx.fxml;
    opens pidev.javafx.controller.reponse;
}

