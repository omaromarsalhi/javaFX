package pidev.javafx.crud.user;

import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.crud.user.IserviceUser;
import pidev.javafx.model.user.Municipalite;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceMunicipalite implements IserviceUser<Municipalite> {



    @Override
    public void ajouter(Municipalite municipalite) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
    }

    @Override
    public void modifier(Municipalite municipalite) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
    }

    @Override
    public void supprimer(int id) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
    }

    @Override
    public void supprimerByEmail(String email) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
    }

    @Override
    public Municipalite getOneById(int id) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        return null;
    }

    @Override
    public List<Municipalite> getAll() {
        Connection cnx = ConnectionDB.getInstance().getCnx();
            List<Municipalite> munis = new ArrayList<>();

            String req = "SELECT * FROM `municipalite`";

            try {
                Statement stmt = cnx.createStatement();
                ResultSet rs = stmt.executeQuery(req);


                while (((ResultSet) rs).next()) {
                    Municipalite muni = new Municipalite();
                    muni.setId(rs.getInt("idMunicipalite"));
                    muni.setName(rs.getString("name"));
                    muni.setAdresse(rs.getString("Adresse"));


                    munis.add(muni);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return munis;
        }
}

