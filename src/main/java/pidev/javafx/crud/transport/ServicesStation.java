package pidev.javafx.crud.transport;


import javafx.collections.ObservableList;
import pidev.javafx.crud.CrudInterface;
import pidev.javafx.model.Transport.Station;


import pidev.javafx.crud.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ServicesStation implements CrudInterface<Station> {
    Connection cnx = DataSource.GetInstance().getCnx();;
    private PreparedStatement prepare;

    @Override
    public void addItem(Station S) {
        String sql = "INSERT INTO station (NomStation,AddressStation,Type_Vehicule ) VALUES (?,?,?) ";
        try {
            prepare = cnx.prepareStatement(sql);
            prepare.setString(1,S.getNomStation());
            prepare.setString(2,S.getAddressStation());
            prepare.setString(3, S.getType_Vehicule());

            prepare.executeUpdate();
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void updateItem(Station o) {

    }

    @Override
    public ObservableList<Station> selectItems() {
        return null;
    }

    @Override
    public void deleteItem(int id) {
        String deleteQuery = "DELETE FROM station WHERE idStation = ?";
        try {
            prepare = cnx.prepareStatement(deleteQuery);
            prepare.setInt(1, id);
            prepare.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Station findById(int id) {
return new Station();
    }

    @Override
    public Station selectFirstItem() {
        return null;
    }

    @Override
    public Set<Station> getAll() {
        Set <Station> abonnementList = new HashSet<>();
        String req = "Select * from station";


        try (PreparedStatement preparedStatement = cnx.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Station abs = new Station();
                abs.setAddressStation(resultSet.getString("AddressStation"));
                abs.setNomStation(resultSet.getString("NomStation"));
                abs.setType_Vehicule(resultSet.getString("Type_Vehicule"));
                abs.setIdStation(resultSet.getInt("idStation"));
                abonnementList.add(abs);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return abonnementList;
    }
}
