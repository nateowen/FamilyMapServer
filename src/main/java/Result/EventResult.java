package Result;

import Model.Event;

/**
 * This Class returns the JSON String Result of the Client requested data from a specific User
 * */
public class EventResult {

    /** Array of event objects */
    private Event[] data;

    private String message;
    /** Boolean true or false whether request is successful or not */
    private boolean success;

    public EventResult() { }

    /**
     * This function will return the string and answer to whether the Client request for data was successful or not
     *
     * @param events Array of event objects
     * @param success Boolean true or false whether request is successful or not
     * @return Returns the JSON String of the Array of Events and whether this request was successful or not.
     * */
    public EventResult(Event[] events, boolean success) {

        /** Sets the database table of Events equal to this array of data */
        this.data = events;
        /** Set to true or false if data added or not */
        this.success = success;
    }

    public EventResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
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
