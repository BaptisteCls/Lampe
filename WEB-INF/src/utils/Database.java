package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection getConnection(String dbName){
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/"+dbName;
            String nom = "postgres";
            String mdp = "2004";
            return DriverManager.getConnection(url,nom,mdp);
        } catch (Exception e) {}
        return null;
    }
  }
