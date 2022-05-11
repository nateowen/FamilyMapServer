package DAO;

import Model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is how AuthToken will access the Database in order to add, change, clear or load an authToken
 * */
public class AuthTokenDAO {

    /**
     * Connecting AuthTokenDAO to the SQL Database
     * */
    private final Connection connect;

    /**
     * Constructor for AuthTokenDAO
     *
     * @param connect This will connect the Server to the SQL Database
     * */
    public AuthTokenDAO(Connection connect) {
        this.connect = connect;
    }

    /**
     * Creates an authorization token for a user
     *
     * @return Returns the string of the AuthToken generated
     * */
    public void insertToken(String authtoken, String username) throws DataAccessException {
        String sql = "INSERT INTO AuthorityToken (authtoken, username) VALUES (?, ?)";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, authtoken);
            statement.setString(2, username);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public String findToken(String authtoken) throws DataAccessException {
        AuthToken token;
        String sql = "SELECT * FROM AuthorityToken WHERE authtoken = ?;";
        ResultSet rs;

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, authtoken);
            rs = statement.executeQuery();
            if (rs.next()) {
                token = new AuthToken(rs.getString("authToken"), rs.getString("username"));
                return token.getUsername();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
        return null;
    }

    /**
     * Clears the AuthorityToken table in the database
     *  */
    public void clearAuthToken() throws DataAccessException {
        String sql = "DELETE FROM AuthorityToken";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing AuthToken Table.");
        }
    }
}
