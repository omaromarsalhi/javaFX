package pidev.javafx.model.reclamation;

import javafx.scene.Node;

public class Reclamation extends Node {

    private int idReclamation;
    private String privateKey;
    private String subject;
    private String date;
    private String description;
    private String imagePath;
    private int userId;

    public Reclamation(int idReclamation,int userId,String privateKey, String subject, String date, String description,String imagePath){
        this.idReclamation = idReclamation;
        this.userId=userId;
        this.subject = subject;
        this.date = date;
        this.description = description;
        this.privateKey = privateKey;
        this.imagePath = imagePath;
    }


    public Reclamation() {

    }




    public int getUser() {
        return userId;
    }

    public void setUser(int userId) {
        this.userId = userId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "idReclamation=" + idReclamation +
                ", privateKey='" + privateKey + '\'' +
                ", subject='" + subject + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
