package pidev.javafx.crud;

import javafx.collections.ObservableList;

import java.util.Set;

public interface CrudInterface<T> {

    void addItem(T variable);

    void updateItem(T variable);

    ObservableList<T> selectItems();
    T findById(int id);
    T selectFirstItem();

    void deleteItem(int id);
    void ajouter(T t);

    void modifier (T t);
    void supprimer(int id);
    void  getById(int id);
    public Set<T> getAll();
}

