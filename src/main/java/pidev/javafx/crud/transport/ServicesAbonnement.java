package pidev.javafx.crud.transport;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import pidev.javafx.Controller.ConnectionDB;
import pidev.javafx.entities.Transport.Abonnement;
import pidev.javafx.entities.Transport.Transport;
import pidev.javafx.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class ServicesAbonnement implements IServices <Abonnement> {

    Connection cnx = DataSource.GetInstance().getCnx();
    private PreparedStatement prepare;
    private Set abonnementList;

Alert alert=new Alert(Alert.AlertType.CONFIRMATION);


    @Override
    public void ajouter(Abonnement a) {

        LocalDate futureDate;
        LocalDate currentDate = LocalDate.now();
        if(a.getType().equals("Annuel")) {
              futureDate = currentDate.plusDays(365);
        }
        else{
              futureDate = currentDate.plusDays(31);
        }
        Date sqlDate = Date.valueOf(futureDate);
        cnx = ConnectionDB.connectDb();
        String sql = "INSERT INTO `abonnement`( `Type_Abonnement`,`Nom`,`Prenom`, `Date_Fin`, `Image`) " +
                "VALUES (?,?,?,?,?)";
        try {
            prepare = cnx.prepareStatement(sql);
            prepare.setString(1,a.getType());
            prepare.setString(2, a.getNom());
            prepare.setString(3,a.getPrenom());
            prepare.setDate(4,sqlDate);
            prepare.setString(5,a.getImage());


            prepare.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier(Abonnement T) {

        cnx = ConnectionDB.connectDb();
        String sql = "UPDATE abonnement\n" +
                "SET Type_Abonnement=?, Nom=?, Prenom=?, Image=? \n" +
                "WHERE idAbonnement=?;\n ";

        try {
            prepare = cnx.prepareStatement(sql);
            prepare.setString(1,T.getType());
            prepare.setString(2, T.getNom());
            prepare.setString(3,T.getPrenom());
            prepare.setString(4,T.getImage());
            prepare.setInt(5,T.getIdAboonnement());
            prepare.executeUpdate();
            System.out.println("oui");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error inserting data.");
        }

    }

    @Override
    public void supprimer(int id) {
        String deleteQuery = "DELETE FROM abonnement WHERE idAbonnement = ?";
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
    public Set<Abonnement> getAll() {
        Set <Abonnement> abonnementList = new HashSet<>();
        String req = "Select * from abonnement";


        try (PreparedStatement preparedStatement = cnx.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Abonnement abs = new Abonnement();
                abs.setNom(resultSet.getString("Nom"));
                abs.setPrenom(resultSet.getString("Prenom"));
                abs.setType(resultSet.getString("Type_Abonnement"));
                abs.setDateDebut(resultSet.getTimestamp("Date_Debut"));
                abs.setDateFin(resultSet.getDate("Date_Fin"));
                abs.setIdAboonnement(resultSet.getInt("idAbonnement"));
                //System.out.println(abs);

                abonnementList.add(abs);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return abonnementList;


    }
}
