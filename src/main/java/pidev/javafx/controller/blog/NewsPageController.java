package pidev.javafx.controller.blog;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pidev.javafx.model.blog.News;
import pidev.javafx.tools.blog.NewsDataApi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;

public class NewsPageController implements Initializable {
    List<News> newsList;
    @FXML
    private VBox newsContainer;
    @FXML
    private AnchorPane loadingPage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        newsList = new ArrayList<>();
        loadNews().start();
    }

    public Thread loadNews(){
        Task<List<News>> task=new Task<>() {
            @Override
            protected List<News> call() throws Exception {
                return NewsDataApi.getNews();
            }
        };

        task.setOnSucceeded( workerStateEvent -> {
            for (News news : task.getValue()) {
                try {
                    afficherNews(news);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            loadingPage.setVisible( false );
        } );

        return new Thread(task);
    }


    public  void afficherNews(News news) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/blog/news.fxml"));
        VBox vBox = fxmlLoader.load();
        NewsController newsController = fxmlLoader.getController();
        newsController.setData(news);
        newsContainer.getChildren().add(vBox);
    }
}
