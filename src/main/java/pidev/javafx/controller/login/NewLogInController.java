package pidev.javafx.controller.login;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class NewLogInController implements Initializable {

    @FXML
    private StackPane blackSide;
    @FXML
    private Button signupBtn;
    @FXML
    private AnchorPane yellowSide;


    private boolean btnState;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnState=true;
        signupBtn.setOnMouseClicked( event -> {
            translate(340,-260);
            btnState=!btnState;
        } );
    }

    public void translate(int yellowX,int blackX){
        TranslateTransition translateTransitionYellowPart=new TranslateTransition( Duration.seconds( 0.6 ),yellowSide);
        FadeTransition fadeTransition=new FadeTransition(Duration.seconds( 0.3 ),blackSide);
        TranslateTransition translateTransitionBlackPart=new TranslateTransition( Duration.seconds( 0.6 ),blackSide);

        translateTransitionYellowPart.setToX((btnState)?yellowX:0);
        translateTransitionBlackPart.setToX( (btnState)?blackX:0 );

        fadeTransition.setToValue( 0 );
        fadeTransition.play();

        translateTransitionYellowPart.play();
        translateTransitionBlackPart.play();

        if(btnState)
            yellowSide.setStyle( "-fx-background-radius: 0 10 10 0;" +
                    "  -fx-border-radius: 0 10 10 0;" );
        else
            yellowSide.setStyle("");

        translateTransitionBlackPart.setOnFinished( event -> {
            fadeTransition.setToValue( 1 );
            fadeTransition.play();
        } );

    }
}
