package pidev.javafx.model.reclamation;

import java.util.Date;

public class Reclamation {
private int idReclamation;
private String privateKey;
private String subject;
private String titre;
private Date date;
private String description;

public Reclamation(int idReclamation, String subject, String titre, Date date, String description){
    this.idReclamation = idReclamation;
    this.subject = subject;
    this.titre = titre;
    this.date = date;
    this.description = description;
}
    public Reclamation( String  privateKey ,String subject, String titre, String description){
        this.privateKey = privateKey;
        this.subject = subject;
        this.titre = titre;
        this.description = description;
    }

public Reclamation( String subject, String titre){
    this.subject = subject;
    this.titre = titre;
}
public Reclamation() { }

    public Reclamation(String privateKey, String subject, String titre, java.sql.Date date, String description) {
    this.privateKey = privateKey;
    this.subject = subject;
    this.titre = titre;
    this.date = date;
    this.description = description;
    }

    public int getId() {
        return idReclamation;
    }

    public void setId(int idReclamation) {
        this.idReclamation =idReclamation;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
