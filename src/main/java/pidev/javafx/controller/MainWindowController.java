package pidev.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {
    @FXML
    private Button closeBtn;

    @FXML
    private Button reduireBtn;

    @FXML
    private Button agrendirBtn;

    @FXML
    private AnchorPane mainBorderPain;

    @FXML
    void onCloseBtnClicked() {
        closeBtn.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    @FXML
    void onReduireBtnClicked(ActionEvent event) {
        Stage currentStage = (Stage) reduireBtn.getScene().getWindow();
        currentStage.setIconified(true);
    }

    @FXML
    void onAgrendirBtnClicked(ActionEvent event) {
        Stage currentStage = (Stage) agrendirBtn.getScene().getWindow();
        boolean etatFenetre = currentStage.isMaximized();
        currentStage.setMaximized(!etatFenetre);
    }

    @FXML
    void onBlogClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/blog.fxml"));
        AnchorPane blogPane = loader.load();
        mainBorderPain.getChildren().setAll(blogPane);
    }

    @FXML
    void onNewsClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newsPage.fxml"));
        AnchorPane blogPane = loader.load();
        mainBorderPain.getChildren().setAll(blogPane);
    }
}
