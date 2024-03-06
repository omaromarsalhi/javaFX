package pidev.javafx.controller.blog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import pidev.javafx.Models.News;
import pidev.javafx.Utils.NewsDataApi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewsPageController implements Initializable {
    List<News> newsList;
    @FXML
    private VBox newsContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newsList = new ArrayList<>(loadNews());
        System.out.println(newsList);
        for (News news : newsList) {
            try {
                afficherNews(news);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<News> loadNews () {
        NewsDataApi newsDataApi = new NewsDataApi();
        return newsDataApi.getNews();
    }

    public void afficherNews (News news) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/news.fxml"));
        VBox vBox = fxmlLoader.load();
        NewsController newsController = fxmlLoader.getController();
        newsController.setData(news);
        newsContainer.getChildren().add(vBox);
    }
}
