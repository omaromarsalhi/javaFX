package pidev.javafx.Controller.Blog;

import pidev.javafx.Models.Account;
import pidev.javafx.Models.Post;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pidev.javafx.Utils.DataSource;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class BlogController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll("Tous", "Municipalité", "Citoyens");
        choiceBox.setValue("Tous");
        posts = new ArrayList<>(getPost());
        try {
            for (Post post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/blogtest1/post.fxml"));
                VBox vBox = fxmlLoader.load();
                PostController postController = fxmlLoader.getController();
                postController.setData(post);
                postsContainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private Button closeBtn;

    @FXML
    private Button reduireBtn;

    @FXML
    private Button agrendirBtn;

    @FXML
    private VBox postsContainer;

    @FXML
    private ChoiceBox choiceBox;

    List<Post> posts;

    public List<Post> getPost(){
        DataSource.getInstance();
        List<Post> ls = new ArrayList<>();
        Post post;

        post = new Post();
        Account account = new Account();
        account.setName("Omar Marrakchi");
        account.setProfileImg("/img/profilImg.png");
        account.setVerified(true);

        post.setAccount(account);
        post.setDate("Feb 24, 2024 at 2:00 PM");
        post.setCaption("rodou belkom fama hofra\nlebes mehech kbira barcha.");
        post.setImage("/img/reclamation.png");
        post.setTotalReactions(69);
        post.setNbComments(3);

        ls.add(post);

        post = new Post();
        account = new Account();
        account.setName("Omar Marrakchi");
        account.setProfileImg("/img/profilImg.png");
        account.setVerified(true);

        post.setAccount(account);
        post.setDate("Feb 24, 2024 at 2:00 PM");
        post.setCaption("cherche ps5 kasion nthifa\nbuget 500dt\nkalmouni al 27277990");
        post.setTotalReactions(10);
        post.setNbComments(15);

        ls.add(post);

        return ls;
    }

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
        Stage currentStage = (Stage) reduireBtn.getScene().getWindow();
        boolean etatFenetre = currentStage.isMaximized();
        currentStage.setMaximized(!etatFenetre);
    }

}
