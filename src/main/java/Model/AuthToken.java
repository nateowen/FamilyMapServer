package Model;

/**
 * This class will create and associate an authorization token to a new User based on their username.
 * Use the UUID library from Java
 * */
public class AuthToken {

    /** Creates a new authorization token for the current User */
    private String authtoken;
    /** Associates the authToken to this User's username */
    private String username;

    /** AuthToken constructor used to create new AuthToken objects and assign them to new Users.
     *
     * @param authorizationToken the unique token used to authorize who the user is
     * @param userName username associated to the User that is receiving the authToken
     *
     * */
    public AuthToken(String authorizationToken, String userName) {
        /** Sets authToken in database equal to this authToken */
        this.authtoken = authorizationToken;
        /** Sets and associates the current User's username to this authToken */
        this.username = userName;
    }

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
}
