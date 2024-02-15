package pidev.javafx.model.MarketPlace;

import java.sql.Timestamp;

public class Serivice extends Product{
    public Serivice(int id, int idUser, String name, String descreption, String imgSource, Float price, Float quantity, Boolean state, Timestamp timestamp) {
        super( id, idUser, name, descreption, imgSource, price, quantity, state, timestamp, "SERVICE" );
    }

    public Serivice() {
    }
}
