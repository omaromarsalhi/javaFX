package pidev.javafx.crud.ticket;

import pidev.javafx.crud.reclamation.DataSource;
import pidev.javafx.crud.reclamation.Iservice;
import pidev.javafx.model.ticket.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ServiceTicket implements Iservice<Ticket> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Ticket ticket) {
        String req = "INSERT INTO `ticket`( `customName`, `type`, `ticketNumber`) VALUES (?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, ticket.getCustomName());
            ps.setString(2, ticket.getType().toString());
            ps.setInt(3, ticket.getTicketNumber());
            ps.executeUpdate();
            System.out.println("Ticket added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Ticket ticket) {
        String req = "UPDATE `ticket` SET `customName`=?, `date`=?, `type`=?, `ticketNumber`=? WHERE `idTicket`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, ticket.getCustomName());
            ps.setDate(2, new java.sql.Date(ticket.getDate().getTime()));
            ps.setString(3, ticket.getType());
            ps.setInt(4, ticket.getTicketNumber());
            ps.setInt(5, ticket.getIdTicket());
            ps.executeUpdate();
            System.out.println("Ticket updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(String idReclamation) {

    }

    @Override
    public void supprimer(int idTicket) {
        String req = "DELETE FROM `ticket` WHERE `idTicket`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idTicket);
            ps.executeUpdate();
            System.out.println("Ticket deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Ticket getOneById(int idTicket) {
        Ticket ticket = null;
        String req = "SELECT * FROM `ticket` WHERE `idTicket`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idTicket);
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
    public Set<Ticket> getAll() {
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
        return Tickets;
    }
}
