package pidev.javafx.Controller.Blog;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.util.Duration;
import pidev.javafx.Models.Account;
import pidev.javafx.Models.Post;
import pidev.javafx.Services.BlogService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class PopUpModifierPostController implements Initializable {

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
    @FXML
    private Label enlverImgBtn;
    @FXML
    private ImageView rightArrow;
    @FXML
    private ImageView leftArrow;


    String SourceString;
    private int idPostUpadte;
    private int idCompteUpdate;
    private int nbReaction;
    private String imgPath;
    private List<String> images;
    private List<String> imagesToShow;
    int currentImgToShow;
    boolean imageUpdeted;
    boolean enleverImgClicked;
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
    public TextArea getCaption() {
        return caption;
    }
    public String getImgPath() {
        return imgPath;
    }
    public List<String> getImages() {
        return images;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        images = new ArrayList<>();
        leftArrow.setVisible(false);
        rightArrow.setVisible(false);
        imageUpdeted = false;
        currentImgToShow = 0;
        enleverImgClicked = false;
    }

    public void onAddImgBtnClicked(MouseEvent event) {
        imageUpdeted = true;
        images.clear();
        imagesToShow = new ArrayList<>();
        currentImgToShow = 0;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisisez des images/videos");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("Videos", "*.mp4")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            for (File selectedFile : selectedFiles) {
                SourceString = selectedFile.getAbsolutePath();
                images.add(SourceString);

                String correctedPath = SourceString.replace("\\", "/");
                imagesToShow.add(correctedPath);
            }
            addImgBtn.setText("remplacer les images");
            Image img = new Image(new File(imagesToShow.get(0)).toURI().toString());
            imgPost.setImage(img);
            enlverImgBtn.setVisible(true);
            enlverImgBtn.setManaged(true);
            if (imagesToShow.size() > 1) {
                rightArrow.setVisible(true);
                rightArrow.setManaged(true);
                leftArrow.setManaged(true);
            }
            rightArrow.setOnMouseClicked(mouseEvent -> {
                currentImgToShow ++;
                if (currentImgToShow > 0) {
                    leftArrow.setVisible(true);
                    leftArrow.setManaged(true);
                }
                if (currentImgToShow == images.size() - 1) {
                    rightArrow.setVisible(false);
                }
                if (currentImgToShow < images.size()) {
                    Image img2 = new Image(new File(imagesToShow.get(currentImgToShow)).toURI().toString());
                    imgPost.setImage(img2);
                }
            });
            leftArrow.setOnMouseClicked(mouseEvent -> {
                currentImgToShow --;
                if (currentImgToShow == 0) {
                    leftArrow.setVisible(false);
                }
                if (currentImgToShow < images.size() - 1) {
                    rightArrow.setVisible(true);
                }
                if (currentImgToShow < images.size()) {
                    Image img3 = new Image(new File(imagesToShow.get(currentImgToShow)).toURI().toString());
                    imgPost.setImage(img3);
                }
            });
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
        images = post.getImages();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy HH:mm");
        String formattedDate = dateFormat.format(post.getDate());
        dateLabel.setText(formattedDate);
        if (!images.isEmpty()) {
            if (images.size() > 1) {
                rightArrow.setVisible(true);
            }
            img = new Image("file:src/main/resources" + images.get(0));
            imgPost.setImage(img);
            addImgBtn.setText("changer la photo");
            enlverImgBtn.setVisible(true);

            rightArrow.setOnMouseClicked(mouseEvent -> {
                currentImgToShow ++;
                if (currentImgToShow > 0) {
                    leftArrow.setVisible(true);
                    leftArrow.setManaged(true);
                }
                if (currentImgToShow == images.size() - 1) {
                    rightArrow.setVisible(false);
                }
                if (currentImgToShow < images.size()) {
                    Image img2 = new Image("file:src/main/resources" + post.getImages().get(currentImgToShow));
                    imgPost.setImage(img2);
                }
            });
            leftArrow.setOnMouseClicked(mouseEvent -> {
                currentImgToShow --;
                if (currentImgToShow == 0) {
                    leftArrow.setVisible(false);
                }
                if (currentImgToShow < images.size() - 1) {
                    rightArrow.setVisible(true);
                }
                if (currentImgToShow < images.size()) {
                    Image img3 = new Image("file:src/main/resources" + post.getImages().get(currentImgToShow));
                    imgPost.setImage(img3);
                }
            });
        } else {
            img = new Image(getClass().getResourceAsStream("/img/aucuneImg.png"));
            imgPost.setImage(img);
            addImgBtn.setText("ajouter une photo");
            enlverImgBtn.setVisible(false);
        }
        nbReaction = post.getTotalReactions();
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
            p.setCaption(caption.getText().trim());
        }
        if (!images.isEmpty() && imageUpdeted) {
            bs.supprimerImages(idPostUpadte);
            for (String image : images) {
                try {
                    Path sourcePath = Paths.get(image);
                    if (image.endsWith(".png")) {
                        randomFileName = UUID.randomUUID().toString() + ".png";
                    } else {
                        randomFileName = UUID.randomUUID().toString() + ".jpg";
                    }
                    Path destinationPath = Paths.get(destinationString, randomFileName);
                    Files.copy(sourcePath, destinationPath);
                    imgPath = "/blogImgPosts" + "/" + randomFileName;
                    bs.ajouterImages(imgPath, idPostUpadte);
                    p.getImages().add(imgPath);
                } catch (IOException e) {
                    System.err.println("Erreur lors de la copie du fichier : " + e.getMessage());
                }
            }
        }

        if (enleverImgClicked) {
            BlogService blogService = new BlogService();
            blogService.supprimerImages(idPostUpadte);
        }

        p.setTotalReactions(nbReaction);
        bs.modifier(p);
        SourceString = null;
    }

    @FXML
    void enleverImgBtnClicked(MouseEvent event) {
        enleverImgClicked = true;
        Image img = new Image(getClass().getResourceAsStream("/img/aucuneImg.png"));
        imgPost.setImage(img);
        addImgBtn.setText("ajouter des photos");
        enlverImgBtn.setVisible(false);
        rightArrow.setVisible(false);
        leftArrow.setVisible(false);
        SourceString = null;
        imgPath = null;
        images.clear();

    }
}