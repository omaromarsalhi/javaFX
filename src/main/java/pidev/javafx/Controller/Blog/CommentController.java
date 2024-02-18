package pidev.javafx.Controller.Blog;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pidev.javafx.Models.Comment;

public class CommentController {

    @FXML
    private TextArea caption;
    @FXML
    private Label date;

    public Label getDate() {
        return date;
    }

    public void setData(Comment comment) {
        caption.setText(comment.getCaption());
        caption.setEditable(false);
    }
}
