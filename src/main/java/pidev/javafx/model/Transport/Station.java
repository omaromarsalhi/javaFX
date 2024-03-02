package pidev.javafx.model.Transport;

import java.util.Objects;

public class Station {
    String nomStation;
    String image_station;
    String addressStation;
    String type_Vehicule;
    int    idStation;


    public Station(String nomStation, String image_station, String addressStation, String type_Vehicule, int idStation) {
        this.nomStation     = nomStation;
        this.image_station  = image_station;
        this.addressStation = addressStation;
        this.type_Vehicule  = type_Vehicule;
        this.idStation      = idStation;
    }


    public Station(String nomStation, String image_station, String addressStation, String type_Vehicule) {
        this.nomStation = nomStation;
        this.image_station = image_station;
        this.addressStation = addressStation;
        this.type_Vehicule = type_Vehicule;
    }

    public String getImage_station() {
        return image_station;
    }

    public void setImage_station(String image_station) {
        this.image_station = image_station;
    }


    @Override
    public String toString() {
        return "Station{" +
                "NomStation='" + nomStation + '\'' +
                ", AddressStation='" + addressStation + '\'' +
                ", Type_Vehicule='" + type_Vehicule + '\'' +
                ", idStation=" + idStation +
                '}';
    }

    public String getNomStation() {
        return nomStation;
    }

    public void setNomStation(String nomStation) {
        this.nomStation = nomStation;
    }

    public String getAddressStation() {
        return addressStation;
    }

    public void setAddressStation(String addressStation) {
        this.addressStation = addressStation;
    }
public Station(){}
    public Station(String nomStation, String addressStation, String type_Vehicule) {
        this.nomStation = nomStation;
        this.addressStation = addressStation;
        this.type_Vehicule = type_Vehicule;

    }

    public String getType_Vehicule() {
        return type_Vehicule;
    }

    public void setType_Vehicule(String type_Vehicule) {
        this.type_Vehicule = type_Vehicule;
    }

    public int getIdStation() {
        return idStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return idStation == station.idStation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStation);
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }
}
