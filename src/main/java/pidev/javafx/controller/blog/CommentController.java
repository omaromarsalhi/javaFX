package pidev.javafx.controller.blog;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pidev.javafx.model.blog.Account;
import pidev.javafx.model.blog.Comment;
import pidev.javafx.crud.blog.BlogService;
import pidev.javafx.crud.blog.CommentService;

import java.sql.Timestamp;

public class CommentController {

    @FXML
    private TextArea caption;
    @FXML
    private Label date;
    @FXML
    private Label SupprimerBtn;
    @FXML
    private ImageView sendBtn;
    @FXML
    private ImageView AccImg;
    @FXML
    private Label ModifierBtn;

    public Label getDate() {
        return date;
    }
    public Label getSupprimerBtn() {
        return SupprimerBtn;
    }
    public ImageView getSendBtn() {
        return sendBtn;
    }
    public Label getModifierBtn() {
        return ModifierBtn;
    }

    public void setData(Comment comment) {
        BlogService blogService = new BlogService();
        Account compte = blogService.getComte(comment.getIdCompte());
        caption.setText(comment.getCaption());
        caption.setEditable(false);
        sendBtn.setVisible(false);
        Image img = new Image(getClass().getResourceAsStream(compte.getProfileImg()));
        AccImg.setImage(img);
    }

    public void supprimerComment(int id) {
        CommentService cs = new CommentService();
        cs.supprimer(id);
    }

    @FXML
    void onModifierClicked(MouseEvent event) {
        sendBtn.setVisible(true);
        caption.setEditable(true);
        caption.requestFocus();
        caption.getStyleClass().add("yel-border");
    }

    public void modifierComment(int id) {
        CommentService cs = new CommentService();
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        cs.modifier(caption.getText(), timestamp, id);
        sendBtn.setVisible(false);
        caption.setEditable(false);
    }
}
