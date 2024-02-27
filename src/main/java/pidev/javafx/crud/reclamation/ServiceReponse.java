package pidev.javafx.crud.reclamation;

import pidev.javafx.model.reclamation.Reclamation;
import pidev.javafx.model.reclamation.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ServiceReponse  implements  Iservice<Response> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Response response) {
        String req = "INSERT INTO `response`(`id`, `reclamation`, `description`, `etatReponse`) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, response.getId());
            ps.setString(2, response.getReclamation().toString());
            ps.setString(3, response.getDescription());
            ps.setString(4, response.getEtatReponse());
            ps.executeUpdate();
            System.out.println("Response added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Response response) {
        String req = "UPDATE `response` SET `reclamation`=?, `description`=?, `etatReponse`=? WHERE `id`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, response.getReclamation().toString());
            ps.setString(2, response.getDescription());
            ps.setString(3, response.getEtatReponse());
            ps.setInt(4, response.getId());
            ps.executeUpdate();
            System.out.println("Response updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(String idReclamation) {

    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `response` WHERE `id`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Response deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public Response getOneById(int id) {
        String req = "SELECT `id`,`id_reclamation`, `description`,`etatReponse` FROM `response` WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id); // Set the parameter value
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String description = res.getString("description");
                Reclamation reclamation = new Reclamation();
                reclamation.getIdReclamation(res.getInt("id_reclamation"));
                return new Response(id, reclamation, description, res.getString("etatReponse"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Response by id: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Set<Response> getAll() {
        Set<Response> responses = new HashSet<>();

        String req = "SELECT * FROM `response` WHERE 1";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet res = ps.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id");
                String description = res.getString("description");
                Reclamation reclamation = new Reclamation();
                reclamation.setIdReclamation(res.getInt("id_reclamation"));
                Response r = new Response(id, reclamation, description, res.getString("etatReponse"));
                responses.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return responses;
    }

    public Set<Response> getResponseByReclamationId(int id_reclamation) {
        Set<Response> responses = new HashSet<>();

        String req = "SELECT * FROM `response` WHERE id_reclamation = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id_reclamation);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                int id = res.getInt("id");
                String description = res.getString("description");
                Reclamation reclamation = new Reclamation();
                reclamation.setIdReclamation(res.getInt("id_reclamation"));
                Response r = new Response(id, reclamation, description, res.getString("etatReponse"));
                responses.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return responses;
    }

}


