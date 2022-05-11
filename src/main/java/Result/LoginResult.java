package Result;

/**
 * This Class returns the JSON String Result of whether the user was logged in successfully or not
 * */
public class LoginResult {

    /** Client's authToken that will be returned after logging in User */
    private String authtoken;
    /** Client's username that will be returned after logging in User */
    private String username;
    /** Client's personID that will be returned after logging in User */
    private String personID;
    /** Boolean variable which will tell the client if the login was successful or not */
    private boolean success;
    /** Used to print Error Message if user isn't registered. */
    private String message;

    /**
     * This function will be the result of the logging in and whether it was successful or not.
     *
     * @param authtoken Client's authToken that will be returned in JSON String
     * @param username Client's username that will be returned in JSON String
     * @param personID Client's personID that will be returned in JSON String
     * @param success Boolean variable that will tell the Client if login was successful or not.
     * @return Returns a JSON string to return to the Client
     * */
    public LoginResult(String authtoken, String username, String personID, boolean success) {

        /** Setting database authToken equal to authToken assigned */
        this.authtoken = authtoken;
        /** Setting database username equal to Client's username */
        this.username = username;
        /** Setting database personID equal to Client's personID */
        this.personID = personID;
        /** True or False string if logged in or not */
        this.success = success;
    }

    public LoginResult(String message, boolean success) {

        this.message = message;

        /** True or False string if logged in or not */
        this.success = success;
    }

    /**
     * GETTERS AND SETTERS
     * */
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
