package pidev.javafx.Controller.Blog;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pidev.javafx.Models.Account;
import pidev.javafx.Models.Post;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import pidev.javafx.Services.BlogService;
import pidev.javafx.Utils.DataSource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.*;


public class BlogController implements Initializable {

    @FXML
    private VBox postsContainer;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private Button publierBtn;

    @FXML
    private Button addImgBtn;

    @FXML
    private TextArea captionText;

    Set<Post> posts;

    String SourceString;

    final String destinationString = "C:/Users/Omar Marrakchi/Desktop/javaFX/src/main/resources/blogImgPosts";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        choiceBox.getItems().addAll("Tous", "Municipalité", "Citoyens");
        choiceBox.setValue("Tous");

        posts = new HashSet<>(getPost());

            for (Post post : posts) {
                try {
                    loadPost(post);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    public void loadPost (Post post) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/post.fxml"));
        VBox vBox = fxmlLoader.load();

        PostController postController = fxmlLoader.getController();
        postController.setData(post);

        postsContainer.getChildren().add(vBox);
    }

    public Set<Post> getPost(){
        BlogService bs = new BlogService();
        return bs.getAll();
    }

    @FXML
    void onAddImgBtnClicked(MouseEvent event) {
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
            //System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void onPublierClicked(MouseEvent event) {
        String imgPath = null;
        String randomFileName = null;
        Post p = new Post();
        BlogService bs = new BlogService();

        p.setDate(LocalDateTime.now());

        if(captionText.getText().isEmpty()) {
            p.setCaption(null);
        }else {
            p.setCaption(captionText.getText());
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
                System.err.println("Erreur lors de la copie du fichier : " + e.getMessage());
            }
            p.setImage(imgPath);
        }else {
            p.setImage(imgPath);
        }
        p.setNbComments(0);
        p.setTotalReactions(0);

        bs.ajouter(p);
        posts.add(p);
        System.out.println(posts);

        try {
            loadPost(p);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
