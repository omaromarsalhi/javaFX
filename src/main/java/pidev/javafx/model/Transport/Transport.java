package pidev.javafx.model.Transport;

import pidev.javafx.crud.DataSource;
import pidev.javafx.crud.marketplace.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class Transport {
private int idTransport;
private String type_vehicule;
    public String depart;
    public   String arivee;
private String Reference;

    @Override
    public String toString() {
        return "Transport{" +
                "idTransport=" + idTransport +
                ", type_vehicule='" + type_vehicule + '\'' +
                ", depart='" + depart + '\'' +
                ", arivee='" + arivee + '\'' +
                ", Reference='" + Reference + '\'' +
                ", Vehicule_Image='" + Vehicule_Image + '\'' +
                ", Prix=" + Prix +
                ", Heure=" + Heure +
                '}';
    }

    private String Vehicule_Image;

private Float Prix;

private Time Heure;

    public Transport() {
    }

    public Transport( String type_vehicule, String depart, String arivee, String reference, String vehicule_Image, Float prix, Time heure) {

        this.type_vehicule = type_vehicule;
        this.depart = depart;
        this.arivee = arivee;
        Reference = reference;
        Vehicule_Image = vehicule_Image;
        Prix = prix;
        Heure = heure;
    }


    public Boolean Supprimer(int id){
        String deleteQuery = "DELETE FROM transport WHERE idTransport = ?";
        try (Connection connection  = ConnectionDB.connectDb();

             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    };

    public int getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(int idTransport) {
        this.idTransport = idTransport;
    }

    public String getType_vehicule() {
        return type_vehicule;
    }

    public void setType_vehicule(String type_vehicule) {
        this.type_vehicule = type_vehicule;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArivee() {
        return arivee;
    }

    public void setArivee(String arivee) {
        this.arivee = arivee;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public String getVehicule_Image() {
        return Vehicule_Image;
    }

    public void setVehicule_Image(String vehicule_Image) {
        Vehicule_Image = vehicule_Image;
    }

    public Float getPrix() {
        return Prix;
    }

    public void setPrix(Float prix) {
        Prix = prix;
    }

    public Time getHeure() {
        return Heure;
    }

    public void setHeure(Time heure) {
        Heure = heure;
    }

    public void getReference(String reference) {
    }



}
