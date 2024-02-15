package pidev.javafx.model.MarketPlace;

import java.sql.Timestamp;

public class Bien extends Product{
    private Categorie categorie;

    public Bien() {
    }

    public Bien(int id, int idUser, String name, String descreption, String imgSource, Float price, Float quantity, Boolean state, Timestamp timestamp, Categorie categorie) {
        super( id, idUser, name, descreption, imgSource, price, quantity, state, timestamp, "BIEN" );
        this.categorie = categorie;
    }



    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
