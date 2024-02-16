package pidev.javafx.crud.reclamation;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
public interface Iservice <T> {
    public void ajouter(T t);
    public void modifier(T t);
    public void supprimer(String idReclamation);
    public T getOneById(int idReclamation);
    public Set<T> getAll();


}
