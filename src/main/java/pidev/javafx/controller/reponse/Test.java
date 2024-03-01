package pidev.javafx.controller.reponse;


import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import pidev.javafx.controller.reclamation.modifer;
import pidev.javafx.controller.reclamation.Tool;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.crud.reclamation.ServiceReponse;
import pidev.javafx.model.reclamation.Reclamation;
import pidev.javafx.model.reclamation.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Test {
    @FXML
    public ListView lista;
    private Button importButton;

    @FXML
    private TextField subject;
    @FXML
    private TextField privateKey;
    @FXML
    private TextField title;
   @FXML
   private TextField id;
    @FXML
    private ImageView imageView;

    @FXML
    private TextArea description ;
    @FXML
    private TextArea description1 ;

    ServiceReclamation si = new ServiceReclamation();
    ServiceReponse sirep = new ServiceReponse();


    public void initialize() {
        ServiceReponse service = new ServiceReponse();
        List<Response> reclamationsrp = new ArrayList<>(service.getAll());

        // Add the Reclamation objects to the ListView
        lista.getItems().addAll(reclamationsrp);

        // Set a custom cell factory
        lista.setCellFactory(reclamationListView -> new ListCell<Response>() {
            private HBox hbox = new HBox();
            private Label label = new Label();
            private Button button = new Button("Action");
            private ImageView imageView = new ImageView();
            private ContextMenu contextMenu = new ContextMenu();

            {
                MenuItem editItem = new MenuItem("Edit");
                editItem.setOnAction(event -> {
                    // Handle edit action
                });

                MenuItem deleteItem = new MenuItem("Delete");
                deleteItem.setOnAction(event -> {
                    // Handle delete action
                });

                contextMenu.getItems().addAll(editItem, deleteItem);

                hbox.getChildren().addAll(label, button, imageView);
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);

                // Add a fade in animation to the HBox
                FadeTransition ft = new FadeTransition(Duration.millis(300), hbox);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                // Set a custom style for the HBox
                hbox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px;");
            }

            @Override
            protected void updateItem(Response reclamation, boolean empty) {
                super.updateItem(reclamation, empty);
                if (empty || reclamation == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    label.setText(reclamation.getReclamation().getPrivateKey() + " | " + reclamation.getReclamation().getDate() +" | "+reclamation.getReclamation().getSubject() + " | " + reclamation.getReclamation().getTitre() +" | "+reclamation.getDescription());
                    if (reclamation.getReclamation().getImagePath() != null) {
                        try (InputStream is = new FileInputStream(reclamation.getReclamation().getImagePath())) {
                            Image image = new Image(is);
                            imageView.setImage(image);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    setGraphic(hbox);
                    setContextMenu(contextMenu);
                }
            }
        });

        // Handle double-click events
//        lista.setOnMouseClicked(event -> {
//            if (event.getClickCount() == 2 && (!lista.getSelectionModel().isEmpty())) {
//                // Get the selected item
//                Reclamation selectedItem = (Reclamation) lista.getSelectionModel().getSelectedItem();
//
//                // Create an instance of the other controller
//                modifer testController = new modifer();
//
//                // Use the details of the selected item to populate the fields in the other controller
//                testController.displayDetailsInTextField();
//            }
//        });
    }



    //    public void initialize() {
//        ServiceReponse service = new ServiceReponse();
//        List<Response> reclamationsrp = new ArrayList<>(service.getAll());
//
//        // Add the Reclamation objects to the ListView
//        lista.getItems().addAll(reclamationsrp);
//
//        // Optionally, you can set a custom cell factory to control how each Reclamation is displayed
//        lista.setCellFactory(reclamationListView -> new ListCell<Response>() {
//            @Override
//            protected void updateItem(Response reclamation, boolean empty) {
//                super.updateItem(reclamation, empty);
//                if (empty || reclamation == null) {
//                    setText(null);
//                } else {
//                    setText(reclamation.getReclamation().getPrivateKey() + " | " + reclamation.getReclamation().getDate() +" | "+reclamation.getReclamation().getSubject() + " | " + reclamation.getReclamation().getTitre() +" | "+reclamation.getDescription() );
//                    //    System.out.println(reclamation.toString());
//
//                    ImageView imageView = new ImageView();
//
//                    imageView.setFitHeight(50);
//                    imageView.setFitWidth(50);
//                    if (!empty && reclamation.getReclamation().getImagePath() != null) {
//                        try (InputStream is = new FileInputStream(reclamation.getReclamation().getImagePath())) {
//                            Image image = new Image(is);
//                            imageView.setImage(image);
//                            setGraphic(imageView);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
//                }
//            }
//        });
//    }
    public void displayDetailsInTextField() {
        lista.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!lista.getSelectionModel().isEmpty())) {
                // Get the selected item
                Response selectedItem = (Response) lista.getSelectionModel().getSelectedItem();
                id.setText(String.valueOf(selectedItem.getId()));
                privateKey.setText(selectedItem.getReclamation().getPrivateKey());
                subject.setText(selectedItem.getReclamation().getSubject());
                title.setText(selectedItem.getReclamation().getTitre());
                description.setText(selectedItem.getReclamation().getDescription());
                description1.setText(selectedItem.getDescription());
            }
        });
    }
    @FXML
    void showPopup(ActionEvent event) {
        Tool t= new Tool() {
            @Override
            public void onclic(Reclamation event) {
                System.out.println(event.getDate());
                id.setText(event.getPrivateKey());
                privateKey.setText(event.getPrivateKey());
                subject.setText(event.getSubject());
                title.setText(event.getTitre());
                description.setText(event.getDescription());
                description1.setText(event.getDescription());
            }
        };
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/reclamation/showallreclamation.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                modifer m =fxmlLoader.getController();
                m.onlic(t);

               // Popupupdate controller = fxmlLoader.getController();
                //controller.setData(reclamation);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Popup Window");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    public void modifer_Reclamation(ActionEvent actionEvent) {
        int idInt = Integer.parseInt(id.getText());
        Response   rec = new Response(idInt,description1.getText());
        sirep.modifier(rec);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Reclmation has been modified !");
        // Show the alert
        alert.show();
    }

    public void supprimer_Reclamation() {
        int iddd= Integer.parseInt(id.getText());

        sirep.supprimer(iddd);


        displayDetailsInTextField();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Reclmation Delelte!");

        // Show the alert
        alert.showAndWait();
    }
}
