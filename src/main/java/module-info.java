module pidev.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive java.desktop;

    // PDF libraries
    requires itextpdf;
    requires org.apache.pdfbox;

    // HTTP and networking libraries
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires java.net.http;

    // Image processing libraries
    requires aspose.imaging;

    // Google Cloud libraries
//    requires protobuf.java;
//    requires google.cloud.speech;
//    requires proto.google.cloud.speech.v1;
//
//    // Additional dependencies
    requires javafx.swing;
    requires assemblyai.java;
    requires gax;


    opens pidev.javafx.model.reclamation to javafx.base;

    // Ticket Controller
    opens pidev.javafx.controller.ticket;
    exports pidev.javafx.controller.ticket to javafx.fxml;

    // Reclamation Controller
    opens pidev.javafx.controller.reclamation;
    exports pidev.javafx.controller.reclamation to javafx.fxml;

    // MarketPlace Controllers and Models
    opens pidev.javafx.controller.marketPlace to javafx.fxml;
    opens pidev.javafx.model.MarketPlace to javafx.fxml;
    exports pidev.javafx.model.MarketPlace;
    exports pidev.javafx.controller.marketPlace;

    // User Market Dashboard Controller
    opens pidev.javafx.controller.userMarketDashbord to javafx.fxml;
    exports pidev.javafx.controller.userMarketDashbord;

    // CRUD Controllers
    opens pidev.javafx.crud to javafx.fxml;
    opens pidev.javafx.crud.marketplace to javafx.fxml;
    exports pidev.javafx.crud;

    // Tools Controllers
    opens pidev.javafx.tools to javafx.fxml;
    exports pidev.javafx.tools;

    // Contrat Controllers
    opens pidev.javafx.controller.contrat to javafx.fxml;
    exports pidev.javafx.controller.contrat;

    // Chat Controller
    opens pidev.javafx.controller.chat to javafx.fxml;
    exports pidev.javafx.controller.chat;

    // Test Controllers
    opens pidev.javafx.test to javafx.fxml;
    exports pidev.javafx.test;

    // Main Window Controller
    opens pidev.javafx.controller.mainWindow to javafx.fxml;
    exports pidev.javafx.controller.mainWindow;

    // Reponse Controller
    opens pidev.javafx.controller.reponse to javafx.fxml;
    exports pidev.javafx.controller.reponse;
}
