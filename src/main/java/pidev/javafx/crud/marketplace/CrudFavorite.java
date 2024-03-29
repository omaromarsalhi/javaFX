package pidev.javafx.crud.marketplace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.model.MarketPlace.Favorite;
import pidev.javafx.tools.UserController;

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
        String sql = "SELECT * FROM favorite where idUser= ? ";

        connect = ConnectionDB.getInstance().getCnx();
        ObservableList<Favorite> favoriteList = FXCollections.observableArrayList();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt( 1, UserController.getInstance().getCurrentUser().getId() );
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

    public int selectIdLastItem() {
        String sql = "SELECT idFavorite FROM favorite order by idFavorite limit 1";
        connect = ConnectionDB.getInstance().getCnx();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next())
                return result.getInt( 1 );
        } catch (SQLException e) {
            System.out.println("Error selecting items: " + e.getMessage());
        }
        return -1;
    }

    public void addItem(Favorite favorite) {
        String sql = "INSERT INTO favorite "
                + "( idUser, specifications)"
                + " VALUES (?, ?)";

        connect = ConnectionDB.getInstance().getCnx();

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
        String sql = "DELETE FROM favorite  WHERE idFavorite = ?";

        connect = ConnectionDB.getInstance().getCnx();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt( 1,id );
            prepare.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting item: " + e.getMessage());
        }
    }


}
