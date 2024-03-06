module pidev.javafx{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires org.json;
    requires java.desktop;

    opens pidev.javafx to javafx.fxml;
    exports pidev.javafx;
    exports pidev.javafx.controller;
    opens pidev.javafx.controller to javafx.fxml;
    opens pidev.javafx.controller.blog to javafx.fxml;
    exports pidev.javafx.Models;
    opens pidev.javafx.Models to javafx.fxml;
}

