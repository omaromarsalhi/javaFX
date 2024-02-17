package pidev.javafx.Controller.Blog;


import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import pidev.javafx.Models.Post;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import pidev.javafx.Models.Reactions;
import pidev.javafx.Services.BlogService;
import pidev.javafx.Services.ReactionService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;


public class BlogController implements Initializable {

    @FXML
    private VBox postsContainer;

    @FXML
    private VBox postContainer;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private Button publierBtn;

    @FXML
    private Button addImgBtn;

    @FXML
    private TextArea captionText;

    List<Post> posts;

    List<PostController> postControllers;

    String SourceString;

    final String destinationString = "C:/Users/Omar Marrakchi/Desktop/javaFX/src/main/resources/blogImgPosts";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        choiceBox.getItems().addAll("Tous", "Municipalité", "Citoyens");
        choiceBox.setValue("Tous");

        posts = new ArrayList<>(getPost());
        postControllers = new ArrayList<>();

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
        postController.setIdPost(post.getId());
        postControllers.add(postController);
        postController.setData(post);
        postsContainer.getChildren().add(vBox);

        postController.getSupprimerPostBtn().setOnAction(actionEvent -> {
            supprimerPost(post.getId());
            postsContainer.getChildren().remove(vBox);
        });

        postController.getModifierPost().setOnAction(actionEvent -> {
            afficherPopupModifier(post.getId(), postsContainer, vBox);
        });

        postController.getImgAngry().setOnMouseClicked(mouseEvent -> {
            postController.onAngryClicked();
            addOrUpdateReaction("Angry", post.getId(), postController);
        });
        postController.getImgHaha().setOnMouseClicked(mouseEvent -> {
            postController.onHahaClicked();
            addOrUpdateReaction("Haha", post.getId(), postController);
        });
        postController.getImgLike().setOnMouseClicked(mouseEvent -> {
            postController.onLikePressed();
            addOrUpdateReaction("Like", post.getId(), postController);
        });
        postController.getImgSad().setOnMouseClicked(mouseEvent -> {
            postController.onSadClicked();
            addOrUpdateReaction("Sad", post.getId(), postController);
        });
        postController.getLikeContainer().setOnMouseReleased(mouseEvent -> {
            boolean testTimer = postController.onLikeContainerMouseReleased();
            if(testTimer) {
                addOrDeleteLike(post.getId(), postController);
            }
        });
        setReaction(postController, post.getId());

        postController.getCommentContainer().setOnMouseClicked(mouseEvent -> {
            System.out.println("zzzzz");
            afficherPopUpComments(post.getId());
        });
    }

    public void loadPostAbove(Post post) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/post.fxml"));
        VBox vBox = fxmlLoader.load();
        PostController postController = fxmlLoader.getController();
        postController.setIdPost(post.getId());
        postControllers.add(postController);
        postController.setData(post);
        postsContainer.getChildren().add(2, vBox);

        postController.getSupprimerPostBtn().setOnAction(actionEvent -> {
            supprimerPost(post.getId());
            postsContainer.getChildren().remove(vBox);
        });

        postController.getModifierPost().setOnAction(actionEvent -> {
            afficherPopupModifier(post.getId(), postsContainer, vBox);
        });
    }

    public List<Post> getPost(){
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
        }
    }

    @FXML
    void onPublierClicked(MouseEvent event) {
        String imgPath = null;
        String randomFileName = null;
        Post p = new Post();
        BlogService bs = new BlogService();

        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        p.setDate(timestamp);

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

        captionText.clear();
        SourceString = null;
        addImgBtn.setText("Ajouter une Photo");

        int id = bs.getLastId();
        p.setId(id);
        posts.add(0, p);

        try {
            loadPostAbove(p);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimerPost(int idPost) {
        BlogService bs = new BlogService();
        bs.supprimer(idPost);
        Optional<Post> optionalPost = posts.stream()
                .filter(obj -> obj.getId() == idPost)
                .findFirst();
        if (optionalPost.isPresent()) {
            Post p = optionalPost.get();
            posts.remove(p);
        } else {
            System.out.println("Aucun objet trouvé avec l'ID : " + idPost);
        }
    }

    public void afficherPopupModifier(int idPost, VBox postsContainer, VBox postVbox) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/popUpModifierPost.fxml"));
            Parent parent = fxmlLoader.load();
            PopUpModifierPostController popUpController = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene sc = new Scene(parent);
            stage.setScene(sc);
            stage.setTitle("Modifier la Publication");
            sc.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);

            parent.setVisible(false);
            stage.show();
            stage.setY(200);
            stage.setX(650);
            parent.setVisible(true);
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.3), parent);
            transition.setFromY(600);
            transition.setToY(0);
            transition.play();

            popUpController.getData(idPost);
            popUpController.getPublierBtn().setOnAction(actionEvent -> {
                BlogService bs = new BlogService();
                popUpController.modifierPost();
                postsContainer.getChildren().remove(postVbox);
                int idPostUpdeted = popUpController.getId();
                Post psotUpdeted =  bs.getOneById(idPostUpdeted);
                Optional<Post> optionalPost = posts.stream()
                        .filter(obj -> obj.getId() == idPostUpdeted)
                        .findFirst();
                if (optionalPost.isPresent()) {
                    Post oldPost = optionalPost.get();
                    posts.remove(oldPost);
                    posts.add(0, psotUpdeted);
                    System.out.println(posts);
                } else {
                    System.out.println("Aucun objet trouvé avec l'ID : " + idPost);
                }
                try {
                    loadPostAbove(psotUpdeted);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdateReaction (String type, int idPost, PostController postController) {
        ReactionService rs = new ReactionService();
        if(rs.getReaction(idPost) == null){
            rs.ajouterReaction(type, idPost);
        }else {
            rs.modifierReaction(idPost, type);
        }
        postController.setNbReactions(rs.nbrReaction(idPost));
    }
    public void addOrDeleteLike(int idPost, PostController postController) {
        ReactionService rs = new ReactionService();
        if(rs.getReaction(idPost) == null){
            rs.ajouterReaction("Like", idPost);
        }else {
            rs.enleverReaction(idPost);
            postController.setReaction(Reactions.NON);
        }
        postController.setNbReactions(rs.nbrReaction(idPost));
    }

    public void setReaction (PostController postController, int idPost) {
        ReactionService rs = new ReactionService();
        String reaction = rs.getReaction(idPost);
        if(reaction == null){
            postController.setReaction(Reactions.NON);
        } else if (reaction.equals("Haha")) {
            postController.setReaction(Reactions.HAHA);
        } else if (reaction.equals("Sad")) {
            postController.setReaction(Reactions.SAD);
        } else if (reaction.equals("Angry")) {
            postController.setReaction(Reactions.ANGRY);
        } else if (reaction.equals("Like")) {
            postController.setReaction(Reactions.LIKE);
        }
        postController.setNbReactions(rs.nbrReaction(idPost));
    }

    public void afficherPopUpComments(int idPost) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PopUpComments.fxml"));
            Parent parent = fxmlLoader.load();
            PopUpCommentsController popUpCommentsController = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene sc = new Scene(parent);
            stage.setScene(sc);
            stage.setTitle("Modifier la Publication");
            sc.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();

            parent.setVisible(false);
            stage.show();
            stage.setY(200);
            stage.setX(513);
            parent.setVisible(true);
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.3), parent);
            transition.setFromY(600);
            transition.setToY(0);
            transition.play();

            popUpCommentsController.getData(idPost);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}