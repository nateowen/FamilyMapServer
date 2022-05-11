package Services;

import DAO.*;
import GenerateFamilyTree.GenerateFakeFamily;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;

import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * This Class will handle what the result was for Registering a new User
 * */
public class ServeRegister extends GenerateFakeFamily {

    public String authtoken;

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    /**
     * Register Service Constructor (Used later in the program)
     * */
    public ServeRegister() throws FileNotFoundException, DataAccessException {
        super();
    }

    /**
     * Used to assess whether the register was approved or not
     * and then connect to the database if register is successful
     *
     * @param request this is the request from the Request to Register
     * @return a Result object
     * */
    public RegisterResult passToDAO(RegisterRequest request) throws DataAccessException {
        DatabaseConnect db = new DatabaseConnect();

        UserDAO newUser = new UserDAO(db.getConnection());
        PersonDAO newPerson = new PersonDAO(db.getConnection());
        AuthTokenDAO token = new AuthTokenDAO(db.getConnection());

        String personID = UUID.randomUUID().toString();
        User user = new User(request.getUsername(), request.getPassword(), request.getEmail(),
                request.getFirstName(), request.getLastName(), request.getGender(), personID);

        try {
            // Person object for User that just registered. Used to call recursiveFakeFamily()
            Person newFamilyTree = new Person(user.getPersonID(), user.getUsername(), user.getFirstName(),
                    user.getLastName(), user.getGender(), null, null, null);

            int birthYear = 2000;

            // Call recursiveFakeFamily() function to generate family tree for newly registered User
            recursiveFakeFamily(newFamilyTree, newFamilyTree.getAssociatedUsername(),
                    4, user.getGender(), birthYear, db.getConnection());

            // Resets the Name's of the User's Person Object to match the User and sets the PersonID's equal to each other
            user.setPersonID(newFamilyTree.getPersonID());
            newFamilyTree.setFirstName(user.getFirstName());
            newFamilyTree.setLastName(user.getLastName());

            // Inserting the new Family Tree and User into the database
            newPerson.insert(newFamilyTree);
            newUser.insert(user);

            // Create random authToken and inserts into database
            authtoken = newUser.login(user.getUsername(), user.getPassword());
            token.insertToken(authtoken, user.getUsername());

            db.closeConnection(true);
            return new RegisterResult(authtoken, request.getUsername(), personID, true);
        }
        catch (DataAccessException | FileNotFoundException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new RegisterResult("Error: Missing value or username already taken.", false);
        }
    }

}
