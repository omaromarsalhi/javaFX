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
import pidev.javafx.Services.ReactionService;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @FXML
    private Label nbReactions;

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

    public ImageView getSendBtn() {
        return sendBtn;
    }

    public ImageView getCloseBtn() {
        return closeBtn;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(id);
        comments = new ArrayList<>(getComments());
        for (Comment comment : comments) {
            try {
                loadComment(comment);
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
        commentController.setData(comment);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy HH:mm");
        String formattedDate = dateFormat.format(comment.getDate());
        commentController.getDate().setText(formattedDate);
        commentContainer.getChildren().add(vBox);

        commentController.getSupprimerBtn().setOnMouseClicked(mouseEvent -> {
            commentController.supprimerComment(comment.getId());
            commentContainer.getChildren().remove(vBox);
        });

        commentController.getSendBtn().setOnMouseClicked(mouseEvent -> {
            modiferComment(commentController, comment.getId());
            commentContainer.getChildren().remove(vBox);
        });
    }
    private void loadCommentAbove(Comment comment) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/Comment.fxml"));
        VBox vBox = fxmlLoader.load();
        CommentController commentController = fxmlLoader.getController();
        //postController.setIdPost(post.getId());
        commentController.setData(comment);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy HH:mm");
        String formattedDate = dateFormat.format(comment.getDate());
        commentController.getDate().setText(formattedDate);
        commentContainer.getChildren().add(4,vBox);

        commentController.getSupprimerBtn().setOnMouseClicked(mouseEvent -> {
            commentController.supprimerComment(comment.getId());
            commentContainer.getChildren().remove(vBox);
        });

        commentController.getSendBtn().setOnMouseClicked(mouseEvent -> {
            modiferComment(commentController, comment.getId());
            commentContainer.getChildren().remove(vBox);
        });
    }

    public List<Comment> getComments(){
        CommentService cs = new CommentService();
        return cs.getAll(id);
    }


    void onClosedBtn() {
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
        ReactionService rs = new ReactionService();
        nbReactions.setText(String.valueOf(rs.nbrReaction(idPost)));
    }

    @FXML
    void onSendBtnClicked() {
        CommentService cs = new CommentService();
        Comment comment = new Comment();

        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        comment.setDate(timestamp);
        comment.setCaption(CommentText.getText());
        comment.setIdPost(id);

        cs.ajouterComment(comment);
        comment.setId(cs.getLastId());
        comments.add(0, comment);

        CommentText.setText("");

        try {
            loadCommentAbove(comment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void modiferComment(CommentController commentController, int id) {
        CommentService cs = new CommentService();
        commentController.modifierComment(id);
        Optional<Comment> optionalPost = comments.stream()
                .filter(obj -> obj.getId() == id)
                .findFirst();
        if (optionalPost.isPresent()) {
            Comment oldComment = optionalPost.get();
            comments.remove(oldComment);
            Comment commentUpdeted = cs.getOneById(id);
            comments.add(0, commentUpdeted);
            try {
                loadCommentAbove(commentUpdeted);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Aucun comment trouvé avec l'ID : " + id);
        }
    }
}
