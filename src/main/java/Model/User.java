package Model;

import java.util.Objects;

/**
 * This is the class where all the User information will be stored from the Database
 * */
public class User {

    /** username variable for the User in the database */
    private String username;
    /** password variable for the User in the database */
    private String password;
    /** email variable for the User in the database */
    private String email;
    /** first name of User in the database */
    private String firstName;
    /** last name of User in the database */
    private String lastName;
    /** gender of User in the database */
    private String gender;
    /** Creates personID for the User in the database */
    private String personID;

    /** Constructor for the User, stores all the class variables
     *
     * @param username the database's username
     * @param password the database's password
     * @param email the database's email
     * @param firstName the database's first name
     * @param lastName the database's last name
     * @param gender the database's gender
     * @param personID the database's personID
     *
     * */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        /** Sets new User object username equal to the username in the database */
        this.username = username;
        /** Sets new User object password equal to the password in the database */
        this.password = password;
        /** Sets new User object email equal to the email in the database */
        this.email = email;
        /** Sets new User object first name equal to the first name in the database */
        this.firstName = firstName;
        /** Sets new User object last name equal to the last name in the database */
        this.lastName = lastName;
        /** Sets new User object gender equal to the gender in the database */
        this.gender = gender;
        /** Sets the personID from new User Object equal to personID in database */
        this.personID = personID;
    }

    public User() { }

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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User oUser = (User) obj;
        return Objects.equals(username, oUser.username) && Objects.equals(password, oUser.password) &&
                Objects.equals(email, oUser.email) && Objects.equals(firstName, oUser.firstName) &&
                Objects.equals(lastName, oUser.lastName) && Objects.equals(gender, oUser.gender) &&
                Objects.equals(personID, oUser.personID);
    }
}
