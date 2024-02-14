package pidev.javafx.Controller.Blog;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
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

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
    private ImageView imgLove;

    @FXML
    private ImageView imgCare;

    @FXML
    private ImageView imgHaha;

    @FXML
    private ImageView imgWow;

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

    private Post post;

    private int idPost;

    public int getIdPost() {return idPost;}

    public void setIdPost(int idPost) {this.idPost = idPost;}

    public MenuItem getSupprimerPostBtn() {return supprimerPostBtn;}

    public MenuItem getModifierPost() {return ModifierPost;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    public void onLikeContainerMouseReleased(MouseEvent me){
        if(System.currentTimeMillis() - startTime > 300){
            if(!reactionsContainer.isVisible()) {
                reactionsContainer.setVisible(true);
                iconLikeContainer.setVisible(false);
            }else {
                reactionsContainer.setVisible(false);
                iconLikeContainer.setVisible(true);
            }
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
        }
    }

    @FXML
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
    }

    public void setReaction(Reactions reaction){
        Image image = new Image(getClass().getResourceAsStream(reaction.getImgSrc()));
        imgReaction.setImage(image);
        reactionName.setText(reaction.getName());
        reactionName.setTextFill(Color.web(reaction.getColor()));

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() + 1);
        }

        currentReaction = reaction;

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() - 1);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
    }

    public void setData(Post post){
        this.post = post;
        Image img;

        /*img = new Image(getClass().getResourceAsStream(post.getAccount().getProfileImg()));
        imgProfile.setImage(img);
        username.setText(post.getAccount().getName());
        if(post.getAccount().isVerified()){
            imgVerified.setVisible(true);
        }else{
            imgVerified.setVisible(false);
        }*/

        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy HH:mm");
        String formattedDate = dateFormat.format(post.getDate());
        date.setText(formattedDate);

        if(post.getCaption() != null && !post.getCaption().isEmpty()){
            caption.setText(post.getCaption());
        }else{
            postContainer.setPrefHeight(postContainer.getPrefHeight() - (caption.getPrefHeight() - 10));
            caption.setVisible(false);
            caption.setManaged(false);
        }

        if(post.getImage() != null && !post.getImage().isEmpty()){
            img = new Image("file:src/main/resources" + post.getImage());
            imgPost.setImage(img);
        }else{
            postContainer.setPrefHeight(postContainer.getPrefHeight() - imgPost.getFitHeight());
            imgPost.setVisible(false);
            imgPost.setManaged(false);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        nbComments.setText(post.getNbComments() + " comments");

        currentReaction = Reactions.NON;
    }
}
