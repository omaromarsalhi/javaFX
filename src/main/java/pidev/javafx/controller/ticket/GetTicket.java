package pidev.javafx.controller.ticket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pidev.javafx.crud.ticket.ServiceTicket;
import pidev.javafx.model.Ticket.Ticket;


public class GetTicket {

    @FXML
    private VBox Box1;

    @FXML
    private TextField customNa1;

    @FXML
    private HBox formBox;

    @FXML
    private HBox formBox1;

    @FXML
    private HBox formBox2;

    @FXML
    private HBox formBox3;

    @FXML
    private Button imageBtn;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField ticketNuM1;

    @FXML
    private TextField typeT1;
    ServiceTicket si=new ServiceTicket();

    @FXML
    void ajouterTicket(ActionEvent event) {
        Ticket ticket = new Ticket(customNa1.getText(),typeT1.getText(), Integer.parseInt(ticketNuM1.getText()));
        si.addItem(ticket);
    }

}
