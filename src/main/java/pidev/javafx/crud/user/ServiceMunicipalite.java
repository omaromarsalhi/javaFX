package pidev.javafx.crud.user;

import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.crud.user.IserviceUser;
import pidev.javafx.model.user.Municipalite;


import java.sql.*;
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

            String req = "SELECT * FROM `municipaliter`";

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
    public int getOneByNomMunicipalite(String nomMunicipalite) {

        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "SELECT * FROM `municipaliter` where name=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, nomMunicipalite);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {//au moins 1

                return rs.getInt("idMunicipalite") ;
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return -1;


    }
    public List<Municipalite> rechercherMunicipalite(String recherche) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        Municipalite muni=new Municipalite();
        List<Municipalite> resultat = new ArrayList<>();
        String req = "SELECT * FROM municipalite WHERE CONCAT(name, ' ', adresse) LIKE ? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, "%" + recherche + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String adresse = rs.getString("adresse");
                muni = new Municipalite(name,adresse);
                resultat.add(muni);
                System.out.println(resultat);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultat;
    }
}

