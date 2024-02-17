package pidev.javafx.Controller.Blog;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pidev.javafx.Models.Post;
import pidev.javafx.Services.BlogService;

import java.text.SimpleDateFormat;

public class PopUpCommentsController {
    @FXML
    private ImageView closeBtn;
    @FXML
    private Label caption;
    @FXML
    private Label date;
    @FXML
    private ImageView imgPost;

    private int id;
    @FXML
    void onClosedBtn(MouseEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void getData(int idPost) {
        BlogService bs = new BlogService();
        Post post = bs.getOneById(idPost);
        Image img;

        id = idPost;
        caption.setText(post.getCaption());
        //imgPath = post.getImage();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy HH:mm");
        String formattedDate = dateFormat.format(post.getDate());
        date.setText(formattedDate);
        if(post.getImage() != null && !post.getImage().isEmpty()){
            img = new Image("file:src/main/resources" + post.getImage());
            imgPost.setImage(img);
        }else{
            //popUpVbox.setPrefHeight(popUpVbox.getPrefHeight() - imgPost.getFitHeight());
            imgPost.setVisible(false);
            imgPost.setManaged(false);
        }
        //nbReaction = post.getTotalReactions();
        //nbComments = post.getNbComments();
    }
}
