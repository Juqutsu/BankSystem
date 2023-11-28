import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {

    public void sequel() {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306";
        String dbName = "benutzer";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String password = "otto1.";

        try {
            // Register JDBC driver
            Class.forName(driver);

            // Open a connection
            Connection connection = DriverManager.getConnection(url + dbName, userName, password);

            System.out.println("Connected to the database");

            // Close the connection
            connection.close();
            System.out.println("Connection closed");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
