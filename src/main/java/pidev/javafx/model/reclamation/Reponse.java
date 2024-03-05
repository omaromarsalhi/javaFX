package pidev.javafx.model.reclamation;

import java.util.Objects;

public class Reponse {
    private int id;
    private Reclamation reclamation;
    private String description;

    public Reponse(int id, Reclamation reclamation, String description) {
        this.id = id;
        this.reclamation = reclamation;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reponse response = (Reponse) o;
        return id == response.id && Objects.equals(reclamation, response.reclamation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reclamation, description);
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", reclamation=" + reclamation + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
