package pidev.javafx.Services;


import javafx.geometry.Pos;
import pidev.javafx.Models.Account;
import pidev.javafx.Models.Post;
import pidev.javafx.Utils.DataSource;
import java.sql.*;
import java.util.*;


public class BlogService implements IService<Post> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Post post) {
        String req = "INSERT INTO `post`(`date_post`, `caption`, `compte`, `nbReactions`) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setObject(1, post.getDate());
            ps.setString(2,post.getCaption());
            ps.setInt(3, post.getIdCompte());
            ps.setInt(4,post.getTotalReactions());
            ps.executeUpdate();
            System.out.println("Post added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Post post) {
        String req = "UPDATE `post` SET `date_post`=?, `caption`=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setObject(1, post.getDate());
            ps.setString(2, post.getCaption());
            ps.setInt(3, post.getId());
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
                int idCompte = res.getInt(4);
                int nbReactions = res.getInt(5);
                Post p = new Post(id, timestamp, caption, idCompte, nbReactions);
                p.setImages(getImages(id));
                return p;
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
                int idCompte = res.getInt(4);
                int nbReactions = res.getInt(5);
                Post p = new Post(id, timestamp, caption, idCompte, nbReactions);
                p.setImages(getImages(id));
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

    public List<Post> rechercherPosts(String recherche) {
        List<Post> resultat = new ArrayList<>();
        String req = "SELECT * FROM `post` WHERE `caption` LIKE ? OR `compte` IN (SELECT `id` FROM `compte` WHERE `nom` LIKE ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, "%" + recherche + "%");
            ps.setString(2, "%" + recherche + "%");
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt(1);
                Timestamp timestamp = res.getTimestamp(2);
                String caption = res.getString(3);
                int idCompte = res.getInt(4);
                int nbReactions = res.getInt(5);
                Post p = new Post(id, timestamp, caption, idCompte, nbReactions);
                p.setImages(getImages(id));
                resultat.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultat;
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

    public void modifierNombreReactions(int idPost, int nouveauNbReactions, Timestamp date) {
        String req = "UPDATE `post` SET `date_post`=?, `nbReactions`=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setObject(1, date);
            ps.setInt(2, nouveauNbReactions);
            ps.setInt(3, idPost);
            ps.executeUpdate();
            System.out.println("Nombre de réactions du post avec l'ID " + idPost + " mis à jour !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Post> getPostsTriesParNbReactions() {
        List<Post> posts = new ArrayList<>();
        String req = "SELECT * FROM `post` ORDER BY `nbReactions` DESC";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt(1);
                Timestamp timestamp = res.getTimestamp(2);
                String caption = res.getString(3);
                int idCompte = res.getInt(4);
                int nbReactions = res.getInt(5);
                Post p = new Post(id, timestamp, caption, idCompte, nbReactions);
                p.setImages(getImages(id));
                posts.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }

    public List<Post> getUnverifiedPosts() {
        List<Post> unverifiedPosts = new ArrayList<>();
        String req = "SELECT * FROM `post` p JOIN `compte` c ON p.compte = c.id WHERE c.verified = 0 ORDER BY p.date_post DESC";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt(1);
                Timestamp timestamp = res.getTimestamp(2);
                String caption = res.getString(3);
                int idCompte = res.getInt(4);
                int nbReactions = res.getInt(5);
                Post p = new Post(id, timestamp, caption, idCompte, nbReactions);
                p.setImages(getImages(id));
                unverifiedPosts.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return unverifiedPosts;
    }

    public List<Post> getVerifiedPosts() {
        List<Post> unverifiedPosts = new ArrayList<>();
        String req = "SELECT * FROM `post` p JOIN `compte` c ON p.compte = c.id WHERE c.verified = 1 ORDER BY p.date_post DESC";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt(1);
                Timestamp timestamp = res.getTimestamp(2);
                String caption = res.getString(3);
                int idCompte = res.getInt(4);
                int nbReactions = res.getInt(5);
                Post p = new Post(id, timestamp, caption, idCompte, nbReactions);
                p.setImages(getImages(id));
                unverifiedPosts.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return unverifiedPosts;
    }

    public void ajouterImages (String path, int idPost) {
        String req = "INSERT INTO `image_psot`(`path`, `idPost`) VALUES (?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,path);
            ps.setInt(2,idPost);
            ps.executeUpdate();
            System.out.println("img added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> getImages(int idPost) {
        List<String> imagePaths = new ArrayList<>();
        String req = "SELECT `path` FROM `image_psot` WHERE `idPost` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idPost);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String path = rs.getString("path");
                imagePaths.add(path);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving images: " + e.getMessage());
        }
        return imagePaths;
    }

    public void supprimerImages (int idPost) {
        String req = "DELETE FROM `image_psot` WHERE idPost=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idPost);
            ps.executeUpdate();
            System.out.println("image deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}


