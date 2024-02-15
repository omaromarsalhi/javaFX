package pidev.javafx.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "/fxml/mainWindow.fxml" ));
//        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "/fxml/marketPlace/myMarket.fxml" ));
//        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "/fxml/Employe/showEmployee.fxml" ));
        Scene scene = new Scene(fxmlLoader.load());
//        scene.getStylesheets().add( String.valueOf( getClass().getResource("/style/Buttons.css") ) );
//        scene.getStylesheets().add( String.valueOf( getClass().getResource("/style/styleShowItems.css") ) );
        stage.setTitle("Hello!");
        stage.setResizable( true );
//        stage.setWidth( 950 );
//        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> System.exit(0));
    }


    public static void main(String[] args) {
        launch();
    }
}