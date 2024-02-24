package pidev.javafx.crud.reclamation;

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
        String req = "INSERT INTO `response`(`id`, `privateKey`, `description`, `reponse`) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, response.getId());
            ps.setString(2, response.getPrivateKey());
            ps.setString(3, response.getDescription());
            ps.setString(4, response.getResponse());
            ps.executeUpdate();
            System.out.println("Response added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Response response) {
        String req = "UPDATE `response` SET `privateKey`=?, `description`=?, `reponse`=? WHERE `id`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, response.getPrivateKey());
            ps.setString(2, response.getDescription());
            ps.setString(3, response.getResponse());
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
        Response response = null;
        String req = "SELECT * FROM `response` WHERE `id`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                response = new Response(rs.getInt("id"), rs.getString("privateKey"), rs.getString("description"), rs.getString("reponse"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return response;

    }

    @Override
    public Set<Response> getAll() {
        Set<Response> responses = new HashSet<>();
        String req = "SELECT * FROM `response`";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                responses.add(new Response(rs.getInt("id"), rs.getString("privateKey"), rs.getString("description"), rs.getString("reponse")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return responses;
    }
}


