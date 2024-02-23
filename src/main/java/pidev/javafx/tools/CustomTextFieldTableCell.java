package pidev.javafx.tools;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.util.Callback;
import javafx.util.StringConverter;


public final class CustomTextFieldTableCell<S, T> extends TextFieldTableCell<S, T> {
//    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(final StringConverter<T> converter) {
//        return new Callback<TableColumn<S, T>, TableCell<S, T>>() {
//            @Override
//            public TableCell<S, T> call(TableColumn<S, T> column) {
//                final TextFieldTableCell<S, T> result = new TextFieldTableCell<>(converter);
//                final Popup popup = new Popup();
//
//                final EventHandler<MouseEvent> hoverListener = new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        final Label popupContent = new Label(result.getText().toUpperCase() );
//                        popupContent.setStyle("-fx-background-color: #64b5f6; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: white;");
//
//                        popup.getContent().clear();
//                        popup.getContent().addAll(popupContent);
//
//                        if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
//                            popup.hide();
//                        } else if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
//                            popup.show(result, event.getScreenX() + 10, event.getScreenY()-30);
//                        }
//                    }
//                };
//
//                result.setOnMouseEntered(hoverListener);
//                result.setOnMouseExited(hoverListener);
//
//                return result;
//            }
//        };
//    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(final StringConverter<T> converter) {
        return new Callback<TableColumn<S, T>, TableCell<S, T>>() {
            @Override
            public TableCell<S, T> call(TableColumn<S, T> column) {
                final TextFieldTableCell<S, T> result = new TextFieldTableCell<>(converter);
                final Popup popup = new Popup();

                final EventHandler<MouseEvent> hoverListener = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final Label popupContent = new Label(result.getText().toUpperCase() );
                        popupContent.setStyle("-fx-background-color: #64b5f6; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px; -fx-text-fill: white;"+
                                "-fx-border-radius: 10;" +
                                        "-fx-background-radius: 10;");
                        popupContent.setWrapText( true );
                        popupContent.setFont( Font.font( "System", FontWeight.MEDIUM,FontPosture.REGULAR,16 ) );
                        popupContent.setMaxWidth( 250 );
                        popup.getContent().clear();
                        popup.getContent().addAll(popupContent);

                        if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
                            popup.hide();
                            result.setOnMouseEntered(this);
                        } else if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
                            popup.show(result, event.getScreenX() + 10, event.getScreenY()-30);
                            result.setOnMouseEntered(null);
                        }
                    }
                };

                result.setOnMouseEntered(hoverListener);
                result.setOnMouseExited(hoverListener);

                return result;
            }
        };
    }
}
