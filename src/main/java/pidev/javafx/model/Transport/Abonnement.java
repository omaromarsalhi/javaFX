package pidev.javafx.model.Transport;
import java.sql.Timestamp;
import java.util.Date;



public class Abonnement {
    private int idAboonnement;
    private  String prenom;
    private String type;
    private Timestamp dateDebut;
    private Date dateFin;
    private String nom;
    private String image;

    public Abonnement(String nom, String prenom, String type, String image) {

        this.nom = nom;
        this.image = image;
        this.prenom = prenom;
        this.type =type;
    }

    public Abonnement(int idAboonnement, String type, Timestamp  dateDebut, Date dateFin) {
        this.idAboonnement = idAboonnement;
        this.type = type;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Abonnement(Date dateFin, String nom, String prenom) {
        this.dateFin = dateFin;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Abonnement() {

    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "idAboonnement=" + idAboonnement +
                ", type='" + type + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", Nom='" + nom + '\'' +
                ", Prenom='" + prenom + '\'' +
                '}';
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }



    public int getIdAboonnement() {
        return idAboonnement;
    }

    public String getType() {
        return type;
    }



    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public java.sql.Date getDateFin() {
        return (java.sql.Date) dateFin;
    }


    public void setIdAboonnement(int idAboonnement) {
        this.idAboonnement = idAboonnement;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}
