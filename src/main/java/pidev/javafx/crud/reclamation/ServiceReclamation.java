package pidev.javafx.crud.reclamation;



import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.model.Reclamation.Reclamation;

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
<<<<<<< HEAD
        String req = "INSERT INTO `reclamation`(`privateKey`, `subject`, `titre`, `description`, `imagePath`) VALUES (?,?,?,?,?)";
=======
        String req = "INSERT INTO `reclamation`(`privateKey` ,`idUser`, `subject`, `description`, imagePath) VALUES (?,?,?,?,?)";
>>>>>>> main
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, reclamation.getPrivateKey());
            ps.setInt(2, reclamation.getUser());
            ps.setString(3, reclamation.getSubject());
            ps.setString(4, reclamation.getDescription());
<<<<<<< HEAD
            ps.setString(5, reclamation.getImagePath()); // Add the image path
=======
            ps.setString(5, reclamation.getImagePath());
>>>>>>> main
            ps.executeUpdate();
            System.out.println("Reclamation added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


<<<<<<< HEAD
    @Override
=======
>>>>>>> main
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


    public void supprimer(int idReclamation) {
        String req = "DELETE FROM `reclamation` WHERE `idReclamtion`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idReclamation);
            ps.executeUpdate();
            System.out.println("Reclamation deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

<<<<<<< HEAD
    @Override
    public void supprimer(int id) {

    }

    @Override
=======

>>>>>>> main
    public Reclamation getOneById(int idReclamation) {
        Reclamation reclamation = null;
        String req = "SELECT * FROM `reclamation` WHERE `idReclamtion`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idReclamation);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reclamation = new Reclamation(rs.getInt("idReclamtion"),-1,rs.getString("privateKey"), rs.getString("subject"), rs.getDate("date").toString(), rs.getString("description"), rs.getString("imagePath"));
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
<<<<<<< HEAD
                reclamations.add(new Reclamation(rs.getString("privateKey"), rs.getString("subject"), rs.getString("titre"), rs.getDate("date"), rs.getString("description"), rs.getString("imagePath")));
=======
                System.out.println(rs.toString());
                reclamations.add(new Reclamation(rs.getInt("idReclamtion"),-1,rs.getString("privateKey"), rs.getString("subject"), rs.getDate("date").toString(), rs.getString("description"), rs.getString("imagePath")));
>>>>>>> main
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamations;
    }
    public Set<Reclamation> getAllbyid(int i) {
        Set<Reclamation> reclamations = new HashSet<>();
        String req = "SELECT * FROM `reclamation` where idUser = ? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.toString());
                reclamations.add(new Reclamation(rs.getInt("idReclamtion"),rs.getInt("idUser"),rs.getString("privateKey"), rs.getString("subject"), rs.getDate("date").toString(), rs.getString("description"), rs.getString("imagePath")));
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
    public  String getReponsebyid(int id )
    {
        String reponse=null;

        String req = "SELECT `description` FROM `response` WHERE id_reclamation = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                 reponse = rs.getString("description");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reponse;

    }




}
