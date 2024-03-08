module pidev.javafx{
    requires javafx.controls;
    requires java.sql;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive java.desktop;
    requires itextpdf;
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


    opens pidev.javafx.controller.marketPlace to javafx.fxml;
    opens pidev.javafx.controller.reclamation to javafx.fxml;
    opens pidev.javafx.controller.Municipalite to javafx.fxml;
    opens pidev.javafx.controller.user to javafx.fxml;
    opens pidev.javafx.controller.login to javafx.fxml;
    opens pidev.javafx.controller.transport to javafx.fxml;
    opens pidev.javafx.controller.abonnement to javafx.fxml;
    opens pidev.javafx.controller.station to javafx.fxml;
    exports pidev.javafx.model.MarketPlace;
    opens pidev.javafx.model.MarketPlace to javafx.fxml;
    exports pidev.javafx.controller.marketPlace;
    exports pidev.javafx.controller.blog;
    opens pidev.javafx.controller.blog;
    opens pidev.javafx.controller.userMarketDashbord to javafx.fxml;
    exports pidev.javafx.crud;
    opens pidev.javafx.crud to javafx.fxml;
    exports pidev.javafx.tools.marketPlace;
    exports pidev.javafx.tools.user;
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
    exports pidev.javafx.controller.user;
    exports pidev.javafx.model.chat;
    opens pidev.javafx.tools.marketPlace to javafx.fxml;
    opens pidev.javafx.tools to javafx.fxml;
    exports pidev.javafx.tools;
    exports pidev.javafx.model.Transport;
    opens pidev.javafx.model.Transport;
}

