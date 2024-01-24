module pidev.javafx{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens pidev.javafx to javafx.fxml;
    exports pidev.javafx;
}

