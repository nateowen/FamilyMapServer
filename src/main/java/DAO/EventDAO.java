package DAO;

import Model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is how Events will access the Database in order to add, change, clear or load an event
 * */
public class EventDAO {
    /**
     * Connecting EventDAO to the SQL Database
     * */
    private final Connection connect;

    /**
     * Constructor for EventDAO
     *
     * @param connect This will connect the Server to the SQL Database
     * */
    public EventDAO(Connection connect) {
        this.connect = connect;
    }

    /**
     * Adds an event to the database
     *
     * @param event takes in the event that will be added to the database
     * */
    public void addEvent(Event event) throws DataAccessException {
        String sql = "INSERT INTO Event (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, event.getEventID());
            statement.setString(2, event.getAssociatedUsername());
            statement.setString(3, event.getPersonID());
            statement.setFloat(4, event.getLatitude());
            statement.setFloat(5, event.getLongitude());
            statement.setString(6, event.getCountry());
            statement.setString(7, event.getCity());
            statement.setString(8, event.getEventType());
            statement.setInt(9, event.getYear());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Uses the eventID to find a certain even
     *
     * @param eventID takes in the eventID that the Client is searching for.
     * @return Returns an event object when specified event is found
     * */
    public Event findEvent(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE eventID = ?;";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, eventID);
            rs = statement.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUserName"),
                        rs.getString("personID"), rs.getFloat("latitude"),
                        rs.getFloat("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));

                return event;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");

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

    /**
     * Uses the username to find an event associated to a certain user
     *
     * @param associatedUsername takes in the username associated to the event that the Client is searching for
     * @return Returns a list of type Event that returns all the events associated to specified User
     * */
    public ArrayList<Event> findByUser(String associatedUsername) throws DataAccessException {
        String sql = "SELECT * FROM Event WHERE associatedUsername = ?;";
        Event event;
        ArrayList<Event> eventList = new ArrayList<>();
        ResultSet rs;

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, associatedUsername);
            rs = statement.executeQuery();
            while (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUserName"),
                        rs.getString("personID"), rs.getFloat("latitude"),
                        rs.getFloat("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));

                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        }
        return eventList;
    }

    /**
     * Clears the Event table in the database
     * */
    public void clearEvent() throws DataAccessException {
        String sql = "DELETE FROM Event;";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    public void clearAssociatedUsername(String username) throws DataAccessException {
        // DELETE FROM Event WHERE username = ?;
        String sql = "DELETE FROM Event WHERE associatedUsername = ?;";
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
