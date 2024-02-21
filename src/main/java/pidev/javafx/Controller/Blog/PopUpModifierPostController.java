package pidev.javafx.Controller.Blog;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pidev.javafx.Models.Account;
import pidev.javafx.Models.Post;
import pidev.javafx.Services.BlogService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    @FXML
    private Label dateLabel;
    @FXML
    private ImageView AccountImg;


    String SourceString;
    private int idPostUpadte;
    private int idCompteUpdate;
    private int nbComments;
    private int nbReaction;
    private String imgPath;
    final String destinationString = "src/main/resources/blogImgPosts";

    public int getId() {
        return idPostUpadte;
    }

    public Button getPublierBtn() {
        return publierBtn;
    }

    public int getIdCompteUpdate() {
        return idCompteUpdate;
    }

    public ImageView getCloseBtn() {
        return closeBtn;
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
            String correctedPath = SourceString.replace("\\", "/");
            Image img = new Image(new File(correctedPath).toURI().toString());
            System.out.println(correctedPath);
            imgPost.setImage(img);
        }
    }

    public void getData(int idPost) {
        BlogService bs = new BlogService();
        Post post = bs.getOneById(idPost);
        Account account = bs.getComte(post.getIdCompte());
        Image img;

        Image img1 = new Image(getClass().getResourceAsStream(account.getProfileImg()));
        AccountImg.setImage(img1);

        idPostUpadte = idPost;
        idCompteUpdate = post.getIdCompte();
        caption.setText(post.getCaption());
        imgPath = post.getImage();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy HH:mm");
        String formattedDate = dateFormat.format(post.getDate());
        dateLabel.setText(formattedDate);
        if (post.getImage() != null && !post.getImage().isEmpty()) {
            img = new Image("file:src/main/resources" + post.getImage());
            imgPost.setImage(img);
            addImgBtn.setText("changer la photo");
        } else {
            img = new Image(getClass().getResourceAsStream("/blogImgPosts/aucuneImg.png"));
            imgPost.setImage(img);
            addImgBtn.setText("ajouter une photo");
        }

        nbReaction = post.getTotalReactions();
        nbComments = post.getNbComments();
    }

    @FXML
    void modifierPost() {

        String randomFileName = null;
        Post p = new Post();
        BlogService bs = new BlogService();

        p.setId(idPostUpadte);
        p.setIdCompte(idCompteUpdate);
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        p.setDate(timestamp);

        if (caption.getText().isEmpty()) {
            p.setCaption(null);
        } else {
            p.setCaption(caption.getText());
        }
        if (SourceString != null) {
            try {
                Path sourcePath = Paths.get(SourceString);
                if (SourceString.endsWith(".png")) {
                    randomFileName = UUID.randomUUID().toString() + ".png";
                } else {
                    randomFileName = UUID.randomUUID().toString() + ".jpg";
                }
                Path destinationPath = Paths.get(destinationString, randomFileName);
                Files.copy(sourcePath, destinationPath);
                imgPath = "/blogImgPosts" + "/" + randomFileName;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            p.setImage(imgPath);
        } else {
            p.setImage(imgPath);
        }
        p.setNbComments(nbComments);
        p.setTotalReactions(nbReaction);
        bs.modifier(p);
        SourceString = null;
    }

}