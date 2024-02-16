package pidev.javafx.crud.transport;

import pidev.javafx.Controller.ConnectionDB;
import pidev.javafx.entities.Transport.Abonnement;
import pidev.javafx.entities.Transport.Station;
import pidev.javafx.entities.Transport.Transport;
import pidev.javafx.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ServicesStation implements IServices<Station>{
    Connection cnx = DataSource.GetInstance().getCnx();;
    private PreparedStatement prepare;

    @Override
    public void ajouter(Station S) {
        cnx = ConnectionDB.connectDb();
        String sql = "INSERT INTO stations (NomStation,AddressStation,Type_Vehicule ) VALUES (?,?,?) ";
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
    public void modifier(Station o) {

    }

    @Override
    public void supprimer(int id) {
        String deleteQuery = "DELETE FROM stations WHERE idStation = ?";
        try {
            prepare = cnx.prepareStatement(deleteQuery);
            prepare.setInt(1, id);
            prepare.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getById(int id) {

    }

    @Override
    public Set<Station> getAll() {
        Set <Station> abonnementList = new HashSet<>();
        String req = "Select * from stations";


        try (PreparedStatement preparedStatement = cnx.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Station abs = new Station();
                abs.setAddressStation(resultSet.getString("NomStation"));
                abs.setNomStation(resultSet.getString("AddressStation"));
                abs.setType_Vehicule(resultSet.getString("Type_Vehicule"));

                //System.out.println(abs);

                abonnementList.add(abs);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return abonnementList;
    }
}
