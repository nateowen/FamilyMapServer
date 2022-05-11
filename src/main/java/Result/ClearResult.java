package Result;

import DAO.AuthTokenDAO;
import DAO.EventDAO;
import DAO.PersonDAO;
import DAO.UserDAO;

/**
 * This Class will return the JSON String Result of whether the Client's request to clear all
 * database information was successful or not
 * */
public class ClearResult {
    public String message;
    /** Boolean set to true or false whether the request was successful or not */
    public boolean success;

    public ClearResult() { }

    /**
     * This function will return the JSON String Result of whether the Client's request was successful or not
     * The success is decided through the boolean variable
     *
     * @param success Boolean true or false if request is successful or not.
     * */
    public ClearResult(String message, boolean success) {
        this.message = message;
        /** Returns the Response Body to the webpage */
        this.success = success;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


}
