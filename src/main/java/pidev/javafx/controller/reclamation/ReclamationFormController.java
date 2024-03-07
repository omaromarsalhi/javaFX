package pidev.javafx.controller.reclamation;


import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import pidev.javafx.crud.reclamation.ServiceReclamation;
import pidev.javafx.model.MarketPlace.Product;
import pidev.javafx.model.reclamation.Reclamation;
import pidev.javafx.tools.UserController;
import pidev.javafx.tools.marketPlace.CustomMouseEvent;
import pidev.javafx.tools.marketPlace.EventBus;
import pidev.javafx.tools.marketPlace.MyListener;
import pidev.javafx.tools.marketPlace.MyTools;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.ResourceBundle;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;



import java.io.IOException;

public class ReclamationFormController implements Initializable {

    @FXML
    private VBox Box1;
    @FXML
    private TextArea Pdescretion;
    @FXML
    private TextField Pname;
    @FXML
    private HBox formBox;
    @FXML
    private Button imageBtn;

    @FXML
    private AnchorPane loadingPage;


    private File chosenFile;
    private MyListener listener;
    private static String usageOfThisForm;
    private HBox buttonsBox;
    private boolean isImageUpdated;
    private Product product;
    private boolean isAllInpulValid;
    String formLayoutBeforRegexCheck;
    String formLayoutAfterRegexCheck;
    Reclamation reclamation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadingPage.setVisible(false);

        formLayoutBeforRegexCheck = "-fx-border-color:black;" + "-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-color: white;" +
                "-fx-background-radius: 10";

        formLayoutAfterRegexCheck = "-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-color: white;" +
                "-fx-background-radius: 10";

        isAllInpulValid = false;

        createFormBtns();
        Box1.getChildren().add(buttonsBox);


        imageBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            setExtFilters(fileChooser);
            fileChooser.setTitle("Save Image");
            chosenFile = fileChooser.showOpenDialog(Stage.getWindows().get(0));
        });
        setRegEx();
    }


    private void setExtFilters(FileChooser chooser) {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }


    private void setRegEx() {
        Popup popup4Regex = MyTools.getInstance().createPopUp();

        Pname.setOnKeyTyped(event -> {
            isAllInpulValid = Pname.getText().matches("^[A-Za-z0-9 ]*$");
            String color = (isAllInpulValid) ? "green" : "red";
            if (Pname.getText().isEmpty()) {
                Pname.setStyle("");
                formBox.setStyle(formLayoutBeforRegexCheck);
                isAllInpulValid = true;
            } else {
                Pname.setStyle("-fx-border-color: transparent transparent " + color + " transparent;" +
                        "-fx-border-width:0 0 2 0;" +
                        "-fx-border-radius: 0");
                formBox.setStyle("-fx-border-color:" + color + ";" +
                        formLayoutAfterRegexCheck);
            }
            if ((!isAllInpulValid && !Pname.getText().isEmpty()))
                formBox.setStyle("-fx-border-color:red;" +
                        formLayoutAfterRegexCheck);
        });

        Pname.setOnMouseEntered(event -> {
            if (!isAllInpulValid && !Pname.getText().isEmpty()) {
                ((Label) popup4Regex.getContent().get(0)).setText("ONLY CHARACTERS AND NUMBERS ARE ALLOWED");
                popup4Regex.getContent().get(0).setStyle(popup4Regex.getContent().get(0).getStyle() + "-fx-background-color: #ed1c27;");
                popup4Regex.show(Stage.getWindows().get(0), event.getScreenX() + 40, event.getScreenY() - 40);
            }
        });

        Pname.setOnMouseExited(event -> {
//            if(isAllInpulValid[3])
            popup4Regex.hide();
        });
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = rnd.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    public void onAddClicked(MouseEvent event)  {
        if (isAllInpulValid && !Pdescretion.getText().isEmpty()) {
            reclamation = new Reclamation(
                    0,
                    UserController.getInstance().getCurrentUser().getId(),
                    generateRandomString(20),
                    Pname.getText(),
                    "",
                    Pdescretion.getText(),
                    ""
            );

            if (chosenFile != null)
                reclamation.setImagePath(MyTools.getInstance().getPathAndSaveIMG(chosenFile.getAbsolutePath()));
            System.out.println(reclamation);
            ServiceReclamation.getInstance().ajouter(reclamation);
            String htmlContent = null;
            try {
                htmlContent = new String(Files.readAllBytes(Paths.get("src/main/java/pidev/javafx/controller/reclamation/AAAA.html")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String dynamicText = "This is some dynamic text.";
            String htmlEmailBody = EmailController.addDynamicText(htmlContent, dynamicText);
            EmailController.sendEmail("khalil.rmila@esprit.tn", "HTML Email Test", htmlEmailBody);
//            Content content = new Content("text/plain", "and easy to do anywhere, even with Java");


            Thread thread = sleepThread(event);
            loadingPage.setVisible(true);
            thread.start();

        } else {
            Alert confirmationAlert = new Alert(Alert.AlertType.ERROR);
            confirmationAlert.setTitle("Error");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setGraphic(null);
            confirmationAlert.getDialogPane().getStylesheets().add("file:src/main/resources/style/alertStyle.css");
            confirmationAlert.setContentText("There is some wrong Data please fix it !!!");
            confirmationAlert.show();
        }
    }


    private Thread sleepThread(MouseEvent event) {
        Task<Void> myTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                return null;
            }
        };

        myTask.setOnSucceeded(e -> {
            loadingPage.setVisible(false);
            EventBus.getInstance().publish( "refresh",new CustomMouseEvent<>(reclamation ) );
            EventBus.getInstance().publish("exitFormUser", event);
        });
        return new Thread(myTask);
    }


    public void createFormBtns() {
        Button addProd = new Button();
        Button clearProd = new Button();
        Button cancel = new Button();

        buttonsBox = new HBox();

        addProd.setPrefWidth(50);
        clearProd.setPrefWidth(50);
        cancel.setPrefWidth(50);

        addProd.setPrefHeight(32);
        clearProd.setPrefHeight(32);
        cancel.setPrefHeight(32);

        Image img1 = new Image(String.valueOf(getClass().getResource("/icons/marketPlace/tab2.png")));
        Image img2 = new Image(String.valueOf(getClass().getResource("/icons/marketPlace/broom.png")));
        Image img3 = new Image(String.valueOf(getClass().getResource("/icons/marketPlace/paper.png")));

        addProd.setGraphic(new ImageView(img1));
        clearProd.setGraphic(new ImageView(img2));
        cancel.setGraphic(new ImageView(img3));

        addProd.setOnMouseClicked(this::onAddClicked);

        clearProd.setOnMouseClicked(event -> {
            Pdescretion.setText("");
            Pname.setText("");
        });
        cancel.setOnMouseClicked(event -> EventBus.getInstance().publish("exitFormUser", event));

        buttonsBox.getChildren().addAll(addProd, clearProd, cancel);
        buttonsBox.setSpacing(20);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setId("itemInfo");
        buttonsBox.getStylesheets().add(String.valueOf(getClass().getResource("/style/marketPlace/Buttons.css")));
        buttonsBox.setPadding(new Insets(0, 0, 10, 0));
    }

    VonageClient client = VonageClient.builder().apiKey("65a7971e").apiSecret("9sH4i1gwIDqSOw91").build();

    private void sendSMSToUser(String recipientPhoneNumber, String TP) {
        TextMessage message = new TextMessage(
                "Vonage APIs", // This should be your Vonage virtual number
                recipientPhoneNumber,
                "Your temporary password is: " + TP
        );
        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
        }
    }

//    public static void sendEmail(Mail mail) throws IOException {
//        SendGrid sg = new SendGrid(System.getenv("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\n"));
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
//        } catch (IOException ex) {
//            throw ex;
//        }
//    }
}
