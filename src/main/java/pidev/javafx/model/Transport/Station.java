package pidev.javafx.model.Transport;

import java.util.Objects;

public class Station {

    String NomStation;
    String AddressStation;
    String Type_Vehicule;
    int idStation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return idStation == station.idStation && Objects.equals(NomStation, station.NomStation) && Objects.equals(AddressStation, station.AddressStation) && Objects.equals(Type_Vehicule, station.Type_Vehicule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NomStation, AddressStation, Type_Vehicule, idStation);
    }

    @Override
    public String toString() {
        return "Station{" +
                "NomStation='" + NomStation + '\'' +
                ", AddressStation='" + AddressStation + '\'' +
                ", Type_Vehicule='" + Type_Vehicule + '\'' +
                ", idStation=" + idStation +
                '}';
    }

    public String getNomStation() {
        return NomStation;
    }

    public void setNomStation(String nomStation) {
        NomStation = nomStation;
    }

    public String getAddressStation() {
        return AddressStation;
    }

    public void setAddressStation(String addressStation) {
        AddressStation = addressStation;
    }
public Station(){}
    public Station(String nomStation, String addressStation, String type_Vehicule) {
        NomStation = nomStation;
        AddressStation = addressStation;
        Type_Vehicule = type_Vehicule;

    }

    public String getType_Vehicule() {
        return Type_Vehicule;
    }

    public void setType_Vehicule(String type_Vehicule) {
        Type_Vehicule = type_Vehicule;
    }

    public int getIdStation() {
        return idStation;
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }
}
