package Services;

import DAO.*;
import Result.ClearResult;

/**
 * This class handles the Client's request to delete all of teh database information
 * */
public class ServeClear {

    /** User object used to clear all the user data */
    UserDAO users;
    /** Event object used to clear all the event data */
    EventDAO events;
    /** AuthToken object used to clear all the authorization token data */
    AuthTokenDAO authTokens;
    /** Person object used to clear all the people data */
    PersonDAO persons;

    /**
     * Clear Service Constructor
     * */
    public ServeClear() { }

    /**
     * Returns a ClearResult object whether the database was cleared or not
     *
     * @return Returns ClearResult object of whether the database was cleared or not
     * */
    public ClearResult clearDatabase() throws DataAccessException {
        DatabaseConnect db = new DatabaseConnect();

        try {
            users = new UserDAO(db.getConnection());
            events = new EventDAO(db.getConnection());
            authTokens = new AuthTokenDAO(db.getConnection());
            persons = new PersonDAO(db.getConnection());

            users.clearUser();
            events.clearEvent();
            authTokens.clearAuthToken();
            persons.clearPerson();

            db.closeConnection(true);
            return new ClearResult("Clear succeeded.", true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new ClearResult("Error: Clear could not be completed.", false);
        }
    }

}
