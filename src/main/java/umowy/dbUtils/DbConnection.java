package umowy.dbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static final String CONN = "jdbc:sqlserver://localhost\\DESKTOP-SPD9T1I:1433;database=master;integratedSecurity=true;";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(CONN);
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
