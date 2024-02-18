package pidev.javafx.Services;

import pidev.javafx.Models.Comment;
import pidev.javafx.Models.Post;
import pidev.javafx.Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentService {
    Connection cnx = DataSource.getInstance().getCnx();

    public void ajouterComment(Comment comment) {
        String req = "INSERT INTO `comment_post`(`caption`, `dateComment`, `idPost`) VALUES (?,?,?)" ;
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, comment.getCaption());
            ps.setObject(2, comment.getDate());
            ps.setInt(3, comment.getIdPost());

            ps.executeUpdate();
            System.out.println("comment added !");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Comment> getAll(int idTest) {
        List<Comment> comments = new ArrayList<>();
        String req = "SELECT * FROM `comment_post` WHERE idPost=? ORDER BY dateComment DESC";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idTest);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt(1);
                String caption = res.getString(2);
                Timestamp timestamp = res.getTimestamp(3);
                int idPost = res.getInt(4);
                Comment c = new Comment(id, timestamp,caption, idPost);
                comments.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(comments);
        return comments;
    }
}
