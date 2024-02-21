package pidev.javafx.Services;


import pidev.javafx.Models.Account;
import pidev.javafx.Models.Post;
import pidev.javafx.Utils.DataSource;
import java.sql.*;
import java.util.*;


public class BlogService implements IService<Post> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Post post) {
        String req = "INSERT INTO `post`(`date_post`, `caption`, `image`, `compte`, `nbReactions`, `nbComments`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setObject(1, post.getDate());
            ps.setString(2,post.getCaption());
            ps.setString(3,post.getImage());
            ps.setInt(4, post.getIdCompte());
            ps.setInt(5,post.getTotalReactions());
            ps.setInt(6,post.getNbComments());
            ps.executeUpdate();
            System.out.println("Post added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Post post) {
        String req = "UPDATE `post` SET `date_post`=?, `caption`=?, `image`=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setObject(1, post.getDate());
            ps.setString(2, post.getCaption());
            ps.setString(3, post.getImage());
            ps.setInt(4, post.getId());
            ps.executeUpdate();
            System.out.println("Post updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `post` WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Post deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Post getOneById(int id) {
        String req = "SELECT * FROM `post` WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                Timestamp timestamp = res.getTimestamp(2);
                String caption = res.getString(3);
                String img = res.getString(4);
                int idCompte = res.getInt(5);
                int nbReactions = res.getInt(6);
                int nbComments = res.getInt(7);
                return new Post(id, timestamp, caption, img, idCompte, nbReactions, nbComments);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String req = "SELECT * FROM `post` ORDER BY date_post DESC";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt(1);
                Timestamp timestamp = res.getTimestamp(2);
                String caption = res.getString(3);
                String img = res.getString(4);
                int idCompte = res.getInt(5);
                int nbReactions = res.getInt(6);
                int nbComments = res.getInt(7);
                Post p = new Post(id, timestamp, caption, img, idCompte, nbReactions, nbComments);
                posts.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }
    public int getLastId () {
        int lastId = -1;
        String req = "SELECT MAX(id) FROM `post`";
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

    public Account getComte (int id){
        String req = "SELECT * FROM `compte` WHERE id=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int idc = res.getInt(1);
                String nom = res.getString(2);
                String img = res.getString(3);
                boolean verified = res.getBoolean(4);
                return new Account(idc, nom, img, verified);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}


