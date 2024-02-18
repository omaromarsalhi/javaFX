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
                Comment c = new Comment(id, caption,timestamp, idPost);
                comments.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(comments);
        return comments;
    }

    public void supprimer(int id) {
        String req = "DELETE FROM `comment_post` WHERE idComment=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("comment deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getLastId(){
        int lastId = -1;
        String req = "SELECT MAX(id) FROM `comment_post`";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            if (res.next()) {
                lastId = res.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lastId;
    }

    public void modifier(String caption, Timestamp date, int id) {
        String req = "UPDATE `comment_post` SET `caption`=?,`dateComment`=? WHERE idComment=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            System.out.println(id);
            ps.setString(1, caption);
            ps.setObject(2, date);
            ps.setInt(3, id);
            ps.executeUpdate();
            System.out.println("Post updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Comment getOneById(int id) {
        String req = "SELECT * FROM `comment_post` WHERE idComment=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int idc = res.getInt(1);
                String caption = res.getString(2);
                Timestamp timestamp = res.getTimestamp(3);
                int idPost = res.getInt(4);
                return new Comment(idc, caption, timestamp, idPost);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int nbrReaction(int id) {
        String req = "SELECT COUNT(*) FROM `comment_post` WHERE idPost=?";
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
