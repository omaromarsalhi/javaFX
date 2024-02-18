package pidev.javafx.Controller.Blog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pidev.javafx.Models.Comment;
import pidev.javafx.Models.Post;
import pidev.javafx.Services.BlogService;
import pidev.javafx.Services.CommentService;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PopUpCommentsController implements Initializable {
    @FXML
    private ImageView closeBtn;
    @FXML
    private Label caption;
    @FXML
    private Label date;
    @FXML
    private ImageView imgPost;
    @FXML
    private VBox commentContainer;
    @FXML
    private ImageView sendBtn;
    @FXML
    private TextArea CommentText;

    private int id;
    List<Comment> comments;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public PopUpCommentsController(int id) {
        this.id = id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(id);
        comments = new ArrayList<>(getComments());
        for (Comment comment : comments) {
            try {
                loadComment(comment);
                System.out.println("aaa");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void loadComment(Comment comment) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/Comment.fxml"));
        VBox vBox = fxmlLoader.load();
        CommentController commentController = fxmlLoader.getController();
        //postController.setIdPost(post.getId());
        commentController.setData(comment);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy HH:mm");
        String formattedDate = dateFormat.format(comment.getDate());
        commentController.getDate().setText(formattedDate);

        commentContainer.getChildren().add(vBox);
    }

    public List<Comment> getComments(){
        CommentService cs = new CommentService();
        return cs.getAll(id);
    }

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

    @FXML
    void onSendBtnClicked(MouseEvent event) {
        CommentService cs = new CommentService();
        Comment comment = new Comment();

        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        comment.setDate(timestamp);
        comment.setCaption(CommentText.getText());
        comment.setIdPost(id);

        cs.ajouterComment(comment);
    }
}
