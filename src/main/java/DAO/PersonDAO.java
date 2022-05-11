package DAO;

import Model.Event;
import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is how Person will access the Database in order to add, change, clear or load a Person
 * */
public class PersonDAO {

    /**
     * Connecting PersonDAO to the SQL Database
     * */
    private final Connection connect;

    /**
     * Constructor for PersonDAO
     *
     * @param connect This will connect the Server to the SQL Database
     * */
    public PersonDAO(Connection connect) { this.connect = connect; }

    /**
     * Creates an event for each person depending on how many generations there are.
     *
     * @param person adds person object to database
     *
     * @return Returns a person object
     * */
    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, gender, fatherID," +
                " motherID, spouseID) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, person.getPersonID());
            statement.setString(2, person.getAssociatedUsername());
            statement.setString(3, person.getFirstName());
            statement.setString(4, person.getLastName());
            statement.setString(5, person.getGender());
            statement.setString(6, person.getFatherID());
            statement.setString(7, person.getMotherID());
            statement.setString(8, person.getSpouseID());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public Person fetch(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE personID = ?;";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, personID);
            rs = statement.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUserName"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));

                return person;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");

        } finally {
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

    public ArrayList<Person> findByUser(String associatedUsername) throws DataAccessException {
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        Person person;
        ArrayList<Person> personList = new ArrayList<>();
        ResultSet rs;

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, associatedUsername);
            rs = statement.executeQuery();
            while (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));

                if (Objects.equals(rs.getString("associatedUsername"), person.getAssociatedUsername())) {
                    personList.add(person);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        }
        return personList;
    }

    /**
     * Clears the Person table in the database
     * */
    public void clearPerson() throws DataAccessException {
        String sql = "DELETE FROM Person;";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }

    /**
     * Clears the Person Table when called from the Fill Service specifically
     * */
    public void clearAssociatedUsername(String username) throws DataAccessException {
        // DELETE FROM Person WHERE username = ?;
        String sql = "DELETE FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing Person Table.");
        }
    }

}
