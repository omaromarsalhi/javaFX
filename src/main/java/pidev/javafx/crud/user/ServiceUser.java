package pidev.javafx.crud.user;

import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.crud.user.IserviceUser;
import pidev.javafx.model.user.Role;
import pidev.javafx.model.user.User;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IserviceUser<User> {

    @Override
    public void ajouter(User user) { //ajouter citoyen
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "INSERT INTO `user`(`firstName`,`email`,`password`,`adresse`,`date`,`role`) VALUES (?,?,?,?,?,?)";
        try {

            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getAdresse());
            ps.setString(5, String.valueOf(LocalDate.now()));
            ps.setString(6, String.valueOf(user.getRole()));

            ps.executeUpdate();

            System.out.println("Personne added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void ajouteremploye(User user) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "INSERT INTO `user`(`firstName`,`lastname`,`email`,`age`,`num`,`password`,`adresse`,`date`,`role`,`cin`,`status`,`idMunicipalite`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());
            ps.setString(4, String.valueOf(user.getAge()));
            ps.setString(5, String.valueOf(user.getNum()));
            ps.setString(6, user.getPassword());
            ps.setString(7, user.getAdresse());
            ps.setString(8, String.valueOf(LocalDate.now()));
            ps.setString(9, String.valueOf(user.getRole()));
            ps.setString(10, user.getCin());
            ps.setString(11, user.getStatus());
            ps.setString(12, String.valueOf(user.getIdMunicipalite()));

            ps.executeUpdate();
            System.out.println("Personne added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void ajouterResponsable(User user) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "INSERT INTO `user`(`firstName`,`lastname`,`email`,`age`,`num`,`password`,`adresse`,`date`,`role`,`cin`,`status`,`idMunicipalite`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());
            ps.setString(4, String.valueOf(user.getAge()));
            ps.setString(5, String.valueOf(user.getNum()));
            ps.setString(6, user.getPassword());
            ps.setString(7, user.getAdresse());
            ps.setString(8, String.valueOf(LocalDate.now()));
            ps.setString(9, String.valueOf(user.getRole()));
            ps.setString(10, user.getCin());
            ps.setString(11, user.getStatus());
            ps.setString(12, String.valueOf(user.getIdMunicipalite()));
            System.out.println(user.getIdMunicipalite());

            ps.executeUpdate();
            System.out.println("Personne added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }



    @Override
    public void modifier(User user) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "UPDATE `user` SET `lastname` = ?, `age` = ?, `cin` = ?, `dob` = ?, `num` = ?, `status` = ?, `photos` = ?, `gender` = ?  WHERE `email` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, user.getLastname());
            ps.setInt(2, user.getAge());
            ps.setString(3, user.getCin());
            ps.setString(4, user.getDob());
            ps.setInt(5, user.getNum());
            ps.setString(6, user.getStatus());
            ps.setString(7, user.getPhotos());
            ps.setString(8, user.getGender());
            ps.setString(9, user.getEmail());
            System.out.println(user.getGender());
            ps.executeUpdate();
            System.out.println("User updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void isconnected(User user) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "UPDATE `user` SET `IsConnected`= ? WHERE  `email`= ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, user.getIsConnected());
            ps.setString(2,user.getEmail());
            System.out.println(user.getIsConnected());
            ps.executeUpdate();
            System.out.println("User updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void supprimer(int id) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "DELETE FROM `user` WHERE `cin`= ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("User deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void supprimerByEmail(String email) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "DELETE FROM `user` WHERE `email`= ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);
            ps.executeUpdate();
            System.out.println("User deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User getOneById(int id) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        return null;
    }


    @Override
    public List<User> getAll() {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM `user`";
        try {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(req);
            while (((ResultSet) rs).next()) {
                User user = new User();
                user.setFirstname(rs.getString("firstName"));
                user.setLastname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setIdMunicipalite((rs.getInt("idMunicipalite")));
                user.setAge(Integer.parseInt(rs.getString("age")));
                user.setCin(rs.getString("cin"));
                user.setPhotos(rs.getString("photos"));
                user.setAdresse(rs.getString("adresse"));
                user.setStatus(rs.getString("status"));
                user.setNum(Integer.parseInt(rs.getString("num")));
                user.setDate(rs.getString("date"));
                user.setDob(rs.getString("dob"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;

    }


    public boolean chercherParEmail(String email) {         //hethi bech nahiha
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "SELECT firstName,lastname,email FROM `user` where email=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {//au moins 1
                System.out.println(rs.getString("firstName") + " " + rs.getString("lastName") + " " + rs.getString("email"));
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }
    public int chercherParIsconnected() {//hethi bech nahiha
        Connection cnx = ConnectionDB.getInstance().getCnx();
        int idMunicipalite=-1;
        String req = "SELECT  idMunicipalite FROM `user` where IsConnected=1";

           try {
             PreparedStatement ps = cnx.prepareStatement(req);
             ResultSet rs = ps.executeQuery();
              if (rs.next()) {
                // Récupérez la valeur de la colonne idMunicipalite
                idMunicipalite = rs.getInt("idMunicipalite");
              }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return idMunicipalite;
    }

    public User findParEmail(String email) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "SELECT * FROM `user` WHERE email=?";
        User user = null;
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String firstname = rs.getString("firstName");
                String lastName = rs.getString("lastname");
                int age = rs.getInt("age");
                int num = rs.getInt("num");
                String adresse = rs.getString("adresse");
                String dob = rs.getString("dob");
                String cin = rs.getString("cin");
                String role = rs.getString("role");
                String status = rs.getString("status");
                String date = rs.getString("date");
                String photos = rs.getString("photos");
                String gender = rs.getString("gender");
                user = new User(firstname, email, cin, age, num, adresse, dob, lastName, status, date, Role.valueOf(role),photos,gender);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }


    public Boolean TestAuthentifier(String email, String password) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "SELECT COUNT(*) AS user_exists From user WHERE email = ? and password=?;";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.getInt("user_exists") == 1) {
                return true;
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return false;
    }
    public String RetriveHashedPassword(String email) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "SELECT password FROM user WHERE email=?";
        String password = null;

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                password = rs.getString("password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password;
    }
    public void modifierPassword(String email,String password) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "UPDATE `user` SET `password`=?WHERE `email` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, password);
            ps.setString(2, email);

            ps.executeUpdate();
            System.out.println("User updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void modifierEmail(String email,String newEmail) {
        Connection cnx = ConnectionDB.getInstance().getCnx();
        String req = "UPDATE `user` SET `email`=?WHERE `email` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, newEmail);
            ps.setString(2, email);

            ps.executeUpdate();
            System.out.println("User updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
