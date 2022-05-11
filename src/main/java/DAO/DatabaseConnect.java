package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This Class will be used to Connect our Server to our SQL Database
 * */
public class DatabaseConnect {
    /** A Connection object is necessary to join the java server and sql database */
    private Connection connect;

    /**
     * Function to open the connection with the database
     * */
    public Connection openConnection() throws DataAccessException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:FamilyDatabase.db";
            connect = DriverManager.getConnection(CONNECTION_URL);
            connect.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }
        return connect;
    }

    public Connection getConnection() throws DataAccessException {
         if (connect == null) {
             return openConnection();
         } else {
             return connect;
         }
    }

    public void closeConnection(boolean commit) throws DataAccessException {

         try {
             if (commit) {
                 connect.commit();
             }
             else {
                 connect.rollback();
             }

             connect.close();
             connect = null;
         }
         catch (SQLException e) {
             e.printStackTrace();
             throw new DataAccessException("Unable to close database connection");
         }
    }

}
