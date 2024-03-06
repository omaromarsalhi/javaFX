package pidev.javafx.controller.userMarketDashbord;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pidev.javafx.crud.user.ServiceUser;
import pidev.javafx.model.Contrat.Contract;
import pidev.javafx.model.MarketPlace.Transaction;
import pidev.javafx.model.user.User;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class TransactionDetailsController  implements Initializable {
    @FXML
    private Label Ldate;
    @FXML
    private Label Llocation;
    @FXML
    private Label LpaymentMode;
    @FXML
    private Label Lprice;
    @FXML
    private ImageView buyerImage;
    @FXML
    private Label buyerLastName;
    @FXML
    private Label buyerName;
    @FXML
    private VBox contratDetails;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button generatePdfBtn;
    @FXML
    private HBox purshaseDetailsBox;
    @FXML
    private ImageView sellerImage;
    @FXML
    private Label sellerLastName;
    @FXML
    private Label sellerName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(Transaction transaction, Contract contract){
        Lprice.setText(  transaction.getQuantity()*transaction.getPricePerUnit()+"$" );
        LpaymentMode.setText(contract.getPaymentMethod().toString() );
        Ldate.setText( DateTimeFormatter.ofPattern("dd/MM/yy hh:mm").format(transaction.getEffectiveDate().toLocalDateTime())  );
        Llocation.setText( contract.getRecingLocation() );

        var servideUser=new ServiceUser();
        User seller=servideUser.getUserById( transaction.getIdSeller());
        User buyer=servideUser.getUserById( transaction.getIdBuyer());

        sellerImage.setImage( new Image( "file:src/main/resources"+seller.getPhotos(),50,50,true,true) );
        sellerName.setText( seller.getFirstname() );
        sellerLastName.setText( seller.getLastname() );

        buyerImage.setImage( new Image( "file:src/main/resources"+buyer.getPhotos(),50,50,true,true) );
        buyerName.setText( buyer.getFirstname() );
        buyerLastName.setText( buyer.getLastname() );


    }

    public Button  getDownloadBtn() {
        return generatePdfBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }



}
