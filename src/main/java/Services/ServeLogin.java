package Services;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.UserDAO;
import Model.User;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;

import java.util.UUID;

/**
 * This class will process the login of a current User and return whether it was successful or not
 * */
public class ServeLogin {

    /**
     * Login Service Constructor (used later in the program)
     * */
    public ServeLogin() { }

    /**
     * Assesses the login verification and passes in the login request from the client and returns the result
     * Takes the result and connects to the database through the DAO class
     *
     * @param request Request to log in to a User from the client
     * @return Returns the result of the request
     * */
    public LoginResult passToDAO(LoginRequest request) throws DataAccessException {
        DatabaseConnect db = new DatabaseConnect();

        try {
            // This connects us to Database
            UserDAO loginUser = new UserDAO(db.getConnection());
            AuthTokenDAO token = new AuthTokenDAO(db.getConnection());
            UserDAO getUser = new UserDAO(db.getConnection());
            User user;

            // This verifies if the username and password are in the database
            String authToken = loginUser.login(request.getUsername(), request.getPassword());
            token.insertToken(authToken, request.getUsername());
            user = getUser.fetch(request.getUsername(), request.getPassword());

            db.closeConnection(true);
            return new LoginResult(authToken, request.getUsername(), user.getPersonID(), true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new LoginResult("Error: No user found.", false);
        }
    }
}
