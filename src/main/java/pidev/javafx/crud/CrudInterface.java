package pidev.javafx.crud;

import javafx.collections.ObservableList;

import java.util.Set;

public interface CrudInterface<T> {

    boolean addItem(T variable);

    void updateItem(T variable);

    ObservableList<T> selectItems();
    T findById(int id);
    T selectFirstItem();

    void deleteItem(int id);

    Set<T> getAll();
}

