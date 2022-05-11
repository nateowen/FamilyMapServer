package Result;

import Model.Person;

/**
 * This Class will return the Response Body of a Person Request as a JSON String
 * */
public class PersonsResult {

    /** Person Array which contains a list of Person object which all have the attributes of a Person object */
    private Person[] data;

    private String message;
    /** Boolean variable which will tell the client if the person request was successful or not */
    private boolean success;

    public PersonsResult() { }

    public PersonsResult(Person[] data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public PersonsResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public Person[] getPersons() {
        return data;
    }

    public void setPersons(Person[] persons) {
        this.data = persons;
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
