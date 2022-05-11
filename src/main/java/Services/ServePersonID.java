package Services;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.PersonDAO;
import Model.Person;
import Request.LoadRequest;
import Result.PersonIDResult;

import java.util.Objects;

/**
 * This Class will handle what the result was for loading a specific Person's ID
 * */
public class ServePersonID {

    /**
     * PersonID Service Constructor (used later in the program)
     * */
    public ServePersonID() { }

    /**
     * This function will take the request to find a specific person based on their ID
     * then it will give the result of that request
     *
     * @return a PersonIDResult object
     * */
    public PersonIDResult passToDAO(String personID, String authtoken) throws DataAccessException {
        DatabaseConnect db = new DatabaseConnect();
        PersonDAO findPerson = new PersonDAO(db.getConnection());
        AuthTokenDAO token = new AuthTokenDAO(db.getConnection());

        try {
            Person person = findPerson.fetch(personID);

            if (person == null) {
                db.closeConnection(true);
                return new PersonIDResult("Error: Invalid personID", false);
            }
            else if (Objects.equals(person.getAssociatedUsername(), token.findToken(authtoken))) {
                db.closeConnection(true);
                return new PersonIDResult(person.getAssociatedUsername(), person.getPersonID(),
                        person.getFirstName(), person.getLastName(), person.getGender(), person.getFatherID(),
                        person.getMotherID(), person.getSpouseID(), true);
            }
            throw new DataAccessException();
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new PersonIDResult("Error: Invalid authToken, personID, or requested person " +
                    "does not belong to this User.", false);
        }
    }
}
