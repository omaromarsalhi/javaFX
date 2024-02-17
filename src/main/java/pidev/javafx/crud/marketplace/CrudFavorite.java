package pidev.javafx.crud.marketplace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pidev.javafx.model.MarketPlace.Favorite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudFavorite {


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private static CrudFavorite instance;

    private CrudFavorite() {}

    public static CrudFavorite getInstance() {
        if (instance == null)
            instance = new CrudFavorite();
        return instance;
    }


    public ObservableList<Favorite> selectItems() {
        Favorite favorite = null;
        String sql = "SELECT * FROM favorite ";

        connect = ConnectionDB.connectDb();
        ObservableList<Favorite> favoriteList = FXCollections.observableArrayList();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                favorite=new Favorite(result.getInt("idFavorite"),
                        result.getInt("idUser"),
                        result.getString("specifications"));
                favoriteList.add(favorite);
            }
        } catch (SQLException e) {
            System.out.println("Error selecting items: " + e.getMessage());
        }
        return favoriteList;
    }

    public void addItem(Favorite favorite) {
        String sql = "INSERT INTO favorite "
                + "( idUser, specifications)"
                + " VALUES (?, ?)";

        connect = ConnectionDB.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, favorite.getIdUser());
            prepare.setString(2, favorite.getSpecifications());
            prepare.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }


    public void deleteItem(int id) {
        String sql = "UPDATE favorite  WHERE idFavorite = ?";

        connect = ConnectionDB.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt( 1,id );
            prepare.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting item: " + e.getMessage());
        }
    }


}