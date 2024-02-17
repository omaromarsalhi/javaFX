package pidev.javafx.Services;

import pidev.javafx.Utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommentService {
    Connection cnx = DataSource.getInstance().getCnx();

    public void ajouterComment(int idPost) {
        String req = "INSERT INTO `comment_post`(`caption`, `dateComment`, `idPost`) VALUES (?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setObject(1, idPost);

            ps.executeUpdate();
            System.out.println("rection added added !");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
