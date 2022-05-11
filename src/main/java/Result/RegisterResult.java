package Result;

import Request.RegisterRequest;

/**
 * This Class returns the JSON String Result of whether the user was registered or not
 * */
public class RegisterResult {

    /**
     * Client's authToken that will be returned after registering User
     */
    private String authtoken;
    /**
     * Client's username that will be returned after registering User
     */
    private String username;
    /**
     * Client's personID that will be returned after registering User
     */
    private String personID;

    private String message;
    /**
     * Boolean variable which will tell the client if the register was successful or not
     */
    private boolean success;

    public RegisterResult() { }

    /**
     * This function will be the result of the registering and whether it was successful or not.
     *
     * @param authtoken Client's authToken that will be returned in JSON String
     * @param username  Client's username that will be returned in JSON String
     * @param personID  Client's personID that will be returned in JSON String
     * @param success   Boolean variable that will tell the Client if register was successful or not.
     * @return Returns a JSON string to return to the Client
     */
    public RegisterResult(String authtoken, String username, String personID, boolean success) {

        /** Setting database authToken equal to authToken assigned */
        this.authtoken = authtoken;
        /** Setting database username equal to Client's username */
        this.username = username;
        /** Setting database personID equal to Client's personID */
        this.personID = personID;
        /** True or False string if registered or not */
        this.success = success;
    }

    public RegisterResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // TODO: Create the response body here and do getters/setters for each method

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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
