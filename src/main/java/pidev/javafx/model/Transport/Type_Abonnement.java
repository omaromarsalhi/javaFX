package pidev.javafx.model.Transport;

public class Type_Abonnement {
    private int id;
    private Float prix;
    private Type_Vehicule type_vehicule;

    public int getId() {
        return id;
    }

    public Float getPrix() {
        return prix;
    }

    public Type_Vehicule getType_vehicule() {
        return type_vehicule;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public void setType_vehicule(Type_Vehicule type_vehicule) {
        this.type_vehicule = type_vehicule;
    }
}
