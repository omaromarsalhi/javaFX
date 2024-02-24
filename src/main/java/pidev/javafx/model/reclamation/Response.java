package pidev.javafx.model.reclamation;

import java.util.Objects;

public class Response {
    private int id;
      private String privateKey;
    private String description ;
    private String reponse;

    public Response(int id, String privateKey, String description, String reponse) {
        this.id = id;
        this.privateKey = privateKey;
        this.description = description;
        this.reponse = reponse;
    }

    public Response(int id, String privateKey, String description) {
        this.id = id;
        this.privateKey = privateKey;
        this.description = description;
    }

    public Response() {
    }


    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", privateKey='" + privateKey + '\'' +
                ", description='" + description + '\'' +
                ", reponse='" + reponse + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response reponse1 = (Response) o;
        return id == reponse1.id && Objects.equals(privateKey, reponse1.privateKey) && Objects.equals(description, reponse1.description) && Objects.equals(reponse, reponse1.reponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, privateKey, description, reponse);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponse() {
        return reponse;
    }

    public void setResponse(String reponse) {
        this.reponse = reponse;
    }
}
