package pidev.javafx.tools.transport;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.model.Transport.Abonnement;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.model.Transport.Abonnement;
import pidev.javafx.crud.CrudInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class rateStation {


    private PreparedStatement prepare;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);


    public void addItem(int rate, int user, int station) {
        Connection cnx = ConnectionDB.getInstance().getCnx();

        // Check if the user and station combination already exists
        boolean rowExists = checkRowExists(user, station);

        if (rowExists) {
            // If the row exists, update the rating
            updateRating(rate, user, station);
        } else {
            // If the row does not exist, insert a new row
            insertNewRow(rate, user, station);
        }
    }

    // Check if the user and station combination already exists in the database
    private boolean checkRowExists(int user, int station) {
        String checkQuery = "SELECT COUNT(*) AS row_count FROM rating WHERE id_user = ? AND id_station = ?";
        Connection cnx = ConnectionDB.getInstance().getCnx();

        try {
            PreparedStatement checkStatement = cnx.prepareStatement(checkQuery);
            checkStatement.setInt(1, user);
            checkStatement.setInt(2, station);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                int rowCount = resultSet.getInt("row_count");
                return rowCount > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    // Update the rating for an existing row with the given user and station values
    private void updateRating(int rate, int user, int station) {
        String updateQuery = "UPDATE rating SET rating = ? WHERE id_user = ? AND id_station = ?";
        Connection cnx = ConnectionDB.getInstance().getCnx();

        try {
            PreparedStatement updateStatement = cnx.prepareStatement(updateQuery);
            updateStatement.setInt(1, rate);
            updateStatement.setInt(2, user);
            updateStatement.setInt(3, station);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Insert a new row with the given user, station, and rating values
    private void insertNewRow(int rate, int user, int station) {
        String insertQuery = "INSERT INTO rating (rating, id_user, id_station) VALUES (?, ?, ?)";
        Connection cnx = ConnectionDB.getInstance().getCnx();

        try {
            PreparedStatement insertStatement = cnx.prepareStatement(insertQuery);
            insertStatement.setInt(1, rate);
            insertStatement.setInt(2, user);
            insertStatement.setInt(3, station);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public float calculateAverageRatingForStation(int idStation) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String averageQuery = "SELECT AVG(rating) AS moyenne FROM rating WHERE id_station = ?";

        try {
            // Retrieve the average rating for the specified station
            PreparedStatement averageStatement = cnx.prepareStatement(averageQuery);
            averageStatement.setInt(1, idStation);
            ResultSet resultSet = averageStatement.executeQuery();

            // Check if there is a result (assuming 'rating' is a float column)
            if (resultSet.next()) {
                float averageRating = resultSet.getFloat("moyenne");

                // Format the result to have one digit before and one digit after the decimal point
                float formattedResult = (float) (Math.round(averageRating * 10.0) / 10.0);

                return formattedResult;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return a default value or handle the case where no average is calculated
        return -1.0f;
    }


    public int calculateRaters(int idStation) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String averageQuery = "SELECT COUNT(rating) AS nbr FROM rating WHERE id_station = ?";

        try {
            // Retrieve the average rating for the specified station
            PreparedStatement averageStatement = cnx.prepareStatement(averageQuery);
            averageStatement.setInt(1, idStation);
            ResultSet resultSet = averageStatement.executeQuery();

            // Check if there is a result (assuming 'rating' is a float column)
            if (resultSet.next()) {
                int Raters = resultSet.getInt("nbr");

                return Raters;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return a default value or handle the case where no average is calculated
        return 1;
    }


    public int[] countDepartureAndArrivalStations(int idStation) {
        int[] counts = new int[2]; // Index 0: Departure stations, Index 1: Arrival stations

        Connection cnx = ConnectionDB.getInstance().getCnx();
        String departureQuery = "SELECT COUNT(*) AS total_departure " +
                "FROM transport " +
                "WHERE Station_depart = ?";
        String arrivalQuery = "SELECT COUNT(*) AS total_arrival " +
                "FROM transport " +
                "WHERE Station_arrive = ?";

        try (PreparedStatement departureStatement = cnx.prepareStatement(departureQuery);
             PreparedStatement arrivalStatement = cnx.prepareStatement(arrivalQuery)) {

            // Count departure stations
            departureStatement.setInt(1, idStation);
            ResultSet departureResultSet = departureStatement.executeQuery();
            if (departureResultSet.next()) {
                counts[0] = departureResultSet.getInt("total_departure");
            }

            // Count arrival stations
            arrivalStatement.setInt(1, idStation);
            ResultSet arrivalResultSet = arrivalStatement.executeQuery();
            if (arrivalResultSet.next()) {
                counts[1] = arrivalResultSet.getInt("total_arrival");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return counts;
    }

    public int getRating(int id){
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String averageQuery = "SELECT rating AS nbr FROM rating WHERE id_user = ?";

        try {
            // Retrieve the average rating for the specified station
            PreparedStatement averageStatement = cnx.prepareStatement(averageQuery);
            averageStatement.setInt(1, id);
            ResultSet resultSet = averageStatement.executeQuery();

            // Check if there is a result (assuming 'rating' is a float column)
            if (resultSet.next()) {
                int rate = resultSet.getInt("nbr");

                return rate;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return a default value or handle the case where no average is calculated
        return 1;

    };



    public int[] countBusAndMetroStations(int idStation) {
        int[] counts = new int[2]; // Index 0: Bus stations, Index 1: Metro stations

        Connection cnx = ConnectionDB.getInstance().getCnx();

        // Count bus stations
        String busQuery = "SELECT COUNT(*) AS total_bus " +
                "FROM transport " +
                "WHERE Station_depart = ? AND Type_Vehicule = 'Bus'";

        // Count metro stations
        String metroQuery = "SELECT COUNT(*) AS total_metro " +
                "FROM transport " +
                "WHERE Station_depart = ? AND Type_Vehicule = 'Metro'";

        try (PreparedStatement busStatement = cnx.prepareStatement(busQuery);
             PreparedStatement metroStatement = cnx.prepareStatement(metroQuery)) {

            // Execute the bus query
            busStatement.setInt(1, idStation);
            ResultSet busResultSet = busStatement.executeQuery();
            if (busResultSet.next()) {
                counts[0] = busResultSet.getInt("total_bus");
            }

            // Execute the metro query
            metroStatement.setInt(1, idStation);
            ResultSet metroResultSet = metroStatement.executeQuery();
            if (metroResultSet.next()) {
                counts[1] = metroResultSet.getInt("total_metro");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return counts;
    }



    public Abonnement selectFirstItem() {
        return null;
    }

    public Set<Abonnement> getAll() {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        Set<Abonnement> abonnementList = new HashSet<>();
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
