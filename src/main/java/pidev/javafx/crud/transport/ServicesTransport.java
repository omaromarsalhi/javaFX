package pidev.javafx.crud.transport;


import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import pidev.javafx.crud.CrudInterface;
import pidev.javafx.crud.DataSource;
import pidev.javafx.model.Transport.Transport;

//import javax.swing.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.sql.SQLIntegrityConstraintViolationException;


public class ServicesTransport implements CrudInterface<Transport> {

    Connection cnx = DataSource.GetInstance().getCnx();
    private PreparedStatement prepare;
    private Set abonnementList;


    public boolean addItem(Transport transport) {

        String sql = "INSERT INTO transport(Type_Vehicule,Station_depart,Station_arrive,Reference,Vehicule_Image,Prix,Heure) VALUES (?,?,?,?,?,?,?) ";

        try {
             prepare = cnx.prepareStatement(sql);
            prepare.setString(1, transport.getType_vehicule());
            prepare.setInt(2, transport.getStation_depart().getIdStation());
            prepare.setInt(3, transport.getStation_arrive().getIdStation());
            prepare.setString(4, transport.getReference());
            prepare.setString(5, transport.getVehicule_Image());
            prepare.setFloat(6, transport.getPrix());
            prepare.setTime(7, transport.getHeure());
            prepare.executeUpdate();
            System.out.println("Personne added !");

            return true;
        }
        catch (SQLIntegrityConstraintViolationException e) {
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reference duplicated");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
return false;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void updateItem(Transport T) {

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


        }
        catch (SQLIntegrityConstraintViolationException e) {

            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reference duplicated");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error inserting data.");

        }

    }

    @Override
    public ObservableList<Transport> selectItems() {
        return null;
    }

    public void deleteItem(int id) {
        String deleteQuery = "DELETE FROM transport WHERE idTransport = ?";
        try {
                prepare = cnx.prepareStatement(deleteQuery);
                prepare.setInt(1, id);
            prepare.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Transport findById(int id) {
return new Transport();
    }

    @Override
    public Transport selectFirstItem() {
        return null;
    }

    @Override
    public Set<Transport> getAll() {
Set<Transport> dataList =new HashSet<>();
        String sql = "SELECT transport.*, station.nom_station\n" +
                "FROM transport\n" +
                "LEFT JOIN station " +
                "ON transport.Station_depart = station.id_station\n" +
                "WHERE votre_condition;\n ";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {




            while (resultSet.next()) {
                Transport data = new Transport();

                data.setIdTransport(Integer.parseInt(resultSet.getString("idTransport")));
                data.setType_vehicule(resultSet.getString("Type_Vehicule"));
                data.setReference(resultSet.getString("Reference"));
             //  data.setDepart(resultSet.getInt("Station_depart"));
//                data.setArivee(resultSet.getInt("Station_arrive"));
                data.setPrix((resultSet.getFloat("Prix")));
                data.setHeure(resultSet.getTime("Heure"));
                data.setVehicule_Image((resultSet.getString("Vehicule_Image")));

                dataList.add(data);

            }

     return  dataList;
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }}
