package pidev.javafx.model.Transport;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class Transport {
    private int idTransport;
    private String type_vehicule;
    private String Vehicule_Image;
    private Station station_depart;
    private Float Prix;
    private Station station_arrive;
    private Time Heure;
    private String Reference;



    public String depart;
    public void setStation_depart(Station station_depart) {
        this.station_depart = station_depart;
    }
    public Station getStation_depart() {
        return station_depart;
    }
    public void setStation_arrive(Station station_arrive) {
        this.station_arrive = station_arrive;
    }




    public Station getStation_arrive() {
        return station_arrive;
    }


    public   String arivee;

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
    public Transport( String type_vehicule, Station station_depart, Station station_arrive, String reference, String vehicule_Image, Float prix, Time heure) {
        this.idTransport = idTransport;
        this.type_vehicule = type_vehicule;
        this.station_depart = station_depart;
        this.station_arrive = station_arrive;
        Reference = reference;
        Vehicule_Image = vehicule_Image;
        Prix = prix;
        Heure = heure;
    }



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
