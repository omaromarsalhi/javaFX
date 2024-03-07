package pidev.javafx.crud.reclamation;

import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.model.reclamation.Reponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ServiceReponse implements Iservice<Reponse> {
    Connection cnx = ConnectionDB.getInstance().getCnx();
    private static ServiceReponse instance;
    public static ServiceReponse getInstance() {
        if (instance == null)
            instance = new ServiceReponse();
        return instance;
    }

    public void ajouter(Reponse response) {
        String req = "INSERT INTO `response`(`id`, `id_reclamation`, `description`) VALUES (?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, response.getId());
            ps.setInt(2, response.getReclamation());
            ps.setString(3, response.getDescription());
            ps.executeUpdate();
            System.out.println("Response added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void modifier(Reponse response) {
        String req = "UPDATE `response` SET `id_reclamation`=?, `description`=? WHERE `id`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, response.getReclamation());
            ps.setString(2, response.getDescription());
            ps.setInt(3, response.getId());
            ps.executeUpdate();
            System.out.println("Response updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(String idReclamation) {
        String req = "DELETE FROM `reclamation` WHERE `privateKey`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, idReclamation);
            ps.executeUpdate();
            System.out.println("Reclamation deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void supprimer(int id) {
        String req = "DELETE FROM `response` WHERE `id`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Response deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Reponse getOneById(int id) {
        String req = "SELECT `id`,`id_reclamation`, `description` FROM `response` WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id); // Set the parameter value
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String description = res.getString("description");
                int reclamation = res.getInt("id_reclamation");
                return new Reponse(id, reclamation, description);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Response by id: " + e.getMessage());
        }
        return null;
    }
    public Set<Reponse> getAll() {
        Set<Reponse> responses = new HashSet<>();
        String req = "SELECT r.id, r.description, rm.idReclamtion, rm.PrivateKey, rm.Date, rm.Subject, rm.titre, rm.Description FROM `response` r JOIN `reclamation` rm ON r.id_reclamation = rm.idReclamtion";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet res = ps.executeQuery();
            while (res.next()){
                int id = res.getInt("id");
                String description = res.getString("description");
                int reclamation = res.getInt("id_reclamation");
                Reponse r = new Reponse(id, reclamation, description);
                responses.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return responses;
    }

}
