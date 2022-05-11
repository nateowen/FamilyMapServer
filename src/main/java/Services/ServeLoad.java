package Services;

import DAO.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Request.RegisterRequest;
import Result.LoadResult;

/**
 * This Class will handle the Client request to Load a specific user's info, events, and family tree
 * */
public class ServeLoad {
    /**
     * Load Service Constructor
     * */
    public ServeLoad() { }

    /**
     * Assesses the request to load a specific User's family tree and returns the User object with their info
     *
     * @param request Passes in the request to load user data
     * @return Returns a User object with their info
     * */
    public LoadResult passToDAO(LoadRequest request) throws DataAccessException {
        DatabaseConnect db = new DatabaseConnect();

        try {
            UserDAO users = new UserDAO(db.getConnection());
            PersonDAO persons = new PersonDAO(db.getConnection());
            EventDAO events = new EventDAO(db.getConnection());
            AuthTokenDAO authTokens = new AuthTokenDAO(db.getConnection());

            users.clearUser();
            events.clearEvent();
            persons.clearPerson();
            authTokens.clearAuthToken();

            for (User user : request.getUsers()) {
                users.insert(user);
            }
            for (Person person : request.getPersons()) {
                persons.insert(person);
            }
            for (Event event : request.getEvents()) {
                events.addEvent(event);
            }

            db.closeConnection(true);

            String message = "Successfully added " +
                    request.getUsers().length + " users, " +
                    request.getPersons().length + " persons, and " +
                    request.getEvents().length + " events to the database.";

            return new LoadResult(message, true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new LoadResult("Error: Could not load users.", false);
        }
    }
}
