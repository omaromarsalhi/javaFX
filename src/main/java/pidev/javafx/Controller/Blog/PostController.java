package pidev.javafx.Controller.Blog;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import pidev.javafx.Models.Account;
import pidev.javafx.Models.Post;
import pidev.javafx.Models.Reactions;

import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pidev.javafx.Services.BlogService;
import pidev.javafx.Services.ReactionService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PostController extends VBox implements Initializable {
    @FXML
    private ImageView imgProfile;

    @FXML
    private Label username;

    @FXML
    private ImageView imgVerified;

    @FXML
    private Label date;

    @FXML
    private ImageView audience;

    @FXML
    private Label caption;

    @FXML
    private ImageView imgPost;

    @FXML
    private Label nbReactions;

    @FXML
    private Label nbComments;

    @FXML
    private HBox reactionsContainer;

    @FXML
    private HBox iconLikeContainer;

    @FXML
    private VBox postContainer;

    @FXML
    private ImageView imgLike;

    @FXML
    private ImageView imgHaha;

    @FXML
    private ImageView imgSad;

    @FXML
    private ImageView imgAngry;

    @FXML
    private HBox likeContainer;

    @FXML
    private ImageView imgReaction;

    @FXML
    private Label reactionName;

    private long startTime = 0;
    private Reactions currentReaction;

    @FXML
    private MenuButton menuBtnPost;

    @FXML
    private MenuItem supprimerPostBtn;

    @FXML
    private MenuItem ModifierPost;

    @FXML
    private HBox CommentContainer;

    @FXML
    private ImageView IconReaction1;

    @FXML
    private ImageView IconReaction2;

    @FXML
    private ImageView IconReaction3;

    @FXML
    private ImageView IconReaction4;

    private Post post;
    private int idPost;
    private int idCompte;

    public ImageView getImgLike() {return imgLike;}
    public ImageView getImgAngry() {
        return imgAngry;
    }
    public ImageView getImgHaha() {
        return imgHaha;
    }
    public ImageView getImgSad() {
        return imgSad;
    }
    public HBox getLikeContainer() {
        return likeContainer;
    }
    public HBox getCommentContainer() {
        return CommentContainer;
    }
    public int getIdPost() {return idPost;}
    public void setIdPost(int idPost) {this.idPost = idPost;}
    public int getIdCompte() {
        return idCompte;
    }
    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }
    public MenuButton getMenuBtnPost() {
        return menuBtnPost;
    }

    public MenuItem getSupprimerPostBtn() {return supprimerPostBtn;}

    public MenuItem getModifierPost() {return ModifierPost;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IconReaction2.setVisible(false);
        IconReaction2.setManaged(false);
        IconReaction3.setVisible(false);
        IconReaction3.setManaged(false);
        IconReaction4.setVisible(false);
        IconReaction4.setManaged(false);
    }

    @Override
    public String toString() {
        return "PostController{" +
                "idPost=" + idPost +
                '}';
    }

    @FXML
    public void onLikeContainerPressed(MouseEvent me){
        startTime = System.currentTimeMillis();
    }

    @FXML
    public boolean onLikeContainerMouseReleased(){
        if(System.currentTimeMillis() - startTime > 250){
            if(!reactionsContainer.isVisible()) {
                reactionsContainer.setVisible(true);
                iconLikeContainer.setVisible(false);
            }else {
                reactionsContainer.setVisible(false);
                iconLikeContainer.setVisible(true);
            }
            return false;
        }else {
            iconLikeContainer.setVisible(true);
            if(reactionsContainer.isVisible()){
                reactionsContainer.setVisible(false);
            }
            if(currentReaction == Reactions.NON){
                setReaction(Reactions.LIKE);
            }else{
                setReaction(Reactions.NON);
            }
            return true;
        }
    }

    /*@FXML
    public void onReactionImgPressed(MouseEvent me){
        switch (((ImageView) me.getSource()).getId()){
            case "imgHaha":
                setReaction(Reactions.HAHA);
                break;
            case "imgSad":
                setReaction(Reactions.SAD);
                break;
            case "imgAngry":
                setReaction(Reactions.ANGRY);
                break;
            default:
                setReaction(Reactions.LIKE);
                break;
        }
        reactionsContainer.setVisible(false);
        iconLikeContainer.setVisible(true);
    }*/

    @FXML
    public void onLikePressed() {
        setReaction(Reactions.LIKE);
        reactionsContainer.setVisible(false);
        iconLikeContainer.setVisible(true);
    }
    public void onHahaClicked() {
        setReaction(Reactions.HAHA);
        reactionsContainer.setVisible(false);
        iconLikeContainer.setVisible(true);
    }
    public void onSadClicked() {
        setReaction(Reactions.SAD);
        reactionsContainer.setVisible(false);
        iconLikeContainer.setVisible(true);
    }
    public void onAngryClicked() {
        setReaction(Reactions.ANGRY);
        reactionsContainer.setVisible(false);
        iconLikeContainer.setVisible(true);
    }

    public void setReaction(Reactions reaction){
        Image image = new Image(getClass().getResourceAsStream(reaction.getImgSrc()));
        imgReaction.setImage(image);
        reactionName.setText(reaction.getName());
        reactionName.setTextFill(Color.web(reaction.getColor()));
    }

    public void setIconReaction(ArrayList<String> types){
        Image image;
        int k=0;
        if(types.size() == 0){
            image = new Image(getClass().getResourceAsStream(Reactions.LIKE.getImgSrc()));
            IconReaction1.setImage(image);
        }
        else {
            for(int i = 1; i <= types.size(); i++){
                try {
                    k++;
                    Field field = getClass().getDeclaredField("IconReaction" + i);
                    ImageView value = (ImageView) field.get(this);
                    value.setVisible(true);
                    value.setManaged(true);
                    if(types.get(i-1).equals("Like")) {
                        image = new Image(getClass().getResourceAsStream(Reactions.LIKE.getImgSrc()));
                        value.setImage(image);
                    }
                    else if(types.get(i-1).equals("Haha")) {
                        image = new Image(getClass().getResourceAsStream(Reactions.HAHA.getImgSrc()));
                        value.setImage(image);
                    }
                    else if(types.get(i-1).equals("Sad")) {
                        image = new Image(getClass().getResourceAsStream(Reactions.SAD.getImgSrc()));
                        value.setImage(image);
                    }
                    else if(types.get(i-1).equals("Angry")) {
                        image = new Image(getClass().getResourceAsStream(Reactions.ANGRY.getImgSrc()));
                        value.setImage(image);
                    }
                    for(int j = k+1; j <= 4; j++){
                        Field field1 = null;
                        field = getClass().getDeclaredField("IconReaction" + j);
                        ImageView value1 = (ImageView) field.get(this);
                        value1.setVisible(false);
                        value1.setManaged(false);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
        }
    }

    public void setNbReactions(int nbr) {
        nbReactions.setText(String.valueOf(nbr));
    }
    public void setNbComments(int nbr){nbComments.setText(String.valueOf(nbr) + " commentaires");}

    public void setData(Post post, int idCompte){
        this.post = post;
        Image img;
        BlogService bs = new BlogService();
        Account account = bs.getComte(idCompte);

        img = new Image(getClass().getResourceAsStream(account.getProfileImg()));
        imgProfile.setImage(img);
        username.setText(account.getName());
        if(account.isVerified()){
            imgVerified.setVisible(true);
        }else{
            imgVerified.setVisible(false);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy HH:mm");
        String formattedDate = dateFormat.format(post.getDate());
        date.setText(formattedDate);

        if(post.getCaption() != null && !post.getCaption().isEmpty()){
            caption.setText(post.getCaption());
            caption.applyCss();
            Platform.runLater(() -> {
                double captionHeight = caption.getBoundsInLocal().getHeight();
                postContainer.setPrefHeight(postContainer.getPrefHeight() + captionHeight);
                VBox.setVgrow(caption, Priority.ALWAYS);
            });

        }else{caption.setVisible(false);}

        if(post.getImage() != null && !post.getImage().isEmpty()){
            img = new Image("file:src/main/resources" + post.getImage());
            imgPost.setImage(img);
        }else{
            imgPost.setVisible(false);
            imgPost.setManaged(false);
            // Réduisez la hauteur du VBox de la hauteur de l'ImageView
            postContainer.setPrefHeight(postContainer.getPrefHeight() - imgPost.getFitHeight() - 14);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        nbComments.setText(post.getNbComments() + " comments");

        currentReaction = Reactions.NON;
    }
}
