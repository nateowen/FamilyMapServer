package Result;

import Model.Event;
import Model.Person;

/**
 * This Class will return the JSON String Result whether the Client requested fill was complete or not.
 * */
public class FillResult {

    private String message;

    /** Boolean true or false whether request was successful or not */
    private boolean success;

    public FillResult() { }

    /**
     * This function returns the JSON String to the Client whether their request was successful or not
     *
     * @param success Boolean true or false whether request was successful or not
     * @return Returns the JSON String with what was added and if it was successful or not.
     * */
    public FillResult(String message, boolean success) {

        this.message = message;
        /** Boolean true or false whether request was successful or not */
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
