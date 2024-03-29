package pidev.javafx.crud.ticket;

import javafx.collections.ObservableList;
import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.crud.CrudInterface;
import pidev.javafx.model.Ticket.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static pidev.javafx.model.Ticket.Ticket.idTicket;

public class ServiceTicket implements CrudInterface<Ticket> {

    Connection cnx = ConnectionDB.getInstance().getCnx();

    @Override
    public void addItem(Ticket variable) {
        String req = "INSERT INTO `ticket`( `customName`, `type`, `ticketNumber`) VALUES (?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, Ticket.getCustomName());
            ps.setString(2, Ticket.getType().toString());
            ps.setInt(3, Ticket.getTicketNumber());
            ps.executeUpdate();
            System.out.println("Ticket added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateItem(Ticket variable) {
        String req = "UPDATE `ticket` SET `customName`=?, `date`=?, `type`=?, `ticketNumber`=? WHERE `idTicket`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, Ticket.getCustomName());
            ps.setDate(2, new java.sql.Date(Ticket.getDate().getTime()));
            ps.setString(3, Ticket.getType());
            ps.setInt(4, Ticket.getTicketNumber());
            ps.setInt(5, Ticket.getIdTicket());
            ps.executeUpdate();
            System.out.println("Ticket updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ObservableList<Ticket> selectItems() {
        Set<Ticket> Tickets = new HashSet<>();
        String req = "SELECT * FROM `ticket`";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tickets.add(new Ticket(rs.getInt("idTicket"), rs.getString("customName"),  rs.getString("type"), rs.getInt("ticketNumber")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return (ObservableList<Ticket>) Tickets;
    }

    @Override
    public Ticket findById(int id) {
        Ticket ticket = null;
        String req = "SELECT * FROM `ticket` WHERE `idTicket`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ticket = new Ticket(rs.getInt("idTicket"), rs.getString("customName"), rs.getString("type"), rs.getInt("ticketNumber"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ticket;
    }

    @Override
    public Ticket selectFirstItem() {
        return null;
    }

    @Override
    public void deleteItem(int id) {
        String req = "DELETE FROM `ticket` WHERE `idTicket`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Ticket deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
