package pidev.javafx.crud;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    String url="jdbc:mysql://localhost:3306/pi-dev";
    String name="root";
    String passwd="";
    Connection connect;
    static DataSource instance;
    private DataSource(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connect=DriverManager.getConnection(url,name,passwd);
            System.out.println("success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataSource GetInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        ;
        return instance;

    }


    public Connection getCnx(){
        return this.connect;
    }
}
