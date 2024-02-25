package pidev.javafx.Controller.Blog;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.json.JSONException;
import pidev.javafx.Models.Account;
import pidev.javafx.Models.Post;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import pidev.javafx.Models.Reactions;
import pidev.javafx.Services.BlogService;
import pidev.javafx.Services.CommentService;
import pidev.javafx.Services.ReactionService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;
import org.json.JSONObject;



public class BlogController implements Initializable {

    @FXML
    private VBox postsContainer;
    @FXML
    private VBox postContainer;
    @FXML
    private Button publierBtn;
    @FXML
    private Button addImgBtn;
    @FXML
    private TextArea captionText;
    @FXML
    private ImageView ProfileImg;
    @FXML
    private TextField searchBar;
    @FXML
    private MenuButton menuButtonFiltrer;
    @FXML
    private MenuItem choix1;
    @FXML
    private MenuItem choix2;
    @FXML
    private MenuItem choix3;
    @FXML
    private MenuItem choix4;

    private int ConnectedAccount;
    List<Post> posts;
    String SourceString;
    final String destinationString = "src/main/resources/blogImgPosts";

    public TextField getSearchBar() {
        return searchBar;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectedAccount = 5;
        BlogService blogService = new BlogService();
        Account account = blogService.getComte(ConnectedAccount);
        Image img = new Image(getClass().getResourceAsStream(account.getProfileImg()));
        ProfileImg.setImage(img);

        posts = new ArrayList<>(getPost());

        for (Post post : posts) {
            try {
                loadPost(post);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void loadPost(Post post) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/post.fxml"));
        VBox vBox = fxmlLoader.load();
        PostController postController = fxmlLoader.getController();
        postController.setIdPost(post.getId());
        postController.setIdCompte(post.getIdCompte());
        postController.setData(post, post.getIdCompte());
        postsContainer.getChildren().add(vBox);

        if (post.getIdCompte() == ConnectedAccount) {
            postController.getMenuBtnPost().setVisible(true);
        } else {
            postController.getMenuBtnPost().setVisible(false);
        }

        postController.getSupprimerPostBtn().setOnAction(actionEvent -> {
            supprimerPost(post.getId(), postController, vBox);
        });

        postController.getModifierPost().setOnAction(actionEvent -> {
            afficherPopupModifier(post.getId(), postsContainer, vBox);
        });
        postController.getImgAngry().setOnMouseClicked(mouseEvent -> {
            postController.onAngryClicked();
            addOrUpdateReaction("Angry", post, ConnectedAccount, postController);
        });
        postController.getImgHaha().setOnMouseClicked(mouseEvent -> {
            postController.onHahaClicked();
            addOrUpdateReaction("Haha", post, ConnectedAccount, postController);
        });
        postController.getImgLike().setOnMouseClicked(mouseEvent -> {
            postController.onLikePressed();
            addOrUpdateReaction("Like", post, ConnectedAccount, postController);
        });
        postController.getImgSad().setOnMouseClicked(mouseEvent -> {
            postController.onSadClicked();
            addOrUpdateReaction("Sad", post, ConnectedAccount, postController);
        });
        postController.getLikeContainer().setOnMouseReleased(mouseEvent -> {
            boolean testTimer = postController.onLikeContainerMouseReleased();
            if (testTimer) {
                addOrDeleteLike(post, postController, ConnectedAccount);
            }
        });
        setReaction(postController, post, ConnectedAccount);

        postController.getCommentContainer().setOnMouseClicked(mouseEvent -> {
            afficherPopUpComments(postController, post.getId());
        });
        setNbComments(postController, post.getId());

        postController.getTranslateBtn().setOnAction(actionEvent -> {
            String originalText = postController.getCaption().getText();
            if(!postController.isTranslated()) {
                try {
                    String translatedText = translateText(originalText, "fr", "ar");
                    postController.getCaption().setText(translatedText);
                    if (translatedText.equals(originalText)) {
                        postController.getTranslateBtn().setText("Non disponible");
                    }else {
                        postController.getTranslateBtn().setText("Version Original");
                    }
                    postController.setTranslated(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                postController.getCaption().setText(post.getCaption());
                postController.getTranslateBtn().setText("Traduire");
                postController.setTranslated(false);
            }
        });
    }

    public void loadPostAbove(Post post) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/post.fxml"));
        VBox vBox = fxmlLoader.load();
        PostController postController = fxmlLoader.getController();
        postController.setIdPost(post.getId());
        postController.setData(post, post.getIdCompte());
        postsContainer.getChildren().add(2, vBox);

        if (post.getIdCompte() == ConnectedAccount) {
            postController.getMenuBtnPost().setVisible(true);
        } else {
            postController.getMenuBtnPost().setVisible(false);
        }

        postController.getSupprimerPostBtn().setOnAction(actionEvent -> {
            supprimerPost(post.getId(), postController, vBox);
            postsContainer.getChildren().remove(vBox);
        });

        postController.getModifierPost().setOnAction(actionEvent -> {
            afficherPopupModifier(post.getId(), postsContainer, vBox);
        });

        postController.getImgAngry().setOnMouseClicked(mouseEvent -> {
            postController.onAngryClicked();
            addOrUpdateReaction("Angry", post, ConnectedAccount, postController);
        });
        postController.getImgHaha().setOnMouseClicked(mouseEvent -> {
            postController.onHahaClicked();
            addOrUpdateReaction("Haha", post, ConnectedAccount, postController);
        });
        postController.getImgLike().setOnMouseClicked(mouseEvent -> {
            postController.onLikePressed();
            addOrUpdateReaction("Like", post, ConnectedAccount, postController);
        });
        postController.getImgSad().setOnMouseClicked(mouseEvent -> {
            postController.onSadClicked();
            addOrUpdateReaction("Sad", post, ConnectedAccount, postController);
        });
        postController.getLikeContainer().setOnMouseReleased(mouseEvent -> {
            boolean testTimer = postController.onLikeContainerMouseReleased();
            if (testTimer) {
                addOrDeleteLike(post, postController, ConnectedAccount);
            }
        });
        setReaction(postController, post, ConnectedAccount);

        postController.getCommentContainer().setOnMouseClicked(mouseEvent -> {
            afficherPopUpComments(postController, post.getId());
        });
        setNbComments(postController, post.getId());

        postController.getTranslateBtn().setOnAction(actionEvent -> {
            String originalText = postController.getCaption().getText();
            if(!postController.isTranslated()) {
                try {
                    String translatedText = translateText(originalText, "fr", "ar");
                    postController.getCaption().setText(translatedText);
                    if (translatedText.equals(originalText)) {
                        postController.getTranslateBtn().setText("Non disponible");
                    }else {
                        postController.getTranslateBtn().setText("Version Original");
                    }
                    postController.setTranslated(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                postController.getCaption().setText(post.getCaption());
                postController.getTranslateBtn().setText("Traduire");
                postController.setTranslated(false);
            }
        });
    }

    public List<Post> getPost() {
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
        if (!captionText.getText().isEmpty() || SourceString != null) {
            String imgPath = null;
            String randomFileName = null;
            Post p = new Post();
            BlogService bs = new BlogService();

            long currentTimeMillis = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(currentTimeMillis);
            p.setDate(timestamp);

            if (captionText.getText().isEmpty()) {
                p.setCaption(null);
            } else {
                p.setCaption(captionText.getText());
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
                    System.err.println("Erreur lors de la copie du fichier : " + e.getMessage());
                }
                p.setImage(imgPath);
            } else {
                p.setImage(imgPath);
            }
            p.setIdCompte(ConnectedAccount);
            p.setNbComments(0);
            p.setTotalReactions(0);
            bs.ajouter(p);
            captionText.clear();
            SourceString = null;
            addImgBtn.setText("Ajouter une Photo");
            p.setId(bs.getLastId());
            posts.add(0, p);
            try {
                loadPostAbove(p);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void supprimerPost(int idPost, PostController postController, VBox vBox) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce post ?");
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/icon/supp.png")));
        alert.setGraphic(icon);
        Stage primaryStage = (Stage) vBox.getScene().getWindow(); // Remplacez "yourParentNode" par le noeud parent de votre scène principale
        alert.initOwner(primaryStage);
        alert.initStyle(StageStyle.TRANSPARENT);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/Style/alerte.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        ButtonType buttonTypeOui = new ButtonType("Oui");
        ButtonType buttonTypeNon = new ButtonType("Non");
        alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeOui) {
                // L'utilisateur confirme la suppression
                BlogService bs = new BlogService();
                bs.supprimer(idPost);
                Optional<Post> optionalPost = posts.stream()
                        .filter(obj -> obj.getId() == idPost)
                        .findFirst();
                if (optionalPost.isPresent()) {
                    Post p = optionalPost.get();
                    posts.remove(p);
                    postsContainer.getChildren().remove(vBox);
                } else {
                    System.out.println("Aucun objet trouvé avec l'ID : " + idPost);
                }
            } else {
                System.out.println("Suppression annulée");
            }
        });
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
            Scene scene = postsContainer.getScene();
            Stage mainWindow = (Stage) scene.getWindow();
            BoxBlur blur = new BoxBlur(5, 5, 3);
            stage.initOwner(mainWindow);
            stage.show();
            scene.getRoot().setEffect(blur);
            parent.setVisible(false);
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
                Post psotUpdeted = bs.getOneById(idPostUpdeted);
                Optional<Post> optionalPost = posts.stream()
                        .filter(obj -> obj.getId() == idPostUpdeted)
                        .findFirst();
                if (optionalPost.isPresent()) {
                    Post oldPost = optionalPost.get();
                    posts.remove(oldPost);
                    posts.add(0, psotUpdeted);
                } else {
                    System.out.println("Aucun post trouvé avec l'ID : " + idPost);
                }
                try {
                    loadPostAbove(psotUpdeted);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                TranslateTransition closeTransition = new TranslateTransition(Duration.seconds(0.3), parent);
                closeTransition.setFromY(0);
                closeTransition.setToY(600);
                closeTransition.setOnFinished(event -> {
                    stage.close();
                    scene.getRoot().setEffect(null); //
                });
                closeTransition.play();
            });

            popUpController.getCloseBtn().setOnMouseClicked(mouseEvent -> {
                TranslateTransition closeTransition = new TranslateTransition(Duration.seconds(0.3), parent);
                closeTransition.setFromY(0);
                closeTransition.setToY(600);
                closeTransition.setOnFinished(event -> {
                    stage.close();
                    scene.getRoot().setEffect(null); //
                });
                closeTransition.play();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdateReaction(String type, Post post, int idCompte, PostController postController) {
        ReactionService rs = new ReactionService();
        BlogService blogService = new BlogService();
        if (rs.getReaction(post.getId(), idCompte) == null) {
            rs.ajouterReaction(type, post.getId(), idCompte);
        } else {
            rs.modifierReaction(post.getId(), type, idCompte);
        }
        int nbrReaction = rs.nbrReaction(post.getId());
        postController.setNbReactions(nbrReaction);
        post.setTotalReactions(nbrReaction);
        blogService.modifierNombreReactions(post.getId(), nbrReaction);
        ArrayList<String> types = new ArrayList<>(rs.getTypeReaction(post.getId()));
        postController.setIconReaction(types);
    }

    public void addOrDeleteLike(Post post, PostController postController, int idCompte) {
        ReactionService rs = new ReactionService();
        BlogService blogService = new BlogService();
        if (rs.getReaction(post.getId(), idCompte) == null) {
            rs.ajouterReaction("Like", post.getId(), idCompte);
        } else {
            rs.enleverReaction(post.getId(), idCompte);
            postController.setReaction(Reactions.NON);
        }
        int nbrReaction = rs.nbrReaction(post.getId());
        postController.setNbReactions(nbrReaction);
        post.setTotalReactions(nbrReaction);
        blogService.modifierNombreReactions(post.getId(), nbrReaction);
        ArrayList<String> types = new ArrayList<>(rs.getTypeReaction(post.getId()));
        postController.setIconReaction(types);
    }

    public void setReaction(PostController postController, Post post, int idCompte) {
        ReactionService rs = new ReactionService();
        BlogService blogService = new BlogService();
        String reaction = rs.getReaction(post.getId(), idCompte);
        if (reaction == null) {
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
        int nbrReaction = rs.nbrReaction(post.getId());
        postController.setNbReactions(nbrReaction);
        ArrayList<String> types = new ArrayList<>(rs.getTypeReaction(post.getId()));
        postController.setIconReaction(types);
    }

    public void setNbComments(PostController postController, int idPost) {
        CommentService cs = new CommentService();
        postController.setNbComments(cs.nbrComment(idPost));
    }

    public void afficherPopUpComments(PostController postController, int idPost) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PopUpComments.fxml"));
            PopUpCommentsController popUpCommentsController = new PopUpCommentsController(idPost);
            fxmlLoader.setControllerFactory(controllerClass -> popUpCommentsController);
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene sc = new Scene(parent);
            stage.setScene(sc);
            stage.setTitle("Modifier la Publication");
            sc.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = postsContainer.getScene();
            Stage mainWindow = (Stage) scene.getWindow();
            BoxBlur blur = new BoxBlur(5, 5, 3);
            stage.initOwner(mainWindow);

            stage.show();
            scene.getRoot().setEffect(blur);
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

            popUpCommentsController.getSendBtn().setOnMouseClicked(mouseEvent -> {
                popUpCommentsController.onSendBtnClicked();
                setNbComments(postController, idPost);
            });
            popUpCommentsController.getCloseBtn().setOnMouseClicked(mouseEvent -> {
                TranslateTransition closeTransition = new TranslateTransition(Duration.seconds(0.3), parent);
                closeTransition.setFromY(0);
                closeTransition.setToY(600);
                closeTransition.setOnFinished(event -> {
                    setNbComments(postController, idPost);
                    stage.close();
                    scene.getRoot().setEffect(null);
                });
                closeTransition.play();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSearchKeyRelesed(KeyEvent event) {
        BlogService blogService = new BlogService();
        List<Post> postCherche = new ArrayList<>();
        postCherche = blogService.rechercherPosts(searchBar.getText());
        if (searchBar.getText().length() > 3) {
            int size = postsContainer.getChildren().size();
            if (size > 2) {
                postsContainer.getChildren().remove(2, size);
            }
            for (Post post : postCherche) {
                try {
                    loadPost(post);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if(searchBar.getText().isEmpty()) {
            postCherche = blogService.getAll();
            int size = postsContainer.getChildren().size();
            if (size > 2) {
                postsContainer.getChildren().remove(2, size);
            }
            for (Post post : postCherche) {
                try {
                    loadPost(post);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @FXML
    void choix1Clicked(ActionEvent event) {
        BlogService blogService = new BlogService();
        menuButtonFiltrer.setText("Tous");
        posts = blogService.getAll();
        int size = postsContainer.getChildren().size();
        if (size > 2) {
            postsContainer.getChildren().remove(2, size);
        }
        for (Post post : posts) {
            try {
                loadPost(post);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void choix2Clicked(ActionEvent event) {
        BlogService blogService = new BlogService();
        List<Post> postList = new ArrayList<>();
        menuButtonFiltrer.setText("Municipalite");
        postList = blogService.getVerifiedPosts();
        int size = postsContainer.getChildren().size();
        if (size > 2) {
            postsContainer.getChildren().remove(2, size);
        }
        for (Post post : postList) {
            try {
                loadPost(post);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void choix3Clicked(ActionEvent event) {
        BlogService blogService = new BlogService();
        List<Post> postList = new ArrayList<>();
        menuButtonFiltrer.setText("Citoyens");
        postList = blogService.getUnverifiedPosts();
        int size = postsContainer.getChildren().size();
        if (size > 2) {
            postsContainer.getChildren().remove(2, size);
        }
        for (Post post : postList) {
            try {
                loadPost(post);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void choix4Clicked(ActionEvent event) {
        BlogService blogService = new BlogService();
        List<Post> postList = new ArrayList<>();
        menuButtonFiltrer.setText("Plus de Reactions");
        postList = blogService.getPostsTriesParNbReactions();
        int size = postsContainer.getChildren().size();
        if (size > 2) {
            postsContainer.getChildren().remove(2, size);
        }
        for (Post post : postList) {
            try {
                loadPost(post);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String translateText(String textToTranslate, String sourceLanguage, String targetLanguage) throws IOException {
        String apiKey = "db017c40fad98dc5b9fc";
        String url = "https://api.mymemory.translated.net/get?q=" + URLEncoder.encode(textToTranslate, "UTF-8") +
                "&langpair=" + "fr" + "|" + "ar" +
                "&key=" + apiKey; // Ajoutez la clé API comme paramètre

        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse JSON response to extract translated text
        String translatedText = parseTranslatedText(response.toString());
        return translatedText;
    }

    private String parseTranslatedText(String jsonResponse) {
        try {
            // Convertir la réponse JSON en objet JSON
            JSONObject jsonObject = new JSONObject(jsonResponse);

            // Vérifier si la réponse contient le champ "responseData"
            if (jsonObject.has("responseData")) {
                JSONObject responseData = jsonObject.getJSONObject("responseData");

                // Vérifier si le champ "translatedText" est présent dans "responseData"
                if (responseData.has("translatedText")) {
                    // Extraire et renvoyer le texte traduit
                    return responseData.getString("translatedText");
                } else {
                    return "Erreur : champ 'translatedText' manquant dans responseData";
                }
            } else {
                return "Erreur : champ 'responseData' manquant dans la réponse JSON";
            }
        } catch (JSONException e) {
            // Gérer les erreurs de parsing JSON
            return "Erreur lors de l'analyse de la réponse JSON : " + e.getMessage();
        }
    }
}