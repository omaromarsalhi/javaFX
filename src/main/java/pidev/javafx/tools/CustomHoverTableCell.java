package pidev.javafx.tools;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import pidev.javafx.model.MarketPlace.Bien;

import java.util.concurrent.atomic.AtomicInteger;

public final class CustomHoverTableCell<S, T> extends TableCell<S, T> {

    private final Popup popup = new Popup();
    private final Timeline fiveSecondsWonder = new Timeline();


    public  <S> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn() {
        return new Callback<TableColumn<S, T>, TableCell<S, T>>() {
            @Override
            public TableCell<S, T> call(TableColumn<S, T> column) {
                return new CustomHoverTableCell<>();
            }
        };
    }

    @Override
    protected void updateItem(T item, boolean empty) {

        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            setGraphic((ImageView) item);
            setOnMouseEntered(this::onMouseEntered);
            setOnMouseExited(this::onMouseExited);
        }
    }

    private void onMouseEntered(MouseEvent event) {
        Bien rowData = (Bien) getTableView().getItems().get(getIndex());

        final VBox mainContainer=new VBox();
        mainContainer.setMinSize( 100,100 );
        mainContainer.setAlignment( Pos.CENTER );
        mainContainer.setStyle("-fx-background-color: #64b5f6; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: white;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;");
        final ImageView imageView=new ImageView();
        imageView.setImage( new Image("file:src/main/resources/"+rowData.getImageSourceByIndex( 0 ),90,90,true,true ) );
        mainContainer.getChildren().add( imageView );

        if(rowData.getAllImagesSources().size()>1) {
            AtomicInteger indexOfImage= new AtomicInteger(1);
            fiveSecondsWonder.getKeyFrames().add( new KeyFrame( Duration.seconds( 0.8 ),event1 -> {
                imageView.setImage( new Image("file:src/main/resources/"+rowData.getImageSourceByIndex( indexOfImage.get() ),90,90,true,true ) );
                indexOfImage.getAndIncrement();
                if(indexOfImage.get()>=rowData.getAllImagesSources().size())
                    indexOfImage.set( 0 );
            } ) );
            fiveSecondsWonder.setCycleCount( Timeline.INDEFINITE );
            fiveSecondsWonder.play();
        }

        popup.getContent().clear();
        popup.getContent().add(mainContainer);
        popup.show(Stage.getWindows().get(0), event.getScreenX() + 20, event.getScreenY()-30);
    }

    private void onMouseExited(MouseEvent event) {
        fiveSecondsWonder.stop();
        popup.hide();
    }
}

