package Request;

/**
 * Class is used to process the clients request to Register a new User
 * */
public class RegisterRequest {

    /** Takes the client's username and requests a new User with this username */
    private String username;
    /** Takes the client's password and requests a new User with this password */
    private String password;
    /** Takes the client's email and requests a new User with this email */
    private String email;
    /** Takes the client first name and requests a new User with this first name */
    private String firstName;
    /** Takes the client's last name and requests a new User with this last name */
    private String lastName;
    /** Takes the client gender and requests a new User with this gender */
    private String gender;

    /** RegisterRequest Constructor to create a new object when requesting a new User and returns an authToken
     *
     * @param username new User's username for their account
     * @param password new User's password for their account
     * @param email new User's email for their account
     * @param firstName new User's first name
     * @param lastName new User's last name
     * @param gender new User's gender
     *
     * */
    public RegisterRequest(String username, String password, String email, String firstName,
                           String lastName, String gender) {
        /** Sets new User's username to the database username */
        this.username = username;
        /** Sets new User's password to the database password */
        this.password = password;
        /** Sets new User's email to the database email */
        this.email = email;
        /** Sets new User's first name to the database first name */
        this.firstName = firstName;
        /** Sets new User's last name to the database last name */
        this.lastName = lastName;
        /** Sets new User's gender to the database gender */
        this.gender = gender;
    }

    public RegisterRequest() { }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
