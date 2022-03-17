package framepack.apiFunctions;

import framepack.utils.Utility;
import org.json.JSONArray;
import org.json.JSONObject;
import reports.ReportTrail;

import java.sql.*;
import java.util.*;


public class DbManager {

    static DbManager dbInstance;
    private DbManager(){

    }

    public static DbManager getDbInstance(){
        if (dbInstance == null){
            dbInstance = new  DbManager();
        }
        return dbInstance;
    }

    public Connection getDbConnection( String dbServerUrl,String dbUserName, String dbPassword){
        ReportTrail.info("Establishing a DB connection ");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbServerUrl,dbUserName,dbPassword);
        }catch (SQLException se){

        }
        return connection;
    }

    public ResultSet executeQuery(Connection connection, String queryToExecute)  {
        ReportTrail.info("Executing query on DB as " + queryToExecute);
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(queryToExecute);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    public static JSONArray resultSetToJsonArray(ResultSet rs) {
        ReportTrail.info("Converting the ResultSet into a jsonArray");
        JSONArray json = new JSONArray();
        ResultSetMetaData rsmd;
        try {
            rsmd = rs.getMetaData();
            while (rs.next()) {
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                for (int i = 1; i <= numColumns; i++) {
                    String column_name = rsmd.getColumnName(i);
                    obj.put(column_name, rs.getObject(column_name));
                }
                json.put(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return json;
    }

    public static List<Map<String, ?>> resultSetToListOfMap(ResultSet rs) {
        ReportTrail.info("Converting the ResultSet into a ListOfMap");
        ResultSetMetaData rsmd;
        List<Map<String, ?>> results = new ArrayList<Map<String, ?>>();
        try {
            rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<String, Object>();
                for (int i = 1; i <= columns; i++) {
                    row.put(rsmd.getColumnLabel(i), rs.getObject(i));
                }
                results.add(row);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return results;
    }


    public static HashMap<String, String> resultSetSingleRandomRowToHashMap(ResultSet rs) {
        ReportTrail.info("Converting the ResultSet into a ListOfMap");
        ResultSetMetaData rsmd;
        HashMap<String, String> resultRow = new HashMap<>();
        List<HashMap<String, String>> results = new ArrayList<>();
        try {
            rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            while (rs.next()) {
                HashMap<String, String> row = new HashMap<>();
                for (int i = 1; i <= columns; i++) {

                    row.put(rsmd.getColumnLabel(i), rs.getObject(i) == null? null : rs.getObject(i).toString());
                }
                results.add(row);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //Getting a random row of results and returning
        int size = results.size();
        int index = Utility.getRandomInt(0,size-1);
        resultRow = results.get(index);
        return resultRow;
    }

    public static HashMap<String, String> resultSetSingleRowToHashMap(ResultSet rs) {
        ReportTrail.info("Converting the ResultSet into a HashMap");
        ResultSetMetaData rsmd;
        HashMap<String, String> results = new HashMap<>();
        try {
            rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            rs.next();
            for (int i = 1; i <= columns; i++) {
                results.put(rsmd.getColumnLabel(i), rs.getObject(i) == null? null : rs.getObject(i).toString());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return results;
    }


}
