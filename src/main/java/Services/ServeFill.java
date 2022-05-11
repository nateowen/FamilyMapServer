package Services;

import DAO.*;
import GenerateFamilyTree.GenerateFakeFamily;
import Model.Person;
import Model.User;
import Result.FillResult;

import java.io.*;

/**
 * This Class handles the Client's request to fill the Family Tree for a specific User
 * */
public class ServeFill extends GenerateFakeFamily {

    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Fill Service constructor which uses the User and their login info to then fill their family tree
     * */
    public ServeFill() throws FileNotFoundException, DataAccessException {
        super();
    }

    public FillResult passToDAO(String username, Integer generations) throws DataAccessException {
        DatabaseConnect db = new DatabaseConnect();
        PersonDAO person = new PersonDAO(db.getConnection());
        EventDAO events = new EventDAO(db.getConnection());
        UserDAO user = new UserDAO(db.getConnection());

        try {
            // Checks to see if the User is in the database
            String password = " ";
            User fillUser = user.fetch(username, password);

            Person newPerson = new Person(fillUser.getPersonID(), fillUser.getUsername(), fillUser.getFirstName(),
                    fillUser.getLastName(), fillUser.getGender(), null, null, null);

            // Clears the Event and Person Tables associated with the User
            person.clearAssociatedUsername(username);
            events.clearAssociatedUsername(username);

            int birthYear = 2000;
            // Helper function generates the family tree for the User and their new Person object
            recursiveFakeFamily(newPerson, username, generations, newPerson.getGender(), birthYear, db.getConnection());

            // Setting the personID's equal to each other so Client can identify User and Person object by PersonID
            newPerson.setPersonID(fillUser.getPersonID());

            // Resets the firstName and lastName of insertPerson to the User's names
            newPerson.setFirstName(fillUser.getFirstName());
            newPerson.setLastName(fillUser.getLastName());

            double numPeople = (Math.pow(2, generations + 1)) - 1;
            double numberOfEvents = (numPeople * 3) - 1;

            person.insert(newPerson);

            db.closeConnection(true);
            // Returning the Response Body
            message = "Successfully added " + (int)numPeople + " persons and "
                    + (int)numberOfEvents + " events to the database.";
            return new FillResult(message, true);

        } catch (DataAccessException | FileNotFoundException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new FillResult("Error: Invalid username or Invalid generations.", false);
        }
    }
}
