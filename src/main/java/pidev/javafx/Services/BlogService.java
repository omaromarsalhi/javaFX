package pidev.javafx.Services;

import pidev.javafx.Models.Post;
import pidev.javafx.Utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;

public class BlogService implements IService<Post> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Post post) {
        String req = "INSERT INTO `post`(`date_post`, `caption`, `image`, `nbReactions`, `nbComments`) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setObject(1, post.getDate());
            ps.setString(2,post.getCaption());
            ps.setString(3,post.getImage());
            ps.setInt(4,post.getTotalReactions());
            ps.setInt(5,post.getNbComments());

            ps.executeUpdate();
            System.out.println("Post added !");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Post post) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Post getOneById(int id) {
        return null;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();

        String req = "SELECT * FROM `post` ORDER BY id DESC";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){

                int id = res.getInt(1);

                Timestamp timestamp = res.getTimestamp(2);
                //Timestamp timestamp = timestamp.toLocalDateTime();

                String caption = res.getString(3);
                String img = res.getString(4);
                int nbReactions = res.getInt(5);
                int nbComments = res.getInt(6);


                Post p = new Post(id,timestamp,caption,img, nbReactions, nbComments);
                posts.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }
}
