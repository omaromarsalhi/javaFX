package pidev.javafx.model.Transport;

public class Station {

    String NomStation;
    String AddressStation;
    String Type_Vehicule;
    int idStation;

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
