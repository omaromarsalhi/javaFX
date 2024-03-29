module pidev.javafx {
    requires javafx.controls;
    requires java.sql;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive java.desktop;

    // PDF libraries
    requires itextpdf;
<<<<<<< HEAD
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
=======
    requires twilio;
    requires org.json;
    requires org.apache.pdfbox;
    requires javafx.web;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires java.mail;
    requires com.google.api.client.auth;
    requires com.google.api.client;
    requires google.api.client;
    requires com.google.api.client.json.gson;
    requires jdk.jsobject;
    requires java.net.http;
    requires com.google.gson;
    requires okhttp3;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires kernel;
    requires xmlworker;
    requires client;
    requires aspose.imaging;
    requires javafx.swing;
>>>>>>> main


    opens pidev.javafx.model.reclamation to javafx.base;

    // Ticket Controller
    opens pidev.javafx.controller.ticket;
    exports pidev.javafx.controller.ticket to javafx.fxml;

    // Reclamation Controller
    opens pidev.javafx.controller.reclamation;
    exports pidev.javafx.controller.reclamation to javafx.fxml;

    // MarketPlace Controllers and Models
    opens pidev.javafx.controller.marketPlace to javafx.fxml;
<<<<<<< HEAD
=======
    opens pidev.javafx.controller.reclamation to javafx.fxml;
    opens pidev.javafx.controller.Municipalite to javafx.fxml;
    opens pidev.javafx.controller.user to javafx.fxml;
    opens pidev.javafx.controller.login to javafx.fxml;
    opens pidev.javafx.controller.transport to javafx.fxml;
    opens pidev.javafx.controller.abonnement to javafx.fxml;
    opens pidev.javafx.controller.station to javafx.fxml;
    exports pidev.javafx.model.MarketPlace;
>>>>>>> main
    opens pidev.javafx.model.MarketPlace to javafx.fxml;
    exports pidev.javafx.model.MarketPlace;
    exports pidev.javafx.controller.marketPlace;
<<<<<<< HEAD

    // User Market Dashboard Controller
    opens pidev.javafx.controller.userMarketDashbord to javafx.fxml;
=======
    exports pidev.javafx.controller.blog;
    opens pidev.javafx.controller.blog;
    opens pidev.javafx.controller.userMarketDashbord to javafx.fxml;
    exports pidev.javafx.crud;
    opens pidev.javafx.crud to javafx.fxml;
    exports pidev.javafx.tools.marketPlace;
    exports pidev.javafx.tools.user;
    exports pidev.javafx.controller.contrat;
    opens pidev.javafx.controller.contrat to javafx.fxml;
>>>>>>> main
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
<<<<<<< HEAD
=======
    exports pidev.javafx.test;
    opens pidev.javafx.test to javafx.fxml;
    exports pidev.javafx.controller.mainWindow;
    opens pidev.javafx.controller.mainWindow to javafx.fxml;
    exports pidev.javafx.crud.marketplace;
    opens pidev.javafx.crud.marketplace to javafx.fxml;
    exports pidev.javafx.controller.user;
    exports pidev.javafx.model.chat;
    opens pidev.javafx.tools.marketPlace to javafx.fxml;
    opens pidev.javafx.tools to javafx.fxml;
    exports pidev.javafx.tools;
    exports pidev.javafx.model.Transport;
    opens pidev.javafx.model.Transport;
}
>>>>>>> main

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
