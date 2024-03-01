package pidev.javafx.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pidev.javafx.controller.user.UserController;
//import pidev.javafx.controller.user.UserController;
import java.io.IOException;



public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "/fxml/mainWindow/mainWindow.fxml" ));

        Scene scene = new Scene(fxmlLoader.load(), Color.TRANSPARENT);
        UserController.setUser(1);
        stage.initStyle( StageStyle.TRANSPARENT);
        stage.setTitle("Hello!");
        stage.setResizable( true );
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> System.exit(0));
    }


    public static void main(String[] args) {
        launch();
    }
}