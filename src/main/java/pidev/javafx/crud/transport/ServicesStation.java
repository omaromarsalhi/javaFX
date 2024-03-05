package pidev.javafx.crud.transport;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.crud.CrudInterface;
import pidev.javafx.model.Transport.Station;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.sql.SQLIntegrityConstraintViolationException;


public class ServicesStation    {

    private PreparedStatement prepare;

    public boolean addItem(Station S) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String sql = "INSERT INTO station (NomStation,AddressStation,Type_Vehicule,Image_Station) VALUES (?,?,?,?) ";
        try {
            prepare = cnx.prepareStatement(sql);
            prepare.setString(1,S.getNomStation());
            prepare.setString(2,S.getAddressStation());
            prepare.setString(3, S.getType_Vehicule());
            prepare.setString(4,S.getImage_station());

            prepare.executeUpdate();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur d'insertion");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public void updateItem(Station o) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String sql = "UPDATE `station` " +
                "SET  `NomStation`=?,`AddressStation`=?" +
                ",`Type_Vehicule`=?,`Image_Station`=? " +
                "WHERE idStation=?";


        try {
            prepare = cnx.prepareStatement(sql);
            prepare.setString(1,o.getNomStation());
            prepare.setString(2, o.getAddressStation());
            prepare.setString(3,o.getType_Vehicule() );
            prepare.setString(4, o.getImage_station());
            prepare.setInt(5,o.getIdStation());

            prepare.executeUpdate();
            System.out.println("oui");


        }
        catch (SQLIntegrityConstraintViolationException e) {

            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur d'insertion");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error inserting data.");
        }
    }

    public ObservableList<Station> selectItems() {
        return null;
    }

    public void deleteItem(int id) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String deleteQuery = "DELETE FROM station WHERE idStation = ?";
        try {
            prepare = cnx.prepareStatement(deleteQuery);
            prepare.setInt(1, id);
            prepare.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Station findById(int id) {
        return new Station();
    }


    public Station selectFirstItem() {
        return null;
    }

    public Set<Station> getAll() {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        Set <Station> abonnementList = new HashSet<>();
        String req = "Select * from station";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Station abs = new Station();
                abs.setAddressStation(resultSet.getString("AddressStation"));
                abs.setNomStation(resultSet.getString("NomStation"));
                abs.setType_Vehicule(resultSet.getString("Type_Vehicule"));
                abs.setImage_station(resultSet.getString("Image_Station"));
                abs.setIdStation(resultSet.getInt("idStation"));
                abonnementList.add(abs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return abonnementList;
    }
}
