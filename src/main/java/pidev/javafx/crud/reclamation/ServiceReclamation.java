package pidev.javafx.crud.reclamation;



import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.crud.marketplace.CrudBien;
import pidev.javafx.model.reclamation.Reclamation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ServiceReclamation  {

    Connection cnx = ConnectionDB.getInstance().getCnx();




    private static ServiceReclamation instance;

    private ServiceReclamation() {}

    public static ServiceReclamation getInstance() {
        if (instance == null)
            instance = new ServiceReclamation();
        return instance;
    }

    public void ajouter(Reclamation reclamation) {
        String req = "INSERT INTO `reclamation`(`privateKey`, `subject`, `description`, imagePath) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, reclamation.getPrivateKey());
            ps.setString(2, reclamation.getSubject());
            ps.setString(3, reclamation.getDescription());
            ps.setString(4, reclamation.getImagePath());
            ps.executeUpdate();
            System.out.println("Reclamation added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void modifier(Reclamation reclamation) {
        String req = "UPDATE `reclamation` SET `subject`=?, `description`=? WHERE `privateKey`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, reclamation.getSubject());
            ps.setString(2, reclamation.getDescription());
            ps.setString(3, reclamation.getPrivateKey());
            ps.executeUpdate();
            System.out.println("Reclamation updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void supprimer(String idReclamation) {
        String req = "DELETE FROM `reclamation` WHERE `idReclamtion`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, idReclamation);
            ps.executeUpdate();
            System.out.println("Reclamation deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public Reclamation getOneById(int idReclamation) {
        Reclamation reclamation = null;
        String req = "SELECT * FROM `reclamation` WHERE `idReclamtion`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idReclamation);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reclamation = new Reclamation(rs.getInt("idReclamtion"),rs.getString("privateKey"), rs.getString("subject"), rs.getDate("date").toString(), rs.getString("description"), rs.getString("imagePath"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamation;
    }


    public Set<Reclamation> getAll() {
        Set<Reclamation> reclamations = new HashSet<>();
        String req = "SELECT * FROM `reclamation`";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.toString());
                reclamations.add(new Reclamation(rs.getInt("idReclamtion"),rs.getString("privateKey"), rs.getString("subject"), rs.getDate("date").toString(), rs.getString("description"), rs.getString("imagePath")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamations;
    }


    public Set<String> getAllPrivateKeys() {
        Set<String> privateKeys = new HashSet<>();
        String req = "SELECT privateKey FROM `reclamation`";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                privateKeys.add(rs.getString("privateKey"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return privateKeys;
    }

}
