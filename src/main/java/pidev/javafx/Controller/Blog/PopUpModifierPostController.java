package pidev.javafx.Controller.Blog;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pidev.javafx.Models.Post;
import pidev.javafx.Services.BlogService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.UUID;

public class PopUpModifierPostController {

    @FXML
    private ImageView closeBtn;
    @FXML
    private TextArea caption;
    @FXML
    private ImageView imgPost;
    @FXML
    private VBox popUpVbox;
    @FXML
    private Button addImgBtn;
    @FXML
    private Button publierBtn;

    String SourceString;
    private int id;
    private String imgPath;
    final String destinationString = "C:/Users/Omar Marrakchi/Desktop/javaFX/src/main/resources/blogImgPosts";

    public int getId() {
        return id;
    }

    public Button getPublierBtn() {
        return publierBtn;
    }

    @FXML
    void onClosedBtn(MouseEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close(); // Ferme le pop-up
    }

     public void onAddImgBtnClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choisisez une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers .png", "*.png"),
                new FileChooser.ExtensionFilter("Fichiers .jpg", "*.jpg")
        );
        File selectedFile = fileChooser.showOpenDialog(Stage.getWindows().get(0));

        if (selectedFile != null) {
            SourceString = selectedFile.getAbsolutePath();
            addImgBtn.setText(SourceString);
        }
    }

    public void getData(int idPost) {
        BlogService bs = new BlogService();
        Post post = bs.getOneById(idPost);
        Image img;

        id = idPost;
        caption.setText(post.getCaption());
        imgPath = post.getImage();

        if(post.getImage() != null && !post.getImage().isEmpty()){
            img = new Image("file:src/main/resources" + post.getImage());
            imgPost.setImage(img);
            addImgBtn.setText("changer la photo");
        }else{
            popUpVbox.setPrefHeight(popUpVbox.getPrefHeight() - imgPost.getFitHeight());
            imgPost.setVisible(false);
            imgPost.setManaged(false);
            addImgBtn.setText("ajouter une photo");
        }
    }

    @FXML
    void onModifierClicked(MouseEvent event) {
        String randomFileName = null;
        Post p = new Post();
        BlogService bs = new BlogService();

        p.setId(id);
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        p.setDate(timestamp);

        if(caption.getText().isEmpty()) {
            p.setCaption(null);
        }else {
            p.setCaption(caption.getText());
        }

        if(SourceString != null) {
            try {
                Path sourcePath = Paths.get(SourceString);
                if (SourceString.endsWith(".png")) {
                    randomFileName = UUID.randomUUID().toString() + ".png";
                }else {
                    randomFileName = UUID.randomUUID().toString() + ".jpg";
                }
                Path destinationPath = Paths.get(destinationString, randomFileName);
                Files.copy(sourcePath, destinationPath);
                imgPath = "/blogImgPosts" + "/" + randomFileName;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            p.setImage(imgPath);
        }else {
            p.setImage(imgPath);
        }
        p.setNbComments(0);
        p.setTotalReactions(0);
        bs.modifier(p);
        SourceString = null;
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}