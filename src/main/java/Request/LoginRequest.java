package Request;

import Model.User;

/**
 * This class will handle the Login requests for already registered Users
 * */
public class LoginRequest {

    /** Current User's username */
    private String username;

    /** Current User's password */
    private String password;

    /** User object that will be used to authenticate the login */
    private User user;

    /** LoginRequest Constructor used to create new LoginRequest objects when a user is logging into their account.
     *
     * @param username User's username that will be verified with a username in the database
     * @param password User's password that will be verified with a password in the database
     * */
    public LoginRequest(String username, String password) {
        /** Sets the username in the database equal to this username */
        this.username = username;
        /** Sets the password in the database equal to this password */
        this.password = password;
    }


    /**
     * GETTERS AND SETTERS
     * */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
