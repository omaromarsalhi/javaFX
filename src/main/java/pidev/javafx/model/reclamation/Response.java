package pidev.javafx.model.reclamation;

import java.util.Objects;

public class Response {
    private int id;
    private Reclamation reclamation;
    private String description;
    private String etatReponse;

    public Response(int id, Reclamation reclamation, String description, String etatReponse) {
        this.id = id;
        this.reclamation = reclamation;

        this.description = description;
        this.etatReponse = etatReponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtatReponse() {
        return etatReponse;
    }

    public void setEtatReponse(String etatReponse) {
        this.etatReponse = etatReponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return id == response.id && Objects.equals(reclamation, response.reclamation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reclamation, description, etatReponse);
    }

    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", reclamation=" + reclamation + '\'' +
                ", description='" + description + '\'' +
                ", etatReponse='" + etatReponse + '\'' +
                '}';
    }
}