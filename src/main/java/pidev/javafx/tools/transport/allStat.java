package pidev.javafx.tools.transport;

import pidev.javafx.crud.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class allStat {



    public int[] stat( ) {
        int[] counts = new int[2]; // Index 0: Departure stations, Index 1: Arrival stations

        Connection cnx = ConnectionDB.getInstance().getCnx();
        String busQuery = "SELECT COUNT(*) AS total_departure " +
                "FROM station " +
                "WHERE Type_Vehicule = 'Bus'";
        String metroQuery = "SELECT COUNT(*) AS total_arrival " +
                "FROM station " +
                "WHERE Type_Vehicule = 'Metro'";
        String bothQuery = "SELECT COUNT(*) AS total_arrival " +
                "FROM station " +
                "WHERE Type_Vehicule = 'Both'";

        try (PreparedStatement departureStatement = cnx.prepareStatement(busQuery);
             PreparedStatement arrivalStatement = cnx.prepareStatement(metroQuery))
        {


            ResultSet departureResultSet = departureStatement.executeQuery();

            if (departureResultSet.next()) {
                counts[0] = departureResultSet.getInt("total_departure");
            }


            ResultSet arrivalResultSet = arrivalStatement.executeQuery();
            if (arrivalResultSet.next()) {
                counts[1] = arrivalResultSet.getInt("total_arrival");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return counts;
    }


    public int[] statAbn( ) {
        int[] counts = new int[2]; // Index 0: Departure stations, Index 1: Arrival stations

        Connection cnx = ConnectionDB.getInstance().getCnx();
        String busQuery = "SELECT COUNT(*) AS total_departure " +
                "FROM abonnement " +
                "WHERE  Type_Abonnement = 'Annuel'";
        String metroQuery = "SELECT COUNT(*) AS total_arrival " +
                "FROM station " +
                "WHERE Type_Abonnement = 'mensuel'";


        try (PreparedStatement departureStatement = cnx.prepareStatement(busQuery);
             PreparedStatement arrivalStatement = cnx.prepareStatement(metroQuery))
        {


            ResultSet departureResultSet = departureStatement.executeQuery();

            if (departureResultSet.next()) {
                counts[0] = departureResultSet.getInt("total_departure");
            }


            ResultSet arrivalResultSet = arrivalStatement.executeQuery();
            if (arrivalResultSet.next()) {
                counts[1] = arrivalResultSet.getInt("total_arrival");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return counts;
    }

    public int[] stat_station( int id) {
        int[] counts = new int[2]; // Index 0: Departure stations, Index 1: Arrival stations

        Connection cnx = ConnectionDB.getInstance().getCnx();
        String busQuery = "SELECT COUNT(*) AS total_departure " +
                "FROM transport " +
                "WHERE Type_Vehicule = 'Bus' AND idStation=?";
        String metroQuery = "SELECT COUNT(*) AS total_arrival " +
                "FROM station " +
                "WHERE Type_Vehicule = 'Metro' AND idStation=?";


        try (PreparedStatement departureStatement = cnx.prepareStatement(busQuery);
             PreparedStatement arrivalStatement = cnx.prepareStatement(metroQuery))
        {

            departureStatement.setInt(1, id);
            ResultSet departureResultSet = departureStatement.executeQuery();
            if (departureResultSet.next()) {
                counts[0] = departureResultSet.getInt("total_departure");
            }

            arrivalStatement.setInt(1, id);
            ResultSet arrivalResultSet = arrivalStatement.executeQuery();
            if (arrivalResultSet.next()) {
                counts[1] = arrivalResultSet.getInt("total_arrival");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return counts;
    }
}
