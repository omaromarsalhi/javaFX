package pidev.javafx.controller.blog;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;
import pidev.javafx.crud.user.ServiceUser;
import pidev.javafx.model.blog.Account;
import pidev.javafx.model.blog.Comment;
import pidev.javafx.model.blog.Post;
import pidev.javafx.crud.blog.BlogService;
import pidev.javafx.crud.blog.CommentService;
import pidev.javafx.crud.blog.ReactionService;
import pidev.javafx.model.user.Role;
import pidev.javafx.model.user.User;
import pidev.javafx.tools.UserController;

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
    @FXML
    private ImageView rightArrow;
    @FXML
    private ImageView leftArrow;
    @FXML
    private Label commentName;

    private List<String> images;
    private int id;
    private int ConectedAccount;
    private int currentImgToShow;
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
        images = new ArrayList<>();
        leftArrow.setVisible(false);
        rightArrow.setVisible(false);
        currentImgToShow = 0;
        commentContainer.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sroll.setVvalue(0);
            }
        });
        ConectedAccount = UserController.getInstance().getCurrentUser().getId();
        BlogService blogService = new BlogService();
        Image img1 = new Image("file:src/main/resources/" + UserController.getInstance().getCurrentUser().getPhotos());
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
        fxmlLoader.setLocation(getClass().getResource("/fxml/blog/Comment.fxml"));
        VBox vBox = fxmlLoader.load();
        CommentController commentController = fxmlLoader.getController();
        commentController.setData(comment);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy HH:mm");
        String formattedDate = dateFormat.format(comment.getDate());
        commentController.getDate().setText(formattedDate);
        if (comments.contains(comment)) {
            commentContainer.getChildren().add(vBox);
        } else {
            commentContainer.getChildren().add(5, vBox);
        }

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

    public void getData(Post post) {
        BlogService bs = new BlogService();
        ServiceUser serviceUser = new ServiceUser();
        User user = serviceUser.getUserById(post.getIdCompte());
        commentName.setText("Publication de " + user.getFirstname() + " " + user.getLastname());
        Image img;
        CommentProp.setImage(new Image("file:src/main/resources/" + user.getPhotos() ));
        username.setText(user.getFirstname() + " " + user.getLastname());
        imgVerified.setVisible(user.getRole() != Role.Citoyen);

        id = post.getId();
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
        images = post.getImages();
        if (!images.isEmpty()) {
            if (images.size() > 1) {
                rightArrow.setVisible(true);
            }
            img = new Image("file:src/main/resources" + images.get(0));
            imgPost.setImage(img);

            rightArrow.setOnMouseClicked(mouseEvent -> {
                currentImgToShow++;
                if (currentImgToShow > 0) {
                    leftArrow.setVisible(true);
                    leftArrow.setManaged(true);
                }
                if (currentImgToShow == images.size() - 1) {
                    rightArrow.setVisible(false);
                }
                if (currentImgToShow < images.size()) {
                    animateImageTransition(-17, post);
                }
            });
            leftArrow.setOnMouseClicked(mouseEvent -> {
                currentImgToShow--;
                if (currentImgToShow == 0) {
                    leftArrow.setVisible(false);
                }
                if (currentImgToShow < images.size() - 1) {
                    rightArrow.setVisible(true);
                }
                if (currentImgToShow < images.size()) {
                    animateImageTransition(17, post);
                }
            });
        } else {
            //popUpVbox.setPrefHeight(popUpVbox.getPrefHeight() - imgPost.getFitHeight());
            imgPost.setVisible(false);
            imgPost.setManaged(false);
        }
        ReactionService rs = new ReactionService();
        nbReactions.setText(String.valueOf(rs.nbrReaction(post.getId())));
    }

    @FXML
    void onSendBtnClicked() {
        String commentTrim = CommentText.getText().trim();
        boolean captionContainsOnlySpaces = commentTrim.isEmpty() || commentTrim.matches("[\\s\\n]+");

        if (!captionContainsOnlySpaces) {
            CommentService cs = new CommentService();
            Comment comment = new Comment();

            long currentTimeMillis = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(currentTimeMillis);
            comment.setDate(timestamp);
            comment.setCaption(CommentText.getText().trim());
            comment.setIdPost(id);
            comment.setIdCompte(ConectedAccount);

            cs.ajouterComment(comment);
            comment.setId(cs.getLastId());
            try {
                loadComment(comment);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            comments.add(0, comment);
            CommentText.setText("");
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
            try {
                loadComment(commentUpdeted);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            comments.add(0, commentUpdeted);
        } else {
            System.out.println("Aucun comment trouvé avec l'ID : " + id);
        }
    }

    private void animateImageTransition(double targetTranslateX, Post post) {
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.2), imgPost);
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.0);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.2), imgPost);
        translateTransition.setByX(targetTranslateX);

        ParallelTransition parallelTransition = new ParallelTransition(fadeOutTransition, translateTransition);
        parallelTransition.play();

        parallelTransition.setOnFinished(event -> {
            Image img = new Image("file:src/main/resources" + post.getImages().get(currentImgToShow));
            imgPost.setImage(img);
            imgPost.setTranslateX(0);

            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.2), imgPost);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);

            fadeInTransition.play();
        });
    }
}
