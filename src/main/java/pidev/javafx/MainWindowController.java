package pidev.javafx;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private boolean isSideBarOpen;
    @FXML
    private Button btn1;
    @FXML
    private VBox sideBar;
    @FXML
    private VBox zipSideBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isSideBarOpen=true;
        zipSideBar.setLayoutX( 0 );
        zipSideBar.setVisible( !isSideBarOpen );



        // the button that changes the state of the sidebar
        btn1.setOnMouseClicked( mouseEvent -> {
            int fadeValueTo,trasitionValue;
            FadeTransition fadeTransition=new FadeTransition( Duration.seconds(0.35),sideBar);
            FadeTransition fadeTransitionOfzipSideBar=new FadeTransition( Duration.seconds(0.2),zipSideBar);
            TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5),sideBar);
            //values i havee created to avoid rewriting the same code to get a dry code
            fadeValueTo=(isSideBarOpen)?0:1;
            trasitionValue=(isSideBarOpen)?-1:1;
            // fade animation of both sidebars
            fadeTransition.setFromValue(-fadeValueTo+1);
            fadeTransition.setToValue(fadeValueTo);
            fadeTransitionOfzipSideBar.setFromValue(fadeValueTo);
            fadeTransitionOfzipSideBar.setToValue(-fadeValueTo+1);


            if (isSideBarOpen) {
                fadeTransition.play();
                fadeTransition.setOnFinished( event -> {
                    zipSideBar.setVisible( true );
                    fadeTransitionOfzipSideBar.play();
                } );
            }
            else {
                fadeTransitionOfzipSideBar.play();
                fadeTransitionOfzipSideBar.setOnFinished( event -> {
                    zipSideBar.setVisible( false );
                    fadeTransition.play();
                } );
            }

            // transistion by X
            translateTransition.setByX(trasitionValue*sideBar.getWidth());
            translateTransition.play();
            isSideBarOpen=(isSideBarOpen)?false:true;
        } );
    }

}
