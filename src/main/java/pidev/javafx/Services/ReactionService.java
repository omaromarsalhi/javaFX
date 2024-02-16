package pidev.javafx.Services;

import pidev.javafx.Models.Reactions;
import pidev.javafx.Utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReactionService {
    Connection cnx = DataSource.getInstance().getCnx();
    public void ajouterReaction(String type,int idPost) {
        String req = "INSERT INTO `reaction_post`(`idPost`, `type`) VALUES (?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setObject(1, idPost);
            ps.setString(2,type);
            ps.executeUpdate();
            System.out.println("rection added added !");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void enleverReaction(int id) {
        String req = "DELETE FROM `reaction_post` WHERE idPost=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("reaction deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getReaction (int id) {
        String req = "SELECT type FROM `reaction_post` WHERE idPost=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
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

    public void modifierReaction (int id, String type) {
        String req = "UPDATE `reaction_post` SET `type`=? WHERE idPost=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(2, id);
            ps.setString(1, type);
            ps.executeUpdate();
            System.out.println("Post updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int nbrReaction(int id) {
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
}
