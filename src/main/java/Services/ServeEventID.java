package Services;

import DAO.*;
import Model.Event;
import Result.EventIDResult;

import java.util.Objects;

/**
 * This Class handles the Client's request to load a specific event based on its ID and which user it is associated to
 * */
public class ServeEventID {

    /**
     * EventID Service Constructor which takes the authToken and eventID
     * to specify which Event and User the Client wants
     * */
    public ServeEventID() { }

    /**
     * This functions assesses the request to load a specific event and
     * returns that Event object and passes it to the DAO to then access the database
     *
     * @param eventID Takes in the result from the request.
     * @return Returns an Event object from the database
     * */
    public EventIDResult passToDAO(String eventID, String authtoken) throws DataAccessException {
        DatabaseConnect db = new DatabaseConnect();
        EventDAO findEvent = new EventDAO(db.getConnection());
        AuthTokenDAO getToken = new AuthTokenDAO(db.getConnection());

        try {
            Event event = findEvent.findEvent(eventID);

            if (event == null) {
                db.closeConnection(true);
                return new EventIDResult("Error: EventID does not exist.", false);
            }
            if (Objects.equals(event.getAssociatedUsername(), getToken.findToken(authtoken))) {
                db.closeConnection(true);
                return new EventIDResult(event.getAssociatedUsername(), event.getEventID(),
                        event.getPersonID(), event.getLatitude(), event.getLongitude(), event.getCountry(),
                        event.getCity(), event.getEventType(), event.getYear(), true);
            }
            throw new DataAccessException();
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new EventIDResult("Error: Invalid authToken, eventID, or requested event " +
                    "doesn't belong to this User.", false);
        }
    }

}
