package pidev.javafx.controller.blog;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import pidev.javafx.model.blog.News;

import java.net.URL;


public class NewsController  {
    @FXML
    private Label caption;
    @FXML
    private Label date;
    @FXML
    private Hyperlink link;
    @FXML
    private ImageView imgPost;
    @FXML
    private Label newsName;
    @FXML
    private Label titleLabel;
    @FXML
    private ImageView imgProfile;
    @FXML
    private VBox newsContainer;

    public void setData(News news) {
        titleLabel.setText("Article Title : " + news.getTitle());
        titleLabel.setWrapText(true);
        titleLabel.setMinHeight(Region.USE_PREF_SIZE);
        VBox.setVgrow(titleLabel, Priority.ALWAYS);
        Platform.runLater(() -> {
            double captionHeight = titleLabel.getBoundsInLocal().getHeight();
            newsContainer.setPrefHeight(newsContainer.getPrefHeight() + captionHeight);
        });

        newsName.setText(news.getSourceId());

        caption.setText(news.getDescription() + "\nLien vers la page :");
        caption.setWrapText(true);
        caption.setMinHeight(Region.USE_PREF_SIZE);
        VBox.setVgrow(caption, Priority.ALWAYS);
        Platform.runLater(() -> {
            double captionHeight = caption.getBoundsInLocal().getHeight();
            newsContainer.setPrefHeight(newsContainer.getPrefHeight() + captionHeight);
        });

        link.setText(news.getLinkUrl());

        date.setText(news.getDate());

        if (news.getImageUrl() != null) {
            try {
                URL url = new URL(news.getImageUrl());
                Image image = new Image(url.openStream());
                imgPost.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            imgPost.setManaged( false );

        if (news.getSourceIcon() != null) {
            try {
                URL url = new URL(news.getSourceIcon());
                Image image = new Image(url.openStream());
                imgProfile.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void handleLinkClick(ActionEvent event) {
        String url = link.getText();
        if (url != null && !url.isEmpty()) {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
            } catch (java.io.IOException | java.net.URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
