package pidev.javafx.model.MarketPlace;

public class Favorite {

    private int idFavorite;
    private int idUser;
    private String specifications;

    public Favorite(int idFavorite, int idUser, String specifications) {
        this.idFavorite = idFavorite;
        this.idUser = idUser;
        this.specifications = specifications;
    }

    public Favorite() {
    }

    public int getIdFavorite() {
        return idFavorite;
    }

    public void setIdFavorite(int idFavorite) {
        this.idFavorite = idFavorite;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
}
