package framepack.apiFunctions;

import reports.ReportTrail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBFunctions {

    public static Connection getDbConnection() {
        String serverName = "";
        String portNumber = "";
        String userName = "";
        String passwd = "";
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", passwd);
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://" +
                            serverName +
                            ":" + portNumber + "/",
                    connectionProps);
        } catch (SQLException e) {
            ReportTrail.error("Encountered error while connecting to DB.");
            e.printStackTrace();
        }
        ReportTrail.info("Connected to database");
        return conn;
    }

}
