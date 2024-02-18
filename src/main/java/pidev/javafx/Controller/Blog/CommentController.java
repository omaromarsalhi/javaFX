package pidev.javafx.Controller.Blog;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pidev.javafx.Models.Comment;
import pidev.javafx.Services.CommentService;

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

    public Label getDate() {
        return date;
    }
    public Label getSupprimerBtn() {
        return SupprimerBtn;
    }

    public ImageView getSendBtn() {
        return sendBtn;
    }

    public void setData(Comment comment) {
        caption.setText(comment.getCaption());
        caption.setEditable(false);
        sendBtn.setVisible(false);
    }

    public void supprimerComment(int id) {
        CommentService cs = new CommentService();
        cs.supprimer(id);
    }

    @FXML
    void onModifierClicked(MouseEvent event) {
        sendBtn.setVisible(true);
        caption.setEditable(true);
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
