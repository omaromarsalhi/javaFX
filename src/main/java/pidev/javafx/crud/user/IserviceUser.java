package pidev.javafx.crud.user;

import pidev.javafx.model.user.User;

import java.util.List;

public interface IserviceUser<T> {
    public void ajouter(T t);


    public void modifier(T t);
    public void supprimer(int id);
    public void supprimerByEmail(String email);
    public T getOneById(int id);
    public List<T> getAll();

}
