package Result;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * This Class will return the JSON String Result of the requested body and clears all the database prior to doing so
 * */
public class LoadResult {

    /** Boolean true or false whether request was successful or not */
    private boolean success;

    private String message;

    public LoadResult() { }

    /**
     * This function returns the JSON String to the Client whether their request was successful or not
     *
     * @param message Message displayed after loading info
     * @param success Boolean true or false whether request was successful or not
     * */
    public LoadResult(String message, boolean success) {

        this.message = message;
        /** Boolean true or false whether request was successful or not */
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
