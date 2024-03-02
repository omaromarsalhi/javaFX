//package pidev.javafx.controller.reclamation;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.stage.FileChooser;
//import pidev.javafx.crud.reclamation.ServiceReclamation;
//import pidev.javafx.model.reclamation.Reclamation;
//import pidev.javafx.crud.reclamation.Iservice;
//import pidev.javafx.controller.reclamation.*;
//
//import java.io.File;
//import java.net.URL;
//import java.security.PrivateKey;
//import java.sql.SQLOutput;
//import java.util.Random;
//import java.util.ResourceBundle;
//
//public class AjouterReclamation {
//
//    @FXML
//    private Button importButton;
//
//    @FXML
//    private TextField privateKey;
//    @FXML
//    private TextField title;
//    @FXML
//    private TextField subject;
//
//    @FXML
//    private TextArea description ;
//   @FXML
//    public ChoiceBox ChoixMul;
//    @FXML
//    ServiceReclamation si = new ServiceReclamation();
//
//
//    public void importFile(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        File selectedFile = fileChooser.showOpenDialog(null);
//
//        if (selectedFile != null) {
//            System.out.println("File imported");
//        } else {
//            System.out.println("File selection cancelled.");
//        }
//    }
//    @FXML
//
//    private String generateRandomString(int length) {
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        Random rnd = new Random();
//        StringBuilder sb = new StringBuilder(length);
//
//        for (int i = 0; i < length; i++) {
//            int index = rnd.nextInt(characters.length());
//            sb.append(characters.charAt(index));
//        }
//
//        return sb.toString();
//    }
//
//    void ajouter_Reclamation()
//    {
//        String selectedSubject = initialize();
//
//        String generatedString = generateRandomString(20);
//        privateKey.setText(generatedString);
//        System.out.println(generatedString);
//
//        Reclamation   rec = new Reclamation(privateKey.getText(), title.getText(),selectedSubject ,description.getText());
//        si.ajouter(rec);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Confirmation");
//        alert.setHeaderText(null);
//        alert.setContentText("Reclmation has been added successfully!");
//
//        // Show the alert
//        alert.show();
//    }
//
//
//
//    public String initialize() {
//
//        ChoixMul.getItems().addAll("Problem technique", "application", "testt","omar salhi","khalil rmila ");
//        return (String) ChoixMul.getSelectionModel().getSelectedItem();
//    }
////    void ajouter_Reclamation()
////    {
////        String selectedSubject = initialize();
////        if (selectedSubject == null) {
////            // Handle the case where no item is selected.
////            // For example, you could show an error message to the user.
////        } else {
////            Reclamation rec = new Reclamation(privateKey.getText(), title.getText(), selectedSubject, description.getText());
////            si.ajouter(rec);
////
////        }
////    }
////
////
////
////    public String initialize() {
////        ChoixMul.getItems().removeAll(ChoixMul.getItems());
////        ChoixMul.getItems().addAll("Problem technique", "application", "testt","omar salhi","khalil rmila ");
////        return (String) ChoixMul.getSelectionModel().getSelectedItem();
////    }
//
//}
