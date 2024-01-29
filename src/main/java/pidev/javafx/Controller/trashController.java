package pidev.javafx.Controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;

import static javafx.scene.layout.FlowPane.setMargin;




public class trashController {
    @FXML
    private Button btn;
    @FXML
    private BorderPane bd;
    @FXML
    private AnchorPane an;
    @FXML
    public void onBtnclicked(ActionEvent event) throws IOException {
        System.out.println("omar");
//        setMargin(bd.getLeft(), new Insets(0, 200, 0, 0));
//        setMargin(an, new Insets(0, 200, 0, 0));

        an.setPrefWidth( 10 );
    }
}




//float prefSideBarWidth=(float)sideBar.getPrefWidth();
//isSideBarOpen=true;
//        zipSideBar.setVisible( !isSideBarOpen );
//        zipSideBar.setLayoutX( 0 );
//Image imgOpen= new Image(String.valueOf( getClass().getResource("/icons/11.png") ),23,23,false,false);
//Image imgClose= new Image(String.valueOf( getClass().getResource("/icons/83.png") ),23,23,false,false);
//
//
//
//
//// the button that changes the state of the sidebar
//        sideBarBtn.setOnMouseClicked( mouseEvent -> {
//        int fadeValueTo,trasitionValue;
//FadeTransition fadeTransition=new FadeTransition( Duration.seconds(0.3),sideBar);
//FadeTransition fadeTransitionOfzipSideBar=new FadeTransition( Duration.seconds(0.3),zipSideBar);
//TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.1),sideBar);
////values i havee created to avoid rewriting the same code to get a dry code
//
//fadeValueTo=(isSideBarOpen)?0:1;
//trasitionValue=(isSideBarOpen)?-1:1;
//        // fade animation of both sidebars
//        fadeTransition.setFromValue(-fadeValueTo+1);
//            fadeTransition.setToValue(fadeValueTo);
//            fadeTransitionOfzipSideBar.setFromValue(fadeValueTo);
//            fadeTransitionOfzipSideBar.setToValue(-fadeValueTo+1);
//
//
//// transistion by X
//            translateTransition.setByX(trasitionValue*prefSideBarWidth);
//
//            if (isSideBarOpen) {
//        fadeTransition.play();
//
//                fadeTransition.setOnFinished( event -> {
//        sideBar.setPrefWidth( zipSideBar.getPrefWidth());
//        leftAnchorPane.setPrefWidth(zipSideBar.getPrefWidth());
//        translateTransition.play();
//                    zipSideBar.setVisible( true );
//                    fadeTransitionOfzipSideBar.play();
//
//                });
//
//                        sideBarBtn.setGraphic( new ImageView( imgClose ));
//        }
//        else {
//        sideBar.setPrefWidth(prefSideBarWidth);
//                translateTransition.play();
//
//                fadeTransitionOfzipSideBar.play();
//                fadeTransitionOfzipSideBar.setOnFinished( event -> {
//        zipSideBar.setVisible( false );
//                    fadeTransition.play();
//                } );
//                        sideBarBtn.setGraphic( new ImageView( imgOpen ));
//
////                fadeTransition.setOnFinished( event -> {
////                     leftAnchorPane.setPrefWidth(prefSideBarWidth);
////                } );
//        }
//
//
//
//
//isSideBarOpen=!isSideBarOpen;
//        } );
