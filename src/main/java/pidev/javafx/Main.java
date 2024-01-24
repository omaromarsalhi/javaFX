package pidev.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "/fxml/mainWindow.fxml" ));
        Scene scene = new Scene(fxmlLoader.load(), 745, 537);
        scene.getStylesheets().add( String.valueOf( getClass().getResource("/style/Buttons.css") ) );
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> javafx.application.Platform.exit());
    }

    public static void main(String[] args) {
        launch();
    }
}