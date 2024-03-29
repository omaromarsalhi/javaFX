package pidev.javafx.model.blog;

import java.sql.Timestamp;
import java.util.Objects;

public class Comment {
    private int id;
    private Timestamp date;
    private String caption;
    private int idPost;
    private int idCompte;

    public Comment(int id, String caption, Timestamp date, int idPost, int idCompte) {
        this.id = id;
        this.date = date;
        this.caption = caption;
        this.idPost = idPost;
        this.idCompte = idCompte;
    }
    public  Comment () {}

    public int getId() {
        return id;
    }
    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }
    public int getIdPost() {
        return idPost;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdCompte() {
        return idCompte;
    }
    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id && Objects.equals(date, comment.date) && Objects.equals(caption, comment.caption);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", date=" + date +
                ", caption='" + caption + '\'' +
                '}';
    }


}
