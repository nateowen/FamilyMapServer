package Services;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.EventDAO;
import Model.Event;
import Result.EventResult;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This Class handles the Client's request to get all the event's and family tree of a specific User
 * */
public class ServeEvent {
    /**
     * Event Service Constructor (Used later in the program)
     * */
    public ServeEvent() { }

    /**
     * Takes in the result for the Load request of all the events and family tree of a specific and
     * returns that User's events and family tree excluding themselves.
     *
     * @return Returns a User object excluding the User themselves
     * */
    public EventResult passToDAO(String authtoken) throws DataAccessException {
        DatabaseConnect db = new DatabaseConnect();
        EventDAO events = new EventDAO(db.getConnection());
        AuthTokenDAO token = new AuthTokenDAO(db.getConnection());

        try {
            ArrayList<Event> eventList = events.findByUser(token.findToken(authtoken));

            if (eventList == null || eventList.size() == 0) {
                db.closeConnection(true);
                return new EventResult("Error: Invalid authToken", false);
            }
            db.closeConnection(true);
            return new EventResult(eventList.toArray(new Event[0]), true);

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new EventResult("Error: Invalid authToken", false);
        }
    }
}
