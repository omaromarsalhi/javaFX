package pidev.javafx.crud.transport;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import pidev.javafx .crud.DataSource;
import pidev.javafx.model.Transport.Abonnement;
import  pidev.javafx.crud.CrudInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


public class ServicesAbonnement implements CrudInterface <Abonnement> {


    private PreparedStatement prepare;

Alert alert=new Alert(Alert.AlertType.CONFIRMATION);


    @Override
    public boolean addItem(Abonnement a) {
        Connection cnx = DataSource.GetInstance().getCnx();

        LocalDate futureDate;
        LocalDate currentDate = LocalDate.now();
        if(a.getType().equals("Annuel")) {
              futureDate = currentDate.plusDays(365);
            java.sql.Date sqlDate = java.sql.Date.valueOf(futureDate);
        }
        else{
              futureDate = currentDate.plusDays(31);

            java.sql.Date sqlDate = java.sql.Date.valueOf(futureDate);


        }
        Date sqlDate = Date.valueOf(futureDate);
        String sql = "INSERT INTO `abonnement`( `Type_Abonnement`,`Nom`,`Prenom`, `dateFin`, `Image`) " +
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
        return false;
    }
    @Override
    public Abonnement findById(int id) {
        Connection cnx = DataSource.GetInstance().getCnx();
        String req = "SELECT * FROM abonnement WHERE idAbonnement = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Abonnement abs = new Abonnement();
                abs.setIdAboonnement(resultSet.getInt("idAbonnement"));
                abs.setNom(resultSet.getString("Nom"));
                abs.setPrenom(resultSet.getString("Prenom"));
                abs.setType(resultSet.getString("Type_Abonnement"));
                abs.setDateDebut(resultSet.getTimestamp("dateDebut"));
                abs.setDateFin(resultSet.getDate("dateFin"));
                abs.setImage(resultSet.getString("image"));
                return abs;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Abonnement: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateItem(Abonnement T) {
        Connection cnx = DataSource.GetInstance().getCnx();
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
    public ObservableList<Abonnement> selectItems() {
        return null;
    }

    @Override
    public void deleteItem(int id) {
        Connection cnx = DataSource.GetInstance().getCnx();
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
    public Abonnement selectFirstItem() {
        return null;
    }

    @Override
    public Set<Abonnement> getAll() {
        Connection cnx = DataSource.GetInstance().getCnx();
        Set <Abonnement> abonnementList = new HashSet<>();
        String req = "Select * from abonnement";


        try (PreparedStatement preparedStatement = cnx.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                Abonnement abs = new Abonnement();
                abs.setNom(resultSet.getString("Nom"));
                abs.setPrenom(resultSet.getString("Prenom"));
                abs.setType(resultSet.getString("Type_Abonnement"));
                abs.setDateDebut(resultSet.getTimestamp("dateDebut"));
                abs.setDateFin(resultSet.getDate("dateFin"));
                abs.setIdAboonnement(resultSet.getInt("idAbonnement"));
                abs.setImage(resultSet.getString("image"));
                abonnementList.add(abs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return abonnementList;


    }
}
