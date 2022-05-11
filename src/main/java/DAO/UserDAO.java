package DAO;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * This class is how User will access the Database in order to add, change, clear or load a User
 * */
public class UserDAO {

    /**
     * Connecting UserDAO to the SQL Database
     * */
    private final Connection connect;

    /**
     * Constructor for UserDAO
     *
     * @param connect This will connect the Server to the SQL Database
     * */
    public UserDAO(Connection connect) { this.connect = connect; }

    /**
     * Creates a new user with a User object
     * */
    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO User (username, password, email, firstName, lastName, gender, personID) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getGender());
            statement.setString(7, user.getPersonID());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Finds a User in the Database based on their Username
     * */
    public User fetch(String username, String password) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE username = ?;";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, username);
            rs = statement.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));

                return user;
            }
            if (!rs.next()) {
                throw new DataAccessException("Error: User not found.");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");

        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String login(String username, String password) throws DataAccessException {
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE (username, password) = (?, ?);";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            rs = statement.executeQuery();
            if (rs.next()) {
                new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));

                return UUID.randomUUID().toString();
            }
            if (!rs.next()) {
                throw new DataAccessException("Error: User not found.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");

        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Clears the User Table in the Database
     * */
    public void clearUser() throws DataAccessException {
        String sql = "DELETE FROM User;";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }

}
