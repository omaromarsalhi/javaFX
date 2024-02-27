package pidev.javafx.crud.ticket;

import pidev.javafx.crud.reclamation.Iservice;
import pidev.javafx.model.ticket.ticket;

import java.util.Set;

public class ServiceTicket implements Iservice<ticket> {

    @Override
    public void ajouter(ticket ticket) {

    }

    @Override
    public void modifier(ticket ticket) {

    }

    @Override
    public void supprimer(String idReclamation) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public ticket getOneById(int idReclamation) {
        return null;
    }

    @Override
    public Set<ticket> getAll() {
        return null;
    }
}
