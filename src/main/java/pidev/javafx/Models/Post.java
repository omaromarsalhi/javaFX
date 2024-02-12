package pidev.javafx.Models;

import pidev.javafx.Models.Account;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private int id;
    private Account account;
    private Timestamp date;
    private String caption;
    private String image;
    private int totalReactions;
    private int nbComments;

    public Post () {}

    public Post(int id , Timestamp date, String caption, String image, int totalReactions, int nbComments) {
        this.id = id;
        this.date = date;
        this.caption = caption;
        this.image = image;
        this.totalReactions = totalReactions;
        this.nbComments = nbComments;
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
                "account=" + account +
                ", date=" + date +
                ", caption='" + caption + '\'' +
                ", image='" + image + '\'' +
                ", totalReactions=" + totalReactions +
                ", nbComments=" + nbComments +
                '}';
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalReactions() {
        return totalReactions;
    }

    public void setTotalReactions(int totalReactions) {
        this.totalReactions = totalReactions;
    }

    public int getNbComments() {
        return nbComments;
    }

    public void setNbComments(int nbComments) {
        this.nbComments = nbComments;
    }
}
