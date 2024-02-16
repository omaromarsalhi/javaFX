package pidev.javafx.model.Transport;
import java.sql.Timestamp;
import java.util.Date;



public class Abonnement {
    private int idAboonnement;
    private String Type;
    private Timestamp dateDebut;
    private Date dateFin;
    private String Nom;
    private String  Image;

    public Abonnement(String nom, String prenom, String type, String image) {

        Nom = nom;
        Image = image;
        Prenom = prenom;
        Type=type;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Abonnement() {

    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "idAboonnement=" + idAboonnement +
                ", type='" + Type + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                '}';
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    private  String Prenom;

    public Abonnement(int idAboonnement, String type, Timestamp  dateDebut, Date dateFin) {
        this.idAboonnement = idAboonnement;
        this.Type = type;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Abonnement(Date dateFin, String nom, String prenom) {
        this.dateFin = dateFin;
        Nom = nom;
        Prenom = prenom;
    }

    public int getIdAboonnement() {
        return idAboonnement;
    }

    public String getType() {
        return Type;
    }



    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }


    public void setIdAboonnement(int idAboonnement) {
        this.idAboonnement = idAboonnement;
    }

    public void setType(String type) {
        this.Type = type;
    }


    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}
