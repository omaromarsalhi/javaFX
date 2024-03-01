package pidev.javafx.model.blog;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Post {
    private int id;
    private int idCompte;
    private Timestamp date;
    private String caption;
    private int totalReactions;
    private List<String> images;

    public Post () {images = new ArrayList<>();}

    public Post(int id , Timestamp date, String caption, int idCompte, int totalReactions) {
        images = new ArrayList<>();
        this.id = id;
        this.date = date;
        this.caption = caption;
        this.idCompte = idCompte;
        this.totalReactions = totalReactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id : " + id +
                "account=" + idCompte +
                ", date=" + date +
                ", caption='" + caption + '\'' +
                ", totalReactions=" + totalReactions +
                '}';
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getIdCompte() {
        return idCompte;
    }
    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
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
    public int getTotalReactions() {
        return totalReactions;
    }
    public void setTotalReactions(int totalReactions) {
        this.totalReactions = totalReactions;
    }
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
