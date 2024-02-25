package pidev.javafx.Controller.Blog;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pidev.javafx.Models.Account;
import pidev.javafx.Models.Comment;
import pidev.javafx.Models.Post;
import pidev.javafx.Services.BlogService;
import pidev.javafx.Services.CommentService;
import pidev.javafx.Services.ReactionService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
    @FXML
    private ImageView ProfileImg;
    @FXML
    private ImageView CommentProp;
    @FXML
    private ScrollPane sroll;
    @FXML
    private Label username;
    @FXML
    private ImageView imgVerified;

    private int id;
    private int ConectedAccount;
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
        commentContainer.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sroll.setVvalue(0);
            }
        });
        ConectedAccount = 5;
        BlogService blogService = new BlogService();
        Account account = blogService.getComte(ConectedAccount);
        Image img1 = new Image(getClass().getResourceAsStream(account.getProfileImg()));
        ProfileImg.setImage(img1);
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

        if (comment.getIdCompte() == ConectedAccount) {
            commentController.getSupprimerBtn().setVisible(true);
            commentController.getModifierBtn().setVisible(true);
        } else {
            commentController.getSupprimerBtn().setVisible(false);
            commentController.getModifierBtn().setVisible(false);
        }

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
        commentContainer.getChildren().add(5, vBox);

        if (comment.getIdCompte() == ConectedAccount) {
            commentController.getSupprimerBtn().setVisible(true);
            commentController.getModifierBtn().setVisible(true);
        } else {
            commentController.getSupprimerBtn().setVisible(false);
            commentController.getModifierBtn().setVisible(false);
        }

        commentController.getSupprimerBtn().setOnMouseClicked(mouseEvent -> {
            commentController.supprimerComment(comment.getId());
            commentContainer.getChildren().remove(vBox);
        });

        commentController.getSendBtn().setOnMouseClicked(mouseEvent -> {
            modiferComment(commentController, comment.getId());
            commentContainer.getChildren().remove(vBox);
        });
    }

    public List<Comment> getComments() {
        CommentService cs = new CommentService();
        return cs.getAll(id);
    }

    public void getData(int idPost) {
        BlogService bs = new BlogService();
        Post post = bs.getOneById(idPost);
        Account account = bs.getComte(post.getIdCompte());
        Image img;

        Image img1 = new Image(getClass().getResourceAsStream(account.getProfileImg()));
        CommentProp.setImage(img1);
        username.setText(account.getName());
        if (account.isVerified()) {
            imgVerified.setVisible(true);
        } else {
            imgVerified.setVisible(false);
        }

        id = idPost;
        if (post.getCaption() != null && !post.getCaption().isEmpty()) {
            caption.setText(post.getCaption());
            caption.setMinHeight(Region.USE_PREF_SIZE);
            caption.setWrapText(true);
            caption.applyCss();
            Platform.runLater(() -> {
                double captionHeight = caption.getBoundsInLocal().getHeight();
                commentContainer.setPrefHeight(commentContainer.getPrefHeight() + captionHeight);
                VBox.setVgrow(caption, Priority.ALWAYS);
            });
        } else {
            caption.setVisible(false);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy HH:mm");
        String formattedDate = dateFormat.format(post.getDate());
        date.setText(formattedDate);
        if (post.getImage() != null && !post.getImage().isEmpty()) {
            img = new Image("file:src/main/resources" + post.getImage());
            imgPost.setImage(img);
        } else {
            //popUpVbox.setPrefHeight(popUpVbox.getPrefHeight() - imgPost.getFitHeight());
            imgPost.setVisible(false);
            imgPost.setManaged(false);
        }
        ReactionService rs = new ReactionService();
        nbReactions.setText(String.valueOf(rs.nbrReaction(idPost)));
    }

    @FXML
    void onSendBtnClicked() {
        if (!CommentText.getText().isEmpty()) {
            CommentService cs = new CommentService();
            Comment comment = new Comment();

            long currentTimeMillis = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(currentTimeMillis);
            comment.setDate(timestamp);
            comment.setCaption(CommentText.getText());
            comment.setIdPost(id);
            comment.setIdCompte(ConectedAccount);

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
