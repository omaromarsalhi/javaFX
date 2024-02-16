package pidev.javafx.crud.transport;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pidev.javafx.Controller.ConnectionDB;
import pidev.javafx.entities.Transport.Abonnement;
import pidev.javafx.entities.Transport.Transport;
import pidev.javafx.utils.DataSource;

//import javax.swing.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;


public class ServicesTransport implements IServices <Transport> {

    Connection cnx = DataSource.GetInstance().getCnx();
    private PreparedStatement prepare;
    private Set abonnementList;


    @Override
    public void ajouter(Transport transport) {

        String sql = "INSERT INTO transport(Type_Vehicule,Depart,Arivee,Reference,Vehicule_Image,Prix,Heure) VALUES (?,?,?,?,?,?,?) ";

        try {
             prepare = cnx.prepareStatement(sql);
            prepare.setString(1, transport.getType_vehicule());
            prepare.setString(2, transport.getDepart());
            prepare.setString(3, transport.getArivee());
            prepare.setString(4, transport.getReference());
            prepare.setString(5, transport.getVehicule_Image());
            prepare.setFloat(6, transport.getPrix());
            prepare.setTime(7, transport.getHeure());
            prepare.executeUpdate();
            System.out.println("Personne added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Transport T) {
        cnx = ConnectionDB.connectDb();
        String sql = "UPDATE transport\n" +
                "SET Type_Vehicule=?, Depart=?, Arivee=?, Reference=?, Vehicule_Image=?, Prix=?, Heure=?\n" +
                "WHERE idTransport=?;\n ";

        try {
            prepare = cnx.prepareStatement(sql);
            prepare.setString(1, T.getType_vehicule());
            prepare.setString(2, T.getDepart());
            prepare.setString(3, T.getArivee());
            prepare.setString(4, T.getReference());
            prepare.setString(5, T.getVehicule_Image());
            prepare.setFloat(6, T.getPrix());
            prepare.setTime(7, T.getHeure());
            prepare.setInt(8, T.getIdTransport());
            prepare.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error inserting data.");
        }
    }

    @Override
    public void supprimer(int id) {
        String deleteQuery = "DELETE FROM transport WHERE idTransport = ?";
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
    public Set<Transport> getAll() {
Set<Transport> dataList =new HashSet<>();
        String sql = "SELECT * FROM transport";
        cnx = ConnectionDB.connectDb();

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {




            while (resultSet.next()) {
                Transport data = new Transport();

                data.setIdTransport(Integer.parseInt(resultSet.getString("idTransport")));
                data.setType_vehicule(resultSet.getString("Type_Vehicule"));
                data.setReference(resultSet.getString("Reference"));
                data.setDepart(resultSet.getString("Depart"));
                data.setArivee(resultSet.getString("Arivee"));
                data.setPrix((resultSet.getFloat("Prix")));
                data.setHeure((resultSet.getTime("Heure")));
                data.setVehicule_Image((resultSet.getString("Vehicule_Image")));

                dataList.add(data);

            }

     return  dataList;
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }}
