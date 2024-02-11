package pidev.javafx.Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource {

    private final String URL = "jdbc:mysql://localHost/pidev";
    private final String USER = "root";
    private final String PASSWORD = "";
    private static DataSource instance;
    private Connection cnx;

    private DataSource(){
        try {
            cnx = DriverManager.getConnection(URL,USER, PASSWORD);
            System.out.println("connection etablie");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static DataSource getInstance() {
        if(instance == null)
            instance = new DataSource();
        return instance;
    }

    public Connection getCnx(){
        return this.cnx;
    }
}
