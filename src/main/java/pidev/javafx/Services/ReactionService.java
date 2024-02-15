package pidev.javafx.Services;

import pidev.javafx.Models.Reactions;
import pidev.javafx.Utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
            System.out.println("Post added !");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
