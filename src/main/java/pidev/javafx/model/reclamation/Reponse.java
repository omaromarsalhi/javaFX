package pidev.javafx.model.reclamation;

import java.util.Objects;

public class Reponse {
    private int id;
    private int idReclamation;
    private String description;

    public Reponse(int id, int idReclamation, String description) {
        this.id = id;
        this.idReclamation = idReclamation;
        this.description = description;
    }

    public Reponse() {}

    public Reponse(int id ,String description) {
        this.id = id;
        this.description=description;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReclamation() {
        return idReclamation;
    }

    public void setReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reponse response = (Reponse) o;
        return id == response.id && Objects.equals(idReclamation, response.idReclamation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idReclamation, description);
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", reclamation=" + idReclamation + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
