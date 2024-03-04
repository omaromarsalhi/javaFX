package pidev.javafx.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pidev.javafx.controller.login.NewLogInController;
import pidev.javafx.tools.UserController;
//import pidev.javafx.tools.UserController;
import java.io.IOException;



public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource( "/fxml/user/LoginSignup.fxml" ));
//        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "/fxml/mainWindow/mainWindow.fxml" ));
//        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "/fxml/user/LoginSignup.fxml" ));
        Scene scene = new Scene(fxmlLoader.load(), Color.TRANSPARENT);
        scene.getStylesheets().add( String.valueOf( getClass().getResource("/style/user/styleLogin.css") ) );

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