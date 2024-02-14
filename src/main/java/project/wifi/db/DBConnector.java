package project.wifi.db;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static project.wifi.db.ConnectionConst.*;

public class DBConnector {

    public Connection connect() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
