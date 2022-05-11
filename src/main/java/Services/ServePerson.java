package Services;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.PersonDAO;
import Model.Person;
import Result.PersonsResult;

import java.util.ArrayList;

/**
 * This Class will handle what the result was for loading a specific Person and their family tree
 * */
public class ServePerson {

    /**
     * Person Service Constructor (used later in the program)
     * */
    public ServePerson() { }

    /**
     * This class will process the request for a person and their family tree
     * then it will return the result and interact with the DAO package
     *
     * @return a PersonResult object
     * */
    public PersonsResult passToDAO(String authtoken) throws DataAccessException {
        DatabaseConnect db = new DatabaseConnect();
        PersonDAO persons = new PersonDAO(db.getConnection());
        AuthTokenDAO token = new AuthTokenDAO(db.getConnection());

        try {
            ArrayList<Person> personList = persons.findByUser(token.findToken(authtoken));

            if (personList == null || personList.size() == 0) {
                db.closeConnection(true);
                return new PersonsResult("Error: Invalid authToken", false);
            }
            db.closeConnection(true);
            return new PersonsResult(personList.toArray(new Person[0]), true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new PersonsResult("Error: Invalid authToken.", false);
        }
    }
}
