package pidev.javafx.crud.blog;



import pidev.javafx.crud.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReactionService {
    public void ajouterReaction(String type,int idPost, int idCompte) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "INSERT INTO `reaction_post`(`idPost`, `idCompte`, `type`) VALUES (?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idPost);
            ps.setInt(2, idCompte);
            ps.setString(3,type);
            ps.executeUpdate();
            System.out.println("rection added added !");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void enleverReaction(int id, int idCompte) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "DELETE FROM `reaction_post` WHERE idPost=? AND idCompte=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.setInt(2, idCompte);
            ps.executeUpdate();
            System.out.println("reaction deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getReaction (int idPost, int idCompte) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "SELECT type FROM `reaction_post` WHERE idPost=? AND idCompte=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idPost);
            ps.setInt(2, idCompte);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String type = res.getString(1);
                return type;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void modifierReaction (int id, String type, int idCompte) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "UPDATE `reaction_post` SET `type`=? WHERE idPost=? AND idCompte=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(2, id);
            ps.setInt(3, idCompte);
            ps.setString(1, type);
            ps.executeUpdate();
            System.out.println("reaction updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int nbrReaction(int id) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "SELECT COUNT(*) FROM `reaction_post` WHERE idPost=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int count = res.getInt(1);
                return count;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public List<String> getTypeReaction(int idPost) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        List<String> types = new ArrayList<>();
        String req = "SELECT type FROM `reaction_post` WHERE idPost=? GROUP BY type ORDER BY COUNT(*) DESC";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idPost);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String type = res.getString("type");
                types.add(type);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return types;
    }
}
