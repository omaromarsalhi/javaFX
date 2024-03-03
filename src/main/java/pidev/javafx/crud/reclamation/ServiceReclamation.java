package pidev.javafx.crud.reclamation;



import pidev.javafx.model.reclamation.Reclamation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ServiceReclamation implements Iservice<Reclamation> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Reclamation reclamation) {
        String req = "INSERT INTO `reclamation`(`privateKey`, `subject`, `titre`, `description`) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, reclamation.getPrivateKey());
            ps.setString(2, reclamation.getSubject());
            ps.setString(3, reclamation.getTitre());
            ps.setString(4, reclamation.getDescription());
            ps.executeUpdate();
            System.out.println("Reclamation added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Reclamation reclamation) {
        String req = "UPDATE `reclamation` SET `subject`=?, `titre`=?, `description`=? WHERE `privateKey`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, reclamation.getSubject());
            ps.setString(2, reclamation.getTitre());
            ps.setString(3, reclamation.getDescription());
            ps.setString(4, reclamation.getPrivateKey());
            ps.executeUpdate();
            System.out.println("Reclamation updated !");
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

    @Override
    public Reclamation getOneById(int idReclamation) {
        Reclamation reclamation = null;
        String req = "SELECT * FROM `reclamation` WHERE `idReclamation`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idReclamation);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reclamation = new Reclamation(rs.getInt("privateKey"), rs.getString("subject"), rs.getString("titre"), rs.getDate("date"), rs.getString("description"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamation;
    }

    @Override
    public Set<Reclamation> getAll() {
        Set<Reclamation> reclamations = new HashSet<>();
        String req = "SELECT * FROM `reclamation`";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reclamations.add(new Reclamation(rs.getString("privateKey"), rs.getString("subject"), rs.getString("titre"), rs.getDate("date"), rs.getString("description")));
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
